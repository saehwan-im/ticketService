package com.walmart.ticketService.model;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Saehwan Im
 *Seat hold that lasts 10 seconds by default
 */
public class SeatHold {
	private static final AtomicInteger count = new AtomicInteger(-1); 
	private final int id;
	private int numSeats;
	private String customerEmail;
	private long expiration;
	private Map<Integer, List<Point>> map;
	

	
	public SeatHold() {
		super();
		id = count.incrementAndGet(); 
	}
	
	
	public SeatHold(int numSeats, String customerEmail) {
		super();
		id = count.incrementAndGet();
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


	public long getExpiration() {
		return expiration;
	}


	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}


	public Map<Integer, List<Point>> getMap() {
		return map;
	}


	public void setMap(Map<Integer, List<Point>> map) {
		this.map = map;
	}


	public int getId() {
		return id;
	}
	
	
}