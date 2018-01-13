package com.walmart.ticketService.exception;

 
/**
 * @author Saehwan Im
 * thrown when the request parameter is invalid
 */
public class InvalidParameterException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public InvalidParameterException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public InvalidParameterException() {
		super();
	}
}
