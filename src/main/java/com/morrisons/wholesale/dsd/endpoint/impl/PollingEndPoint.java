package com.morrisons.wholesale.dsd.endpoint.impl;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morrisons.wholesale.dsd.config.ExternalServiceConfig;
import com.morrisons.wholesale.dsd.dto.PollingResponse;
import com.morrisons.wholesale.dsd.endpoint.BaseGetEndPoint;
import com.morrisons.wholesale.dsd.exception.ErrorCodes;
import com.morrisons.wholesale.dsd.exception.IWMMExceptionFactory;

@Component("pollingEndPoint")
public class PollingEndPoint extends BaseGetEndPoint<PollingResponse> {

	@Autowired
	private IWMMExceptionFactory exceptionFactory;

	@Autowired
	public PollingEndPoint(Client client, ExternalServiceConfig pollingConfig) {

		super(client, pollingConfig);
	}

	@Override
	protected Class<PollingResponse> getOutputEntityClass() {

		return PollingResponse.class;
	}

	@Override
	protected RuntimeException getExceptionForErrorResponse(String message, int status) {

		return exceptionFactory.createException(ErrorCodes.GET_DSD_ORDERS_ERR, message, status);
	}

	@Override
	protected PollingResponse getOutputEntity(Response response) {

		return (PollingResponse) response.getEntity();
	}

	@Override
	protected boolean isStatusValid(int status) {

		return status == Response.Status.ACCEPTED.getStatusCode();
	}
}