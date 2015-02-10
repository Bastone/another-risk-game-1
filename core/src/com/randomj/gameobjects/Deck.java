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
		cards = new ArrayList<Card>(44);
		init(countries);		
	}
	
	public void init(ArrayList<Country> countries) { // Artillery, infantry and cavalry patterns: 001 010 100
		Set<CardType> types = EnumSet.allOf(CardType.class);
		Iterator<CardType> iterator = types.iterator();
		CardType type;
		int i = 0;
		for (Country country: countries) {
			type = iterator.next();
			i++;
			if (type == CardType.WILD_CARD) {
				iterator = types.iterator();
				type = iterator.next();
				i = 0;
			}
			cards.add(new Card(type, country, (int)Math.pow(2, i))); 
		}
		cards.add(new Card(CardType.WILD_CARD, null, 7)); // Wild card pattern: 111
		cards.add(new Card(CardType.WILD_CARD, null, 7));
		
		Collections.shuffle(cards);
	}
	
	public Card draw() {
		return cards.remove(cards.size() - 1);
	}

	public boolean isEmpty() {
		return (cards.size() == 0);
	}

}
	
	
	
	
	
	

