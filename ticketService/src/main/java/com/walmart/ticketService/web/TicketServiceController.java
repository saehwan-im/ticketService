package com.walmart.ticketService.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.walmart.ticketService.web.FindAndHoldSeatsPayload;
import com.walmart.ticketService.web.ReserveSeatsPayload;
import com.walmart.ticketService.configuration.TicketServiceConfiguration;
import com.walmart.ticketService.exception.*;
import com.walmart.ticketService.model.*;
import com.walmart.ticketService.service.TicketService;
import com.walmart.ticketService.util.PayloadValidator;

/**
 * @author Saehwan Im
 *Controller class that delegates the request to the service class and returns the response
 */
@RestController
public class TicketServiceController {
	
	private static final Logger logger = LoggerFactory.getLogger(TicketServiceController.class);
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private PayloadValidator payloadValidator;
	
	@Autowired
	private TicketServiceConfiguration ticketServiceConfiguration;

	@RequestMapping(value="/numSeatsAvailable", method=RequestMethod.GET)
	public ResponseEntity<Integer> numSeatsAvailable(){
    	logger.info("Returning the number of seats available");
		return new ResponseEntity<Integer>(ticketService.numSeatsAvailable(), HttpStatus.OK);
	}
	
    @RequestMapping(value = "/findAndHoldSeats", method = RequestMethod.POST)
   	public ResponseEntity<SeatHold> findAndHoldSeats(@RequestBody FindAndHoldSeatsPayload findAndHoldSeatsPayload ) {
    	int numSeats = findAndHoldSeatsPayload.getNumSeats();
    	String customerEmail = findAndHoldSeatsPayload.getCustomerEmail();
    	
    	logger.info("findAndHoldSeats Payload: numSeats: " + numSeats + ", customerEmail: " + customerEmail);
    	payloadValidator.validateFindAndHoldSeatsPayload(findAndHoldSeatsPayload);
		if (findAndHoldSeatsPayload.getNumSeats() > ticketServiceConfiguration.getNumberOfRows() * ticketServiceConfiguration.getNumberOfColumns()) {
			logger.warn("not enough seats available. requested numSeat :"+findAndHoldSeatsPayload.getNumSeats());
			throw new NoSeatException("not enough seats available");
		}
		return new ResponseEntity<SeatHold>(ticketService.findAndHoldSeats(numSeats, customerEmail), HttpStatus.OK);
   	}
	
    @RequestMapping(value = "/reserveSeats", method = RequestMethod.POST)
   	public ResponseEntity<String> reserveSeats(@RequestBody ReserveSeatsPayload reserveSeatsPayload){
    	int seatHoldId = reserveSeatsPayload.getSeatHoldId();
    	String customerEmail = reserveSeatsPayload.getCustomerEmail();
    	logger.info("reserveSeats Payload: seatHoldId: " + seatHoldId + ", customerEmail: " + customerEmail);
    	payloadValidator.validateReserveSeatsPayload(reserveSeatsPayload);
		return new ResponseEntity<String>(ticketService.reserveSeats(seatHoldId, customerEmail), HttpStatus.OK);
   	}
	
}
