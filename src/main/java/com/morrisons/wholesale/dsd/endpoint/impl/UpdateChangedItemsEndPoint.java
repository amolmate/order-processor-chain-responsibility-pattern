package com.morrisons.wholesale.dsd.endpoint.impl;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.morrisons.wholesale.dsd.config.ExternalServiceConfig;
import com.morrisons.wholesale.dsd.dto.UpdateItemPayload;
import com.morrisons.wholesale.dsd.endpoint.BasePutEndPoint;
import com.morrisons.wholesale.dsd.exception.ErrorCodes;
import com.morrisons.wholesale.dsd.exception.IWMMExceptionFactory;

public class UpdateChangedItemsEndPoint extends BasePutEndPoint<UpdateItemPayload, Response> {

	@Autowired
	private IWMMExceptionFactory exceptionFactory;

	@Autowired
	public UpdateChangedItemsEndPoint(Client client, ExternalServiceConfig updateItemConfig) {

		super(client, updateItemConfig);
	}

	@Override
	protected Class<Response> getOutputEntityClass() {

		return Response.class;
	}

	@Override
	protected RuntimeException getExceptionForErrorResponse(String message, int status) {

		return exceptionFactory.createException(ErrorCodes.UPDATE_CHANGED_ITEMS_ERR, message, status);
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
