package com.randomj.gameobjects;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.randomj.gameobjects.Enums.CardType;
import com.randomj.gameobjects.Enums.MissionType;
import com.randomj.gameobjects.Enums.SubPhase;
import com.randomj.gameobjects.Enums.TurnPhase;
import com.randomj.players.Player;

public class GameInstance { 

	private Map map;
	private Deck deck;
	private ArrayList<Player> players;
	private ArrayList<String> log;
	private ArrayList<Color> colors;
	private ArrayList<Mission> missions;
	private Dice dice;
	private TurnPhase phase;
	private SubPhase subPhase;
	private Player currentPlayer;
	private int id, nextPlayerIndex;
	private boolean conquered, fortified;
	
	public GameInstance() {}
	
	public GameInstance(int id) {
		this.id = id;
		players = new ArrayList<Player>();
		map = new Map();
		log = new ArrayList<String>();
		dice = new Dice();
		
		colors = new ArrayList<Color>(6);	
		colors.add(Color.RED);
		colors.add(Color.GREEN);
		colors.add(Color.BLUE);
		colors.add(Color.YELLOW);
		colors.add(Color.PURPLE);
		colors.add(Color.BLACK);
		Collections.shuffle(colors);
		
		missions = new ArrayList<Mission>();
		long pattern;
		pattern = map.getContinents().get(2).getPattern() + map.getContinents().get(5).getPattern();
		missions.add(new Mission(MissionType.CAPTURE_CONTINENTS_PLUS_ONE, "Capture Europe, Australia and one other continent", pattern));
		pattern = map.getContinents().get(2).getPattern() + map.getContinents().get(1).getPattern();
		missions.add(new Mission(MissionType.CAPTURE_CONTINENTS_PLUS_ONE, "Capture Europe, South America and one other continent", pattern));
		pattern = map.getContinents().get(0).getPattern() + map.getContinents().get(2).getPattern();
		missions.add(new Mission(MissionType.CAPTURE_CONTINENTS, "Capture North America and Africa", pattern));
		pattern = map.getContinents().get(0).getPattern() + map.getContinents().get(5).getPattern();
		missions.add(new Mission(MissionType.CAPTURE_CONTINENTS, "Capture North America and Australia", pattern));
		pattern = map.getContinents().get(1).getPattern() + map.getContinents().get(4).getPattern();
		missions.add(new Mission(MissionType.CAPTURE_CONTINENTS, "Capture Asia and South America", pattern));
		pattern = map.getContinents().get(3).getPattern() + map.getContinents().get(4).getPattern();
		missions.add(new Mission(MissionType.CAPTURE_CONTINENTS, "Capture Asia and Africa", pattern));
		missions.add(new Mission(MissionType.CAPTURE_N_TERRITORIES, "Capture 18 territories and occupy each with two troops", 18, 2));	
	}
	
	public void addPlayer(Player player) {
		player.setColor(colors.remove(colors.size() - 1));
		missions.add(new Mission(MissionType.DEFEAT_PLAYER, "Destroy all  " + player.getColor() + " armies", player));
		players.add(player);
	}

	public void begin() {
		for (int i = players.size(); i < 7; i++)
			missions.add(new Mission(MissionType.CAPTURE_N_TERRITORIES, "Capture 24 territories", 24, 1));
		
		phase = null;
		subPhase = null;
		
		nextPlayerIndex = 0;
		currentPlayer = players.get(nextPlayerIndex);	
				
		deck = new Deck(map.getCountries());
		
		int unitsPerPlayer = 60 - 5 * players.size();
		
		for (Player player: players) {
			player.setMission(missions.remove(MathUtils.random(0, missions.size() - 1)));
			player.addUnits(unitsPerPlayer);
		}
		
		while (!deck.isEmpty()) {		
			Card card = deck.draw();		
			if (card.getType() != CardType.WILD_CARD) {
				currentPlayer.conquerCountry(card.getCountry());
				currentPlayer.setUnits(card.getCountry(), 1);
			}
			nextPlayer();
		}
		deck = new Deck(map.getCountries());
		
		for (Player player: players) {
			while (player.getUnits() > 0)
				player.setRandomUnit();
			for (Continent continent: map.getContinents()) {
				if (player.checkContinent(continent)) {
					player.conquerContinent(continent);
				}
			}
		}
		
		log("Game started");	
		
		phase = TurnPhase.REINFORCEMENT;
		
		nextPlayer();
		
		log(currentPlayer.getName() + ", tap on a territory to fortify it");
	}

	public void nextPlayer() {
		fortified = false;
		conquered = false;
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
	
	public SubPhase getSubPhase() {
		return subPhase;
	}

	public void setSubPhase(SubPhase subPhase) {
		this.subPhase = subPhase;
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
	
	public Player getPlayer(int id) {
		for (Player player: players)
			if (player.getID() == id)
				return player;
		return null;
	}

	public boolean hasFortified() {
		return fortified;
	}

	public void fortified() {
		fortified = true;
	}

	public boolean hasConquered() {
		return conquered;
	}

	public void conquered() {
		conquered = true;
	}

}
