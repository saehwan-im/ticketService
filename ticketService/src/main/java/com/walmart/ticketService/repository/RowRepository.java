package com.walmart.ticketService.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.walmart.ticketService.configuration.TicketServiceConfiguration;
import com.walmart.ticketService.model.Row;
import com.walmart.ticketService.model.Seat;

/**
 * @author Saehwan Im
 *Row repository for accessing and initializing rows of the venue
 */
@Repository
public class RowRepository {
	
	@Autowired
	private TicketServiceConfiguration ticketServiceConfiguration;
	
	private Row[] rows;
	
	public RowRepository() {
		
	}
	
	@PostConstruct
    public void init() {
		if(rows == null) {
			rows = new Row[ticketServiceConfiguration.getNumberOfRows()];
			for(int i=0; i<ticketServiceConfiguration.getNumberOfRows(); i++) {
				this.rows[i] = new Row(ticketServiceConfiguration.getNumberOfColumns());
			}
		}
    }
	
	public Row get(int i) {
		return rows[i];
	}
	
	public Row[] findAll() {
		return rows;
	}
}