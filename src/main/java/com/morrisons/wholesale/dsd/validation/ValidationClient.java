package com.morrisons.wholesale.dsd.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.dto.Order;
import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.endpoint.IBasePutEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMapping;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

@Component
public class ValidationClient {

	private INode<Item, NodeResult> node;
	
	@Autowired
	private IBasePutEndPoint<Order, Response> updateChangedItemsEndPoint;

	public void initialize(List<Orders> orders) {

		node = ValidationNodeBuilder.build();
		
		orders.forEach(this::processOrders);
	}

	private void processOrders(Orders orders) {

		orders.getOrders().forEach(this::validateOrder);
	}

	private void validateOrder(Order order) {

		List<NodeResult> resultList = order.getItems().stream().map(s -> node.processNode(s)).filter(Objects::nonNull).collect(Collectors.toList());
		processResultList(resultList);
		updateItems(order);
	}

	private void updateItems(Order order) {
		
		updateChangedItemsEndPoint.put(getParameterMappings(order), getPayload());
	}

	private ParameterMappings getParameterMappings(Order order) {
		
		ParameterMappings parameterMappings = new ParameterMappings();

		List<ParameterMapping> pathParameters = new ArrayList<>();

		pathParameters.add(new ParameterMapping("customerId", order.getCustomerId()));
		pathParameters.add(new ParameterMapping("supplierName", order.getSupplierId()));
		pathParameters.add(new ParameterMapping("orderId",  order.getOrderId()));


		parameterMappings.setPathParameters(pathParameters);

		return parameterMappings;
	}

	private void processResultList(List<NodeResult> resultList) {
		
		
	}
	
	private Order getPayload() {
		
		return null;
	}
}