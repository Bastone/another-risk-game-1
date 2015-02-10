package com.randomj.gameobjects;

import com.randomj.gameobjects.Enums.MissionType;
import com.randomj.players.Player;

public class Mission {
	private String description;
	private Player target;
	private long pattern;
	private int nTerritories, nUnits;
	private MissionType mission;
	
	public Mission() {}
	
	public Mission(MissionType mission, String description, long pattern) {
		this.mission = mission;
		this.description = description;
		this.pattern = pattern;
	}
	
	public Mission(MissionType mission, String description, Player target) {
		this.mission = mission;
		this.description = description;
		this.target = target;
	}
	
	public Mission(MissionType mission, String description, int nTerritories, int nUnits) {
		this.mission = mission;
		this.description = description;
		this.nTerritories = nTerritories;
		this.nUnits = nUnits;
	}

	public boolean accomplished(Player player) {
		switch (mission) {
		case CAPTURE_CONTINENTS:
			return ((pattern & player.getPattern()) == pattern);
		case CAPTURE_CONTINENTS_PLUS_ONE:
			return ((pattern & player.getPattern()) == pattern && player.getOwnedContinents().size() >= 3);
		case CAPTURE_N_TERRITORIES:
			if (player.getOwnedCountries().size() >= nTerritories)
				if (nUnits == 1)
					return true;
				else {
					int n = 0;
					for (Country country: player.getOwnedCountries())
						if (country.getUnits() >= nUnits)
							n++;
					if (n >= nTerritories)
						return true;
				}
			return false;
		case DEFEAT_PLAYER:
			return (target.defeated());
		default:
			return false;
		}
	}
	
	public String getDescription() {
		return description;
	}
}
