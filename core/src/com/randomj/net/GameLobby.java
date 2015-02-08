package com.randomj.net;

import java.util.ArrayList;

import com.randomj.gameobjects.GameInstance;
import com.randomj.players.Player;

public class GameLobby {
	
	private GameInstance game;
	private ArrayList<Integer> clientsID;
	private int id;
	
	public GameLobby() {
		id = this.hashCode();
		game = new GameInstance(id);
		clientsID = new ArrayList<Integer>(6);
	}

	public ArrayList<Integer> getConnections() {
		return clientsID;
	}

	public boolean isFull() {
		return clientsID.size() == 2;
	}

	public void addClient(int id, Player player) {
		game.addPlayer(player);
		clientsID.add(id);
		if (isFull())
			game.begin();
	}
	
	public int getId() {
		return id;
	}

	public GameInstance getInstance() {
		return game;
	}
}
