package com.randomj.helpers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.randomj.gameobjects.Card;
import com.randomj.gameobjects.Country;
import com.randomj.gameobjects.Dice;
import com.randomj.gameobjects.Enums.SubPhase;
import com.randomj.gameobjects.Enums.TurnPhase;
import com.randomj.gameobjects.GameInstance;
import com.randomj.net.PlayerClient;
import com.randomj.players.Player;

public class GameUpdater {
	
	private PlayerClient client;
	private Country origin, target;
	private GameInstance game;
	private ArrayList<Card> picked;
	private int min;
	private Pixmap mapColors;
	
	public GameUpdater(PlayerClient client) {
		this.client = client;
		mapColors = AssetLoader.mapColors;
		picked = new ArrayList<Card>();
		origin = null;
		target = null;
		min = 0;
	}
	
	public void cardPicked(Card card) {
		if (client.itsYourTurn()) {
			if (game.getPhase() == TurnPhase.REINFORCEMENT)
				if (picked.contains(card)) {
					card.deselect();
					picked.remove(card);
					game.setSubPhase(SubPhase.SELECTION);
					client.send(game);
				} else if (game.getSubPhase() == SubPhase.SELECTION && picked.size() < 3) {
					card.select();
					picked.add(card);
					if (picked.size() == 3)
						if (areTradable(picked))
							game.setSubPhase(SubPhase.CARDS_TRADING);	
					client.send(game);
				}
		}
	}

	public void voidPick() {
		if (client.itsYourTurn()) {
			switch (game.getPhase()) {
			
			case REINFORCEMENT:
				for (Card card: picked)
					card.deselect();
				picked.clear();
				game.setSubPhase(SubPhase.SELECTION);
				client.send(game);
				break;
			
			case ATTACK_PHASE:
				game.log(game.getCurrentPlayer().getName() + ", attack a country or go to the fortify phase");
				game.setSubPhase(SubPhase.SELECTION);
				origin = null;
				target = null;
				client.send(game);
				break;
				
			case FORTIFYING:
				if (!game.hasFortified()) {
					game.log(game.getCurrentPlayer().getName() + ", fortify a country or end turn");
					game.setSubPhase(SubPhase.SELECTION);
					origin = null;
					target = null;
					client.send(game);
				}
				break;
				
			default:
				break;
			}
		}
	}
	
	public void picked(Country country) {
		if (client.itsYourTurn()) {
			switch (game.getPhase()) {
			
			case REINFORCEMENT:
				if (game.getSubPhase() == SubPhase.SELECTION) {
					// Player owns that country and has some units to set
					if (game.getCurrentPlayer().owns(country) && game.getCurrentPlayer().getUnits() != 0) {
						setUnits(country, 1);
						client.send(game);
					}
				}
				break;
				
			case ATTACK_PHASE:		
				switch (game.getSubPhase()) {
				
				case SELECTION: // Selects a country to attack with
					if (game.getCurrentPlayer().owns(country) && country.getUnits() > 1) {
						origin = country;
						game.log("Selected " + country.getName() + ", now pick a target");
						game.setSubPhase(SubPhase.TARGETING);
						client.send(game);
					} else
						voidPick();
					break;
					
				case TARGETING: // Targets a near country
					if (!game.getCurrentPlayer().owns(country) && country.getBorders().contains(origin)) {
						target = country;
						game.log("Targeted " + country.getName());
						game.setSubPhase(SubPhase.ATTACKING);
						client.send(game);
					} else 
						voidPick();
					break;
					
				case MOVING_UNITS:
					
					if (country == target && origin.getUnits() > 1) {
						moveUnits(origin, target, 1);
						client.send(game);
					} else if (country == origin && target.getUnits() > min) {
						moveUnits(target, origin, 1);
						client.send(game);
					}
					break;
					
				default: 
					break;
				}
				break;
				
			case FORTIFYING:
				switch (game.getSubPhase()) {

				case SELECTION:
					if (game.getCurrentPlayer().owns(country)) {
						origin = country;
						game.log("Selected " + country.getName() + ", select another close country you own");
						game.setSubPhase(SubPhase.TARGETING);
						client.send(game);
					} else 
						voidPick();
					break;
					
				case TARGETING:
					if (game.getCurrentPlayer().owns(country) && country.getBorders().contains(origin)) {
						target = country;
						game.log("Selected " + country.getName() + ", tap to move your units");
						game.setSubPhase(SubPhase.MOVING_UNITS);
						client.send(game);
					} else 
						voidPick();
					break;
					
				case MOVING_UNITS:
					if (country == target && origin.getUnits() > 1) {
						moveUnits(origin, target, 1);
						game.fortified();
						client.send(game);
					} else if (country == origin && target.getUnits() > 1) {
						moveUnits(target, origin, 1);
						game.fortified();
						client.send(game);
					}
					break;		
					
				default:
					break;
				}
				break;
			default:
				break;		
			}
		}
	}
	
