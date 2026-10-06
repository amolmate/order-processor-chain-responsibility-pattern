package com.morrisons.wholesale.dsd.endpoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.morrisons.wholesale.dsd.config.ExternalServiceConfig;
import com.morrisons.wholesale.dsd.dto.PollingResponse;
import com.morrisons.wholesale.dsd.endpoint.BaseEndPoint;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

@Component("pollingEndPoint")
public class PollingEndPoint extends BaseEndPoint<HttpHeaders, PollingResponse>
		implements IBaseGetEndPoint<PollingResponse> {

	@Autowired
	public PollingEndPoint(RestTemplate restTemplate, ExternalServiceConfig pollingConfig) {

		super(restTemplate, pollingConfig);
	}

	@Override
	protected Class<PollingResponse> getOutputEntityClass() {

		return PollingResponse.class;
	}

	@Override
	protected boolean isStatusValid(int status) {

		return status == HttpStatus.ACCEPTED.value();
	}

	@Override
	public PollingResponse get(ParameterMappings parameterMappings) {
		
		return getOutputEntity(send(parameterMappings, null));
	}

	@Override
	protected HttpMethod getHttpMethod() {
		
		return HttpMethod.GET;
	}
	
	@Override
	protected PollingResponse getOutputEntity(ResponseEntity<PollingResponse> response) {

		return response.getBody();
	}
}
