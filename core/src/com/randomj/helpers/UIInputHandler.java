package com.randomj.helpers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.randomj.gameobjects.Card;
import com.randomj.ui.Button;
import com.randomj.ui.CardsViewer;
import com.randomj.ui.Window;

public class UIInputHandler implements InputProcessor { 
	
	private OrthographicCamera cam;
	private Button cardsButton, nextButton, missionButton;
	private Window missionWindow;
	private CardsViewer cardsViewer;
	private GameUpdater updater;
	
	public UIInputHandler(OrthographicCamera cam, GameRenderer renderer, GameUpdater updater) {
		this.cam = cam;
		this.cardsButton = renderer.getCardsButton();
		this.nextButton = renderer.getNextButton();
		this.missionButton = renderer.getMissionButton();
		this.cardsViewer = renderer.getCardsViewer();
		this.missionWindow = renderer.getMissionWindow();
		this.updater = updater;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 pick = cam.getPickRay(screenX, screenY).origin;
		if (cardsViewer.isVisible() && !cardsViewer.hits((int) pick.x, (int) pick.y))
			cardsViewer.hide();
		if (missionWindow.isVisible() && !missionWindow.hits((int) pick.x, (int) pick.y))
			missionWindow.hide();
		if (cardsButton.hits(pick.x, pick.y)) {
			cardsButton.down();
			return true;
		}
		if (nextButton.hits(pick.x, pick.y) && nextButton.isEnabled()) {
			nextButton.down();
			return true;
		}
		if (missionButton.hits(pick.x, pick.y)) {
			missionButton.down();
			return true;
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector3 pick = cam.getPickRay(screenX, screenY).origin;
		if (cardsButton.hits(pick.x, pick.y)) {
			cardsViewer.show();
			cardsButton.up();
			return true;
		}
		if (missionButton.hits(pick.x, pick.y)) {
			missionWindow.show();
			missionButton.up();
			return true;
		}			
		if (nextButton.hits(pick.x, pick.y) && nextButton.isEnabled()) {
			nextButton.up();		
			updater.buttonPressed();
			return true;
		}
		if (cardsViewer.isVisible() && cardsViewer.hits((int)pick.x, (int)pick.y)) {
			Card card = cardsViewer.pickCard((int)pick.x, (int)pick.y);
			if (card != null)
				updater.cardPicked(card);
			return true;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
