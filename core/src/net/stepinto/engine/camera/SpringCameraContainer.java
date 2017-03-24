package net.stepinto.engine.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class SpringCameraContainer extends BoundedCameraContainer2D {

	private float cutoffDist = .05f;
	private float distPerSec = 16f;
	private float zoomPerSec = 6f;
	private float rotPerSec = 10f;
	
	public SpringCameraContainer(OrthographicCamera camera, Vector2 target, Vector2 minPos, Vector2 maxPos, float minZoom, float maxZoom) {
		super(camera, target, minPos, maxPos, minZoom, maxZoom);
		
	}
	
	public SpringCameraContainer(OrthographicCamera camera, Vector2 target, Vector2 minPos, Vector2 maxPos) {
		super(camera, target, minPos, maxPos);
		
	}
	
	public SpringCameraContainer(OrthographicCamera camera, Vector2 target,	Vector2 maxPos) {
		super(camera, target, maxPos);
		
	}
	
	{
		if(getTargetPos() != null){
			Vector2 position = new Vector2(getTargetPos().x, getTargetPos().y);
			setPosition(position);
		}
	}


	@Override
	public void update(float dt) {

		Vector2 target = getTargetPos();

		if (target != null) {
			Vector2 position = new Vector2(getCamera().position.x, getCamera().position.y);
			Vector2 diff = new Vector2(target.x - position.x, target.y - position.y);
			
			if(diff.len() < cutoffDist){
				position = target;
			} else {
				position.x += distPerSec * dt * diff.x;
				position.y += distPerSec * dt * diff.y;
			}
			
			
			clampPosition(position);
			setPosition(position);
		}
		
		float currZoom = getCamera().zoom;
		float diff = getTargetZoom() - currZoom;
		getCamera().zoom += diff * dt * zoomPerSec;
		
		
		
		if(Math.abs(getRotationAmount()) < .1f ){
			rotate(getRotationAmount());
		} else {
			
			//THIS WORKS BUT DOENSN'T MAKE SENSE LOGICALLY. NEEDS UPDATING EVENTUALLY!
			rotate(getRotationAmount() * dt * rotPerSec);
		}
		
		getCamera().update();
	}
	
	
	
}
