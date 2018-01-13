package com.walmart.ticketService.exception;

/**
 * @author Saehwan Im
 *thrown when trying to reserve a seat hold where the customer email passed in does not match the email in the seat hold
 */
public class UnmatchingEmailException extends RuntimeException{

	private static final long serialVersionUID = 5951287153503311292L;
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public UnmatchingEmailException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public UnmatchingEmailException() {
		super();
	}
}
