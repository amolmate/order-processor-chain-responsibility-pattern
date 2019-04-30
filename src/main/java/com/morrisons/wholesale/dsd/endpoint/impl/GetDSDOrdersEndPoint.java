package com.morrisons.wholesale.dsd.endpoint.impl;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.morrisons.wholesale.dsd.config.ExternalServiceConfig;
import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.endpoint.BaseEndPoint;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;
import com.morrisons.wholesale.dsd.exception.ErrorCodes;
import com.morrisons.wholesale.dsd.exception.IWMMExceptionFactory;

@Component("getDSDOrdersEndPoint")
public class GetDSDOrdersEndPoint extends BaseEndPoint<HttpHeaders, Orders> implements IBaseGetEndPoint<Orders> {

	@Autowired
	private IWMMExceptionFactory exceptionFactory;

	@Autowired
	public GetDSDOrdersEndPoint(RestTemplate restTemplate, ExternalServiceConfig dSDOrdersConfig) {

		super(restTemplate, dSDOrdersConfig);
	}

	@Override
	protected RuntimeException getExceptionForErrorResponse(String message, int status) {

		return exceptionFactory.createException(ErrorCodes.GET_DSD_ORDERS_ERR, message, status);
	}

	@Override
	protected Class<Orders> getOutputEntityClass() {

		return Orders.class;
	}

	@Override
	protected boolean isStatusValid(int status) {

		return status == Response.Status.ACCEPTED.getStatusCode();
	}

	@Override
	public Orders get(ParameterMappings parameterMappings) {

		return send(parameterMappings, null);
	}

	@Override
	protected HttpMethod getHttpMethod() {

		return HttpMethod.GET;
	}

	@Override
	protected Orders getOutputEntity(ResponseEntity<Orders> response) {

		return response.getBody();
	}
}