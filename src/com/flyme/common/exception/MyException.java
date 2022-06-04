package com.flyme.common.exception;

public class MyException extends Exception {
	private static final long serialVersionUID = 1L;
	protected Throwable throwable;

	public MyException(String message) {
		super(message);
	}

	public MyException(String message, Throwable throwable) {
		super(message);
		this.throwable = throwable;
	}

	public Throwable getCause() {
		return this.throwable;
	}
}