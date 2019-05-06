package com.morrisons.wholesale.dsd.endpoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.morrisons.wholesale.dsd.config.RedisConfig;
import com.morrisons.wholesale.dsd.dto.RedisCatlogueItem;
import com.morrisons.wholesale.dsd.endpoint.BaseEndPoint;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;
import com.morrisons.wholesale.dsd.exception.IWMMExceptionFactory;

@Component("redisCacheEndPoint")
public class RedisCacheCatlogueEndPoint extends BaseEndPoint<HttpHeaders, RedisCatlogueItem>
		implements IBaseGetEndPoint<RedisCatlogueItem> {

	@Autowired
	private IWMMExceptionFactory exceptionFactory;

	@Autowired
	public RedisCacheCatlogueEndPoint(RestTemplate restTemplate, RedisConfig redisConfig) {

		super(restTemplate, redisConfig);
	}

	@Override
	public RedisCatlogueItem get(ParameterMappings parameterMappings) {
		return null;
	}

	@Override
	protected HttpMethod getHttpMethod() {
		return null;
	}

	@Override
	protected boolean isStatusValid(int status) {
		return false;
	}

	@Override
	protected Class<RedisCatlogueItem> getOutputEntityClass() {
		return null;
	}

	@Override
	protected RuntimeException getExceptionForErrorResponse(String message, int status) {
		return null;
	}
}