package com.morrisons.wholesale.dsd.processor.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morrisons.wholesale.dsd.aggregationservice.AggregationService;
import com.morrisons.wholesale.dsd.configservice.ConfigService;
import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.endpoint.impl.GetOrdersEndpointGenerator;
import com.morrisons.wholesale.dsd.processor.WholesaleDSDOrderProcessorTemplate;
import com.morrisons.wholesale.dsd.util.Util;
import com.morrisons.wholesale.dsd.validation.NodeResult;
import com.morrisons.wholesale.dsd.validation.ValidationClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("wholesaleDSDOrderProcessor")
public class WholesaleDSDOrderProcessorTemplateImpl
		extends WholesaleDSDOrderProcessorTemplate<Customers, List<Orders>, NodeResult> {

	private GetOrdersEndpointGenerator getOrdersEndpointGenerator;

	private ConfigService configService;

	private ValidationClient validationClient;

	private AggregationService aggregationService;

	@Autowired
	public WholesaleDSDOrderProcessorTemplateImpl(GetOrdersEndpointGenerator generator, ConfigService configService,
			ValidationClient client, AggregationService aggregationService) {

		this.getOrdersEndpointGenerator = generator;
		this.configService = configService;
		this.validationClient = client;
		this.aggregationService = aggregationService;
	}

	@Override
	protected Customers getDataFromConifgService() {

		//return Util.convertMapToDTO(configService.getCustomersConfigFromDynamo());
		return configService.getCustomersConfigFromService();
	}

	@Override
	protected List<Orders> getDSDOrdersWithStatusRaised(Customers customers) {

		// check if orderServiceConfigDescriptor is null
		if (customers == null) {

			log.error("Could not read data from config service");
			return Collections.emptyList();
		}

		return getOrdersEndpointGenerator.generateEndPointFromCustomerList(customers);
	}

	@Override
	protected NodeResult validateDSDOrders(List<Orders> ordersList, Customers customers) {

		validationClient.initialize(ordersList, customers);
		return null;
	}

	@Override
	protected void aggregateOrders(Customers customers) {

		customers.getCustomers().forEach(c -> aggregationService.aggregate(c.getName()));
	}
}