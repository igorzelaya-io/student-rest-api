package com.example.exception;

public class StudentNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7272303639193378954L;

	public StudentNotFoundException() {
		super();
	}
	
	public StudentNotFoundException(String message) {
		super(message);
	}
	
	public StudentNotFoundException(Throwable cause) {
		super(cause);
	}
	
	public StudentNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	
}
