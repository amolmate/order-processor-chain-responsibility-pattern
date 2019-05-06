package com.morrisons.wholesale.dsd.endpoint;

import org.springframework.http.ResponseEntity;

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

@FunctionalInterface
public interface IBaseEndPoint<I, O> {

	ResponseEntity<O> send(ParameterMappings parameterMappings, I input);
}