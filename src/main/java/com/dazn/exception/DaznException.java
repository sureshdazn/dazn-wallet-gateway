package com.dazn.exception;

import java.util.concurrent.ExecutionException;

public class DaznException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public DaznException(String category, String message, Exception e) {
		
		super(e);
		
		if(e instanceof InterruptedException) {
			// Interrupted exception handling
		} else if(e instanceof ExecutionException) {
			// Execution exception handling
		} else if(e instanceof NullPointerException) {
			// Null pointer exception handling
		} else {
			// remaining exceptions handling
		}
		
	}
	
}
