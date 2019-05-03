package com.morrisons.wholesale.dsd.endpoint;

import javax.inject.Inject;
import javax.ws.rs.client.Client;

import com.morrisons.wholesale.dsd.config.ExternalServiceConfig;

public class DummyBaseGetEndPoint extends BaseGetEndPointTest {

	/*@Inject
	public DummyBaseGetEndPoint(Client client, ExternalServiceConfig externalServiceConfig) {
		super(client, externalServiceConfig);
	}

	@Override
	protected Class<DummyIO> getOutputEntityClass() {
		return DummyIO.class;
	}

	@Override
	protected RuntimeException getExceptionForErrorResponse(String message, int status) {
		return new RuntimeException();
	}*/
}
