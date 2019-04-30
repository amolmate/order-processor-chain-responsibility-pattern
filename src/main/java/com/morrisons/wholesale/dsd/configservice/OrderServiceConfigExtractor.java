package com.morrisons.wholesale.dsd.configservice;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderServiceConfigExtractor
{
	private Logger logger = LoggerFactory.getLogger(OrderServiceConfigExtractor.class);

	public <T> T getSettingOrDefault(String settingName, Map<String, Object> orderServiceConfigDescriptor, Class<T> cls, T defaultVal)
	{
		if (orderServiceConfigDescriptor.containsKey(settingName))
		{
			return cls.cast(orderServiceConfigDescriptor.get(settingName));
		}
		if (logger.isDebugEnabled())
		{
			logger.warn(String.format("No Settings found for key %s using default configuration provided %s", settingName, defaultVal));
		}
		return defaultVal;
	}

}