package com.randomj.gameobjects;

public class Enums {  

	public enum AIType {
		NOOB, NORMAL, EXPERT, NONE
	}
	
	public enum GameState {
		READY, RUNNING, PAUSE
	}
	
	public enum TurnPhase {
		DISTRIBUTION, REINFORCEMENT, ATTACK_PHASE, BATTLE_PHASE, FORTIFYING
	}
	
	public enum CardType {
		INFANTRY, CAVALRY, ARTILLERY, WILD_CARD 
	}

}
