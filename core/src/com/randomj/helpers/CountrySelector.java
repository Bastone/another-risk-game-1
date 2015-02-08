package com.randomj.helpers;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector3;
import com.randomj.gameobjects.Country;

public class CountrySelector {

	private Pixmap mapColors;
	private ArrayList<Country> countries;
	
	public CountrySelector(ArrayList<Country> countries) {
		this.countries = countries;
		mapColors = AssetLoader.mapColors;
	}

	public Country pickCountry(Vector3 pick) {			
		int color = mapColors.getPixel((int) pick.x, (int) pick.y);		
		if (color != 0xffffffff) {
			for (Country country: countries) {
				if (country.getColor() == color) {
					Gdx.app.log("Country", country.getName());
					return country;
				}				
			}	
		}
		return null;
	}
}
