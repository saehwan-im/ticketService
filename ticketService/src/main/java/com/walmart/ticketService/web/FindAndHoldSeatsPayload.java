package com.walmart.ticketService.web;

/**
 * @author Saehwan Im
 *Payload class that holds the request parameter for findAndHoldSeats
 */
public class FindAndHoldSeatsPayload {
	private int numSeats;
	private String customerEmail;

	public FindAndHoldSeatsPayload() {
		numSeats = -1;
		customerEmail = null;
	}

	public FindAndHoldSeatsPayload(int numSeats, String customerEmail) {
		this.numSeats = numSeats;
		this.customerEmail = customerEmail;
	}

	public int getNumSeats() {
		return numSeats;
	}

	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
}
