package com.walmart.ticketService.exception;

/**
 * @author Saehwan Im
 *thrown when trying to reserve a seat hold that does not exist
 */
public class NoSuchSeatHoldException extends RuntimeException{

	private static final long serialVersionUID = -8805959788804772841L;
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public NoSuchSeatHoldException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public NoSuchSeatHoldException() {
		super();
	}
}
