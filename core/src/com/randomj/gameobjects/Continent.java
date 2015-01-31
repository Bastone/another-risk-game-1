package com.randomj.gameobjects;

public class Continent {

	private String name;
	private int bonusUnits;
	private long pattern;
	
	public Continent() {}
	
	public Continent(String name, int bonusUnits, long pattern) {
		this.name = name;
		this.bonusUnits = bonusUnits;
		this.pattern = pattern;
	}
	
	public long getPattern() {
		return pattern;
	}

	public int getBonusUnits() {
		return bonusUnits;
	}

	public String getName() {
		return name;
	}
	
	public String toString() {
		return String.format("%-30s\t%-8d\t%42s", name, bonusUnits, Long.toBinaryString(pattern) );
	}

}
