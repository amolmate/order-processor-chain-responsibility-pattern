package com.morrisons.wholesale.dsd.thread;

import java.util.concurrent.Callable;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;
import com.morrisons.wholesale.dsd.exception.WMMException;

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
			
			/*Response response = getDSDOrdersEndPoint.get(parameterMappings);
			Orders orders = (Orders) response.getEntity();*/
			return getDSDOrdersEndPoint.get(parameterMappings);

		} catch (WMMException e) {

			LOGGER.error("Trace : ", e);
			LOGGER.debug("could not fetch DSD Orders. error code : {} ", e.getHttpStatusCode());
		}
		return null;
	}
}