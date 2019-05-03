package com.morrisons.wholesale.dsd.endpoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.morrisons.wholesale.dsd.config.ExternalServiceConfig;
import com.morrisons.wholesale.dsd.dto.Categories;
import com.morrisons.wholesale.dsd.endpoint.BaseEndPoint;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

@Component("wholesaleStoreServiceEndPoint")
public class WholesaleStoreServiceEndPoint extends BaseEndPoint<HttpHeaders, Categories>
		implements IBaseGetEndPoint<ResponseEntity<Categories>> {

	@Autowired
	public WholesaleStoreServiceEndPoint(RestTemplate restTemplate, ExternalServiceConfig storeServiceConfig) {

		super(restTemplate, storeServiceConfig);
	}

	@Override
	protected HttpMethod getHttpMethod() {

		return HttpMethod.GET;
	}

	@Override
	public ResponseEntity<Categories> get(ParameterMappings parameterMappings) {

		return send(parameterMappings, null);
	}

	@Override
	protected boolean isStatusValid(int status) {

		return false;
	}

	@Override
	protected RuntimeException getExceptionForErrorResponse(String message, int status) {

		return null;
	}

	@Override
	protected Class<Categories> getOutputEntityClass() {

		return Categories.class;
	}
}