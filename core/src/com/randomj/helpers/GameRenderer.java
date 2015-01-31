package com.randomj.helpers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;
import com.randomj.gameobjects.Country;
import com.randomj.gameobjects.Map;
import com.randomj.net.PlayerClient;
import com.randomj.ui.Button;
import com.randomj.ui.CardsViewer;
import com.randomj.ui.DiceViewer;


public class GameRenderer {
	
	private static final int BUTTON_MARGIN = 20, CARDS_MARGIN = 10, TEXT_PADDING = 35, TEXT_GAP = 20;
	private Sprite mapSprite, consoleSprite;
	private Button nextButton, cardsButton;
	private BitmapFont font;
	private CardsViewer cardsViewer;
	private DiceViewer diceViewer;
	private PlayerClient client;
	
	public GameRenderer(int gameWidth, int gameHeight, int screenWidth, int screenHeight, PlayerClient client) {
		
		this.client = client;
		
		int xDiv = screenWidth / 7;
		int yDiv = screenHeight / 5;
		
		font = new BitmapFont();
		
		// Map
		mapSprite = new Sprite(AssetLoader.map);
		mapSprite.setSize(gameWidth, gameHeight);
			
		// Consoles
		consoleSprite = new Sprite(AssetLoader.console);
		consoleSprite.setBounds(xDiv, 0, xDiv * 5, yDiv);
			
		// Buttons
		cardsButton = new Button(BUTTON_MARGIN, BUTTON_MARGIN, xDiv - BUTTON_MARGIN * 2, yDiv - BUTTON_MARGIN * 2, TEXT_PADDING);
		cardsButton.setText("Cards");
		nextButton = new Button(xDiv * 6 + BUTTON_MARGIN, BUTTON_MARGIN, xDiv - BUTTON_MARGIN * 2, yDiv - BUTTON_MARGIN * 2, TEXT_PADDING);
		nextButton.setText("Prova");
		
		// Cards
		cardsViewer = new CardsViewer(xDiv, yDiv, CARDS_MARGIN);
		cardsViewer.update(client.getPlayer());

		// Dice
		diceViewer = new DiceViewer(screenWidth, screenHeight);
	}

	public void renderMap(SpriteBatch batch, ShapeRenderer shape) {
		Map map = client.getInstance().getMap();
		batch.begin(); 
		mapSprite.draw(batch);	    
		batch.end();

		shape.begin(ShapeType.Filled);
		for (Country country: map.getCountries()) {
			if (country.isFree())
				shape.setColor(Color.GRAY);
			else
				shape.setColor(country.getOwner().getColor());
			shape.circle(country.getX(), country.getY(), 15);
		}
		shape.end(); 

		batch.begin();
		for (Country country: map.getCountries())
			font.draw(batch, country.getUnits() +  "", country.getX() - 2, country.getY() + 5);	
		batch.end();
	}
	
	public void renderUI(SpriteBatch batch, float delta) {
		ArrayList<String> log = client.getInstance().getLastLogs();
		
	    batch.begin();
	    
	    // Console
	    consoleSprite.draw(batch);
	    for (int i = 0; i < log.size(); i++)
	    	font.draw(batch, log.get(i), consoleSprite.getX() + TEXT_PADDING, consoleSprite.getY() + TEXT_PADDING + TEXT_GAP * i);
	    
		// Buttons
	    cardsButton.draw(batch, font);
	    nextButton.draw(batch, font);
	    
	    // Cards    
	    cardsViewer.draw(batch);
	    
	    // Dice
	    diceViewer.draw(batch, delta);
	    
	    batch.end();
		
	}

	public Button getCardsButton() {
		return cardsButton;
	}
	
	public Button getNextButton() {
		return nextButton;
	}
	
	public CardsViewer getCardsViewer() {
		return cardsViewer;
	}

	public DiceViewer getDiceViewer() {
		return diceViewer;
	}

}
