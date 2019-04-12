package com.morrisons.wholesale.dsd.exception;

public interface IWMMExceptionFactory {

	public WMMException createException(int errorCode);

	public WMMException createException(int errorCode, Throwable throwable);

	public WMMException createException(int errorCode, String message);

	public WMMException createException(int errorCode, String message, Throwable throwable);

	public WMMException createException(int errorCode, String message, int status);
}
