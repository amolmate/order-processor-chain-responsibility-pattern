package com.morrisons.wholesale.dsd.configservice;

import com.morrisons.wholesale.dsd.endpoint.impl.OrderServiceConfigDescriptor;

public interface ConfigService {

	OrderServiceConfigDescriptor getCustomersFromConfigService();
}