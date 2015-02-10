package com.randomj.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.randomj.helpers.AssetLoader;

public class Window {
	private String text;
	private Sprite sprite;
	private int padding;
	private boolean visible;
	
	public Window(int x, int y, int width, int height, int padding) {
		this.padding = padding;
		sprite = new Sprite(AssetLoader.button_pressed);
		sprite.setBounds(x, y, width, height);
		visible = false;
	}

	public void draw(SpriteBatch batch, BitmapFont font) {
		if (visible) {
			sprite.draw(batch);
			font.draw(batch, text, sprite.getX() + padding, sprite.getY() + padding);
		}
	}

	public boolean hits(float x, float y) {
		return sprite.getBoundingRectangle().contains(x, y);
	}
	
	public void show() {
		visible = true;
	}
	
	public void hide() {
		visible = false;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setText(String text) {
		this.text = text;
	}
}
