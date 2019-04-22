package com.morrisons.wholesale.dsd.endpoint.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morrisons.wholesale.dsd.dto.Customer;
import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.dto.SupportedSupplier;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.IEndPointGenerator;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMapping;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;
import com.morrisons.wholesale.dsd.thread.FetchDSDOrdersThread;

@Component
public class GetOrdersEndpointGenerator implements IEndPointGenerator {

	@Autowired
	private IBaseGetEndPoint<Orders> getDSDOrdersEndPoint;

	@Override
	public List<Callable<Orders>> generateEndPointFromCustomerList(Customers customers) {

		List<Callable<Orders>> callableList = new ArrayList<>();

		List<Customer> customerList = customers.getCustomers();

		customerList.forEach(s -> getCallableForCustomer(s, callableList));
		return callableList;
	}

	private void getCallableForCustomer(Customer customer, List<Callable<Orders>> callableList) {

		List<SupportedSupplier> supplierList = customer.getSupportedSuppliers();

		supplierList.forEach(s -> callableList
				.add(new FetchDSDOrdersThread(getDSDOrdersEndPoint, getParameterMappings(s, customer.getName()))));
	}

	private ParameterMappings getParameterMappings(SupportedSupplier supplier, String customerId) {

		ParameterMappings parameterMappings = new ParameterMappings();

		List<ParameterMapping> pathParameters = new ArrayList<>();
		List<ParameterMapping> queryParameters = new ArrayList<>();
		List<ParameterMapping> headerParameters = new ArrayList<>();

		pathParameters.add(new ParameterMapping("customerId", customerId));
		pathParameters.add(new ParameterMapping("supplierName", supplier.getName()));

		queryParameters.add(new ParameterMapping("status", "raised"));
		headerParameters.add(new ParameterMapping("correlationId", UUID.randomUUID().toString()));

		parameterMappings.setPathParameters(pathParameters);
		parameterMappings.setQueryParameters(queryParameters);
		parameterMappings.setHeaderParameters(headerParameters);
		return parameterMappings;
	}
}