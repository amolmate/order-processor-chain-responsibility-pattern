package com.morrisons.wholesale.dsd.endpoint;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.morrisons.wholesale.dsd.config.ExternalServiceConfig;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;
import com.morrisons.wholesale.dsd.exception.ErrorCodes;
import com.morrisons.wholesale.dsd.exception.WMMException;

/**
 * 
 * @author amol13704
 *
 * @param <I>
 *            Input structure
 * @param <O>
 *            Output Structure
 */

public abstract class BasePostEndPoint<I, O> extends BaseEndPoint<I, O> implements IBasePostEndPoint<I, O> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BasePostEndPoint.class);

	public BasePostEndPoint(Client client, ExternalServiceConfig externalServiceConfig) {

		super(client, externalServiceConfig);
	}

	@Override
	public final O post(ParameterMappings parameterMappings, I input) {

		return send(parameterMappings, input);
	}

	@Override
	protected final Response getResponse(Invocation.Builder invocationBuilder, I input) {

		try {
			Entity<I> e = Entity.json(input);
			return invocationBuilder.post(e);
		} catch (WMMException ex) {
			throw ex;
		} catch (Exception e) {

			String message = "Connection refused exception occured";
			WMMException we = new WMMException(ErrorCodes.CONNECTION_REFUSED_ERR, message, e,
					WMMException.DEFAULT_HTTP_STATUS_CODE);
			LOGGER.error(we.getMessage(), we);
			throw we;
		}
	}

	@Override
	protected boolean isStatusValid(int status) {

		return status == Response.Status.CREATED.getStatusCode();
	}
}
