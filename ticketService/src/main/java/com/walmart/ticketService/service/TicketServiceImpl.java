package com.walmart.ticketService.service;

import java.awt.Point;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.walmart.ticketService.configuration.TicketServiceConfiguration;
import com.walmart.ticketService.exception.ConflictException;
import com.walmart.ticketService.exception.ExpiredSeatHoldException;
import com.walmart.ticketService.exception.NoSeatException;
import com.walmart.ticketService.exception.NoSuchSeatHoldException;
import com.walmart.ticketService.exception.UnmatchingEmailException;
import com.walmart.ticketService.model.*;
import com.walmart.ticketService.repository.*;

/**
 * @author Saehwan Im
 *Ticket Service Implementation
 */
@Service("ticketService")
public class TicketServiceImpl implements TicketService {
	
	@Autowired
	private TicketServiceConfiguration ticketServiceConfiguration;
	
	@Autowired
	private RowRepository rowRepository;
	
	@Autowired
	private SeatHoldRepository seatHoldRepository;
	
	@Autowired
	private SeatReservationRepository seatReservationRepository;
	private int rowSpan;
	private int colSpan;

	public TicketServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public TicketServiceImpl(int rowSpan, int colSpan) {
		// TODO Auto-generated constructor stub
		this.rowSpan = rowSpan;
		this.colSpan = colSpan;
	}
	
	@PostConstruct
	public void init() {
		if(this.rowSpan ==0)
			this.rowSpan = ticketServiceConfiguration.getNumberOfRows();		
		if(this.colSpan ==0)
			this.colSpan = ticketServiceConfiguration.getNumberOfColumns();
	}
	

	/**
	 * The number of seats in the venue that are neither held nor reserved
	 *
	 * @return the number of tickets available in the venue
	 */
	@Override
	public int numSeatsAvailable() {
		/*
		 * get all Seat where available = true
		 */
		this.resetExpiredSeatHolds();
		List<Seat> availableSeats = Arrays.stream(rowRepository.findAll())
		.flatMap(x -> Arrays.stream(x.getSeats()))
        .filter(x -> x.isAvailable() == true)
        .collect(Collectors.toList());
		
		return availableSeats.size();
	}
	

	/**
	 * Find and hold the best available seats for a customer
	 *
	 * @param numSeats
	 *            the number of seats to find and hold
	 * @param customerEmail
	 *            unique identifier for the customer
	 * @return a SeatHold object identifying the specific seats and related
	 *         information
	 */
	@Override
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		int numAvailableSeats = this.numSeatsAvailable();
		if(numAvailableSeats < numSeats) {
			throw new NoSeatException("Not enough seats in the venue. Number of available seats is: "+numAvailableSeats);
		}
		SeatHold seatHold = new SeatHold();
		seatHold.setNumSeats(numSeats);
		seatHold.setCustomerEmail(customerEmail);
		
		int numSeatsRemainder = numSeats;
		int num = colSpan < numSeatsRemainder ? colSpan : numSeatsRemainder;
		for (int i = 0; i < rowSpan; i++) {
			if(numSeatsRemainder == 0)
				break;
			try {
				while(!canHoldCurrentNum(num))
					num--;
				while(numSeatsRemainder > 0) {
					num = numSeatsRemainder < num ? numSeatsRemainder :num;
					Point startToEnd = rowRepository.get(i).holdSeats(num);
					if (startToEnd != null){
						numSeatsRemainder -= num;
						seatHold = updateSeatHold(i, startToEnd, seatHold);
					}else if (startToEnd == null) {
						break;
					}
				}
			} catch (ConflictException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//retry the same row if enough consecutive seats available
				if(rowRepository.get(i).canHoldSeats(numSeats)) {
					i--;
				}
				continue;
			}
		}
		if(numSeatsRemainder != 0) {
			
			throw new NoSeatException("Not enough seats in the venue. Number of available seats is: "+numAvailableSeats);
		}
		seatHold.setExpiration(Instant.now().toEpochMilli() + ticketServiceConfiguration.getSeatHoldExpireTime());
		seatHoldRepository.save(seatHold);
		return seatHold;
	}

	/**
	 * Commit seats held for a specific customer
	 *
	 * @param seatHoldId
	 *            the seat hold identifier
	 * @param customerEmail
	 *            the email address of the customer to which the seat hold is
	 *            assigned
	 * @return a reservation confirmation code
	 */
	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {

		SeatHold seatHold = seatHoldRepository.get(seatHoldId);
		if(seatHold == null) {
			throw new NoSuchSeatHoldException("No seat hold with ID " +seatHoldId+" was found");
		}else if (!seatHold.getCustomerEmail().equals(customerEmail)){
			throw new UnmatchingEmailException("Provided email "+customerEmail+" does not match the customerEmail for the seat hold with ID "+seatHoldId);
		}
		if(seatHold.getExpiration() < Instant.now().toEpochMilli()) {
			this.resetExpiredSeatHolds();
			throw new ExpiredSeatHoldException("Requested seat hold is expired");
		}
		String confirmationCodeInput = seatHoldId + customerEmail + Instant.now().toEpochMilli();
		String confirmationCode = Hashing.sha256().hashString(confirmationCodeInput, StandardCharsets.UTF_8).toString();
		SeatReservation seatReservation = new SeatReservation(seatHoldRepository.get(seatHoldId), confirmationCode);
		seatReservationRepository.save(seatReservation);
		seatHoldRepository.delete(seatHold);

		return confirmationCode;
	}
	
	/**Method that checks all seat holds' expiration time and make the seats available if expired
	 * */
	private void resetExpiredSeatHolds() {
		for(SeatHold seatHold : seatHoldRepository.findAll()) {
			if(seatHold.getExpiration() < Instant.now().toEpochMilli()) {
				Map<Integer, List<Point>> map = seatHold.getMap();
				for(Integer k: map.keySet()) {
					for(Point p: map.get(k)) {
						while(p.x <= p.y) {
							this.rowRepository.get(k).getSeats()[p.x].setAvailable(true);
							p.x++;
						}
					}
				}
				seatHoldRepository.delete(seatHold);
			}
		}
	}
	
	/**Method that updates the SeatHold argument and returns back
	 * */	
	private SeatHold updateSeatHold(int rowNum, Point startToEnd, SeatHold seatHold) {
		Map<Integer, List<Point>> map = seatHold.getMap();
		if(map == null) {
			map = new HashMap<Integer, List<Point>>();
			List<Point> startToEndList = new ArrayList<>();
			startToEndList.add(startToEnd);
			map.put(rowNum,startToEndList);
			seatHold.setMap(map);
		}
		else if(map != null && map.get(rowNum) == null) {
			List<Point> startToEndList = new ArrayList<>();
			startToEndList.add(startToEnd);
			map.put(rowNum,startToEndList);
			seatHold.setMap(map);
		}
		else if(map.get(rowNum) != null) {
			map.get(rowNum).add(startToEnd);
			seatHold.setMap(map);
		}
		return seatHold;
	}
	
	/**Method that checks if there is any row that enough available seats for the argument number.
	 * */	
	private boolean canHoldCurrentNum(int num) {
		for(int j=0; j<rowSpan; j++) {
			if(rowRepository.get(j).canHoldSeats(num)) 
				return true;
		}
		return false;
	}
}
