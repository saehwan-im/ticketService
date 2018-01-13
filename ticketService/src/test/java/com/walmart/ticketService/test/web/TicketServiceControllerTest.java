package com.walmart.ticketService.test.web;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.walmart.ticketService.TicketServiceApplication;
import com.walmart.ticketService.service.TicketServiceImpl;

/**
 * @author Saehwan Im
 *Test class that tests the web layer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TicketServiceApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TicketServiceControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext wac;

	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void verifyNumSeatsAvailable() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/numSeatsAvailable").accept(MediaType.APPLICATION_JSON))
			.andDo(print());
	}
	
	@Test
	public void testNumSeatsAvailable() throws Exception {
		mockMvc.perform(get("/numSeatsAvailable")).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void verifyFindAndHoldSeats() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/findAndHoldSeats/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"numSeats\" : \"5\", \"customerEmail\" : \"verifyFindAndHoldSeats@test.ticketService\" }")
		.accept(MediaType.APPLICATION_JSON))
		.andDo(print());
	}
	
	@Test
	public void verifyMalformedFindAndHoldSeats() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/findAndHoldSeats/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"numSeats\" : \"5\", \"customerEmail\" : \"verifyMalformedFindAndHoldSeatstest.ticketService\" }")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.errorCode").value(400))
		.andExpect(jsonPath("$.message").value("customerEmail is in invalid format"))
		.andDo(print());
	}
	
	@Test
	public void testFindAndHoldSeats() throws Exception {
		String body = "{\"numSeats\": 1,\"customerEmail\":\"test@test.ticketService\"}";
		mockMvc.perform(post("/findAndHoldSeats").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testFailedFindAndHoldSeats() throws Exception {
		String body = "{\"numSeats\": 100000000,\"customerEmail\":\"test@test.ticketService\"}";
		try {
		mockMvc.perform(post("/findAndHoldSeats").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isNotFound()).andReturn();
		}catch(Exception E){
			E.printStackTrace();
		}
	}
	@Test
	public void verifyReserveSeats() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/reserveSeats/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"seatHoldId\": \"0\", \"customerEmail\" : \"verifyReserveSeats@test.ticketService\"}")
        .accept(MediaType.APPLICATION_JSON))
		.andDo(print());
	}
	
	@Test
	public void verifyInvalidReserveSeats() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/reserveSeats/")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"seatHoldId\": \"999\", \"customerEmail\" : \"verifyInvalidReserveSeats@test.ticketService\"}")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.errorCode").value(404))
		.andExpect(jsonPath("$.message").value("seat hold does not exist"))
		.andDo(print());
	}


	@Test
	public void testFailedReserveSeats() throws Exception {
		String body = "{\"seatHoldId\": -100,\"customerEmail\":\"test@test.ticketService\"}";
		mockMvc.perform(post("/reserveSeats").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isBadRequest()).andReturn();
	}

	@Test
	public void testFailedReserveSeats2() throws Exception {
		String body = "{\"seatHoldId\": 0,\"customerEmail\":\"test@test.ticketService\"}";
		mockMvc.perform(post("/reserveSeats").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isNotFound()).andReturn();
	}
}