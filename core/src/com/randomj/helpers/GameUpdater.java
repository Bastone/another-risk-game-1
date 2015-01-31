package com.randomj.helpers;

import com.badlogic.gdx.math.MathUtils;
import com.randomj.gameobjects.Country;
import com.randomj.gameobjects.Enums.TurnPhase;
import com.randomj.gameobjects.GameInstance;
import com.randomj.net.PlayerClient;

public class GameUpdater {
	
	private PlayerClient client;
	private GameRenderer renderer;
	private Country origin, target;
	
	public GameUpdater(PlayerClient client, GameRenderer renderer) {
		this.client = client;
		this.renderer = renderer;
		this.origin = null;
		this.target = null;
		if (client.getPlayer().equals(client.getInstance().getCurrentPlayer()))
			renderer.getNextButton().setText("Placing " + client.getInstance().getCurrentPlayer().getUnits() + " more units");
		else
			renderer.getNextButton().setText("Waiting");
		renderer.getNextButton().disable();
	}
	
	public void voidPick() {
		GameInstance game =  client.getInstance();
		switch (game.getPhase()) {
		case ATTACK_PHASE:
			if (renderer.getDiceViewer().isVisible()) {
				renderer.getDiceViewer().hide();
			}
			break;
		case FORTIFYING:
			break;
		default:
			break;
		
		}
	}
	
	public void picked(Country country) {
		GameInstance game =  client.getInstance();
		if (client.getPlayer().equals(game.getCurrentPlayer())) {
			switch (game.getPhase()) {
			case REINFORCEMENT:
				if (game.getCurrentPlayer().owns(country) && game.getCurrentPlayer().getUnits() != 0) {
					game.getCurrentPlayer().setUnits(country, 1);
					renderer.getNextButton().setText("Placing " + game.getCurrentPlayer().getUnits() + " more units");
					game.log(client.getPlayer().getName() + " places 1 unit in " + country.getName());
					client.send(game);
				}
				if (game.getCurrentPlayer().getUnits() == 0) {
					renderer.getNextButton().setText("Next");
					renderer.getNextButton().enable();
				}
			break;
			case ATTACK_PHASE:
				if (origin == null) {
					if (game.getCurrentPlayer().owns(country) && country.getUnits() > 1) {
						origin = country;
						game.log("Selected: " + country.getName());
						renderer.getNextButton().setText("Choose target");
					}
				} else if (target == null) {
					if (origin.getBorders().contains(country)) {
						target = country;
						game.log("Targeted: " + country.getName());
						renderer.getNextButton().setText("Attack");
						renderer.getNextButton().enable();
					} else if (origin == country) {
						origin = null;
						game.log("Unselected: " + country.getName());
						renderer.getNextButton().setText("Choose origin");
					}
				} else if (target == country) {
					target = null;
					game.log("Untargeted: " + country.getName());
					renderer.getNextButton().setText("Choose target");
					renderer.getNextButton().disable();
				}		
				break;
			case FORTIFYING:
				break;
			default:
				break;			
			}
		}
	}
	
	public void buttonPressed() {
		GameInstance game =  client.getInstance();
		if (client.getPlayer().equals(game.getCurrentPlayer())) {
			switch (game.getPhase()) {
			case REINFORCEMENT:
				game.setPhase(TurnPhase.ATTACK_PHASE);	
				renderer.getNextButton().setText("Choose origin");
				renderer.getNextButton().disable();
				break;
			case ATTACK_PHASE:
				int nDiceAttacker = origin.getUnits();
				int nDiceDefender = target.getUnits();
				MathUtils.clamp(nDiceAttacker, 1, 3);
				MathUtils.clamp(nDiceDefender, 1, nDiceAttacker);
				
				game.getDice().roll(nDiceAttacker, nDiceDefender);
				renderer.getDiceViewer().show();
				break;
			case FORTIFYING:
				break;
			default:
				break;			
			}
		}
	}
}
