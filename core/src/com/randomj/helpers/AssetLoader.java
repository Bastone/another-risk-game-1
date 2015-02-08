package com.randomj.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class AssetLoader { 

	public static Texture map, button, button_pressed, console, slot, slot_value;
	public static Array<Texture> dice;
	public static Texture infantry, artillery, cavalry, wild_card;
	public static Pixmap mapColors; 
	
	public static void load() {
		map = new Texture(Gdx.files.internal("map.png"));
		
		map.getTextureData().prepare();
		mapColors = map.getTextureData().consumePixmap();
		
		button = new Texture(Gdx.files.internal("button.png"));
		button_pressed = new Texture(Gdx.files.internal("button_pressed.png"));
		console = new Texture(Gdx.files.internal("console.png"));
		
		dice = new Array<Texture>();
		dice.add(new Texture(Gdx.files.internal("die1.png")));
		dice.add(new Texture(Gdx.files.internal("die2.png")));
		dice.add(new Texture(Gdx.files.internal("die3.png")));
		dice.add(new Texture(Gdx.files.internal("die4.png")));
		dice.add(new Texture(Gdx.files.internal("die5.png")));
		dice.add(new Texture(Gdx.files.internal("die6.png")));
		
		infantry = new Texture(Gdx.files.internal("infantry.png"));
		cavalry = new Texture(Gdx.files.internal("cavalry.png"));
		artillery = new Texture(Gdx.files.internal("artillery.png"));
		wild_card = new Texture(Gdx.files.internal("wild_card.png"));
		
		slot = new Texture(Gdx.files.internal("slot.png"));
		slot_value = new Texture(Gdx.files.internal("slot_value.png"));
	}
	
	public static void dispose() { 
		map.dispose();	
		button.dispose();
		console.dispose();
		
		infantry.dispose();
		cavalry.dispose();
		artillery.dispose();
		wild_card.dispose();
		
		slot.dispose();
		slot_value.dispose();
	}

}
