package com.randomj.gameobjects;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Dice {

	private ArrayList<Integer> attacker, defender;
	private int attackerLoss, defenderLoss;
	
	public Dice() {}
	
	public void roll(int nDiceAttacker, int nDiceDefender) {
		attackerLoss = defenderLoss = 0;
		attacker = new ArrayList<Integer>(3);
		defender = new ArrayList<Integer>(3);
		int min = Math.min(nDiceAttacker, nDiceDefender);

		while (nDiceAttacker > 0) {
			attacker.add(MathUtils.random(1,6));
			nDiceAttacker--;
		}
		while (nDiceDefender > 0) {
			defender.add(MathUtils.random(1,6));
			nDiceDefender--;
		}

		attacker.sort(Collections.reverseOrder());
		defender.sort(Collections.reverseOrder());
	
		for (int i = 0; i < min; i++) {
			if (attacker.get(i) > defender.get(i))
				attackerLoss ++;
			else
				defenderLoss ++;
		}
	}
	
	public ArrayList<Integer> getAttackerDice() {
		return attacker;
	}
	
	public ArrayList<Integer> getDefenderDice() {
		return defender;
	}
	
	public int getAttackerLoss() {
		return attackerLoss;
	}
	
	public int getDefenderLoss() {
		return defenderLoss;
	}

}
