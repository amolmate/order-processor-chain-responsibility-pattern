package com.morrisons.wholesale.dsd.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.mockito.internal.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morrisons.wholesale.dsd.dto.Audit;
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
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMapping;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

@Component
public class ValidationClient {

	private INode<Item> node;

	private IBasePutEndPoint<UpdateItemPayload, Response> updateChangedItemsEndPoint;

	private IRedisCacheEndPoint<String, RedisCatlogueItem> redisCacheEndPoint;

	private Customers customers;

	@Autowired
	public ValidationClient(IBasePutEndPoint<UpdateItemPayload, Response> updateChangedItemsEndPoint,
			IRedisCacheEndPoint<String, RedisCatlogueItem> redisCacheEndPoint) {

		this.updateChangedItemsEndPoint = updateChangedItemsEndPoint;
		this.redisCacheEndPoint = redisCacheEndPoint;
	}

	public void initialize(List<Orders> orders, Customers customers) {

		this.customers = customers;

		node = ValidationNodeBuilder.build(customers, redisCacheEndPoint);

		orders.forEach(s -> s.getOrders().forEach(this::processOrder));
	}

	private void processItem(Item item) {

		node.processNode(item);

		if (item.isItemValidated()) {

			enrichItemWithIdentifier(item);
			enrichFieldsForItem(item);
		}
	}

	private void enrichItemWithIdentifier(Item item) {

		customers.getCustomers().forEach(c -> c.getSupportedSuppliers().forEach(s -> getIdentifier(item, s)));
	}

	private void getIdentifier(Item item, SupportedSupplier supplier) {

		String supplierName = supplier.getName();

		if (StringUtils.isNotBlank(supplierName)) {

			if (supplierName.equals(item.getSupplierName())) {

				getValueFromRedisAndEnrich(item, supplier.getIdentifier());
			}
		}
	}

	private void getValueFromRedisAndEnrich(Item item, String identifier) {

		RedisCatlogueItem redisItem = getItemFromRedisCacheCatlogue(identifier);
		// likewise enrich all items
		item.setSkuMin(redisItem.getMin());
	}

	private void enrichFieldsForItem(Item item) {

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

		return StringUtil.join(UUID.randomUUID().toString(), System.currentTimeMillis());
	}

	private RedisCatlogueItem getItemFromRedisCacheCatlogue(String key) {

		return redisCacheEndPoint.get(key);
	}
}