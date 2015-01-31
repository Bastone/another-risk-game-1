package com.randomj.net;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.randomj.gameobjects.GameInstance;
import com.randomj.players.Player;

public class GameLobby {
	
	private GameInstance game;
	private ArrayList<Integer> clientsID;
	private ArrayList<Color> colors;
	private int id;
	
	public GameLobby() {
		id = this.hashCode();
		game = new GameInstance(id);
		clientsID = new ArrayList<Integer>(6);
		colors = new ArrayList<Color>(6);
		
		colors.add(Color.RED);
		colors.add(Color.GREEN);
		colors.add(Color.BLUE);
		colors.add(Color.YELLOW);
		colors.add(Color.PURPLE);
		colors.add(Color.BLACK);
	}

	public ArrayList<Integer> getConnections() {
		return clientsID;
	}
	
	public Color pickColor() {
		return colors.remove(MathUtils.random(0, colors.size() - 1));
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
