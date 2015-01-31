package com.randomj.net;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.randomj.gameobjects.GameInstance;
import com.randomj.players.Player;

public class GameServer {

	private ArrayList<GameLobby> lobbies;
	private Server server;
	
	public GameServer() throws IOException {
		
		lobbies = new ArrayList<GameLobby>();
		
		server = new Server(16384, 2048*2);
		Network.register(server);   
	    server.addListener(new Listener() {
	    
	    	public void received (Connection connection, Object object) {	  		    		
	    		if (object instanceof Player) {
	    			Player player = (Player) object;
	    			System.out.println("è arrivato " + player.getName());
	    			
					GameLobby lobby;								
					if (lobbies.size() == 0) {
						lobby = new GameLobby();
						lobbies.add(lobby);
						System.out.println("lobby " + lobby.getId() + " creata");
					} else if (lobbies.get(lobbies.size() - 1).isFull()) {
						lobby = new GameLobby();
						lobbies.add(lobby);
						System.out.println("lobby " + lobby.getId() + " creata");
					} else
						lobby = lobbies.get(lobbies.size() - 1);  
					
					
					player.setID();
					player.setColor(lobby.pickColor());
					lobby.addClient(connection.getID(), player);
					connection.sendTCP(player);
					if (lobby.isFull())
						sendToLobby(lobby, lobby.getInstance());
					return;
	    		}
	    		
	    		if (object instanceof GameInstance) {
	    			GameInstance game = (GameInstance) object;
	    			for (GameLobby lobby: lobbies)
	    				if (lobby.getId() == game.getID()) {
	    					sendToLobbyExceptSender(lobby, game, connection.getID());
	    					return;
	    				}		
	    			return;
	    		}	    		
	        }
	     });

	    server.bind(Network.port);   
	    server.start();
	    System.out.println("Server online");
	}

	public void sendToLobby(GameLobby lobby, GameInstance game) {
		for (int id: lobby.getConnections())
			server.sendToTCP(id, game);
	}
	
	public void sendToLobbyExceptSender(GameLobby lobby, GameInstance game, int senderID) {
		for (int id: lobby.getConnections())
			if (id != senderID)
				server.sendToTCP(id, game);
	}
	
	public static void main(String args[]) throws IOException {
		new GameServer();
	}

}
