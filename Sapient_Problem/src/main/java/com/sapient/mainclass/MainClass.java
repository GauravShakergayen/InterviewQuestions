package com.sapient.mainclass;

import java.time.LocalDateTime;
import java.time.Month;

import com.sapient.model.Card;
import com.sapient.model.Customer;
import com.sapient.model.Station;
import com.sapient.service.MetroService;

public class MainClass {
	static MetroService metroService = new MetroService();
	static Card card = new Card();

	public static void main(String[] args) {
		
		card.setCardId(123);
		card.setAmountAvailable(500);
		card.setCustomer(new Customer(1, "Gaurav Shakergayen"));
		
		metroService.swipeIn(card, Station.A7,
				LocalDateTime.of(2016, Month.JUNE, 24, 12, 40));
		metroService.swipeOut(card, Station.A2,
				LocalDateTime.of(2016, Month.JUNE, 24, 12, 55));
		
		metroService.calculateStationFall(Station.A7);
	}
}
