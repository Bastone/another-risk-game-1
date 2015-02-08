package com.randomj.gameobjects;
import com.randomj.gameobjects.Enums.CardType;

public class Card {
	
	private Country country;
	private CardType type;
	private int pattern;
	
	public Card() {}
	
	public Card(CardType type, Country country, int pattern) {
		this.country = country;
		this.type = type;
		this.pattern = pattern;
	}
	
	public boolean isWildCard() {
		return (type == CardType.WILD_CARD);
	}
	
	public Country getCountry() {
		return country;
	}
	
	public CardType getType() {
		return type;
	}
	
	public int getPattern() {
		return pattern;
	}

	public String toString() {
		if (type == CardType.WILD_CARD)
			return type.toString() + " " + Integer.toBinaryString(pattern);
		else
			return type.toString() + " " + country.getName() + " " + Integer.toBinaryString(pattern);
	}
}
