package com.morrisons.wholesale.dsd.endpoint;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.util.CollectionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.HttpHeaders;
import com.morrisons.wholesale.dsd.config.ExternalServiceConfig;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMapping;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

/**
 * 
 * @author amol13704
 *
 * @param <I>
 *            Input structure
 * @param <O>
 *            Output Structure
 */

public abstract class BaseEndPoint<I, O> implements IBaseEndPoint<I, O> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseEndPoint.class);

	private final Client client;

	private final ExternalServiceConfig externalServiceConfig;

	public BaseEndPoint(Client client, ExternalServiceConfig externalServiceConfig) {

		this.client = client;
		this.externalServiceConfig = externalServiceConfig;
	}

	/**
	 * Main method
	 */
	@Override
	public final O send(ParameterMappings parameterMappings, I input) {

		printRequest(input);

		Invocation.Builder invocationBuilder = getInvocationBuilder(parameterMappings);

		Response response = getResponse(invocationBuilder, input);

		return getOutput(response, parameterMappings);
	}

	protected void printRequest(I input) {

		try {
			
			ObjectMapper objectMapper = new ObjectMapper();
			String out = objectMapper.writeValueAsString(input);

			LOGGER.debug("Request Body : {}", out);
		} catch (JsonProcessingException e) {

			LOGGER.error("Error while printing the request : ", e);
		}
	}

	/**
	 * 
	 * @param parameterMappings
	 * @return Invocation.Builder
	 */
	protected Invocation.Builder getInvocationBuilder(ParameterMappings parameterMappings) {

		WebTarget target = getWebTarget();

		target = setParameters(target, parameterMappings, externalServiceConfig.getApiKey());

		Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);

		String authorization = externalServiceConfig.getAuthorization();
		if (StringUtils.isNotBlank(authorization)) {
			invocationBuilder.header(HttpHeaders.AUTHORIZATION, authorization);
		}

		if (parameterMappings != null) {
			invocationBuilder = setHeaders(invocationBuilder, parameterMappings.getHeaderParameters());
		}

		return invocationBuilder;
	}

	/**
	 * 
	 * @return WebTarget
	 */
	private WebTarget getWebTarget() {

		return client.target(externalServiceConfig.getUri());
	}

	/**
	 * Set Parameters
	 * 
	 * @param webTarget
	 * @param parameterMappings
	 * @param apiKey
	 * @return
	 */
	protected WebTarget setParameters(WebTarget webTarget, ParameterMappings parameterMappings, String apiKey) {

		WebTarget returnWebTarget = webTarget;

		if (parameterMappings != null) {

			List<ParameterMapping> pathParams = parameterMappings.getPathParameters();
			returnWebTarget = setPathParams(returnWebTarget, pathParams);

			List<ParameterMapping> queryParams = parameterMappings.getQueryParameters();
			returnWebTarget = setQueryParams(returnWebTarget, queryParams);
		}

		if (StringUtils.isNotBlank(apiKey)) {
			returnWebTarget = returnWebTarget.queryParam("apikey", apiKey);
		}

		return returnWebTarget;
	}

	/**
	 * Set Path parameters
	 * 
	 * @param webTarget
	 * @param pathParams
	 * @return
	 */
	private WebTarget setPathParams(WebTarget webTarget, List<ParameterMapping> pathParams) {

		WebTarget returnWebTarget = webTarget;

		if (!CollectionUtils.isNullOrEmpty(pathParams)) {

			for (ParameterMapping parameterMap : pathParams) {
				String paramName = parameterMap.getName();
				Object paramValue = parameterMap.getValue();

				LOGGER.debug("Path : paramName : {}, paramValue : {}", paramName, paramValue);
				returnWebTarget = returnWebTarget.resolveTemplate(paramName, paramValue);
			}
		}
		return returnWebTarget;
	}

	/**
	 * Set query parameters
	 * 
	 * @param webTarget
	 * @param queryParams
	 * @return
	 */
	private WebTarget setQueryParams(WebTarget webTarget, List<ParameterMapping> queryParams) {

		WebTarget returnWebTarget = webTarget;

		if (!CollectionUtils.isNullOrEmpty(queryParams)) {

			for (ParameterMapping parameterMap : queryParams) {
				String paramName = parameterMap.getName();
				Object paramValue = parameterMap.getValue();

				LOGGER.debug("Query : paramName : {}, paramValue : {}", paramName, paramValue);
				returnWebTarget = returnWebTarget.queryParam(paramName, paramValue);
			}
		}
		return returnWebTarget;
	}

	/**
	 * Set header parameters
	 * 
	 * @param invocationBuilder
	 * @param headerParams
	 * @return
	 */
	protected Invocation.Builder setHeaders(Invocation.Builder invocationBuilder, List<ParameterMapping> headerParams) {

		Invocation.Builder returnInvocationBuilder = invocationBuilder;

		if (!CollectionUtils.isNullOrEmpty(headerParams)) {

			for (ParameterMapping parameterMapping : headerParams) {

				String paramName = parameterMapping.getName();
				Object paramValue = parameterMapping.getValue();

				LOGGER.debug("Headers : paramName : {}, paramValue : {}", paramName, paramValue);
				returnInvocationBuilder = returnInvocationBuilder.header(paramName, paramValue);
			}
		}

		return returnInvocationBuilder;
	}

	/**
	 * Appropriate method such as get, post, put, etc to be called by the
	 * implementing branch or leaf
	 * 
	 * @param invocationBuilder
	 * @param input
	 * @return
	 */
	protected abstract Response getResponse(Invocation.Builder invocationBuilder, I input);

	/**
	 * Obtain output from Response
	 * 
	 * @param response
	 * @param parameterMappings
	 * @return
	 */
	protected O getOutput(Response response, ParameterMappings parameterMappings) {

		int status = response.getStatus();
		StatusType type = response.getStatusInfo();
		String reasonPhrase = type.getReasonPhrase();
		if (!isStatusValid(status)) {

			response.close();

			String inputs = getInputs(parameterMappings);

			String message = "Response Status : " + status + ", Reason : " + reasonPhrase + ", Inputs : [" + inputs
					+ "]";
			RuntimeException e = getExceptionForErrorResponse(message, status);
			LOGGER.error(message, e);
			throw e;
		}

		O output = getOutputEntity(response);
		response.close();
		return output;
	}

	/**
	 * 
	 * @param response
	 * @return
	 */
	protected O getOutputEntity(Response response) {
		return response.readEntity(getOutputEntityClass());
	}

	/**
	 * Applicable statuses to checked against
	 * 
	 * @param status
	 * @return
	 */
	protected abstract boolean isStatusValid(int status);

	/**
	 * This is used for logging the input parameters
	 * 
	 * @param parameterMappings
	 * @return
	 */
	protected String getInputs(ParameterMappings parameterMappings) {

		StringBuilder sb = new StringBuilder();

		if (parameterMappings != null) {

			sb.append(buildParamValueText(parameterMappings.getPathParameters()));
			sb.append(buildParamValueText(parameterMappings.getQueryParameters()));
			sb.append(buildParamValueText(parameterMappings.getHeaderParameters()));
		}

		return sb.toString();
	}

	/**
	 * This is used for logging the input parameters
	 * 
	 * @param mappings
	 * @return
	 */
	private StringBuilder buildParamValueText(List<ParameterMapping> mappings) {

		StringBuilder sb = new StringBuilder();

		if (!CollectionUtils.isNullOrEmpty(mappings)) {

			boolean isSubsequent = false;
			for (ParameterMapping mapping : mappings) {

				String paramName = mapping.getName();
				Object paramValue = mapping.getValue();

				if (isSubsequent) {
					sb.append(", ");
				}
				sb.append(paramName + " : " + paramValue);
				isSubsequent = true;
			}
		}

		return sb;
	}

	/**
	 * Output structure class to be supplied by the implementing leaf class
	 * 
	 * @return
	 */
	protected abstract Class<O> getOutputEntityClass();

	/**
	 * Exception to be supplied by the implementing leaf class
	 * 
	 * @param message
	 * @return
	 */
	protected abstract RuntimeException getExceptionForErrorResponse(String message, int status);
}
