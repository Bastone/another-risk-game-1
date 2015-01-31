package com.randomj.ui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.randomj.gameobjects.Card;
import com.randomj.helpers.AssetLoader;
import com.randomj.players.Player;

public class CardsViewer {
	private ArrayList<Card> cards;
	private ArrayList<Integer> xPos;
	private ArrayList<Sprite> sprites;
	private int speed;
	private boolean isVisible;
	
	public CardsViewer(int xDiv, int yDiv, int margin) {
		cards = new ArrayList<Card>(5);
		
		xPos = new ArrayList<Integer>(5);
		for (int i = 1; i < 6; i++)
			xPos.add(xDiv * i);
		
		speed = xDiv / 10;
		isVisible = false;
		
		sprites = new ArrayList<Sprite>(5);
		for (int i = 1; i < 6; i++) {
			Sprite sprite = new Sprite(AssetLoader.wild_card);
			sprite.setSize(xDiv - margin * 2, yDiv * 3 / 2);
			sprite.setY(yDiv * 2);
			sprite.setX(xPos.get(0));
			sprites.add(sprite);
		}
	}
	
	public void update(Player player) {
		cards = player.getCards();
	}

	public boolean hits(int x, int y) {
		boolean hit = false;
		for (Sprite sprite: sprites)
			if (sprite.getBoundingRectangle().contains(x,y))
				hit = true;
		return hit;
	}
	
	public void draw(SpriteBatch batch) {
		if (isVisible) {
			for (int i = 0; i < cards.size(); i++) {
				sprites.get(i).draw(batch);
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

	public ArrayList<Card> getCards() {		
		return cards;
	}
}
