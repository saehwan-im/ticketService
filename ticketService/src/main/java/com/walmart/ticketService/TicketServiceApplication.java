package com.walmart.ticketService;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.walmart.ticketService.model.*;
import com.walmart.ticketService.repository.*;

@SpringBootApplication
public class TicketServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(TicketServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TicketServiceApplication.class, args);
	}
}
