package com.morrisons.wholesale.dsd.endpoint.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.morrisons.wholesale.dsd.config.RedisConfig;
import com.morrisons.wholesale.dsd.dto.RedisCatlogueItem;
import com.morrisons.wholesale.dsd.endpoint.BaseEndPoint;
import com.morrisons.wholesale.dsd.endpoint.IBaseGetEndPoint;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

@Component("redisCacheEndPoint")
public class RedisCacheCatlogueEndPoint extends BaseEndPoint<HttpHeaders, RedisCatlogueItem>
		implements IBaseGetEndPoint<RedisCatlogueItem> {

	@Autowired
	public RedisCacheCatlogueEndPoint(RestTemplate restTemplate, RedisConfig redisConfig) {

		super(restTemplate, redisConfig);
	}

	@Override
	public RedisCatlogueItem get(ParameterMappings parameterMappings) {

		return getOutputEntity(send(parameterMappings, null));
	}

	@Override
	protected HttpMethod getHttpMethod() {

		return HttpMethod.GET;
	}

	@Override
	protected boolean isStatusValid(int status) {

		return false;
	}

	@Override
	protected Class<RedisCatlogueItem> getOutputEntityClass() {

		return RedisCatlogueItem.class;
	}

	@Override
	protected RedisCatlogueItem getOutputEntity(ResponseEntity<RedisCatlogueItem> response) {

		return response.getBody();
	}
}