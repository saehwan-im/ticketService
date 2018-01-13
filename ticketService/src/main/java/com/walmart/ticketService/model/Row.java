package com.walmart.ticketService.model;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.walmart.ticketService.configuration.TicketServiceConfiguration;
import com.walmart.ticketService.exception.ConflictException;

/**
 * @author Saehwan Im
 *A row of the venue
 */
@Component
public class Row {
	
	private Seat[] seats;
	
	private int numberOfColumns;
	
	public Row() {
	}
	
	public Row(int numColumns) {
		if(seats == null) {
			this.numberOfColumns = numColumns;
			seats = new Seat[numColumns];
			for(int i=0; i<numColumns; i++) {
				this.seats[i] = new Seat();
			}
		}
	}
	
	public Seat[] getSeats() {
		return seats;
	}

	public Point holdSeats(int numSeats) throws ConflictException {
		for (int i = 0; i <= numberOfColumns - numSeats; i++) {
			if (checkAvailableAll(i, numSeats)) {
				Point startToEnd = new Point(i, i+numSeats-1);
				setSeats(i, numSeats);
				return startToEnd;
			}
		}
		return null;
	}

	public synchronized void setSeats(int start, int numSeats) throws ConflictException {
		if (checkAvailableAll(start, numSeats)) {
			for (int i = start; i < start + numSeats; i++) {
				seats[i].setAvailable(false);
			}
		}else {
			throw new ConflictException("There was a conflict that could possibly cause duplicate SeatHold");//retry on the current row
		}
	}

	public boolean checkAvailableAll(int start, int numSeats) {
		for (int i = start; i < start + numSeats; i++) {
			if (this.seats[i].isAvailable() == false) {
				return false;
			}
		}
		return true;
	}
	
	public boolean canHoldSeats(int numSeats){
		for (int i = 0; i < numberOfColumns+1 - numSeats; i++) {
			if (checkAvailableAll(i, numSeats)) {
				return true;
			}
		}
		return false;
	}

}
