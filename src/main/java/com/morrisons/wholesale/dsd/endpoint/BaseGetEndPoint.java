package com.morrisons.wholesale.dsd.endpoint;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

import com.morrisons.wholesale.dsd.config.ExternalServiceConfig;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

/**
 * 
 * @author amol13704
 *
 * @param <O>
 *            Output Structure
 */
public abstract class BaseGetEndPoint<O> extends BaseEndPoint<Object, O> implements IBaseGetEndPoint<O> {

	public BaseGetEndPoint(Client client, ExternalServiceConfig externalServiceConfig) {

		super(client, externalServiceConfig);
	}

	@Override
	public final O get(ParameterMappings parameterMappings) {

		return send(parameterMappings, null);
	}

	@Override
	protected final Response getResponse(Invocation.Builder invocationBuilder, Object input) {

		return invocationBuilder.get();
	}

	@Override
	protected boolean isStatusValid(int status) {

		return status == Response.Status.OK.getStatusCode();
	}
}