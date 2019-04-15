package com.morrisons.wholesale.dsd.exception;

public final class ErrorCodes {

	/*
	 * Exceptions created without Exception Factory
	 */

	public static final Integer GET_DSD_ORDERS_ERR = 603;
	
	public static final Integer CONNECTION_REFUSED_ERR = 407; 
	
	public static final Integer EXCEPTIONS_FILE_LOADING_ERR = 404;
	
	public static final Integer THREAD_EXECUTOR_ERR = 405;
	
	public static final Integer THREAD_EXECUTOR_FUTURES_ERR = 406;
	
	private ErrorCodes() {
		// Added a private constructor to hide the implicit public one.
	}
}