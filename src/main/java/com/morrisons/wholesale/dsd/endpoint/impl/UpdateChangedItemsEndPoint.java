package com.morrisons.wholesale.dsd.endpoint.impl;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.morrisons.wholesale.dsd.config.ExternalServiceConfig;
import com.morrisons.wholesale.dsd.dto.UpdateItemPayload;
import com.morrisons.wholesale.dsd.endpoint.BaseEndPoint;
import com.morrisons.wholesale.dsd.endpoint.IBasePutEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;
import com.morrisons.wholesale.dsd.exception.ErrorCodes;
import com.morrisons.wholesale.dsd.exception.IWMMExceptionFactory;

@Component("updateChangedItemsEndPoint")
public class UpdateChangedItemsEndPoint extends BaseEndPoint<UpdateItemPayload, String>
		implements IBasePutEndPoint<UpdateItemPayload, String> {

	@Autowired
	private IWMMExceptionFactory exceptionFactory;

	@Autowired
	public UpdateChangedItemsEndPoint(RestTemplate restTemplate, ExternalServiceConfig updateItemConfig) {

		super(restTemplate, updateItemConfig);
	}

	@Override
	protected Class<String> getOutputEntityClass() {

		return String.class;
	}

	@Override
	protected RuntimeException getExceptionForErrorResponse(String message, int status) {

		return exceptionFactory.createException(ErrorCodes.UPDATE_CHANGED_ITEMS_ERR, message, status);
	}

	@Override
	protected boolean isStatusValid(int status) {

		return status == Response.Status.ACCEPTED.getStatusCode();
	}

	@Override
	protected HttpMethod getHttpMethod() {

		return HttpMethod.PUT;
	}

	@Override
	public String put(ParameterMappings parameterMappings, UpdateItemPayload input) {

		return send(parameterMappings, input);
	}

	@Override
	protected String getOutputEntity(ResponseEntity<String> response) {

		return null;
	}
}
