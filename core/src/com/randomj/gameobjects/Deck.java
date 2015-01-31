package com.randomj.gameobjects;


import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

import com.randomj.gameobjects.Enums.CardType;

public class Deck {

	private ArrayList<Card> cards;

	public Deck() {}
	
	public Deck(ArrayList<Country> countries) {
		cards = new ArrayList<Card>();
		init(countries);		
	}
	
	public void init(ArrayList<Country> countries) {
		Set<CardType> types = EnumSet.allOf(CardType.class);
		Iterator<CardType> iterator = types.iterator();
		CardType type;
		for (Country country: countries) {
			type = iterator.next();
			if (type == CardType.WILD_CARD) {
				iterator = types.iterator();
				type = iterator.next();
			}
			cards.add(new Card(type, country));	
		}
		cards.add(new Card(CardType.WILD_CARD, null));
		cards.add(new Card(CardType.WILD_CARD, null));
		
		shuffle();
	}

	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	public Card draw() {
		return cards.remove(cards.size() - 1);
	}

	public boolean isEmpty() {
		return (cards.size() == 0);
	}

}
	
	
	
	
	
	

