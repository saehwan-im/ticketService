package com.walmart.ticketService.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.walmart.ticketService.model.Row;
import com.walmart.ticketService.model.SeatHold;

/**
 * @author Saehwan Im
 *Seat hold repository for CRUD operation
 */
@Repository
public class SeatHoldRepository {
	
	private List<SeatHold> seatHolds;
	
	public SeatHoldRepository() {
		
	}
	
	@PostConstruct
    public void init() {
		if(seatHolds == null) {
			this.seatHolds = new CopyOnWriteArrayList<SeatHold>();
		}
    }
	
	public SeatHold get(Integer id){
		Optional<SeatHold> seatHold = this.seatHolds.stream()
	    .filter(p -> ((Integer)p.getId()).equals(id))
	    .findFirst();
	    if(seatHold.isPresent())
			return seatHold.get();
		else
			return null;
	}
	

	public List<SeatHold>	findAll(){
		return this.seatHolds;
	}

	
	public synchronized SeatHold save(SeatHold seatHold){
		this.seatHolds.add(seatHold);
		return seatHold;
	}

	public synchronized List<SeatHold> saveAll(List<SeatHold> seatHolds){
		this.seatHolds.addAll(seatHolds);
		return seatHolds;
	}
	
	public void delete(SeatHold seatHold) {
		this.seatHolds.remove(seatHold);
	}
	

}
