package com.randomj.helpers;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.randomj.gameobjects.Country;
import com.randomj.gameobjects.Enums.SubPhase;
import com.randomj.gameobjects.GameInstance;
import com.randomj.net.PlayerClient;
import com.randomj.ui.Button;
import com.randomj.ui.CardsViewer;
import com.randomj.ui.DiceViewer;
import com.randomj.ui.Window;


public class GameRenderer {
	
	private static final int BUTTON_MARGIN = 20, CARDS_MARGIN = 10, BUTTON_TEXT_PADDING = 37,
			WINDOW_TEXT_PADDING = 20, CONSOLE_TEXT_PADDING = 35, CARDS_TEXT_PADDING = 40;
	private Sprite mapSprite, consoleSprite;
	private Button nextButton, cardsButton, missionButton;
	private BitmapFont font;
	private CardsViewer cardsViewer;
	private DiceViewer diceViewer;
	private PlayerClient client;
	private GameInstance game;
	private Window missionWindow;
	
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
		cardsButton = new Button(BUTTON_MARGIN, BUTTON_MARGIN, xDiv - BUTTON_MARGIN * 2, yDiv - BUTTON_MARGIN * 2, BUTTON_TEXT_PADDING);
		cardsButton.setText("Cards");
		nextButton = new Button(xDiv * 6 + BUTTON_MARGIN, BUTTON_MARGIN, xDiv - BUTTON_MARGIN * 2, yDiv - BUTTON_MARGIN * 2, BUTTON_TEXT_PADDING);
		nextButton.setText("Waiting");
		nextButton.disable();
		missionButton = new Button(BUTTON_MARGIN, yDiv * 4 + BUTTON_MARGIN,  xDiv - BUTTON_MARGIN * 2, yDiv - BUTTON_MARGIN * 2, BUTTON_TEXT_PADDING);
		missionButton.setText("Mission");
		
		//Windows
		missionWindow = new Window(xDiv * 2, yDiv * 2 + yDiv / 3, xDiv * 3, yDiv / 3, WINDOW_TEXT_PADDING);
		
		// Cards
		cardsViewer = new CardsViewer(xDiv, yDiv, CARDS_MARGIN, CARDS_TEXT_PADDING);

		// Dice
		diceViewer = new DiceViewer(screenWidth, screenHeight);
	}

	public void renderMap(SpriteBatch batch, ShapeRenderer shape) {                                                                                    
		
		// Map
		batch.begin(); 
		mapSprite.draw(batch);	    
		batch.end();
		
		// Circles
		shape.begin(ShapeType.Filled);
		for (Country country: game.getMap().getCountries()) {
			shape.setColor(country.getOwner().getColor());
			shape.circle(country.getX(), country.getY(), 15);
		}
		shape.end(); 
		
		// N. units
		batch.begin();
		for (Country country: game.getMap().getCountries())
			font.draw(batch, country.getUnits() +  "", country.getX() - 2, country.getY() + 5);	
		batch.end();
		
	}

	public void renderUI(SpriteBatch batch, ShapeRenderer shape, float delta) {
		
	    batch.begin();
	    
	    // Console
	    consoleSprite.draw(batch);
	    if (game.getLog() != null) {
	    	font.setColor(game.getCurrentPlayer().getColor());
	    	font.draw(batch, game.getLog(), consoleSprite.getX() + CONSOLE_TEXT_PADDING, consoleSprite.getY() + CONSOLE_TEXT_PADDING);
	    }
		// Buttons	    
	    cardsButton.draw(batch, font);
	    nextButton.draw(batch, font);
	    missionButton.draw(batch, font);
	    
	    // Windows
	    missionWindow.draw(batch, font);
	    
	    // Cards    
	    cardsViewer.draw(batch, font);
	    cardsViewer.drawSelection(shape);
	    
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

	public void setGameInstance(GameInstance game) {
		this.game = game;
		
		missionWindow.setText(client.getPlayer().getMission().getDescription());
		
		if (client.getPlayer().getCards().isEmpty())
			cardsButton.disable();
		else {
			cardsButton.enable();
			cardsViewer.setCards(client.getPlayer().getCards());
		}
		
		if (game.isRolling()) {
			diceViewer.setDice(game.getDice());
			diceViewer.show();
		} else if (diceViewer.isVisible()) {
			diceViewer.hide();
		}
		
		if (client.itsYourTurn()) {
			switch (game.getPhase()) {
			
			case REINFORCEMENT:

				if (game.getSubPhase() == SubPhase.CARDS_TRADING) {
					nextButton.setText("Trade");
					nextButton.enable();
				} else if (game.getCurrentPlayer().getUnits() == 0) {
					nextButton.setText("Go to attack phase");
					nextButton.enable();
				} else {
					nextButton.setText("Placing " + game.getCurrentPlayer().getUnits() + " more units");
					nextButton.disable();
				}
				break;
				
			case ATTACK_PHASE:
				switch (game.getSubPhase()) {
				
				case SELECTION:
					nextButton.setText("Go to fortify phase");
					nextButton.enable();
					break;
				
				case TARGETING:
					nextButton.setText("Choose a target");
					nextButton.disable();
					break;
				
				case ATTACKING:
					nextButton.setText("Attack!");
					nextButton.enable();
					break;
				
				case MOVING_UNITS:
					nextButton.setText("Ok");
					nextButton.enable();
					break;
					
				default:
					break;
				}
				break;
				
			case FORTIFYING:
				switch (game.getSubPhase()) {
				
				case TARGETING:
					nextButton.setText("Choose another country");
					nextButton.disable();
					break;			
				default:
					nextButton.setText("End turn");
					nextButton.enable();
					break;
				}
			
			default:
				break;
			}
		} else {
			nextButton.setText("Waiting for opponent");
			nextButton.disable();
		}
	}

	public Button getMissionButton() {
		return missionButton;
	}

	public Window getMissionWindow() {
		return missionWindow;
	}
}
