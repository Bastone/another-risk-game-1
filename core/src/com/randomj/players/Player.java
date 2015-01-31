package com.randomj.players;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.randomj.gameobjects.Card;
import com.randomj.gameobjects.Country;

public abstract class Player {
	
	protected String name;
	protected Color color;
	protected ArrayList<Country> ownedCountries;
	protected ArrayList<Card> cards;
	protected long pattern;
	protected int units, id;
	
	public void Player() {}
	
	public abstract boolean isHuman();
	
	public void reset() {
		cards = new ArrayList<Card>(5);
		pattern = 0;
		units = 0;
		ownedCountries = new ArrayList<Country>(42);
	}
	
	public void beginTurn() {
		units += (int) (ownedCountries.size() / 3);
	}
	
	public void setRandomUnit() {
		ownedCountries.get(MathUtils.random(ownedCountries.size() - 1)).addUnits(1);
		units -= 1;
	}
	
	public boolean owns(Country country) {
		return ((country.getPattern() & pattern) == country.getPattern());
	}
	
	public void setUnits(Country country, int amount) {
		country.addUnits(amount);
		units -= amount;
	}
	
	public void addUnits(int amount) {
		units += amount;
	}
	
	public void removeUnits(int amount) {
		units -= amount;
	}
	
	public void conquerCountry(Country country) {
		pattern += country.getPattern();	
		ownedCountries.add(country);
		country.setOwner(this);
	}
	
	public void loseCountry(Country country) {
		pattern -= country.getPattern();
		ownedCountries.remove(country);
	}
	
	public void addCard(Card card) {
		cards.add(card);
	}
	
	public int getID() {
		return id;
	}
	
	public void setID() {
		this.id = this.hashCode();
	}

	public String setName() {
		return name;
	}
	
	public String getName() {
		return name;
	}

	public void setColor(Color color) {
		this.color = color;		
	}

	public Color getColor() {
		return color;
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}

	public long getPattern() {
		return pattern;
	}
	
	public int getUnits() {
		return units;
	}	
	
	public ArrayList<Country> getOwnedCountries() {
		return ownedCountries;
	}
	
	public String toString() {
		return name + "(" + units + " units)";
	}
	
	public boolean equals(Player player) {
		return (player.getID() == this.id);
	}

}
