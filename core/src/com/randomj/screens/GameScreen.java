package com.randomj.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.randomj.helpers.AssetLoader;
import com.randomj.helpers.CameraHandler;
import com.randomj.helpers.CountrySelector;
import com.randomj.helpers.GameRenderer;
import com.randomj.helpers.GameUpdater;
import com.randomj.helpers.MapInputHandler;
import com.randomj.helpers.UIInputHandler;
import com.randomj.net.PlayerClient;
import com.randomj.risk.Risk;

public class GameScreen implements Screen {

	private int gameWidth, gameHeight, screenWidth, screenHeight;
	private Risk risk;
	private OrthographicCamera mapCamera, uiCamera;
	private PlayerClient client;
	private GameRenderer renderer;
	private CameraHandler camHandler;
	private SpriteBatch mapBatch, uiBatch;
	private ShapeRenderer shape;
	private CountrySelector selector;
	private GameUpdater updater;
	
	public GameScreen(Risk risk, PlayerClient client) {		
		this.risk = risk;
		this.client = client;
		
		// Dimensions
		gameWidth = AssetLoader.map.getWidth();
		gameHeight = AssetLoader.map.getHeight() ;
		
		screenWidth = Gdx.graphics.getWidth();  
		screenHeight = Gdx.graphics.getHeight();
		
		// Cameras
		mapCamera = new OrthographicCamera(); 
		mapCamera.setToOrtho(false, gameWidth, gameHeight); 
		camHandler = new CameraHandler(mapCamera, gameWidth, gameHeight); 
		
		uiCamera = new OrthographicCamera(); 
		uiCamera.setToOrtho(false, screenWidth, screenHeight); 
		
		// Batches
		mapBatch = new SpriteBatch();
		uiBatch = new SpriteBatch();
		shape = new ShapeRenderer();
		mapBatch.setProjectionMatrix(mapCamera.combined);
		uiBatch.setProjectionMatrix(uiCamera.combined);	
		shape.setProjectionMatrix(mapCamera.combined);
			
		// Renderer
		renderer = new GameRenderer(gameWidth, gameHeight, screenWidth, screenHeight, client);
			
		// Updater
		updater = new GameUpdater(client);
		
		// Country selector
		selector = new CountrySelector(client.getInstance().getMap().getCountries());
		
		// Input handlers
		InputMultiplexer multiplexer = new InputMultiplexer(); 
		multiplexer.addProcessor(new UIInputHandler(uiCamera, renderer, updater)); 
		multiplexer.addProcessor(new MapInputHandler(camHandler, selector, updater));
		Gdx.input.setInputProcessor(multiplexer); 
	}

	@Override
	public void render(float delta) {
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		mapBatch.setProjectionMatrix(mapCamera.combined);
		shape.setProjectionMatrix(mapCamera.combined);
		uiBatch.setProjectionMatrix(uiCamera.combined);
	    
		renderer.renderMap(mapBatch, shape);
		renderer.renderUI(uiBatch, delta);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		
	}
	
	public float getScreenWidth() {
		return screenWidth;
	}

	public float getScreenHeight() {
		return screenHeight;
	}
}
