package com.walmart.ticketService.exception;

/**
 * @author Saehwan Im
 *thrown when the trying to reserve a expired seat hold
 */
public class ExpiredSeatHoldException extends RuntimeException{


	private static final long serialVersionUID = 6300593009258109980L;
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public ExpiredSeatHoldException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public ExpiredSeatHoldException() {
		super();
	}
}
