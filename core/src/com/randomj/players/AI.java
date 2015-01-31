package com.randomj.players;

import com.badlogic.gdx.graphics.Color;
import com.randomj.gameobjects.Enums;
import com.randomj.gameobjects.Enums.AIType;

public class AI extends Player {

	private AIType ai;
	
	public AI() {}
	
	public AI(AIType ai, String name) {
		this.ai = ai;
		this.name = name;
		reset();		
	}

	@Override
	public boolean isHuman() {
		return false;
	}

}
