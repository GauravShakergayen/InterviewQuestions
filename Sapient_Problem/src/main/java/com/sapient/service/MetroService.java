package com.sapient.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.sapient.exception.MinimumBalanceForSwipeInException;
import com.sapient.exception.MinimumBalanceForSwipeOutException;
import com.sapient.model.Card;
import com.sapient.model.Journey;
import com.sapient.model.Station;
import com.sapient.repository.MetroRepository;

public class MetroService {
	
	private Map<Station, Integer> stationFootFall = new HashMap<Station, Integer>();
	private MetroRepository metroRepository = new MetroRepository();
	

	public void swipeIn(Card card, Station source, LocalDateTime startTime) {
		System.out.println("******Swipe In ******");
		System.out.println("Card " + card.getCardId() + " swiped in"
				+ " at station " + source + " with balance  "
				+ card.getAmountAvailable());
		if (card.getAmountAvailable() < 5.5) {
			System.out
					.println("******MinimumBalanceForSwipeInException occured*****");
			throw new MinimumBalanceForSwipeInException(
					"Sorry !! Minimum balance 5.5 is required for swipe in.");
		}

		int count = stationFootFall.containsKey(source) ? stationFootFall
				.get(source) : 0;
		stationFootFall.put(source, count + 1);

		Journey journey = new Journey();
		journey.setSource(source);
		journey.setCard(card);
		journey.setStartTime(startTime);
		metroRepository.addSingleJourney(card, journey);
	}

	public void swipeOut(Card card, Station destination, LocalDateTime endTime) {
		System.out.println("******Swipe Out******");
		int count = stationFootFall.containsKey(destination) ? stationFootFall
				.get(destination) : 0;
		stationFootFall.put(destination, count + 1);

		Journey journey = metroRepository.getSingleJourney(card);
		journey.setDestination(destination);
		journey.setDistance(destination.distance(journey.getSource()));
		journey.setFare(getFare(journey.getSource(),
				journey.getDestination(), endTime));
		journey.setEndTime(endTime);
		

		if (journey.getFare() > card.getAmountAvailable()) {
			System.out
					.println("******MinimumBalanceForSwipeOutException occured*****");
			throw new MinimumBalanceForSwipeOutException(
					"Sorry !! You do not have sufficient balance for swipe out.");
		}
		System.out.println("******Calculating Remaining Balance******");
		journey.setBalance(card.getAmountAvailable() - journey.getFare());
		card.setAmountAvailable(journey.getBalance());
		System.out.println("Card " + card.getCardId() + " swiped out"
				+ " at station " + destination + " total fare is:"
				+ journey.getFare() + " And remaining balance is "
				+ card.getAmountAvailable());
		metroRepository.addAllJourney(card, journey);

	}

	public int calculateStationFall(Station station) {
		System.out.println("******Calculating Station short fall******");
		System.out.println("Station short fall at station " + station + " is "
				+ stationFootFall.getOrDefault(station, new Integer(0)));
		return stationFootFall.getOrDefault(station, new Integer(0));
	}
	
	private double getFare(Station source, Station destination,
			LocalDateTime date) {
		double fare = 0.0;
		int distance = source.distance(destination);

		if (date.getDayOfWeek() == DayOfWeek.SATURDAY
				|| date.getDayOfWeek() == DayOfWeek.SUNDAY) {
			fare = distance * 5.5;

		} else {
			fare = distance * 7;
		}
		return fare;
	}

}
