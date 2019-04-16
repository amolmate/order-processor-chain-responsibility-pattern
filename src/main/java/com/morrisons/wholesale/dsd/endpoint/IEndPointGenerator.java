package com.morrisons.wholesale.dsd.endpoint;

import java.util.List;
import java.util.concurrent.Callable;

import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.endpoint.impl.OrderServiceConfigDescriptor;

public interface IEndPointGenerator {

	List<Callable<Orders>> generateEndPointFromCustomerList(OrderServiceConfigDescriptor orderServiceConfigDescriptor);
}