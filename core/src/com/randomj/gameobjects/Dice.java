package com.randomj.gameobjects;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

public class Dice {

	private ArrayList<Integer> attacker, defender;
	private int attackerLoss, defenderLoss;
	
	public Dice() {}
	
	public void roll(int nDiceAttacker, int nDiceDefender) {
		attackerLoss = defenderLoss = 0;
		attacker = new ArrayList<Integer>(nDiceAttacker);
		defender = new ArrayList<Integer>(nDiceDefender);
		int min = Math.min(nDiceAttacker, nDiceDefender);
		
		for (int i = 0; i < nDiceAttacker; i++) 
			attacker.add(MathUtils.random(1,6));
		for (int i = 0; i < nDiceDefender; i++) 
			defender.add(MathUtils.random(1,6));
	
		
		attacker.sort(Collections.reverseOrder());
		defender.sort(Collections.reverseOrder());
		Gdx.app.log("Sorted dice", toString());
		
		for (int i = 0; i < min; i++) {
			if (attacker.get(i) > defender.get(i))
				defenderLoss ++;
			else
				attackerLoss ++;
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
	
	public String toString() {
		String string = "Attacker: ";
		for (int die: attacker)
			string += die + " ";
		string += " Defender:";
		for (int die: defender)
			string += die + " ";
		return string;
	}
}
