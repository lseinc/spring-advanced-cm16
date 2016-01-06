package com.lse.spring.example.atm;

public class MissingAccountException extends RuntimeException {
	private static final long serialVersionUID = -1987012870480170329L;

	public MissingAccountException() {
		super();
	}
	public MissingAccountException(String message) {
		super(message);
	}
	public MissingAccountException(String message, Throwable cause) {
		super(message,cause);
	}
	public MissingAccountException(Throwable cause) {
		super(cause);
	}
	
}
