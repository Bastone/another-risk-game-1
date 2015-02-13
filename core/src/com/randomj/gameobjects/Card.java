package com.randomj.gameobjects;
import com.randomj.gameobjects.Enums.CardType;

public class Card {
	
	private Country country;
	private CardType type;
	private int pattern;
	private String text;
	private boolean selected;
	
	public Card() {}
	
	public Card(CardType type, Country country, int pattern) {
		this.country = country;
		this.type = type;
		this.pattern = pattern;
		selected = false;
		if (country == null)
			text = "Wild card";
		else
			text = country.getName();
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void select() {
		selected = true;
	}
	
	public void deselect() {
		selected = false;
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
	
	public String getText() {
		return text;
	}

	public String toString() {
		return type.toString() + " " + text + " " + Integer.toBinaryString(pattern);
	}
}
