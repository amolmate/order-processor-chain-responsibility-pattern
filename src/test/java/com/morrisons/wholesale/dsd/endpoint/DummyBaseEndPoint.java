package com.morrisons.wholesale.dsd.endpoint;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.morrisons.wholesale.dsd.config.ExternalServiceConfig;

public class DummyBaseEndPoint extends BaseEndPoint<DummyIO, DummyIO> {
	/*
	 * @Inject public DummyBaseEndPoint(Client client, ExternalServiceConfig
	 * externalServiceConfig) { super(client, externalServiceConfig); }
	 * 
	 * @Override protected Response getResponse(Builder invocationBuilder,
	 * DummyIO input) { return invocationBuilder.get(); }
	 */

	public DummyBaseEndPoint(RestTemplate restTemplate, ExternalServiceConfig externalServiceConfig) {
		super(restTemplate, externalServiceConfig);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean isStatusValid(int status) {
		return status == 1;
	}

	@Override
	protected Class<DummyIO> getOutputEntityClass() {
		return DummyIO.class;
	}

	@Override
	protected HttpMethod getHttpMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected DummyIO getOutputEntity(ResponseEntity<DummyIO> response) {
		// TODO Auto-generated method stub
		return null;
	}
}