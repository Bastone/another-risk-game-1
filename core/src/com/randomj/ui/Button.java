package com.randomj.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.randomj.helpers.AssetLoader;

public class Button { 
	
	private String text;
	private Sprite sprite;
	private boolean enabled;
	private int padding;

	public Button(int x, int y, int width, int height, int padding) {
		sprite = new Sprite(AssetLoader.button);
		sprite.setBounds(x, y, width, height);
		this.padding = padding;
		enable();
	}
	
	public void draw(SpriteBatch batch, BitmapFont font) {
		sprite.draw(batch);
		font.draw(batch, text, sprite.getX() + padding, sprite.getY() + padding);
	}

	public boolean hits(float x, float y) {
		return sprite.getBoundingRectangle().contains(x, y);
	}
	
	public void up() {
		sprite.setTexture(AssetLoader.button);
	}
	
	public void down() {
		sprite.setTexture(AssetLoader.button_pressed);
	}

	public void setText(String text) {
		this.text = text;		
	}
	
	public String getText() {
		return text;
	}

	public void enable() {
		enabled = true;
		sprite.setTexture(AssetLoader.button);
	}
	
	public void disable() {
		enabled = false;
		sprite.setTexture(AssetLoader.magalli);
	}
	
	public boolean isEnabled() {
		return enabled;
	}
}
