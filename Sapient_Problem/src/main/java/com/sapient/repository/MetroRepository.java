package com.sapient.repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sapient.model.Card;
import com.sapient.model.Journey;

public class MetroRepository {
	private Map<Card, Journey> singleJourney = new HashMap<Card, Journey>();
	private Map<Card, List<Journey>> allJourneys = new HashMap<Card, List<Journey>>();

	public Journey getSingleJourney(Card card) {
		return singleJourney.remove(card);
	}

	public void addAllJourney(Card card,Journey journey) {
		allJourneys.put(card, new LinkedList<Journey>());
		allJourneys.get(card).add(journey);
	}

	public void addSingleJourney(Card card, Journey journey) {
		singleJourney.put(card, journey);
	}
	
}
