package com.walmart.ticketService.model;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Saehwan Im
 *Seat reservation class
 */
public class SeatReservation {
	private static final AtomicInteger count = new AtomicInteger(-1); 
	private final int id;
	private int numSeats;
	private String customerEmail;
	private String confirmationCode;
	
	private Map<Integer, List<Point>> map;

	public SeatReservation() {
		super();
		id = count.incrementAndGet(); 
	}
	
	public SeatReservation(SeatHold seatHold) {
		super();
		id = count.incrementAndGet(); 
		this.numSeats = seatHold.getNumSeats();
		this.customerEmail = seatHold.getCustomerEmail();
		this.map = seatHold.getMap();
	}
	
	public SeatReservation(SeatHold seatHold, String confirmationCode) {
		super();
		id = count.incrementAndGet(); 
		this.numSeats = seatHold.getNumSeats();
		this.customerEmail = seatHold.getCustomerEmail();
		this.map = seatHold.getMap();
		this.confirmationCode = confirmationCode;
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


	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	public int getId() {
		return id;
	}

	

}