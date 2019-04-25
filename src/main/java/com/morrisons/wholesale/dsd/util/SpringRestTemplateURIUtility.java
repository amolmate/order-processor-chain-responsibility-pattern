package com.morrisons.wholesale.dsd.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.morrisons.wholesale.dsd.config.ApplicationConfig;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMapping;
import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SpringRestTemplateURIUtility {

	@Autowired
	private ApplicationConfig applicationConfig;

	public String getDSDOrderServiceConfig(ParameterMappings parameterMappings) {

		Map<String, String> uriParams = new HashMap<>();

		List<ParameterMapping> pathParam = parameterMappings.getPathParameters();

		for (ParameterMapping eachPathParam : pathParam) {

			uriParams.put(eachPathParam.getName(), eachPathParam.getValue().toString());
		}

		List<ParameterMapping> querryParam = parameterMappings.getPathParameters();

		// Query parameters
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromUriString(applicationConfig.getDSDOrdersConfig().getUri());

		querryParam.forEach(q -> builder.queryParam(q.getName(), q.getValue()));

		return builder.buildAndExpand(uriParams).toUri().toString();
	}

	public URIBuilder putDSDOrderServiceConfig(String finalUrl) {

		try {

			URIBuilder uri = new URIBuilder(new URI(finalUrl));
			uri.addParameter("apikey", applicationConfig.getDSDOrdersConfig().getApiKey());

			return uri;

		} catch (URISyntaxException e) {
			log.error("Error occured in GenerateURIUtility for generateURI :" + e);
		}
		return null;
	}

	public HttpEntity<HttpHeaders> getHeaderEntity(String authtoken) {

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, authtoken);
		log.info("headers" + headers);
		return new HttpEntity<>(headers);
	}

	public String getFileName(String name) {
		
		String[] split = name.split("/");
		String fileName = null;
		if (split.length > 3) {
			fileName = split[3];
			return fileName;
		}
		return fileName;
	}
}