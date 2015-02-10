package com.randomj.gameobjects;

public class Enums {  

	public enum AIType {
		NOOB, NORMAL, EXPERT, NONE
	}
	
	public enum GameState {
		READY, RUNNING, PAUSE
	}
	
	public enum TurnPhase {
		REINFORCEMENT, ATTACK_PHASE, FORTIFYING, END
	}
	
	public enum SubPhase {
		SELECTION, TARGETING, ATTACKING, MOVING_UNITS, CARDS_TRADING
	}
	
	public enum CardType {
		INFANTRY, CAVALRY, ARTILLERY, WILD_CARD 
	}

	public enum MissionType {
		CAPTURE_CONTINENTS, CAPTURE_CONTINENTS_PLUS_ONE, CAPTURE_N_TERRITORIES, DEFEAT_PLAYER
	}
}
