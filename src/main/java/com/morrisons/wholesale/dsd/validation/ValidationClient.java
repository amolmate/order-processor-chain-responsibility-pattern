package com.morrisons.wholesale.dsd.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.ws.rs.core.Response;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.morrisons.wholesale.dsd.util.RedisUtil;
import com.morrisons.wholesale.dsd.constant.Constants;
import com.morrisons.wholesale.dsd.dto.Audit;
import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.dto.ItemAlternateId;
import com.morrisons.wholesale.dsd.dto.ItemStatus;
import com.morrisons.wholesale.dsd.dto.Order;
import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.dto.UpdateItemPayload;
import com.morrisons.wholesale.dsd.endpoint.IBasePutEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMapping;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

@Component
public class ValidationClient {

	private INode<Item> node;
	
	private IBasePutEndPoint<UpdateItemPayload, Response> updateChangedItemsEndPoint;
	
	private RedissonClient redissonClient;
	
	@Value("${redis.evn}")
	private String env;

	@Autowired
	public ValidationClient(IBasePutEndPoint<UpdateItemPayload, Response> updateChangedItemsEndPoint, RedissonClient redissonClient) {

		this.updateChangedItemsEndPoint = updateChangedItemsEndPoint;
		this.redissonClient = redissonClient;
	}

	public void initialize(List<Orders> orders, Customers customers) {

		node = ValidationNodeBuilder.build(customers, getRedisCatalogueItems(customers));

		orders.forEach(s -> s.getOrders().forEach(this::processOrder));
	}

	private void processItem(Item item) {
		
		node.processNode(item);
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
		audit.setCorrelationId(
				verfiyAndGenerateCorrelationId(Optional.ofNullable(order.getAudit().get(0).getCorrelationId())));
		audit.setWho(order.getAudit().get(0).getWho());
		audit.setWhen(order.getAudit().get(0).getWhen());
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

	public String verfiyAndGenerateCorrelationId(Optional<String> correlationId) {

		return correlationId.isPresent() ? correlationId.get() : UUID.randomUUID().toString();
	}
	
	private Map<String, Map<String, String>> getRedisCatalogueItems(Customers customers) {

		return RedisUtil.getCatalogueItemMap(redissonClient,
				com.morrisons.wholesale.dsd.util.ServiceUtil.getKeyForCatalogueItem(env,
						"catalogue redis cache key"));
	}
}