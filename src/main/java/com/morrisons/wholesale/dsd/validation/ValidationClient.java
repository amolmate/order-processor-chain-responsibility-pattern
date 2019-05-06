package com.morrisons.wholesale.dsd.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morrisons.wholesale.dsd.dto.Audit;
import com.morrisons.wholesale.dsd.dto.Customer;
import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.dto.ItemAlternateId;
import com.morrisons.wholesale.dsd.dto.ItemStatus;
import com.morrisons.wholesale.dsd.dto.Order;
import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.dto.RedisCatlogueItem;
import com.morrisons.wholesale.dsd.dto.SupportedSupplier;
import com.morrisons.wholesale.dsd.dto.UpdateItemPayload;
import com.morrisons.wholesale.dsd.endpoint.IBasePutEndPoint;
import com.morrisons.wholesale.dsd.endpoint.IRedisCacheEndPoint;
import com.morrisons.wholesale.dsd.endpoint.impl.WholesaleStoreServiceCaller;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMapping;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

@Component
public class ValidationClient {

	private INode<Item> node;

	private IBasePutEndPoint<UpdateItemPayload, String> updateChangedItemsEndPoint;

	private IRedisCacheEndPoint<String, RedisCatlogueItem> redisCacheEndPoint;

	private Map<String, Map<String, SupportedSupplier>> customerMap;

	private WholesaleStoreServiceCaller wholesaleStoreServiceCaller;

	@Autowired
	public ValidationClient(IBasePutEndPoint<UpdateItemPayload, String> updateChangedItemsEndPoint,
			IRedisCacheEndPoint<String, RedisCatlogueItem> redisCacheEndPoint,
			WholesaleStoreServiceCaller wholesaleStoreServiceCaller) {

		this.updateChangedItemsEndPoint = updateChangedItemsEndPoint;
		this.redisCacheEndPoint = redisCacheEndPoint;
		this.wholesaleStoreServiceCaller = wholesaleStoreServiceCaller;
	}

	public void initialize(List<Orders> orders, Customers customers) {

		createCustomerMap(customers);

		node = ValidationNodeBuilder.build(customerMap, redisCacheEndPoint, wholesaleStoreServiceCaller);

		orders.forEach(s -> s.getOrders().forEach(this::processOrder));
	}

	private void createCustomerMap(Customers customers) {

		List<Customer> customerList = customers.getCustomers();

		customerMap = new HashMap<>();

		for (Customer customer : customerList) {

			Map<String, SupportedSupplier> supplierMap = new HashMap<>();

			List<SupportedSupplier> supplierList = customer.getSupportedSuppliers();

			for (SupportedSupplier supplier : supplierList) {

				supplierMap.put(supplier.getName(), supplier);
			}

			customerMap.put(customer.getName(), supplierMap);
		}
	}

	private void processItem(Item item) {

		node.processNode(item);

		if (item.isItemValidated()) {

			enrichItemWithIdentifier(item);
		}
	}

	private void enrichItemWithIdentifier(Item item) {

		Map<String, SupportedSupplier> supplierMap = customerMap.get(item.getCustomerName());
		SupportedSupplier supplier = supplierMap.get(item.getSupplierName());
		getValueFromRedisAndEnrich(item, supplier.getIdentifier());
	}

	private void getValueFromRedisAndEnrich(Item item, String identifier) {

		RedisCatlogueItem redisItem = getItemFromRedisCacheCatlogue(identifier);
		item.setSkuMin(redisItem.getMin());
	}

	private void processOrder(Order order) {

		order.getItems().stream().forEach(this::processItem);

		updateItems(order);
	}

	private void updateItems(Order order) {

		updateChangedItemsEndPoint.put(getParameterMappings(order), getPayload(order));
	}

	private ParameterMappings getParameterMappings(Order order) {

		ParameterMappings parameterMappings = new ParameterMappings();

		List<ParameterMapping> pathParameters = new ArrayList<>();

		pathParameters.add(new ParameterMapping("customerId", order.getCustomerId()));
		pathParameters.add(new ParameterMapping("supplierName", order.getSupplierId()));
		pathParameters.add(new ParameterMapping("orderId", order.getOrderId()));

		parameterMappings.setPathParameters(pathParameters);

		return parameterMappings;
	}

	private UpdateItemPayload getPayload(Order order) {

		UpdateItemPayload payload = new UpdateItemPayload();
		Audit audit = new Audit();
		audit.setCorrelationId(verfiyAndGenerateCorrelationId());
		audit.setWho("");
		audit.setWhen("");
		payload.setAudit(audit);
		payload.setStatus(order.getStatus());
		payload.setItems(getItemsStatus(order.getItems()));
		return payload;
	}

	private List<ItemStatus> getItemsStatus(List<Item> items) {

		List<ItemStatus> itemStatusList = new ArrayList<>();

		for (Item item : items) {

			ItemStatus itemStatus = new ItemStatus();

			itemStatus.setItemId(item.getItemId());
			itemStatus.setStatus(item.getStatus());
			ItemAlternateId alternateId = new ItemAlternateId();
			alternateId.setSkuMin(item.getItemId());
			itemStatus.setItemAlternateId(alternateId);
			itemStatusList.add(itemStatus);
		}
		return itemStatusList;
	}

	private String verfiyAndGenerateCorrelationId() {

		return StringUtils.join(UUID.randomUUID().toString(), System.currentTimeMillis());
	}

	private RedisCatlogueItem getItemFromRedisCacheCatlogue(String key) {

		return redisCacheEndPoint.get(key);
	}
}