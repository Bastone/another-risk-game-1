package com.randomj.gameobjects;


import java.util.ArrayList;

import com.randomj.helpers.AssetLoader;

public class Map {

	private ArrayList<Country> countries;
	private ArrayList<Continent> continents;
	private int height;
	
	public Map() {		
		height = 628;
		init(height);
	}
	
	public void init(int height) {

		countries = new ArrayList<Country>(42);
		continents = new ArrayList<Continent>(6);

		//NORTH AMERICA
		countries.add(new Country("Alaska",80,height-71, 0x808014ff, countries.size()));
		countries.add(new Country("Alberta", 152,height-116,0xffff00ff, countries.size()));
		countries.add(new Country("Central America",142,height-263,0xffff64ff, countries.size()));
		countries.add(new Country("Eastern United States",214,height-190, 0x808000ff, countries.size()));
		countries.add(new Country("Greenland",450,height-37, 0xffff14ff, countries.size()));
		countries.add(new Country("Northwest Territory",193,height-79, 0x50642dff, countries.size()));
		countries.add(new Country("Ontario",226,height-124,0x949449ff, countries.size()));
		countries.add(new Country("Quebec",307,height-123, 0xffff80ff, countries.size()));		
		countries.add(new Country("Western United States",130,height-176, 0x50502dff, countries.size()));

		//AFRICA
		countries.add(new Country("Argentina",294,height-489, 0xff0000ff, countries.size()));
		countries.add(new Country("Brazil",339,height-403, 0x804040ff, countries.size()));
		countries.add(new Country("Peru",267,height-425, 0x800000ff, countries.size()));
		countries.add(new Country("Venezuela",268,height-335, 0xff8080ff, countries.size()));

		//EUROPE
		countries.add(new Country("Great Britain",528,height-110, 0x4080ff, countries.size()));
		countries.add(new Country("Iceland",509,height-81, 0x14ffff, countries.size()));
		countries.add(new Country("Northern Europe",606,height-131, 0xffff, countries.size()));
		countries.add(new Country("Scandinavia",606,height-83, 0x1480ffff, countries.size()));
		countries.add(new Country("Southern Europe",639,height-167, 0x144080ff, countries.size()));
		countries.add(new Country("Ukraine",697,height-110, 0x80ff, countries.size()));
		countries.add(new Country("Western Europe",558,height-163, 0x80ffff, countries.size()));

		//AFRICA
		countries.add(new Country("Congo",644,height-364, 0xae5714ff, countries.size()));
		countries.add(new Country("East Africa",696,height-310, 0xff8000ff, countries.size()));
		countries.add(new Country("Egypt",643,height-242, 0x804014ff, countries.size()));
		countries.add(new Country("Madagascar",745,height-454, 0xae5700ff, countries.size()));
		countries.add(new Country("North Africa",555,height-272, 0xff915bff, countries.size()));
		countries.add(new Country("South Africa",649,height-460, 0x804000ff, countries.size()));

		//ASIA
		countries.add(new Country("Afghanistan",805,height-157, 0x80ff64ff, countries.size()));
		countries.add(new Country("China",922,height-205, 0x148040ff, countries.size()));
		countries.add(new Country("India",851,height-239, 0x8080ff, countries.size()));
		countries.add(new Country("Irkutsk",966,height-121, 0x80ff14ff, countries.size()));
		countries.add(new Country("Japan",1098,height-128, 0x80ff00ff, countries.size()));
		countries.add(new Country("Kamchatka",1101,height-75, 0x8040ff, countries.size()));
		countries.add(new Country("Middle East",726,height-219, 0x148000ff, countries.size()));
		countries.add(new Country("Mongolia",962,height-162, 0x4014ff, countries.size()));
		countries.add(new Country("Siam",977,height-287, 0x80ff80ff, countries.size()));
		countries.add(new Country("Siberia",866,height-69, 0x8000ff, countries.size()));
		countries.add(new Country("Ural",796,height-92, 0x4000ff, countries.size()));
		countries.add(new Country("Yakutsk",960,height-70, 0x148080ff, countries.size()));

		//AUSTRALIA
		countries.add(new Country("Eastern Australia",1146,height-476, 0x400040ff, countries.size()));
		countries.add(new Country("Indonesia",1029,height-361, 0x8000ffff, countries.size()));
		countries.add(new Country("New Guinea",1141,height-388, 0xff00ffff, countries.size()));
		countries.add(new Country("Western Australia",1050,height-481, 0x800040ff, countries.size()));

		//CONTINENTS
		long pattern = 0;
		int i = 0;
		for (;i < 9; i++) {
			pattern = pattern | countries.get(i).getPattern();
		}
		continents.add(new Continent("North America", 5, pattern));

		pattern = 0;
		for (;i < 13; i++)
			pattern = pattern | countries.get(i).getPattern();
		continents.add(new Continent("South America", 2, pattern));

		pattern = 0;
		for (; i < 20; i++)
			pattern = pattern | countries.get(i).getPattern();
		continents.add(new Continent("Africa", 3, pattern));

		pattern = 0;
		for (; i < 26; i++)
			pattern = pattern | countries.get(i).getPattern();
		continents.add(new Continent("Europe", 5, pattern));

		pattern = 0;
		for (; i < 38; i++)
			pattern = pattern | countries.get(i).getPattern();
		continents.add(new Continent("Asia", 7, pattern));
		
		pattern = 0;
		for (; i < 42; i++)
			pattern = pattern | countries.get(i).getPattern();
		continents.add(new Continent("Australia", 2, pattern));
		
		//BORDERS	
		ArrayList<Country> borders = new ArrayList<Country>(); 
		
		//ALASKA
		borders.add(countries.get(5));
		borders.add(countries.get(31));
		borders.add(countries.get(1));
		countries.get(0).setBorders(borders);
		borders = new ArrayList<Country>(); 

		//ALBERTA
		borders.add(countries.get(6));
		borders.add(countries.get(5));
		borders.add(countries.get(0));
		borders.add(countries.get(8));
		countries.get(1).setBorders(borders);
		borders = new ArrayList<Country>(); 

		//CENTRAL AMERICA
		borders.add(countries.get(8));
		borders.add(countries.get(12));
		borders.add(countries.get(3));
		countries.get(2).setBorders(borders);
		borders = new ArrayList<Country>();

		//EASTERN UNITED STATES
		borders.add(countries.get(7));
		borders.add(countries.get(8));
		borders.add(countries.get(6));
		borders.add(countries.get(2));
		countries.get(3).setBorders(borders);
		borders = new ArrayList<Country>();

		//GREENLAND
		borders.add(countries.get(5));
		borders.add(countries.get(6));
		borders.add(countries.get(7));
		borders.add(countries.get(14));
		countries.get(4).setBorders(borders);
		borders = new ArrayList<Country>();

		//NORTHWEST TERRITORY
		borders.add(countries.get(0));
		borders.add(countries.get(1));
		borders.add(countries.get(6));
		borders.add(countries.get(4));
		countries.get(5).setBorders(borders);
		borders = new ArrayList<Country>();

		//ONTARIO
		borders.add(countries.get(1));
		borders.add(countries.get(5));
		borders.add(countries.get(3));
		borders.add(countries.get(7));
		borders.add(countries.get(8));
		borders.add(countries.get(4));
		countries.get(6).setBorders(borders);
		borders = new ArrayList<Country>();

		//QUEBEC
		borders.add(countries.get(6));
		borders.add(countries.get(3));
		borders.add(countries.get(4));
		countries.get(7).setBorders(borders);
		borders = new ArrayList<Country>();


		//WESTERN UNITED STATES
		borders.add(countries.get(1));
		borders.add(countries.get(6));
		borders.add(countries.get(3));
		borders.add(countries.get(2));
		countries.get(8).setBorders(borders);
		borders = new ArrayList<Country>();

		//ARGENTINA
		borders.add(countries.get(10));
		borders.add(countries.get(11));
		countries.get(9).setBorders(borders);
		borders = new ArrayList<Country>();

		//BRAZIL	
		borders.add(countries.get(9));
		borders.add(countries.get(11));
		borders.add(countries.get(12));
		borders.add(countries.get(24));
		countries.get(10).setBorders(borders);
		borders = new ArrayList<Country>();

		//PERù	
		borders.add(countries.get(9));
		borders.add(countries.get(10));
		borders.add(countries.get(12));
		countries.get(11).setBorders(borders);
		borders = new ArrayList<Country>();

		//VENEZUELA	
		borders.add(countries.get(2));
		borders.add(countries.get(11));
		borders.add(countries.get(10));
		countries.get(12).setBorders(borders);
		borders = new ArrayList<Country>();

		//GREAT BRITAIN
		borders.add(countries.get(14));
		borders.add(countries.get(19));
		borders.add(countries.get(15));
		borders.add(countries.get(16));
		countries.get(13).setBorders(borders);
		borders = new ArrayList<Country>();

		//ICELAND
		borders.add(countries.get(13));
		borders.add(countries.get(4));
		borders.add(countries.get(16));
		countries.get(14).setBorders(borders);
		borders = new ArrayList<Country>();


		//NORTHERN EUROPE
		borders.add(countries.get(19));
		borders.add(countries.get(13));
		borders.add(countries.get(17));
		borders.add(countries.get(16));
		borders.add(countries.get(18));
		countries.get(15).setBorders(borders);
		borders = new ArrayList<Country>();


		//SCANDINAVIA
		borders.add(countries.get(14));
		borders.add(countries.get(13));
		borders.add(countries.get(15));
		borders.add(countries.get(18));
		countries.get(16).setBorders(borders);
		borders = new ArrayList<Country>();

		//SOUTHERN EUROPE
		borders.add(countries.get(19));
		borders.add(countries.get(15));
		borders.add(countries.get(18));
		borders.add(countries.get(24));
		borders.add(countries.get(22));
		borders.add(countries.get(32));
		countries.get(17).setBorders(borders);
		borders = new ArrayList<Country>();

		//UKRAINE
		borders.add(countries.get(15));
		borders.add(countries.get(16));
		borders.add(countries.get(17));
		borders.add(countries.get(32));
		borders.add(countries.get(26));
		borders.add(countries.get(36));
		countries.get(18).setBorders(borders);
		borders = new ArrayList<Country>();

		//WESTERN EUROPE
		borders.add(countries.get(13));
		borders.add(countries.get(17));
		borders.add(countries.get(15));
		borders.add(countries.get(24));
		countries.get(19).setBorders(borders);
		borders = new ArrayList<Country>();

		//CONGO
		borders.add(countries.get(21));
		borders.add(countries.get(25));
		borders.add(countries.get(24));
		countries.get(20).setBorders(borders);
		borders = new ArrayList<Country>();

		//EAST AFRICA
		borders.add(countries.get(22));
		borders.add(countries.get(24));
		borders.add(countries.get(20));
		borders.add(countries.get(23));
		borders.add(countries.get(25));
		borders.add(countries.get(32));
		countries.get(21).setBorders(borders);
		borders = new ArrayList<Country>();

		//EGYPT
		borders.add(countries.get(24));
		borders.add(countries.get(21));
		borders.add(countries.get(32));
		borders.add(countries.get(17));
		countries.get(22).setBorders(borders);
		borders = new ArrayList<Country>();

		//MADAGASCAR
		borders.add(countries.get(25));
		borders.add(countries.get(21));
		countries.get(23).setBorders(borders);
		borders = new ArrayList<Country>();

		//NORTH AFRICA
		borders.add(countries.get(10));
		borders.add(countries.get(19));
		borders.add(countries.get(22));
		borders.add(countries.get(21));
		borders.add(countries.get(20));
		borders.add(countries.get(17));
		countries.get(24).setBorders(borders);
		borders = new ArrayList<Country>();

		//SOUTH AFRICA
		borders.add(countries.get(20));
		borders.add(countries.get(21));
		borders.add(countries.get(23));
		countries.get(25).setBorders(borders);
		borders = new ArrayList<Country>();

		//AFGHANISTAN
		borders.add(countries.get(32));
		borders.add(countries.get(28));
		borders.add(countries.get(27));
		borders.add(countries.get(36));
		borders.add(countries.get(18));
		countries.get(26).setBorders(borders);
		borders = new ArrayList<Country>();

		//CHINA
		borders.add(countries.get(28));
		borders.add(countries.get(26));
		borders.add(countries.get(34));
		borders.add(countries.get(33));
		borders.add(countries.get(35));
		borders.add(countries.get(36));
		countries.get(27).setBorders(borders);
		borders = new ArrayList<Country>();

		//INDIA
		borders.add(countries.get(32));
		borders.add(countries.get(26));
		borders.add(countries.get(27));
		borders.add(countries.get(34));
		countries.get(28).setBorders(borders);
		borders = new ArrayList<Country>();

		//IRKUTSK
		borders.add(countries.get(35));
		borders.add(countries.get(33));
		borders.add(countries.get(37));
		borders.add(countries.get(31));
		countries.get(29).setBorders(borders);
		borders = new ArrayList<Country>();

		//JAPAN
		borders.add(countries.get(33));
		borders.add(countries.get(31));
		countries.get(30).setBorders(borders);
		borders = new ArrayList<Country>();


		//KAMCHATKA
		borders.add(countries.get(37));
		borders.add(countries.get(29));
		borders.add(countries.get(33));
		borders.add(countries.get(30));
		borders.add(countries.get(0));
		countries.get(31).setBorders(borders);
		borders = new ArrayList<Country>();

		//MIDDLE EAST
		borders.add(countries.get(26));
		borders.add(countries.get(28));
		borders.add(countries.get(21));
		borders.add(countries.get(22));
		borders.add(countries.get(17));
		borders.add(countries.get(18));
		countries.get(32).setBorders(borders);
		borders = new ArrayList<Country>();

		//MONGOLIA
		borders.add(countries.get(27));
		borders.add(countries.get(35));
		borders.add(countries.get(29));
		borders.add(countries.get(31));
		borders.add(countries.get(30));
		countries.get(33).setBorders(borders);
		borders = new ArrayList<Country>();

		//SIAM
		borders.add(countries.get(28));
		borders.add(countries.get(27));
		borders.add(countries.get(39));
		countries.get(34).setBorders(borders);
		borders = new ArrayList<Country>();

		//SIBERIA
		borders.add(countries.get(36));
		borders.add(countries.get(37));
		borders.add(countries.get(29));
		borders.add(countries.get(33));
		borders.add(countries.get(27));
		countries.get(35).setBorders(borders);
		borders = new ArrayList<Country>();

		//URAL
		borders.add(countries.get(35));
		borders.add(countries.get(26));
		borders.add(countries.get(27));
		borders.add(countries.get(18));
		countries.get(36).setBorders(borders);
		borders = new ArrayList<Country>();

		//YAKUTSK
		borders.add(countries.get(35));
		borders.add(countries.get(29));
		borders.add(countries.get(31));
		countries.get(37).setBorders(borders);
		borders = new ArrayList<Country>();

		//EASTERN AUSTRALIA
		borders.add(countries.get(41));
		borders.add(countries.get(40));
		countries.get(38).setBorders(borders);
		borders = new ArrayList<Country>();

		//INDONESIA
		borders.add(countries.get(41));
		borders.add(countries.get(40));
		borders.add(countries.get(34));
		countries.get(39).setBorders(borders);
		borders = new ArrayList<Country>();

		//NEW GUINEA
		borders.add(countries.get(39));
		borders.add(countries.get(38));
		borders.add(countries.get(41));
		countries.get(40).setBorders(borders);
		borders = new ArrayList<Country>();

		//WESTERN AUSTRALIA
		borders.add(countries.get(39));
		borders.add(countries.get(38));
		borders.add(countries.get(40));
		countries.get(41).setBorders(borders);
		borders = new ArrayList<Country>();
		
	}
	
	public void reset() {
		for (Country country: countries)
			country.reset();		
	}

	public ArrayList<Continent> getContinents() {
		return continents;
	}
	
	public ArrayList<Country> getCountries() {
		return countries;
	}
}
