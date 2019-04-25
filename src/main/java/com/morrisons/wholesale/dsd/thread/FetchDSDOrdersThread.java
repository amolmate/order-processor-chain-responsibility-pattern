package com.morrisons.wholesale.dsd.thread;

import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.persistence.sessions.server.ExternalConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.morrisons.wholesale.dsd.config.ApplicationConfig;
import com.morrisons.wholesale.dsd.config.ExternalServiceConfig;
import com.morrisons.wholesale.dsd.dto.Item;
import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMapping;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;
import com.morrisons.wholesale.dsd.exception.WMMException;
import com.morrisons.wholesale.dsd.util.SpringRestTemplateURIUtility;

public class FetchDSDOrdersThread implements Callable<Orders> {

	private static final Logger LOGGER = LoggerFactory.getLogger(FetchDSDOrdersThread.class);

	private ParameterMappings parameterMappings;

	private IBaseGetEndPoint<Orders> getDSDOrdersEndPoint;
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SpringRestTemplateURIUtility springRestTemplateURIUtility;
	
	@Autowired
	private ExternalServiceConfig dSDOrdersConfig;
	
	public FetchDSDOrdersThread(IBaseGetEndPoint<Orders> getDSDOrdersEndPoint, ParameterMappings parameterMappings) {

		this.getDSDOrdersEndPoint = getDSDOrdersEndPoint;
		this.parameterMappings = parameterMappings;
	}

	@Override
	public Orders call() throws Exception {

		try {

			// call get order end point here

			//Orders orders = getDSDOrdersEndPoint.get(parameterMappings);
			//String uri = null;
			
			String uri = springRestTemplateURIUtility.getDSDOrderServiceConfig(parameterMappings);

			HttpEntity<HttpHeaders> httpHeaderEntity = springRestTemplateURIUtility
					.getHeaderEntity(dSDOrdersConfig.getAuthorization());

			ResponseEntity<Orders> response = restTemplate.exchange(uri, HttpMethod.GET, httpHeaderEntity,
					Orders.class);
			Orders orders = response.getBody();

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

		} catch (WMMException e) {

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