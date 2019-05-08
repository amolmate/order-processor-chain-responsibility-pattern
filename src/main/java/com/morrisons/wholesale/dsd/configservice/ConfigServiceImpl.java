package com.morrisons.wholesale.dsd.configservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.morrisons.wholesale.dsd.config.ApplicationConfig;
import com.morrisons.wholesale.dsd.dao.EventDAO;
import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

@Service("configService")
public class ConfigServiceImpl implements ConfigService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigServiceImpl.class);

	@Autowired
	private EventDAO eventDAO;

	@Autowired
	private ApplicationConfig configuration;

	@Autowired
	private IBaseGetEndPoint<Customers> customerServiceConfig;

	@Override
	public OrderServiceConfigDescriptor getCustomersConfigFromDynamo() {

		LOGGER.info("ConfigServiceImpl getOrderServiceConfigByCustomerAndMessageType START");

		OrderServiceConfigDescriptor orderServiceConfigDescriptor = null;

		try {

			Item item = eventDAO.getItem(configuration.getDynamoDbConfiguration().getDynamoWholesaleConfigTable(),
					configuration.getCustomer().getIndexkey(), configuration.getCustomer().getIndexvalue());

			orderServiceConfigDescriptor = new ObjectMapper().readValue(item.toJSON(),
					OrderServiceConfigDescriptor.class);

		} catch (Exception e) {

			LOGGER.error(String.format("No Configuration Found for combination %s ",
					configuration.getCustomer().getIndexvalue()), e);
			throw new RuntimeException();
		}

		LOGGER.info("ConfigServiceImpl getOrderServiceConfigByCustomerAndMessageType END");
		return orderServiceConfigDescriptor;
	}

	@Override
	public Customers getCustomersConfigFromService() {

		return customerServiceConfig.get(getParametersMappings());
	}

	private ParameterMappings getParametersMappings() {
		return null;
	}
}