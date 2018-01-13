package com.walmart.ticketService.exception;


/**
 * @author Saehwan Im
 *thrown when data is changed before saving
 */
public class ConflictException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public ConflictException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public ConflictException() {
		super();
	}
}
