package com.flyme.thirdsdk.easemob.models;

public class Error {
	private String error;
	private String timestamp;
	private String duration;
	private String exception;
	private String error_description;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getError_description() {
		return error_description;
	}

	public void setError_description(String error_description) {
		this.error_description = error_description;
	}
}
