package com.morrisons.wholesale.dsd.endpoint.impl;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.morrisons.wholesale.dsd.config.ExternalServiceConfig;
import com.morrisons.wholesale.dsd.dto.PollingResponse;
import com.morrisons.wholesale.dsd.endpoint.BaseEndPoint;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;
import com.morrisons.wholesale.dsd.exception.ErrorCodes;
import com.morrisons.wholesale.dsd.exception.IWMMExceptionFactory;

@Component("pollingEndPoint")
public class PollingEndPoint extends BaseEndPoint<HttpHeaders, PollingResponse>
		implements IBaseGetEndPoint<PollingResponse> {

	@Autowired
	private IWMMExceptionFactory exceptionFactory;

	@Autowired
	public PollingEndPoint(RestTemplate restTemplate, ExternalServiceConfig pollingConfig) {

		super(restTemplate, pollingConfig);
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
	protected boolean isStatusValid(int status) {

		return status == Response.Status.ACCEPTED.getStatusCode();
	}

	@Override
	public PollingResponse get(ParameterMappings parameterMappings) {
		return getOutputEntity(send(parameterMappings, null));
	}

	@Override
	protected HttpMethod getHttpMethod() {
		return null;
	}
}