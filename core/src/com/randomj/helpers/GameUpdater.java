package com.randomj.helpers;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
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
	private int min;
	
	public GameUpdater(PlayerClient client) {
		this.client = client;
		game =  client.getInstance();
		origin = null;
		target = null;
		min = 0;
	}
	
	public void cardPicked(Card card) {
		
	}

	public void voidPick() {
		if (client.itsYourTurn()) {
			switch (game.getPhase()) {
			
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
				// Player owns that country and has some units to set
				if (game.getCurrentPlayer().owns(country) && game.getCurrentPlayer().getUnits() != 0) {
					setUnits(country, 1);
					game.log(client.getPlayer().getName() + " places 1 unit in " + country.getName());
					client.send(game);
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
				// Goes to attack phase
				nextPhase();
				game.log(game.getCurrentPlayer().getName() + ", attack a country or go to the fortify phase");	
				client.send(game);
				break;
				
			case ATTACK_PHASE: 
				switch (game.getSubPhase()) {
				
				case ATTACKING: // Attacks
					attack(origin, target, game.getDice());
					if (target.getUnits() <= 0) {
						capture(origin, target, game.getDice());
						game.setSubPhase(SubPhase.MOVING_UNITS);
						game.log(game.getCurrentPlayer().getName() + " conquers " + target.getName() + "! Now move your units");
						game.conquered();
					} else if (origin.getUnits() <= 1) {
						game.setSubPhase(SubPhase.SELECTION);
						game.log(game.getCurrentPlayer().getName() + " attack another country or go to");
					}				
					client.send(game);
					break;
					
				case MOVING_UNITS: // Goes back to selection
					target = null;
					origin = null;				
					game.log(game.getCurrentPlayer().getName() + ", attack another country or go to fortify phase");
					game.setPhase(TurnPhase.ATTACK_PHASE);
					game.setSubPhase(SubPhase.SELECTION);
					client.send(game);
					break;
					
				default: // Goes to fortify phase
					nextPhase();
					game.log(game.getCurrentPlayer().getName() + ", fortify a country or end turn");
					client.send(game);
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
				client.send(game);
				break;

			default:
				break;		
			}
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
	
	public boolean areTradable(Player player, ArrayList<Card> traded) {
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
	}
	
	public void nextPhase() {
		GameInstance game = client.getInstance();
		
		switch (game.getPhase()) {
		case REINFORCEMENT:
			game.setPhase(TurnPhase.ATTACK_PHASE);
			game.setSubPhase(SubPhase.SELECTION);
			break;
		case ATTACK_PHASE:
			game.setPhase(TurnPhase.FORTIFYING);
			game.setSubPhase(SubPhase.SELECTION);
			break;
		case FORTIFYING:
			game.setPhase(TurnPhase.REINFORCEMENT);
			break;
		default:
			break;
		}
	}
}
