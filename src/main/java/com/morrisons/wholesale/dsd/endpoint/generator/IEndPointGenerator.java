package com.morrisons.wholesale.dsd.endpoint.generator;

import java.util.List;

import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Orders;

@FunctionalInterface
public interface IEndPointGenerator {

	List<Orders> generateEndPointFromCustomerList(Customers customers);
}