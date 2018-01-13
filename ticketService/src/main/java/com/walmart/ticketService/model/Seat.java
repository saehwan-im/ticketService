package com.walmart.ticketService.model;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author Saehwan Im
 *Seat in the venue
 */
public class Seat{
	
	private boolean isAvailable = true;
	

	public Seat() {
		super();
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
}