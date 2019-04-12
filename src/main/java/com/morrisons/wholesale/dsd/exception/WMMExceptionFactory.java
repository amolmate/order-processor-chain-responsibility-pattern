package com.morrisons.wholesale.dsd.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * All exceptions are to be created via this Factory
 * 
 * @author amol13704
 *
 */
@Component
public class WMMExceptionFactory implements IWMMExceptionFactory {

	private final ExceptionConfigLoader exceptionConfigLoader;

	//@Autowired
	public WMMExceptionFactory(ExceptionConfigLoader exceptionConfigLoader) {

		this.exceptionConfigLoader = exceptionConfigLoader;
	}

	@Override
	public WMMException createException(int errorCode) {

		ExceptionConfigDetails exceptionConfigDetails = exceptionConfigLoader.getErrorMessage(errorCode);

		return createException(exceptionConfigDetails.getErrorCode(), exceptionConfigDetails.getErrorMessage());
	}

	@Override
	public WMMException createException(int errorCode, Throwable throwable) {

		ExceptionConfigDetails exceptionConfigDetails = exceptionConfigLoader.getErrorMessage(errorCode);

		return createException(exceptionConfigDetails.getErrorCode(), exceptionConfigDetails.getErrorMessage(),
				throwable);
	}

	@Override
	public WMMException createException(int errorCode, String message) {

		return new WMMException(errorCode, message, 0);
	}

	@Override
	public WMMException createException(int errorCode, String message, Throwable throwable) {

		return new WMMException(errorCode, message, throwable, 0);
	}

	@Override
	public WMMException createException(int errorCode, String message, int status) {

		return new WMMException(errorCode, message, status);
	}
}
