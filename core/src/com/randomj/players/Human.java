package com.randomj.players;

public class Human extends Player {

	public Human() {}
	
	public Human(String name) {
		this.name = name;
		reset();
	}

	@Override
	public boolean isHuman() {
		return true;
	}

}
