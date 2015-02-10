package com.randomj.ui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.randomj.gameobjects.Card;
import com.randomj.helpers.AssetLoader;

public class CardsViewer {
	private ArrayList<Card> cards;
	private ArrayList<Integer> xPos;
	private ArrayList<Sprite> sprites;
	private Rectangle window;
	private int speed, margin, padding;
	private boolean isVisible;
	
	public CardsViewer(int xDiv, int yDiv, int margin, int padding) {
		this.margin = margin;
		this.padding = padding;
		cards = new ArrayList<Card>(5);
		
		xPos = new ArrayList<Integer>(5);
		for (int i = 1; i < 6; i++)
			xPos.add(xDiv * i);
		
		speed = xDiv / 10;
		isVisible = false;
		
		window = new Rectangle(xDiv, yDiv * 2, xDiv * 5, yDiv * 3 / 2);
	}
	
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
		sprites = new ArrayList<Sprite>(5);
		for (Card card: cards) {
			Sprite sprite = new Sprite(AssetLoader.wild_card);
			switch (card.getType()) {
			case ARTILLERY:
				sprite.setTexture(AssetLoader.artillery);
				break;
			case CAVALRY:
				sprite.setTexture(AssetLoader.cavalry);
				break;
			case INFANTRY:
				sprite.setTexture(AssetLoader.infantry);
				break;
			default:
				break;
			}
			sprite.setSize(window.width / 5 - margin, window.height - margin);
			sprite.setY(window.height + margin);
			sprite.setX(xPos.get(0) + margin);
			sprites.add(sprite);
		}
	}

	public boolean hits(int x, int y) {
		return window.contains(x, y);
	}
	
	public Card pickCard(int x, int y) {
		for (int i = 0; i < sprites.size(); i++)
			if (sprites.get(i).getBoundingRectangle().contains(x, y))
				return cards.get(i);
		return null;
	}
	
	public void draw(SpriteBatch batch, BitmapFont font) {
		if (isVisible) {
			for (int i = 0; i < sprites.size(); i++) {
				sprites.get(i).draw(batch);
				font.draw(batch, cards.get(i).getText(), sprites.get(i).getX()+ padding, 
						sprites.get(i).getY() + padding);
				if (sprites.get(i).getX() < xPos.get(i)) {
					sprites.get(i).translateX(speed);
					if (sprites.get(i).getX() > xPos.get(i))
						sprites.get(i).setX(xPos.get(i));
				}
			}	
		}
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void show() {
		isVisible = true;
	}
	
	public void hide() {
		isVisible = false;
		reset();
	}
	
	public void reset() {
		for (int i = 0; i < cards.size(); i++) {
			sprites.get(i).setX(xPos.get(0));
		}
	}
}
