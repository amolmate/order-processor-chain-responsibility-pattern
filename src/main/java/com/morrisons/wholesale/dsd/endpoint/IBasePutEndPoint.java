package com.morrisons.wholesale.dsd.endpoint;

import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

public interface IBasePutEndPoint<I, O> {

	O put(ParameterMappings parameterMappings, I input);
}
