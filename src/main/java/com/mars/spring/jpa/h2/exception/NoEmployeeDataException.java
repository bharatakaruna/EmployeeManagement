package com.mars.spring.jpa.h2.exception;

public class NoEmployeeDataException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoEmployeeDataException(String s) {
		super(s);
	}
}
