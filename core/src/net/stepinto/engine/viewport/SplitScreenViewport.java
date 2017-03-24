package net.stepinto.engine.viewport;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class SplitScreenViewport extends ScalingViewport {
	
	private Mode mode;
	private float fullWorldWidth;
	private float fullWorldHeight;
	
	public Mode getMode(){
		return this.mode;
	}
	
	public void setMode(Mode mode){
		this.mode = mode;
	}
	public enum Mode
	{
		Top,
		Bottom,
		Left,
		Right,
		TopLeft,
		TopRight,
		BottomLeft,
		BottomRight,
		Full
	}

	public SplitScreenViewport(Mode mode, float worldWidth, float worldHeight) {
		this(mode, worldWidth, worldHeight, new OrthographicCamera());
	}
	
	public SplitScreenViewport(Mode mode, float worldWidth, float worldHeight, Camera camera){
		super(Scaling.fit,(mode == Mode.Top || mode == Mode.Bottom || mode == Mode.Full) ? worldWidth : Math.round(worldWidth / 2.0f) ,
				(mode == Mode.Left || mode == Mode.Right || mode == Mode.Full) ?  worldHeight : Math.round(worldHeight / 2.0f),camera);
		
		fullWorldWidth = worldWidth;
		fullWorldHeight = worldHeight;
		setMode(mode);
		
		
	}
	
	
	@Override 
	public void update(int screenWidth, int screenHeight, boolean centerCamera){
		Vector2 scaled = getScaling().apply(fullWorldWidth, fullWorldHeight, screenWidth, screenHeight);
		int viewportWidth = Math.round(scaled.x);
		int viewportHeight = Math.round(scaled.y);
		
		
		switch(getMode())
		{
		case Top:
			setScreenBounds((screenWidth - viewportWidth) / 2, (screenHeight) / 2, viewportWidth, Math.round(viewportHeight / 2.0f));
			break;
		case Bottom:
			setScreenBounds((screenWidth - viewportWidth) / 2, (screenHeight - viewportHeight) / 2, viewportWidth, Math.round(viewportHeight / 2.0f));
			break;
		case Left:
			setScreenBounds((screenWidth - viewportWidth) / 2, (screenHeight - viewportHeight) / 2, Math.round(viewportWidth / 2.0f), viewportHeight);
			break;
		case Right:
			setScreenBounds((screenWidth) / 2, (screenHeight - viewportHeight) / 2, Math.round(viewportWidth / 2.0f), viewportHeight);
			break;
		case TopLeft:
			setScreenBounds((screenWidth - viewportWidth) / 2, (screenHeight) / 2, Math.round(viewportWidth / 2.0f), Math.round(viewportHeight / 2.0f));
			break;
		case TopRight:
			setScreenBounds((screenWidth) / 2, (screenHeight) / 2, Math.round(viewportWidth / 2.0f), Math.round(viewportHeight / 2.0f));
			break;
		case BottomLeft:
			setScreenBounds((screenWidth - viewportWidth) / 2, (screenHeight - viewportHeight) / 2, Math.round(viewportWidth / 2.0f), Math.round(viewportHeight / 2.0f));
			break;
		case BottomRight:
			setScreenBounds((screenWidth) / 2, (screenHeight - viewportHeight) / 2, Math.round(viewportWidth / 2.0f), Math.round(viewportHeight / 2.0f));
			break;
		case Full:
			setScreenBounds((screenWidth - viewportWidth) / 2, (screenHeight - viewportHeight) / 2, viewportWidth, viewportHeight);
			break;
		default:
		
		}
		
		//setScreenBounds((screenWidth - viewportWidth) / 2, (screenHeight - viewportHeight) / 2, viewportWidth, viewportHeight);
		//setScreenBounds(0,0,200, 200);
		
		apply(centerCamera);
	}

}
