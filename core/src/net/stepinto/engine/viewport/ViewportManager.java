package net.stepinto.engine.viewport;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.stepinto.engine.component.IResizeable;

public class ViewportManager implements IResizeable{

	private Map<String, Viewport>viewports;
	
	public ViewportManager(){
		viewports = new HashMap<String, Viewport>();
	}
	
	public void registerViewport(String viewName, Viewport viewport){
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		viewports.put(viewName, viewport);
		
	}
	
	public void registerViewport(String viewName, int width, int height, SplitScreenViewport.Mode mode){
		SplitScreenViewport viewport = new SplitScreenViewport(mode, width, height, new OrthographicCamera());
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		viewports.put(viewName, viewport);
		
	}
	
	public Viewport getViewport(String name){
		return viewports.get(name);
	}
	
	public void beginRender(SpriteBatch batch, String view){
		batch.setProjectionMatrix(getViewport(view).getCamera().combined);
		batch.begin();
	}
	
	@Override
	public void resize(int w, int h) {
		for(Viewport viewport : viewports.values())
		{
			viewport.update(w,h,false);
		}
	}

}
