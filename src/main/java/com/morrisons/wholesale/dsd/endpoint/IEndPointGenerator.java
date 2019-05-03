package com.morrisons.wholesale.dsd.endpoint;

import java.util.List;

import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.dto.Orders;

public interface IEndPointGenerator {

	List<Orders> generateEndPointFromCustomerList(Customers customers);
}