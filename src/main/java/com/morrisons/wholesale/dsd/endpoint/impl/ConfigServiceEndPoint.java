package com.morrisons.wholesale.dsd.endpoint.impl;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.morrisons.wholesale.dsd.config.ExternalServiceConfig;
import com.morrisons.wholesale.dsd.dto.Customers;
import com.morrisons.wholesale.dsd.endpoint.BaseEndPoint;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

@Component("customerConfigServiceEndPoint")
public class ConfigServiceEndPoint extends BaseEndPoint<HttpHeaders, Customers> implements IBaseGetEndPoint<Customers> {

	@Autowired
	public ConfigServiceEndPoint(RestTemplate restTemplate, ExternalServiceConfig customerServiceConfig) {

		super(restTemplate, customerServiceConfig);
	}

	@Override
	protected Class<Customers> getOutputEntityClass() {

		return Customers.class;
	}

	@Override
	protected boolean isStatusValid(int status) {

		return status == Response.Status.ACCEPTED.getStatusCode();
	}

	@Override
	public Customers get(ParameterMappings parameterMappings) {

		return getOutputEntity(send(parameterMappings, null));
	}

	@Override
	protected HttpMethod getHttpMethod() {

		return HttpMethod.GET;
	}

	@Override
	protected Customers getOutputEntity(ResponseEntity<Customers> response) {

		return response.getBody();
	}
}