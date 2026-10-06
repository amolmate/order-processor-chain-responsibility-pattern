package com.morrisons.wholesale.dsd.endpoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.morrisons.wholesale.dsd.config.ExternalServiceConfig;
import com.morrisons.wholesale.dsd.dto.Orders;
import com.morrisons.wholesale.dsd.endpoint.BaseEndPoint;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

@Component("getDSDOrdersEndPoint")
public class DSDOrdersGetEndPoint extends BaseEndPoint<HttpHeaders, Orders> implements IBaseGetEndPoint<Orders> {

	@Autowired
	public DSDOrdersGetEndPoint(RestTemplate restTemplate, ExternalServiceConfig dSDOrdersConfig) {

		super(restTemplate, dSDOrdersConfig);
	}

	@Override
	protected Class<Orders> getOutputEntityClass() {

		return Orders.class;
	}

	@Override
	protected boolean isStatusValid(int status) {

		return status == HttpStatus.ACCEPTED.value();
	}

	@Override
	public Orders get(ParameterMappings parameterMappings) {

		return getOutputEntity(send(parameterMappings, null));
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
