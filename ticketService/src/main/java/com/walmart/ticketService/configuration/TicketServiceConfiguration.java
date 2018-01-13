package com.walmart.ticketService.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

/**
 * @author Saehwan Im
 *Annotation based configuration class
 */
@Configuration
public class TicketServiceConfiguration extends SpringBootServletInitializer {

	public TicketServiceConfiguration() {
	}

	@Value("${number.of.rows:10}")
	private int numberOfRows;
	
	@Value("${number.of.columns:10}")
	private int numberOfColumns;
	
	//in milliseconds
	@Value("${seat.hold.expire.time:10000}")
	private int seatHoldExpireTime;

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	public int getSeatHoldExpireTime() {
		return seatHoldExpireTime;
	}



	
}
