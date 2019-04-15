package com.morrisons.wholesale.dsd.endpoint.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import com.morrisons.wholesale.dsd.dto.Customer;
import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.endpoint.IEndPointGenerator;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMapping;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;
import com.morrisons.wholesale.dsd.thread.FetchDSDOrdersThread;

public class GetOrdersEndpointGenerator implements IEndPointGenerator {

	public GetOrdersEndpointGenerator() {

	}

	@Override
	public List<Callable<Orders>> generateEndPointFromCustomerList(Customers customers) {

		List<Customer> customerList = customers.getCustomers();

		return customerList.stream().map(this::getCallableForCustomer).collect(Collectors.toList());
	}

	private Callable<Orders> getCallableForCustomer(Customer customer) {
		
		
		return new FetchDSDOrdersThread(getParameterMappings(customer));
	}

	private ParameterMappings getParameterMappings(Customer customer) {
		
		ParameterMappings parameterMappings = new ParameterMappings();
		List<ParameterMapping> pathParameters = new ArrayList<>();
		List<ParameterMapping> queryParameters = new ArrayList<>();
		List<ParameterMapping> headerParameters = new ArrayList<>();
		parameterMappings.setHeaderParameters(headerParameters);
		parameterMappings.setPathParameters(pathParameters);
		parameterMappings.setQueryParameters(queryParameters);
		
		return parameterMappings;
	}
}