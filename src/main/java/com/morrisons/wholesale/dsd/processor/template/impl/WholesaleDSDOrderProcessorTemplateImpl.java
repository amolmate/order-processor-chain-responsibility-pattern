package com.morrisons.wholesale.dsd.processor.template.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morrisons.wholesale.dsd.aggregationservice.AggregationService;
import com.morrisons.wholesale.dsd.configservice.ConfigService;
import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.endpoint.generator.GetOrdersEndpointGenerator;
import com.morrisons.wholesale.dsd.processor.template.WholesaleDSDOrderProcessorTemplate;
import com.morrisons.wholesale.dsd.validation.client.ValidationAndEnrichmentClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("wholesaleDSDOrderProcessor")
public class WholesaleDSDOrderProcessorTemplateImpl
		extends WholesaleDSDOrderProcessorTemplate<Customers, List<Orders>> {

	private GetOrdersEndpointGenerator getOrdersEndpointGenerator;

	private ConfigService configService;

	private ValidationAndEnrichmentClient validationClient;

	private AggregationService aggregationService;

	@Autowired
	public WholesaleDSDOrderProcessorTemplateImpl(GetOrdersEndpointGenerator generator, ConfigService configService,
			ValidationAndEnrichmentClient client, AggregationService aggregationService) {

		this.getOrdersEndpointGenerator = generator;
		this.configService = configService;
		this.validationClient = client;
		this.aggregationService = aggregationService;
	}

	@Override
	protected Customers getDataFromConifgService() {

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
	protected void validateDSDOrders(List<Orders> ordersList, Customers customers) {

		validationClient.initialize(ordersList, customers);
	}

	@Override
	protected void aggregateOrders(Customers customers) {

		customers.getCustomers().forEach(c -> aggregationService.aggregate(c.getName()));
	}
}