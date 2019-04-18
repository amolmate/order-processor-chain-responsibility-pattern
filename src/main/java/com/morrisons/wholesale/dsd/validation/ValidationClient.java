package com.morrisons.wholesale.dsd.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morrisons.wholesale.dsd.dto.Audit;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.dto.ItemStatus;
import com.morrisons.wholesale.dsd.dto.Order;
import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.dto.UpdateItemPayload;
import com.morrisons.wholesale.dsd.endpoint.IBasePutEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMapping;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

@Component
public class ValidationClient {

	private INode<Item, NodeResult> node;

	@Autowired
	private IBasePutEndPoint<UpdateItemPayload, Response> updateChangedItemsEndPoint;

	public void initialize(List<Orders> orders) {

		node = ValidationNodeBuilder.build();

		orders.forEach(this::processOrders);
	}

	private void processOrders(Orders orders) {

		orders.getOrders().forEach(this::validateOrder);
	}

	private void validateOrder(Order order) {

		List<NodeResult> resultList = order.getItems().stream().map(s -> node.processNode(s)).filter(Objects::nonNull)
				.collect(Collectors.toList());
		processResultList(resultList);
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

	private void processResultList(List<NodeResult> resultList) {

	}

	private UpdateItemPayload getPayload(Order order) {

		UpdateItemPayload payload = new UpdateItemPayload();
		Audit audit = new Audit();
		audit.setCorrelationId("");// not sure from where to get its value
		audit.setWho(order.getAudit().get(0).getWho());
		audit.setWhen(order.getAudit().get(0).getWhen());
		payload.setAudit(audit);
		payload.setStatus(order.getStatus());
		payload.setItems(getItems(order.getItems()));
		return payload;
	}

	private List<ItemStatus> getItems(List<Item> items) {

		List<ItemStatus> itemStatusList = new ArrayList<>();

		for (Item item : items) {

			ItemStatus itemStatus = new ItemStatus();

			itemStatus.setItemId(item.getItemId());
			itemStatus.setStatus(item.getStatus());
			itemStatusList.add(itemStatus);
		}
		return itemStatusList;
	}

	public void verfiyAndGenerateCorrelationId(Optional<String> correlationId) {
		String uuid = correlationId.isPresent() ? correlationId.get() : UUID.randomUUID().toString();
		//uuidThreadLocal.set(uuid);
	}
}