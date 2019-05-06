package com.morrisons.wholesale.dsd.configservice;

@FunctionalInterface
public interface ConfigService {

	OrderServiceConfigDescriptor getCustomersFromConfigService();
}