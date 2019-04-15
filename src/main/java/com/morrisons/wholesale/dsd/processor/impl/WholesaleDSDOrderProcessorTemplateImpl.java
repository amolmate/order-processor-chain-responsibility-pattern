package com.morrisons.wholesale.dsd.processor.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morrisons.wholesale.dsd.configservice.ConfigService;
import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.endpoint.impl.GetOrdersEndpointGenerator;
import com.morrisons.wholesale.dsd.processor.WholesaleDSDOrderProcessorTemplate;
import com.morrisons.wholesale.dsd.threadexecutor.IThreadExecutor;
import com.morrisons.wholesale.dsd.validation.NodeResult;

@Service("wholesaleDSDOrderProcessor")
public class WholesaleDSDOrderProcessorTemplateImpl
		extends WholesaleDSDOrderProcessorTemplate<Customers, List<Orders>, NodeResult> {

	private GetOrdersEndpointGenerator getOrdersEndpointGenerator;

	private IThreadExecutor<Orders> threadExecutor;

	private ConfigService configService;

	@Autowired
	public WholesaleDSDOrderProcessorTemplateImpl(GetOrdersEndpointGenerator generator,
			IThreadExecutor<Orders> threadExecutor, ConfigService configService) {

		this.getOrdersEndpointGenerator = generator;
		this.threadExecutor = threadExecutor;
		this.configService = configService;
	}

	@Override
	protected Customers getDataFromConifgService() {

		return configService.getCustomersFromConfigService("customerId", "messageType");
	}

	@Override
	protected List<Orders> getDSDOrdersWithStatusRaised(Customers customers) {

		return threadExecutor.execute(getOrdersEndpointGenerator.generateEndPointFromCustomerList(customers));
	}

	@Override
	protected NodeResult validateDSDOrders() {
		return null;
	}
}