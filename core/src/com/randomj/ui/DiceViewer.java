package com.randomj.ui;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.randomj.gameobjects.Dice;
import com.randomj.helpers.AssetLoader;

public class DiceViewer {

	private ArrayList<Sprite> attackerSprites, defenderSprites;
	private boolean visible, animation;
	private float timelapse;
	private int count;
	private Dice dice;
	
	public DiceViewer(int screenWidth, int screenHeight) {
		attackerSprites = new ArrayList<Sprite>(3);
		defenderSprites = new ArrayList<Sprite>(3);
		
		for (int i = 2; i >= 0; i--) {
			Sprite sprite = new Sprite(AssetLoader.magalli);
			sprite.setSize(screenWidth/30, screenWidth/30);
			sprite.setPosition(screenWidth/2 - sprite.getWidth()*2, screenHeight/2 + (sprite.getHeight() + 10) * i);
			sprite.setColor(Color.BLUE);
			defenderSprites.add(sprite);
		}
		
		for (int i = 2; i >= 0; i--) {
			Sprite sprite = new Sprite(AssetLoader.magalli);
			sprite.setSize(screenWidth/30, screenWidth/30);
			sprite.setPosition(screenWidth/2 + sprite.getWidth()*2, screenHeight/2 + (sprite.getHeight() + 10) * i);
			sprite.setColor(Color.RED);
			attackerSprites.add(sprite);
		}
		
		reset();
	}
	
	public void draw(SpriteBatch batch, float delta) {	
		if(visible) {
			if (animation) {		
				timelapse += delta;
				if(timelapse < 1.0){
					if(count == 2){	
						for (Sprite sprite: attackerSprites)
							sprite.setTexture(AssetLoader.dice.get(MathUtils.random(5)));
						for (Sprite sprite: defenderSprites)
							sprite.setTexture(AssetLoader.dice.get(MathUtils.random(5)));
						count = 0;
					}
					else count++;				
				} else {
					reset();
				}
			} else {
				for (int i = 0; i < dice.getAttackerDice().size(); i++)
					attackerSprites.get(i).setTexture(AssetLoader.dice.get(dice.getAttackerDice().get(i) - 1));
				for (int i = 0; i < dice.getDefenderDice().size(); i++)
					attackerSprites.get(i).setTexture(AssetLoader.dice.get(dice.getDefenderDice().get(i) - 1));
			}
			
			for (Sprite sprite: defenderSprites)
				sprite.draw(batch);
			for (Sprite sprite: attackerSprites)
				sprite.draw(batch);
		}
	}
		
	public void show() {
		visible = true;
		animation = true;
	}
	
	public void hide() {
		visible = false;
		reset();
	}
	
	public void reset() {
		visible = false;
		animation = false;
		count = 0;
		timelapse = 0;
	}
	
	public void setDice(Dice dice) {
		this.dice = dice;
	}

	public boolean isVisible() {
		return visible;
	}
}
