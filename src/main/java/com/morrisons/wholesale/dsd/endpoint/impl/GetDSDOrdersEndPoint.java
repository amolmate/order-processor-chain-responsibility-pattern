package com.morrisons.wholesale.dsd.endpoint.impl;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.morrisons.wholesale.dsd.config.ExternalServiceConfig;
import com.morrisons.wholesale.dsd.endpoint.BaseGetEndPoint;
import com.morrisons.wholesale.dsd.exception.ErrorCodes;
import com.morrisons.wholesale.dsd.exception.IWMMExceptionFactory;

@Component
public class GetDSDOrdersEndPoint extends BaseGetEndPoint<Response> {

	@Autowired
	private IWMMExceptionFactory exceptionFactory;

	//@Autowired
	public GetDSDOrdersEndPoint(Client client, ExternalServiceConfig externalServiceConfig) {

		super(client, externalServiceConfig);
	}

	@Override
	protected RuntimeException getExceptionForErrorResponse(String message, int status) {

		return exceptionFactory.createException(ErrorCodes.GET_DSD_ORDERS_ERR, message, status);
	}

	@Override
	protected Class<Response> getOutputEntityClass() {

		return Response.class;
	}

	@Override
	protected Response getOutputEntity(Response response) {

		return response;
	}

	@Override
	protected boolean isStatusValid(int status) {

		return status == Response.Status.ACCEPTED.getStatusCode();
	}
}