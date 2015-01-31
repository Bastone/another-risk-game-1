package com.randomj.net;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.randomj.gameobjects.Card;
import com.randomj.gameobjects.Continent;
import com.randomj.gameobjects.Country;
import com.randomj.gameobjects.Deck;
import com.randomj.gameobjects.Dice;
import com.randomj.gameobjects.Enums;
import com.randomj.gameobjects.GameInstance;
import com.randomj.gameobjects.Map;
import com.randomj.gameobjects.Enums.TurnPhase;
import com.randomj.players.AI;
import com.randomj.players.Human;
import com.randomj.players.Player;

public class Network {

	static public final int port = 3333, timeout = 5000;
	
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.setReferences(true);
	    kryo.register(Player.class);
	    kryo.register(Human.class);
	    kryo.register(AI.class);
	    kryo.register(Card.class);
	    kryo.register(Continent.class);
	    kryo.register(Country.class);
	    kryo.register(Deck.class);
	    kryo.register(Dice.class);
	    kryo.register(Enums.class);
	    kryo.register(GameInstance.class);
	    kryo.register(Map.class);
	    kryo.register(Color.class);
	    kryo.register(TurnPhase.class);
	    kryo.register(ArrayList.class);
	}
}
