package com.morrisons.wholesale.dsd.endpoint.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morrisons.wholesale.dsd.config.ApplicationConfig;
import com.morrisons.wholesale.dsd.dto.Customer;
import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.dto.SupportedSupplier;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.IEndPointGenerator;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMapping;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;
import com.morrisons.wholesale.dsd.thread.DSDOrdersEndPointCaller;

@Component
public class GetOrdersEndpointGenerator implements IEndPointGenerator {

	@Autowired
	private IBaseGetEndPoint<Orders> getDSDOrdersEndPoint;

	@Autowired
	private ApplicationConfig applicationConfig;

	@Override
	public List<Orders> generateEndPointFromCustomerList(Customers customers) {

		List<Orders> orderList = new ArrayList<>();

		List<Customer> customerList = customers.getCustomers();

		customerList.forEach(s -> getCallableForCustomer(s, orderList));
		return orderList;
	}

	private void getCallableForCustomer(Customer customer, List<Orders> orderList) {

		List<SupportedSupplier> supplierList = customer.getSupportedSuppliers();

		supplierList.forEach(s -> orderList.add(
				new DSDOrdersEndPointCaller(getDSDOrdersEndPoint, getParameterMappings(s, customer.getName())).call()));
	}

	private ParameterMappings getParameterMappings(SupportedSupplier supplier, String customerId) {

		ParameterMappings parameterMappings = new ParameterMappings();

		List<ParameterMapping> pathParameters = new ArrayList<>();
		List<ParameterMapping> queryParameters = new ArrayList<>();
		List<ParameterMapping> headerParameters = new ArrayList<>();

		pathParameters.add(new ParameterMapping("customerId", customerId));
		pathParameters.add(new ParameterMapping("supplierName", supplier.getName()));

		queryParameters.add(new ParameterMapping("status", "raised"));
		queryParameters.add(new ParameterMapping("apikey", applicationConfig.getDSDOrdersConfig().getApiKey()));
		headerParameters.add(new ParameterMapping("correlationId", UUID.randomUUID().toString()));
		headerParameters
				.add(new ParameterMapping("Authorization", applicationConfig.getDSDOrdersConfig().getAuthorization()));

		parameterMappings.setPathParameters(pathParameters);
		parameterMappings.setQueryParameters(queryParameters);
		parameterMappings.setHeaderParameters(headerParameters);
		return parameterMappings;
	}
}