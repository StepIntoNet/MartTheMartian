package net.stepinto.engine.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class CameraContainer2D {

	private OrthographicCamera camera;
	private Vector2 targetPos;
	private float targetZoom;
	private float targetRotation;
	private float currentRotation;


	public CameraContainer2D(OrthographicCamera camera, Vector2 target) {
		this.camera = camera;
		setTargetPos(target);
		setTargetZoom(1.0f);
		setTargetRotation(0.0f);
		setCurrentRotation(0.0f);

	}

	public void update(float dt) {
		camera.position.set(targetPos, 0);
		camera.zoom = targetZoom;
		
		rotate(getRotationAmount());

		camera.update();
	}
	
	protected float getRotationAmount(){
		float targetRot = getTargetRotation();
		float currentRot = getCurrentRotation();
		float diff = targetRot - currentRot;
		float amtToRot = 0;
		
		if (diff != 0) {
			if (targetRot > currentRot) {
				if (diff < 180) {
					amtToRot = diff;
				} else {
					amtToRot = -360 + diff;
				}
			} else {
				if (diff > -180) {
					amtToRot = diff;
				} else {
					amtToRot = 360 + diff;
				}
			}
			return amtToRot;
		} 
		
		return 0.0f;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public Vector2 getTargetPos() {
		return targetPos;
	}

	public void setTargetPos(Vector2 target) {
		this.targetPos = target;
	}

	public float getTargetZoom() {
		return targetZoom;
	}

	public void setTargetZoom(float targetZoom) {
		this.targetZoom = targetZoom;
	}

	public float getTargetRotation() {
		return targetRotation;
	}

	public void setTargetRotation(float targetRotation) {
		this.targetRotation = targetRotation % 360;
		if(this.targetRotation < 0){
			this.targetRotation += 360;
		}
	}

	public float getCurrentRotation() {
		return currentRotation;
	}
	
	public void rotate(float amt){
		setCurrentRotation(getCurrentRotation() + amt);
		camera.rotate(amt);
	}

	protected void setCurrentRotation(float rotationAmount) {
		this.currentRotation = rotationAmount % 360;
		if(this.currentRotation < 0){
			this.currentRotation += 360;
		}
		
		
	}

	protected void setPosition(Vector2 position) {
		getCamera().position.set(position, 0);
	}

	public Vector2 getCurrentPosition() {
		Vector2 pos = new Vector2(getCamera().position.x, getCamera().position.y);
		return pos;
	}
	
	public void resize(int w, int h){}

}
