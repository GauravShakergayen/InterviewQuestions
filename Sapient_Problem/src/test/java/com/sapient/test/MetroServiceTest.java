package com.sapient.test;

import java.time.LocalDateTime;
import java.time.Month;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.sapient.exception.MinimumBalanceForSwipeInException;
import com.sapient.exception.MinimumBalanceForSwipeOutException;
import com.sapient.model.Card;
import com.sapient.model.Customer;
import com.sapient.model.Station;
import com.sapient.service.MetroService;

@SuppressWarnings("deprecation")
public class MetroServiceTest {
	private MetroService metroService = new MetroService();
	private Card card = new Card();

	@Before
	public void setUp() {
		card.setCardId(123);
		card.setAmountAvailable(500);
		card.setCustomer(new Customer(1, "Gaurav Shakergayen"));
	}

	@Test
	public void getFootFallForStation() {

		metroService.swipeIn(card, Station.A2,
				LocalDateTime.of(2016, Month.JUNE, 24, 12, 40));
		metroService.swipeOut(card, Station.A7,
				LocalDateTime.of(2016, Month.JUNE, 24, 12, 55));

		metroService.swipeIn(card, Station.A7,
				LocalDateTime.of(2016, Month.JUNE, 25, 20, 40));
		metroService.swipeOut(card, Station.A10,
				LocalDateTime.of(2016, Month.JUNE, 25, 20, 55));

		Assert.assertEquals(2, metroService.calculateStationFall(Station.A7));
		

	}

	@Test(expected = MinimumBalanceForSwipeInException.class)
	public void testMinimumBalanceForSwipeInException() {
		card.setAmountAvailable(5);
		metroService.swipeIn(card, Station.A2,
				LocalDateTime.of(2016, Month.JUNE, 25, 20, 55));
	}

	@Test(expected = MinimumBalanceForSwipeOutException.class)
	public void testMinimumBalanceForSwipeOutException() {
		card.setAmountAvailable(25);
		metroService.swipeIn(card, Station.A10,
				LocalDateTime.of(2016, Month.JUNE, 25, 20, 00));
		metroService.swipeOut(card, Station.A2,
				LocalDateTime.of(2016, Month.JUNE, 25, 20, 55));
		
	}

}
