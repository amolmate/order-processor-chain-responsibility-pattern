package com.morrisons.wholesale.dsd.exception;

import lombok.Data;

@Data
public class ExceptionConfigDetails {

	private int responseCode;

	private int errorCode;

	private String errorMessage;
}