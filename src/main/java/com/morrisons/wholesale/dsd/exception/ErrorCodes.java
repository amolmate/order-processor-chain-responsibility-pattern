package com.morrisons.wholesale.dsd.exception;

public final class ErrorCodes {

	/*
	 * Exceptions created without Exception Factory
	 */

	public static final Integer GET_DSD_ORDERS_ERR = 601;
	
	public static final Integer UPDATE_CHANGED_ITEMS_ERR = 602;
	
	public static final Integer CONNECTION_REFUSED_ERR = 401; 
	
	public static final Integer EXCEPTIONS_FILE_LOADING_ERR = 404;
	
	public static final Integer THREAD_EXECUTOR_ERR = 403;
	
	public static final Integer THREAD_EXECUTOR_FUTURES_ERR = 402;
	
	private ErrorCodes() {
		// Added a private constructor to hide the implicit public one.
	}
}