package com.randomj.gameobjects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.randomj.players.Player;

public class Country {
	
	private String name;
	private Player owner;
	private ArrayList<Country> borders;
	private int units;
	private long pattern;
	private int color;
	private float x, y;

	public Country() {}
	
	public Country(String name, int color, int n) {
		this.name = name;
		this.color = color;	
		pattern = (long) Math.pow(2, n);
		borders = new ArrayList<Country>(10);
		reset();
	}
	
	public Country(String name, float x, float y, int color, int n) {
		this.name = name;
		this.color = color;	
		pattern = (long) Math.pow(2, n);
		this.x = x;
		this.y = y;
		
		reset();
	}
	
	public void reset() {
		units = 0;
		owner = null;
	}
	
	public void addUnits(int amount) {
		units += amount;
	}

	public void loseUnits(int amount) {
		units -= amount;
	}
	
	public boolean isFree() {
		return (owner == null);
	}
	
	public void drawNoUnits(SpriteBatch batch, BitmapFont font) {
		font.draw(batch, units + "", x - 2, y + 5);	
	}
	
	public void drawCircle(ShapeRenderer shape) {
		if (owner == null)
			shape.setColor(Color.GRAY);
		else
			shape.setColor(owner.getColor());
		shape.circle(x, y, 15);	
	}
	
	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public int getUnits() {
		return units;
	}
	
	public long getPattern() {
		return pattern;
	}
	
	public int getColor() {
		return color;
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public void setBorders(ArrayList<Country> borders) {
		this.borders = borders;
	}

	public ArrayList<Country> getBorders() {
		return borders;
	}
	
	public String toString() {
		return name + " has " + units + " units";
	}
}
