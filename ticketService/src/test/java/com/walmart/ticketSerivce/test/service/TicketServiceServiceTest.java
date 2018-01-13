package com.walmart.ticketSerivce.test.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.walmart.ticketService.configuration.TicketServiceConfiguration;
import com.walmart.ticketService.exception.NoSeatException;
import com.walmart.ticketService.exception.NoSuchSeatHoldException;
import com.walmart.ticketService.model.*;
import com.walmart.ticketService.repository.*;
import com.walmart.ticketService.service.TicketServiceImpl;

/**
 * @author Saehwan Im
 *Test class that tests the service layer
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TicketServiceServiceTest {
	
	@Mock
	private RowRepository rowRepository;
	
	@Mock
	private SeatHoldRepository seatHoldRepository;
	
	@Mock
	private SeatReservationRepository seatReservationRepository;
	
	@Mock
	private TicketServiceConfiguration ticketServiceConfiguration;
	
	@InjectMocks
	private TicketServiceImpl ticketService;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void verifyNumSeatsAvailable(){
		Row[] rows = new Row[10];
		for(int i=0; i<10;i++)
		{
			rows[i] = new Row(10);
		}

		when(rowRepository.findAll()).thenReturn(rows);
		Integer result = ticketService.numSeatsAvailable();
		assertEquals(new Integer(100), result);
	}
	
	@Test
	public void verifyGetSeatHold(){
		SeatHold seatHold = new SeatHold(5, "testGetSeatHoldById@test.ticketService");
		when(seatHoldRepository.get(0)).thenReturn(seatHold);
		SeatHold result = seatHoldRepository.get(0);
		assertEquals("testGetSeatHoldById@test.ticketService", result.getCustomerEmail());
		assertEquals(5, result.getNumSeats());
	}
	
	@Test
	public void verifySaveSeatHold(){
		SeatHold seatHold = new SeatHold(3,"saveSeatHold@test.ticketService");
		when(seatHoldRepository.save(seatHold)).thenReturn(seatHold);
		SeatHold result = seatHoldRepository.save(seatHold);
		assertEquals("saveSeatHold@test.ticketService", result.getCustomerEmail());
		assertEquals(3, result.getNumSeats());
	}
	
	@Test
	public void verifyRemoveSeatHold(){
		SeatHold seatHold = new SeatHold(4,"removeToDo@test.ticketService");
		seatHoldRepository.delete(seatHold);
        verify(seatHoldRepository, times(1)).delete(seatHold);
	}


	@Test(expected = NoSuchSeatHoldException.class)
	public void verifyReserveSeats() {
		assertEquals("test", ticketService.reserveSeats(1, "test@test.ticketService"));
	}
	
	@Test(expected = NoSuchSeatHoldException.class)
	public void verifyFailedReserveSeats() {
		assertEquals("test", ticketService.reserveSeats(100, "test@test.ticketService"));
	}

	@Test(expected = NoSuchSeatHoldException.class)
	public void verifyFailedReserveSeats2() {
		ticketService.reserveSeats(100, "test@test.ticketService");
	}

}