	public void buttonPressed() {
		if (client.itsYourTurn()) {
			switch (game.getPhase()) {
			
			case REINFORCEMENT:
				if (game.getSubPhase() == SubPhase.SELECTION) {
					// Goes to attack phase
					nextPhase();
					game.log(game.getCurrentPlayer().getName() + ", attack a country or go to the fortify phase");	
				} else {
					trade(game.getCurrentPlayer(), picked);
					game.log(game.getCurrentPlayer().getName() + " traded a set of cards!");			
				}
				break;
				
			case ATTACK_PHASE: 
				switch (game.getSubPhase()) {
				
				case ATTACKING: // Attacks
					attack(origin, target, game.getDice());
					game.startRolling();
					if (target.getUnits() <= 0) {
						Player previousOwner = target.getOwner();
						capture(origin, target, game.getDice());
						if (previousOwner.defeated())
							game.getPlayers().remove(previousOwner);
						if (game.getCurrentPlayer().wins()) {
							game.setPhase(TurnPhase.END);
							game.log(game.getCurrentPlayer().getName() + " won!");
						} else {
							game.setSubPhase(SubPhase.MOVING_UNITS);
							game.log(game.getCurrentPlayer().getName() + " conquers " + target.getName() + "! Now move your units");
							game.conquered();
						}
					} else if (origin.getUnits() <= 1) {
						game.setSubPhase(SubPhase.SELECTION);
						game.log(game.getCurrentPlayer().getName() + " attack another country or go to fortufy phase");
					}				
					game.stopRolling();
					break;
					
				case MOVING_UNITS: // Goes back to selection
					target = null;
					origin = null;				
					game.log(game.getCurrentPlayer().getName() + ", attack another country or go to fortify phase");
					game.setPhase(TurnPhase.ATTACK_PHASE);
					game.setSubPhase(SubPhase.SELECTION);
					break;
					
				default: // Goes to fortify phase
					nextPhase();
					game.getCurrentPlayer().addCard(game.getDeck().draw());
					game.log(game.getCurrentPlayer().getName() + ", fortify a country or end turn");
					break;
				}
				break;
								
			case FORTIFYING: // End turn
				nextPhase();
				game.nextPlayer();
				game.getCurrentPlayer().beginTurn();
				if (game.getCurrentPlayer().getCards().size() >= 3) 
					game.log(game.getCurrentPlayer().getName() + ", trade your cards or reinforce your territories");
				else
					game.log(game.getCurrentPlayer().getName() + ", tap on a territory to reinforce it");
				if (!client.isMultiplayer())
					client.setPlayer(game.getCurrentPlayer());
				client.send(game);
				break;
			default:
				break;	
			}
			client.send(game);
		}
	}
	
	private void capture(Country origin, Country target, Dice dice) {
		Player previousOwner = target.getOwner();
		previousOwner.loseCountry(target);
		origin.getOwner().conquerCountry(target);

		min = dice.getAttackerDice().size() - dice.getAttackerLoss();
		origin.loseUnits(min);
		target.addUnits(min);
	}

	public void setUnits(Country target, int amount) {
		target.addUnits(amount);
		target.getOwner().removeUnits(amount);
	}
	
	public void moveUnits(Country origin, Country target, int amount) {
		origin.loseUnits(amount);
		target.addUnits(amount);
	}
	
	public void attack(Country origin, Country target, Dice dice) {
		int nDiceAttacker = origin.getUnits() - 1;
		int nDiceDefender = target.getUnits();
		nDiceAttacker = MathUtils.clamp(nDiceAttacker, 1, 3);
		nDiceDefender = MathUtils.clamp(nDiceDefender, 1, nDiceAttacker);
		
		dice.roll(nDiceAttacker, nDiceDefender);

		origin.loseUnits(dice.getAttackerLoss());
		target.loseUnits(dice.getDefenderLoss());
	}
	
	public boolean areTradable(ArrayList<Card> traded) {
		int nWildCards = 0;
		for (Card card: traded)
			if (card.isWildCard())
				nWildCards++;
		if (nWildCards > 1)
			return false;
		
		int and = traded.get(0).getPattern() & traded.get(1).getPattern() & traded.get(2).getPattern();
		int or = traded.get(0).getPattern() | traded.get(1).getPattern() | traded.get(2).getPattern();
		
		if (or == 7 && and == 0)
			return true;
		else if (and == 1 || and == 2 || and == 4)
			return true;
		
		return false;
	}
	
	public void trade(Player player, ArrayList<Card> traded) {
		int and = traded.get(0).getPattern() & traded.get(1).getPattern() & traded.get(2).getPattern();
		int or = traded.get(0).getPattern() | traded.get(1).getPattern() | traded.get(2).getPattern();
		
		if (or == 7 && and == 0)
			player.addUnits(10);
		else if (and == 1)
			player.addUnits(4);
		else if (and == 2)
			player.addUnits(6);
		else if (and == 4)
			player.addUnits(8);
		
		for (Card card: traded)
			player.getCards().remove(card);
	}
	
	public void nextPhase() {	
		switch (game.getPhase()) {
		case REINFORCEMENT:
			game.setPhase(TurnPhase.ATTACK_PHASE);		
			break;
		case ATTACK_PHASE:
			game.setPhase(TurnPhase.FORTIFYING);
			break;
		case FORTIFYING:
			game.setPhase(TurnPhase.REINFORCEMENT);
			break;
		default:
			break;
		}
		game.setSubPhase(SubPhase.SELECTION);
	}

	public Country pickCountry(Vector3 pick) {
		int color = mapColors.getPixel((int) pick.x, (int) pick.y);		
		if (color != 0xffffffff)
			for (Country country: game.getMap().getCountries())
				if (country.getColor() == color)
					return country;	
		return null;
	}
	
	public void setGameInstance(GameInstance game) {
		this.game = game;
	}
}
