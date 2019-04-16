package com.morrisons.wholesale.dsd.processor.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.morrisons.wholesale.dsd.configservice.ConfigService;
import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.endpoint.impl.GetOrdersEndpointGenerator;
import com.morrisons.wholesale.dsd.endpoint.impl.OrderServiceConfigDescriptor;
import com.morrisons.wholesale.dsd.processor.WholesaleDSDOrderProcessorTemplate;
import com.morrisons.wholesale.dsd.threadexecutor.IThreadExecutor;
import com.morrisons.wholesale.dsd.validation.NodeResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("wholesaleDSDOrderProcessor")
public class WholesaleDSDOrderProcessorTemplateImpl
		extends WholesaleDSDOrderProcessorTemplate<OrderServiceConfigDescriptor, List<Orders>, NodeResult> {

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
	protected OrderServiceConfigDescriptor getDataFromConifgService() {

		return configService.getCustomersFromConfigService();
	}

	@Override
	protected List<Orders> getDSDOrdersWithStatusRaised(OrderServiceConfigDescriptor orderServiceConfigDescriptor) {

		//check if orderServiceConfigDescriptor is null
		if(orderServiceConfigDescriptor == null) {
			
			log.error("Could not read data from config service");
			return Collections.emptyList();
		}
		return threadExecutor
				.execute(getOrdersEndpointGenerator.generateEndPointFromCustomerList(orderServiceConfigDescriptor));
	}

	@Override
	protected NodeResult validateDSDOrders() {
		return null;
	}
}