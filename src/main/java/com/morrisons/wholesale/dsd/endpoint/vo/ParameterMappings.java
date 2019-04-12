package com.morrisons.wholesale.dsd.endpoint.vo;

import java.util.List;

public class ParameterMappings {

	private List<ParameterMapping> pathParameters;
	private List<ParameterMapping> queryParameters;
	private List<ParameterMapping> headerParameters;

	public List<ParameterMapping> getPathParameters() {
		return pathParameters;
	}

	public void setPathParameters(List<ParameterMapping> pathParameters) {
		this.pathParameters = pathParameters;
	}

	public List<ParameterMapping> getQueryParameters() {
		return queryParameters;
	}

	public void setQueryParameters(List<ParameterMapping> queryParameters) {
		this.queryParameters = queryParameters;
	}

	public List<ParameterMapping> getHeaderParameters() {
		return headerParameters;
	}

	public void setHeaderParameters(List<ParameterMapping> headerParameters) {
		this.headerParameters = headerParameters;
	}
}
