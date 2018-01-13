package com.walmart.ticketService.web;

/**
 * @author Saehwan Im
 *Payload class that holds the request parameter for reserveSeatsPayload
 */
public class ReserveSeatsPayload {
	private int seatHoldId;
	private String customerEmail;

	public ReserveSeatsPayload() {
		seatHoldId = -1;
		customerEmail = null;
	}
	
	public int getSeatHoldId() {
		return seatHoldId;
	}

	public void setSeatHoldId(int seatHoldId) {
		this.seatHoldId = seatHoldId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
}
