package com.randomj.helpers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class CameraHandler { 

	private OrthographicCamera cam;
	private float width, height;
	
	public CameraHandler(OrthographicCamera cam, float width, float height) {
		this.cam = cam;
		this.width = width;
		this.height = height;
	}
	
	public void checkCameraBorders() { 
		cam.zoom = MathUtils.clamp(cam.zoom, 0.5f, width / cam.viewportWidth);
		
		float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
		float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

		cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, width - effectiveViewportWidth / 2f);
		cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, height - effectiveViewportHeight / 2f);
	}
	
	public void translateCamera(float deltaX, float deltaY) { 
		cam.translate(deltaX, deltaY);
		checkCameraBorders();
		cam.update();
	}

	public void zoomCamera() {
		cam.zoom -= 0.1f;
		checkCameraBorders();
		cam.update();
	}
	
	public void unZoomCamera() {
		cam.zoom += 0.1f;
		checkCameraBorders();
		cam.update();
	}
	
	public Vector3 pick(float x, float y) {
		Vector3 pick = cam.getPickRay(x, y).origin;
		pick.y = height - pick.y;
		return pick;
	}

}
