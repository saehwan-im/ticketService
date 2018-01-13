package com.walmart.ticketService.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.walmart.ticketService.model.SeatHold;
import com.walmart.ticketService.model.SeatReservation;

/**
 * @author Saehwan Im
 *Seat reservation repository for accessing and adding reservations
 */
@Repository
public class SeatReservationRepository {
	
	private List<SeatReservation> seatReservations;
	
	public SeatReservationRepository() {
		
	}
	
	@PostConstruct
    public void init() {
		if(seatReservations == null) {
			this.seatReservations = new ArrayList<SeatReservation>();
		}
    }
	
	
	public SeatReservation get(Integer id)
	{	
		Optional<SeatReservation> seatReservation = this.seatReservations.stream()
		    .filter(p -> ((Integer)p.getId()).equals(id))
		    .findFirst();
		    if(seatReservation.isPresent())
				return seatReservation.get();
			else
				return null;
	}
	

	public List<SeatReservation>	findAll(){
		return this.seatReservations;
	}

	
	public synchronized SeatReservation save(SeatReservation seatReservation){
		this.seatReservations.add(seatReservation);
		return seatReservation;
	}

	public synchronized List<SeatReservation> saveAll(List<SeatReservation> seatReservations){
		this.seatReservations.addAll(seatReservations);
		return seatReservations;
	}
	

}
