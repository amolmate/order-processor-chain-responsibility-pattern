package com.morrisons.wholesale.dsd.thread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.dto.Order;
import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMapping;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;
import com.morrisons.wholesale.dsd.exception.WMMException;
import com.morrisons.wholesale.dsd.validation.NodeResult;

public class FetchDSDOrdersThread implements Callable<Orders> {

	private static final Logger LOGGER = LoggerFactory.getLogger(FetchDSDOrdersThread.class);

	private ParameterMappings parameterMappings;

	private IBaseGetEndPoint<Orders> getDSDOrdersEndPoint;

	public FetchDSDOrdersThread(IBaseGetEndPoint<Orders> getDSDOrdersEndPoint, ParameterMappings parameterMappings) {

		this.getDSDOrdersEndPoint = getDSDOrdersEndPoint;
		this.parameterMappings = parameterMappings;
	}

	@Override
	public Orders call() throws Exception {

		try {

			// call get order end point here
			
			Orders orders = getDSDOrdersEndPoint.get(parameterMappings);
			
			List<ParameterMapping> pathParameters = parameterMappings.getPathParameters();
			
			
			String suplierName = null;
			
			String customerName = null;
			
			for(ParameterMapping pathParameter : pathParameters) {
				
				if(pathParameter.getName().equals("customerId")) {
					
					customerName = (String) pathParameter.getValue();
				}
				if(pathParameter.getName().equals("supplierName")) {
					
					suplierName = (String) pathParameter.getValue();
				}
				
			}
			
			orders.getOrders().stream().forEach(s -> s.getItems().stream().forEach(i -> setCustomerAndSupplierNameToItem(i, customerName, suplierName)));
			
			return orders;

		} catch (WMMException e) {

			LOGGER.error("Trace : ", e);
			LOGGER.debug("could not fetch DSD Orders. error code : {} ", e.getHttpStatusCode());
		}
		return null;
	}

	private void setCustomerAndSupplierNameToItem(Item item, String customerName, String supplierName){
		
		item.setCustomerName(customerName);
		item.setSupplierName(supplierName);
	}
}