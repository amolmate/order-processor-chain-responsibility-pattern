package com.morrisons.wholesale.dsd.endpoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.morrisons.wholesale.dsd.config.ExternalServiceConfig;
import com.morrisons.wholesale.dsd.dto.AggregationPayload;
import com.morrisons.wholesale.dsd.endpoint.BaseEndPoint;
import com.morrisons.wholesale.dsd.endpoint.IBasePostEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

@Component("aggregationEndPoint")
public class AggregationEndPoint extends BaseEndPoint<AggregationPayload, String>
		implements IBasePostEndPoint<AggregationPayload, String> {

	@Autowired
	public AggregationEndPoint(RestTemplate restTemplate, ExternalServiceConfig updateItemConfig) {

		super(restTemplate, updateItemConfig);
	}

	@Override
	protected Class<String> getOutputEntityClass() {

		return String.class;
	}

	@Override
	protected boolean isStatusValid(int status) {

		return status == HttpStatus.ACCEPTED.value();
	}

	@Override
	protected HttpMethod getHttpMethod() {

		return HttpMethod.POST;
	}

	@Override
	public String post(ParameterMappings parameterMappings, AggregationPayload input) {

		return getOutputEntity(send(parameterMappings, input));
	}

	@Override
	protected String getOutputEntity(ResponseEntity<String> response) {

		return null;
	}
}
