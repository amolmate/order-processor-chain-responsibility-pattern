package com.morrisons.wholesale.dsd.endpoint;

import com.morrisons.wholesale.dsd.endpoint.vo.ParameterMappings;

/**
 * 
 * @author amol13704
 *
 * @param <O>
 *            Output Structure
 */

public interface IBaseGetEndPoint<O> {

	O get(ParameterMappings parameterMappings);
}