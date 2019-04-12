package com.morrisons.wholesale.dsd.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExceptionConfigDetails {

	private int responseCode;

	private int errorCode;

	private String errorMessage;

	private String snsMessage;

	private String snsSubject;

	private boolean isForSNSPublishing;

	public String getSnsMessage() {
		return snsMessage;
	}

	public void setSnsMessage(String snsMessage) {
		this.snsMessage = snsMessage;
	}

	public String getSnsSubject() {
		return snsSubject;
	}

	public void setSnsSubject(String snsSubject) {
		this.snsSubject = snsSubject;
	}

	@JsonProperty("isForSNSPublishing")
	public boolean isForSNSPublishing() {
		return isForSNSPublishing;
	}

	public void setForSNSPublishing(boolean isForSNSPublishing) {
		this.isForSNSPublishing = isForSNSPublishing;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "{" + "\n\tresponseCode : " + responseCode + "\n\terrorCode : " + errorCode + "\n\terrorMessage : "
				+ errorMessage + "\n\tsnsMessage : " + snsMessage + "\n\tsnsSubject : " + snsSubject
				+ "\n\tisForSNSPublishing : " + isForSNSPublishing + "\n}";
	}
}
