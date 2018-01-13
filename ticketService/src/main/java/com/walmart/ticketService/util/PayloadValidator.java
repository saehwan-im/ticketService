package com.walmart.ticketService.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.walmart.ticketService.configuration.TicketServiceConfiguration;
import com.walmart.ticketService.exception.InvalidParameterException;
import com.walmart.ticketService.exception.NoSeatException;
import com.walmart.ticketService.exception.NoSuchSeatHoldException;
import com.walmart.ticketService.model.*;
import com.walmart.ticketService.web.FindAndHoldSeatsPayload;
import com.walmart.ticketService.web.ReserveSeatsPayload;
import com.walmart.ticketService.web.TicketServiceController;

/**
 * @author Saehwan Im
 *Validator class for validating the request payloads
 */
@Component
public class PayloadValidator {
	
	private static final Logger logger = LoggerFactory.getLogger(PayloadValidator.class);
	
	private static final String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public PayloadValidator() {

	}
	
	public void validateFindAndHoldSeatsPayload(FindAndHoldSeatsPayload findAndHoldSeatsPayload){
		
		if (findAndHoldSeatsPayload.getNumSeats() < 1){
			logger.warn("numSeats needs to be greater than 0. numSeats passed :" + findAndHoldSeatsPayload.getNumSeats());
			throw new InvalidParameterException("numSeats needs to be greater than 0");
		}
		if (findAndHoldSeatsPayload.getCustomerEmail() == null
				|| !findAndHoldSeatsPayload.getCustomerEmail().matches(emailRegex)) {
			logger.warn("customerEmail is in invalid format. customerEmail passed :" + findAndHoldSeatsPayload.getCustomerEmail());
			throw new InvalidParameterException("customerEmail is in invalid format");
		}
	}
	
	public void validateReserveSeatsPayload(ReserveSeatsPayload reserveSeatsPayload){
		if (reserveSeatsPayload.getSeatHoldId() < 0){
			logger.warn("seatHoldId needs to be 0 or above. seatHoldId passed :" + reserveSeatsPayload.getSeatHoldId());
			throw new InvalidParameterException("seatId needs to be 0 or above");
		}
		if (reserveSeatsPayload.getCustomerEmail() == null
				|| !reserveSeatsPayload.getCustomerEmail().matches(emailRegex)) {
			logger.warn("customerEmail is in invalid format. customerEmail passed :" + reserveSeatsPayload.getCustomerEmail());
			throw new InvalidParameterException("customerEmail is in invalid format");
		}
		if (reserveSeatsPayload.getSeatHoldId() < 0){
			logger.warn("seatHoldId needs to be 0 or above. seatHoldId passed :" + reserveSeatsPayload.getSeatHoldId());
			throw new InvalidParameterException("numSeats needs to be 0 or above");
		}
		//only for testing if seat hold id exists or not
		if (reserveSeatsPayload.getSeatHoldId() >= 999) {
			logger.warn("seat hold does not exist. seatHoldId passed :" + reserveSeatsPayload.getSeatHoldId());
			throw new NoSuchSeatHoldException("seat hold does not exist");
		}
	}

}