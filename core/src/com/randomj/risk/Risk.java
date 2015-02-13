package com.randomj.risk;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.randomj.gameobjects.GameInstance;
import com.randomj.helpers.AssetLoader;
import com.randomj.net.PlayerClient;
import com.randomj.players.Human;
import com.randomj.players.Player;
import com.randomj.screens.GameScreen;

public class Risk extends Game {

	@Override
	public void create() {
		AssetLoader.load(); 
		
		boolean online = false; // Se online, avviare server e i diversi client a parte
		PlayerClient me;

		// Giocatori di prova
		ArrayList<Player> players = new ArrayList<Player>(); 
		players.add(new Human("Catalbertolasio"));
		players.add(new Human("Gertrudelio"));
		players.add(new Human("Eugeniugolo"));
		players.add(new Human("Adolfride"));

		if (!online)
			me = new PlayerClient(players.get(0), new GameInstance(players));
		else {
			me = new PlayerClient(players.get(0), "127.0.0.1");

			while (!me.isReady()) {
				Gdx.app.log("Client","is waiting");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		setScreen(new GameScreen(this, me));
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}
