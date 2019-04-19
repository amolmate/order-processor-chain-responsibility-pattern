package com.morrisons.wholesale.dsd.endpoint.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morrisons.wholesale.dsd.configservice.OrderServiceConfigExtractor;
import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.IEndPointGenerator;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMapping;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;
import com.morrisons.wholesale.dsd.thread.FetchDSDOrdersThread;

@Component
public class GetOrdersEndpointGenerator implements IEndPointGenerator {

	@Autowired
	private IBaseGetEndPoint<Orders> getDSDOrdersEndPoint;

	@SuppressWarnings("unchecked")
	@Override
	public List<Callable<Orders>> generateEndPointFromCustomerList(
			OrderServiceConfigDescriptor orderServiceConfigDescriptor) {

		List<Callable<Orders>> callableList = new ArrayList<>();

		List<Map<String, Object>> customerList = (List<Map<String, Object>>) OrderServiceConfigExtractor
				.getSettingOrDefault("customers", orderServiceConfigDescriptor, List.class, new ArrayList<>());

		customerList.forEach(s -> getCallableForCustomer(s, callableList));
		return callableList;
	}

	@SuppressWarnings("unchecked")
	private void getCallableForCustomer(Map<String, Object> customer, List<Callable<Orders>> callableList) {

		List<Map<String, Object>> supplierList = (List<Map<String, Object>>) customer.get("supportedSuppliers");

		supplierList.forEach(s -> callableList.add(new FetchDSDOrdersThread(getDSDOrdersEndPoint,
				getParameterMappings(s, (String) customer.get("name")))));
	}

	private ParameterMappings getParameterMappings(Map<String, Object> supplier, String customerId) {

		ParameterMappings parameterMappings = new ParameterMappings();

		List<ParameterMapping> pathParameters = new ArrayList<>();
		List<ParameterMapping> queryParameters = new ArrayList<>();
		List<ParameterMapping> headerParameters = new ArrayList<>();

		pathParameters.add(new ParameterMapping("customerId", customerId));
		pathParameters.add(new ParameterMapping("supplierName", supplier.get("name")));

		queryParameters.add(new ParameterMapping("status", "raised"));
		headerParameters.add(new ParameterMapping("correlationId", UUID.randomUUID().toString()));
		
		parameterMappings.setPathParameters(pathParameters);
		parameterMappings.setQueryParameters(queryParameters);
		parameterMappings.setHeaderParameters(headerParameters);
		return parameterMappings;
	}
}