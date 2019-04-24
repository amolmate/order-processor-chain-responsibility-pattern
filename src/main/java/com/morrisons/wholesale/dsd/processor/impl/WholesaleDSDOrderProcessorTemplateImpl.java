package com.morrisons.wholesale.dsd.processor.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morrisons.wholesale.dsd.aggregationservice.AggregationService;
import com.morrisons.wholesale.dsd.configservice.ConfigService;
import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.endpoint.impl.GetOrdersEndpointGenerator;
import com.morrisons.wholesale.dsd.endpoint.impl.OrderServiceConfigDescriptor;
import com.morrisons.wholesale.dsd.processor.WholesaleDSDOrderProcessorTemplate;
import com.morrisons.wholesale.dsd.threadexecutor.IThreadExecutor;
import com.morrisons.wholesale.dsd.validation.NodeResult;
import com.morrisons.wholesale.dsd.validation.ValidationClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("wholesaleDSDOrderProcessor")
public class WholesaleDSDOrderProcessorTemplateImpl
		extends WholesaleDSDOrderProcessorTemplate<Customers, List<Orders>, NodeResult> {

	private GetOrdersEndpointGenerator getOrdersEndpointGenerator;

	private IThreadExecutor<Orders> threadExecutor;

	private ConfigService configService;

	private ValidationClient validationClient;

	private AggregationService aggregationService;

	@Autowired
	public WholesaleDSDOrderProcessorTemplateImpl(GetOrdersEndpointGenerator generator,
			IThreadExecutor<Orders> threadExecutor, ConfigService configService, ValidationClient client,
			AggregationService aggregationService) {

		this.getOrdersEndpointGenerator = generator;
		this.threadExecutor = threadExecutor;
		this.configService = configService;
		this.validationClient = client;
		this.aggregationService = aggregationService;
	}

	@Override
	protected Customers getDataFromConifgService() {

		return new ObjectMapper().convertValue(configService.getCustomersFromConfigService(),
				new TypeReference<OrderServiceConfigDescriptor>() {
				});
	}

	@Override
	protected List<Orders> getDSDOrdersWithStatusRaised(Customers customers) {

		// check if orderServiceConfigDescriptor is null
		if (customers == null) {

			log.error("Could not read data from config service");
			return Collections.emptyList();
		}
		return threadExecutor.execute(getOrdersEndpointGenerator.generateEndPointFromCustomerList(customers));
	}

	@Override
	protected NodeResult validateDSDOrders(List<Orders> ordersList, Customers customers) {

		validationClient.initialize(ordersList, customers);
		return null;
	}

	@Override
	protected void aggregateOrders() {

		aggregationService.aggregate();
	}
}