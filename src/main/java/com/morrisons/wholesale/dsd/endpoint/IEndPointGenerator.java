package com.morrisons.wholesale.dsd.endpoint;

import java.util.List;
import java.util.concurrent.Callable;

import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Orders;

public interface IEndPointGenerator {

	List<Callable<Orders>> generateEndPointFromCustomerList(Customers customers);
}