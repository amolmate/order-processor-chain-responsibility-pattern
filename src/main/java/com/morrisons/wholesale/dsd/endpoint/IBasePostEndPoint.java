package com.morrisons.wholesale.dsd.endpoint;

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
public interface IBasePostEndPoint<I, O> {

	O post(ParameterMappings parameterMappings, I input);
}