package com.walmart.ticketService.exception;

/**
 * @author Saehwan Im
 *thrown when there are not enough seats available in the venue
 */
public class NoSeatException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3540852994035282586L;
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public NoSeatException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public NoSeatException() {
		super();
	}
}
