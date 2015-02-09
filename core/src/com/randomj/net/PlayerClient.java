package com.randomj.net;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.randomj.gameobjects.GameInstance;
import com.randomj.helpers.GameRenderer;
import com.randomj.helpers.GameUpdater;
import com.randomj.players.Player;

public class PlayerClient {

	private Player player;
	private GameRenderer renderer;
	private GameUpdater updater;
	private GameInstance activeGame;
	private Client client;
	private boolean ready;
	
	public PlayerClient(Player p, String host) {
		this.player = p;
		activeGame = null;
		ready = false;
		
		client = new Client(8192, 2048*2);	
		Network.register(client);

	    client.addListener(new Listener() {
			public void connected (Connection connection) {
				client.sendTCP(player);
			}
			
	    	public void received(Connection connection, Object object) {
	    		if (object instanceof GameInstance) {
	    			activeGame = (GameInstance) object;
	    			player = activeGame.getPlayer(player.getID());
	    			update();
	    			ready = true;
	    			return;
	    		}
	    		if (object instanceof Player) {
	    			player = (Player) object;
	    		}
	    		
	    	}
	    });
		
		client.start();
		
		try {
			client.connect(Network.timeout, host, Network.port);
		} catch (IOException e) {
			Gdx.app.log("Client", "Server non connesso");
		}
	}
	
	public void set(GameRenderer renderer, GameUpdater updater) {
		this.renderer = renderer;
		this.updater = updater;
		update();
	}

	public void update() {
		if (renderer != null && updater != null) {
			renderer.setGameInstance(activeGame);
			updater.setGameInstance(activeGame);
		}
	}

	public void send(GameInstance game) {
		client.sendTCP(game);
		update();
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}

	public GameInstance getInstance() {
		return activeGame;
	}
	
	public boolean isConnected() {
		return client.isConnected();
	}
	
	public boolean isReady() {
		return ready;
	}

	public boolean itsYourTurn() {
		return (player.getID() == activeGame.getCurrentPlayer().getID());
	}
}
