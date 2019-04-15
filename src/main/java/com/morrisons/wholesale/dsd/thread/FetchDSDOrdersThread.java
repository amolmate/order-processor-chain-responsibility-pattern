package com.morrisons.wholesale.dsd.thread;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;
import com.morrisons.wholesale.dsd.exception.WMMException;

public class FetchDSDOrdersThread implements Callable<Orders> {

	private static final Logger LOGGER = LoggerFactory.getLogger(FetchDSDOrdersThread.class);

	private ParameterMappings parameterMappings;

	public FetchDSDOrdersThread(ParameterMappings parameterMappings) {

		this.parameterMappings = parameterMappings;
	}

	@Override
	public Orders call() throws Exception {

		try {

			// call get order end point here

		} catch (WMMException e) {

			LOGGER.error("Trace : ", e);
			LOGGER.debug("error code : {} ", e.getHttpStatusCode());
		}
		return null;
	}
}