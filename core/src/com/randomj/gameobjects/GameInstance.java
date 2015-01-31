package com.randomj.gameobjects;

import java.util.ArrayList;

import com.randomj.gameobjects.Enums.CardType;
import com.randomj.gameobjects.Enums.TurnPhase;
import com.randomj.players.Player;

public class GameInstance { 

	private Map map;
	private Deck deck;
	private ArrayList<Player> players;
	private ArrayList<String> log;
	private Dice dice;
	private TurnPhase phase;
	private Player currentPlayer;
	private int id, nextPlayerIndex;
	
	public GameInstance() {}
	
	public GameInstance(int id) {
		this.id = id;
		players = new ArrayList<Player>();
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}

	public void begin() {	
		map = new Map();
		log = new ArrayList<String>();
		dice = new Dice();
		phase = null;
		
		nextPlayerIndex = 0;
		currentPlayer = players.get(nextPlayerIndex);	
				
		deck = new Deck(map.getCountries());
		
		int unitsPerPlayer = 60 - 5 * players.size();
		
		for (Player player: players)
			player.addUnits(unitsPerPlayer);
		
		while (!deck.isEmpty()) {		
			Card card = deck.draw();		
			if (card.getType() != CardType.WILD_CARD)
				currentPlayer.conquerCountry(card.getCountry());
			nextPlayer();
		}
		
		for (Player player: players) {
			while (player.getUnits() > 0)
				player.setRandomUnit();
		}
		
		log("Game started");
		
		phase = TurnPhase.REINFORCEMENT;
		
		nextPlayer();
	}
	
	private void nextPlayer() {
		currentPlayer = players.get(nextPlayerIndex);		
		if (phase != null) {
			currentPlayer.beginTurn();
			log("It's " + currentPlayer.getName() + " turn");
		}
		
		nextPlayerIndex++;
		if (nextPlayerIndex == players.size())
			nextPlayerIndex = 0;			
	}
	
	public Map getMap() {
		return map;
	}

	public ArrayList<String> getLastLogs() {
		ArrayList<String> lastLogs = new ArrayList<String>(3);
		int limit = Math.max(0, log.size() - 3);
		for (int i = log.size() - 1; i >= limit; i--)
			lastLogs.add(log.get(i));
		return lastLogs;
	}
	
	public void log(String string) {
		log.add(string);
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public TurnPhase getPhase() {
		return phase;
	}

	public void setPhase(TurnPhase phase) {
		this.phase = phase;
	}

	public Deck getDeck() {
		return deck;
	}

	public Dice getDice() {
		return dice;
	}

	public int getID() {
		return id;
	}

}
