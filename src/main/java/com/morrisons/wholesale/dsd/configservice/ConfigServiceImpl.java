package com.morrisons.wholesale.dsd.configservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morrisons.wholesale.dsd.config.ApplicationConfig;
import com.morrisons.wholesale.dsd.dao.EventDAO;
import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.endpoint.impl.OrderServiceConfigDescriptor;

public class ConfigServiceImpl implements ConfigService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigServiceImpl.class);

	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private ApplicationConfig configuration;

	@Override
	public Customers getCustomersFromConfigService(String customerHashKey, String customerIdAndMessageType) {

		LOGGER.info("ConfigServiceImpl getOrderServiceConfigByCustomerAndMessageType START");

		OrderServiceConfigDescriptor orderServiceConfigDescriptor = new OrderServiceConfigDescriptor();

		try {

			Item item = eventDAO.getItem("table Name", customerHashKey, customerIdAndMessageType);
			orderServiceConfigDescriptor = new ObjectMapper().readValue(item.toJSON(),
					OrderServiceConfigDescriptor.class);
		} catch (Exception e) {

			LOGGER.error(String.format("No Configuration Found for combination %s ", customerIdAndMessageType), e);
		}

		LOGGER.info("ConfigServiceImpl getOrderServiceConfigByCustomerAndMessageType END");
		return null;
	}
}