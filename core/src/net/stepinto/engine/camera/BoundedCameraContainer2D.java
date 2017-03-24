package net.stepinto.engine.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class BoundedCameraContainer2D extends CameraContainer2D {

	private Vector2 minPos;
	private Vector2 maxPos;
	
	private float maxZoom;
	private float maxScreenZoom;
	private float minZoom;
	
	public void setMaxZoom(float maxZoom){
		this.maxZoom = maxZoom;
	}
	
	public float getMaxZoom(){
		return maxZoom;
	}
	
	public void setMinZoom(float minZoom){
		this.minZoom = minZoom;
	}
	
	public float getMinZoom(){
		return minZoom;
	}
	
	private float getMaxScreenZoom(){
		return maxScreenZoom;
	}
	
	private void calcMaxScreenZoom(){
		float maxWidth = maxPos.x - minPos.x;
		float maxHeight = maxPos.y - minPos.y;
		
		if(maxWidth / getCamera().viewportWidth < maxHeight / getCamera().viewportHeight) {
			maxScreenZoom = maxWidth / getCamera().viewportWidth;
		} else {
			maxScreenZoom = maxHeight / getCamera().viewportHeight;
		}
	}
	
	@Override
	public void setTargetZoom(float targetZoom) {
		if(targetZoom > getMaxZoom() || targetZoom > getMaxScreenZoom()){
			super.setTargetZoom(getMaxZoom() < getMaxScreenZoom() ? getMaxZoom() : getMaxScreenZoom());
		} else if (targetZoom < getMinZoom()){
			super.setTargetZoom(getMinZoom());
		} else {
		super.setTargetZoom(targetZoom); 
		}
	}

	public BoundedCameraContainer2D(OrthographicCamera camera, Vector2 target, Vector2 maxPos) {
		this(camera, target, new Vector2(0f,0f), maxPos, 0.0f, 5.0f);
		
	}

	public BoundedCameraContainer2D(OrthographicCamera camera, Vector2 target,Vector2 minPos, Vector2 maxPos) {
		this(camera, target, minPos, maxPos, 0.05f, 5.0f);
	}
	
	public BoundedCameraContainer2D(OrthographicCamera camera, Vector2 target,Vector2 minPos, Vector2 maxPos, float minZoom, float maxZoom){
		super(camera, target);
		this.minPos = minPos;
		this.maxPos = maxPos;
		setMinZoom(minZoom);
		setMaxZoom(maxZoom);
		calcMaxScreenZoom();
		setTargetZoom(1.0f);
	}
	
	

	public void clampPosition(Vector2 position) {
		float width = getCamera().viewportWidth * getCamera().zoom;
		float height = getCamera().viewportHeight * getCamera().zoom;
		
		float maxWidth = maxPos.x - minPos.x;
		float maxHeight = maxPos.y - minPos.y;
		
		if(width > maxWidth || height > maxHeight){
			float widthRatio = width / maxWidth;
			float heightRation = height / maxHeight;
			
			if(widthRatio > heightRation){
				getCamera().zoom = maxWidth / getCamera().viewportWidth;
			} else {
				getCamera().zoom = maxHeight / getCamera().viewportHeight;
			}
			width = getCamera().viewportWidth * getCamera().zoom;
			height = getCamera().viewportHeight * getCamera().zoom;
		}

		if (position.x - width / 2 < minPos.x) {
			position.x = minPos.x + width / 2;
		} else if (position.x + width / 2 > maxPos.x) {
			position.x = maxPos.x - width / 2;
		}

		if (position.y - height / 2 < minPos.y) {
			position.y = minPos.y + height / 2;
		} else if (position.y + height / 2 > maxPos.y) {
			position.y = maxPos.y - height / 2;
		}
	}

	@Override
	public void update(float dt) {

		Vector2 target = getTargetPos();
		getCamera().zoom = getTargetZoom();

		if (target != null) {

			Vector2 position = new Vector2();
			position.x = target.x;
			position.y = target.y;

			clampPosition(position);
			setPosition(position);
			
		}
		rotate(getRotationAmount());
		getCamera().update();
	}
	
	@Override
	public void resize(int width, int height){
		calcMaxScreenZoom();
	}
}
