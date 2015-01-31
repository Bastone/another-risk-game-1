package com.randomj.risk;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.randomj.helpers.AssetLoader;
import com.randomj.net.PlayerClient;
import com.randomj.players.Human;
import com.randomj.players.Player;
import com.randomj.screens.GameScreen;

public class Risk extends Game {

	@Override
	public void create() {
		AssetLoader.load(); 

		// Giocatori di prova
		ArrayList<Player> players = new ArrayList<Player>(); 
		players.add(new Human("Catalbertolasio"));
		players.add(new Human("Gertrudelio"));
		players.add(new Human("Eugeniugolo"));
		players.add(new Human("Adolfride"));
		
		// Carte di prova
//		players.get(0).addCard(new Card(CardType.WILD_CARD, null));
//		players.get(0).addCard(new Card(CardType.WILD_CARD, null));
//		players.get(0).addCard(new Card(CardType.WILD_CARD, null));
//		players.get(0).addCard(new Card(CardType.WILD_CARD, null));
//		players.get(0).addCard(new Card(CardType.WILD_CARD, null));
		
		PlayerClient me = new PlayerClient(players.get(0), "127.0.0.1");
//		PlayerClient me = new PlayerClient(players.get(1), "127.0.0.1");
//		PlayerClient me = new PlayerClient(players.get(2), "127.0.0.1");
//		PlayerClient me = new PlayerClient(players.get(3), "127.0.0.1");
		
		while (!me.isReady()) {
			Gdx.app.log("Client","is waiting");
		}
		
		Gdx.app.log("Istanza", me.getInstance().toString());
	
		setScreen(new GameScreen(this, me));
//		setScreen(new SplashScreen());
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}

}
