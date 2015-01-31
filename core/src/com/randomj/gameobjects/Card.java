package com.randomj.gameobjects;
import com.randomj.gameobjects.Enums.CardType;

public class Card {
	
	private Country country;
	private CardType type;
	
	public Card() {}
	
	public Card(CardType type, Country country) {
		this.country = country;
		this.type = type;
	}

	public String toString() {
		if (type == CardType.WILD_CARD)
			return type.toString();
		else
			return type.toString() + " " + country.getName();
	}

	public Country getCountry() {
		return country;
	}
	
	public CardType getType() {
		return type;
	}

}
