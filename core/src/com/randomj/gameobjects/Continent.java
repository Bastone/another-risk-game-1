package com.randomj.gameobjects;

public class Continent {

	private String name;
	private int bonusUnits;
	private long pattern;
	
	public Continent() {}
	
	public Continent(String name, int bonusUnits) {
		this.name = name;
		this.bonusUnits = bonusUnits;
	}
	
	public long getPattern() {
		return pattern;
	}
	
	public void setPattern(long pattern) {
		this.pattern = pattern;
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

	public boolean contains(Country target) {
		return ((pattern & target.getPattern()) == target.getPattern());
	}

	public boolean check(long pattern) {
		return ((this.pattern & pattern) == this.pattern);
	}

}
