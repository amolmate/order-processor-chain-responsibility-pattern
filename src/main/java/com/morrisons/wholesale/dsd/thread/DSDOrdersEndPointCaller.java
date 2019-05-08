package com.morrisons.wholesale.dsd.thread;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMapping;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;
import com.morrisons.wholesale.dsd.exception.ConfigServiceException;

public class DSDOrdersEndPointCaller {

	private static final Logger LOGGER = LoggerFactory.getLogger(DSDOrdersEndPointCaller.class);

	private ParameterMappings parameterMappings;

	private IBaseGetEndPoint<Orders> getDSDOrdersEndPoint;

	public DSDOrdersEndPointCaller(IBaseGetEndPoint<Orders> getDSDOrdersEndPoint, ParameterMappings parameterMappings) {

		this.getDSDOrdersEndPoint = getDSDOrdersEndPoint;
		this.parameterMappings = parameterMappings;
	}

	public Orders call() {

		try {

			// call get order end point here

			Orders orders = getDSDOrdersEndPoint.get(parameterMappings);

			List<ParameterMapping> pathParameters = parameterMappings.getPathParameters();

			String suplierName = null;

			String customerName = null;

			for (ParameterMapping pathParameter : pathParameters) {

				if (pathParameter.getName().equals("customerId")) {

					customerName = (String) pathParameter.getValue();
				}
				if (pathParameter.getName().equals("supplierName")) {

					suplierName = (String) pathParameter.getValue();
				}
			}

			CustomerSupplierNames names = new CustomerSupplierNames(customerName, suplierName);

			orders.getOrders().stream().forEach(s -> s.getItems().stream()
					.forEach(i -> setCustomerAndSupplierNameToItem(i, names.suplierName, names.customerName)));

			return orders;

		} catch (ConfigServiceException e) {

			LOGGER.error("Trace : ", e);
			LOGGER.debug("could not fetch DSD Orders. error code : {} ", e.getHttpStatusCode());
		}
		return null;
	}

	private void setCustomerAndSupplierNameToItem(Item item, String supplierName, String customerName) {

		item.setCustomerName(customerName);
		item.setSupplierName(supplierName);
	}

	private class CustomerSupplierNames {

		private String suplierName;

		private String customerName;

		public CustomerSupplierNames(String suplierName, String customerName) {

			this.suplierName = suplierName;
			this.customerName = customerName;
		}
	}
}