package com.morrisons.wholesale.dsd.configservice;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.morrisons.wholesale.dsd.config.ApplicationConfig;
import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.exception.ConfigServiceException;

@Service("configService")
public class ConfigServiceImpl implements ConfigService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigServiceImpl.class);

	@Autowired
	private ApplicationConfig configuration;

	@Autowired
	private IBaseGetEndPoint<Customers> customerConfigServiceEndPoint;

	@Override
	public Customers getCustomersConfigFromService() {

		try {

			return customerConfigServiceEndPoint.get(null);
		} catch (Exception e) {

			LOGGER.error(StringUtils.join("No Configuration Found ", configuration.getCustomer().getIndexvalue()), e);
			throw new ConfigServiceException(102, "No Configuration Found", e, HttpStatus.NOT_FOUND.value());
		}
	}
}