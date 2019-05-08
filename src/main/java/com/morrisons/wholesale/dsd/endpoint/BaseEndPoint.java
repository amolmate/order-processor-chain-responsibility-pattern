package com.morrisons.wholesale.dsd.endpoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

	private final RestTemplate restTemplate;

	private final ExternalServiceConfig externalServiceConfig;

	public BaseEndPoint(RestTemplate restTemplate, ExternalServiceConfig externalServiceConfig) {

		this.restTemplate = restTemplate;
		this.externalServiceConfig = externalServiceConfig;
	}

	/**
	 * Main method
	 */
	@Override
	public final ResponseEntity<O> send(ParameterMappings parameterMappings, I input) {

		try {

			String uri = buildURIWithParams(parameterMappings);

			LOGGER.info(StringUtils.join("Request URI -> ", uri));

			return restTemplate.exchange(uri, getHttpMethod(), getHttpEntity(input, parameterMappings),
					getOutputEntityClass());
		} catch (Exception exception) {

			LOGGER.error(StringUtils.join("Exception in sending request ", exception.getMessage()));
			throw exception;
		}
	}

	private HttpEntity<Object> getHttpEntity(I input, ParameterMappings parameterMappings) {

		if (input == null) {

			return new HttpEntity<>(getHttpHeader(parameterMappings));
		} else {

			return new HttpEntity<>(input, getHttpHeader(parameterMappings));
		}
	}

	private HttpHeaders getHttpHeader(ParameterMappings parameterMappings) {

		HttpHeaders headers = new HttpHeaders();

		List<ParameterMapping> listMappings = parameterMappings.getHeaderParameters();

		if (CollectionUtils.isNotEmpty(listMappings)) {

			listMappings.forEach(s -> headers.set(s.getName(), (String) s.getValue()));
		}

		return headers;
	}

	private String buildURIWithParams(ParameterMappings parameterMappings) {

		Map<String, String> uriParams = new HashMap<>();

		List<ParameterMapping> pathParam = parameterMappings.getPathParameters();

		for (ParameterMapping eachPathParam : pathParam) {

			uriParams.put(eachPathParam.getName(), eachPathParam.getValue().toString());
		}

		List<ParameterMapping> querryParam = parameterMappings.getQueryParameters();

		// Query parameters
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(externalServiceConfig.getUri());

		querryParam.forEach(q -> builder.queryParam(q.getName(), q.getValue()));

		return builder.buildAndExpand(uriParams).toUri().toString();
	}

	protected abstract HttpMethod getHttpMethod();

	protected O getOutputEntity(ResponseEntity<O> response) {

		return response.getBody();
	}

	/**
	 * Applicable statuses to checked against
	 * 
	 * @param status
	 * @return
	 */
	protected abstract boolean isStatusValid(int status);

	/**
	 * Output structure class to be supplied by the implementing leaf class
	 * 
	 * @return
	 */
	protected abstract Class<O> getOutputEntityClass();
}