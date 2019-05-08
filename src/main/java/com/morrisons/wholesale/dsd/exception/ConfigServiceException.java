package com.morrisons.wholesale.dsd.exception;

/**
 * The Class WMMException.
 */
public class ConfigServiceException extends RuntimeException {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -9048962894147389848L;

	public static final int DEFAULT_HTTP_STATUS_CODE = 0;

	/**
	 * The error code.
	 */
	private final int errorCode;

	/**
	 * The http status code.
	 */
	private final int httpStatusCode;

	/**
	 * Instantiates a new exception.
	 *
	 * @param errorCode
	 *            the error code
	 * @param message
	 *            the message
	 */
	public ConfigServiceException(int errorCode, String message, int httpStatusCode) {
		this(errorCode, message, null, httpStatusCode);
	}

	/**
	 * Instantiates a new exception.
	 *
	 * @param errorCode
	 *            the error code
	 * @param message
	 *            the message
	 * @param throwable
	 *            the throwable
	 */
	public ConfigServiceException(int errorCode, String message, Throwable throwable, int httpStatusCode) {
		super(message, throwable);
		this.errorCode = errorCode;
		this.httpStatusCode = httpStatusCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}
}
