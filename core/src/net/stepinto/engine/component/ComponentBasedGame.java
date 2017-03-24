package net.stepinto.engine.component;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ComponentBasedGame extends ApplicationAdapter implements ApplicationListener {
	
	private SpriteBatch batch;
	private ComponentManager components;
	
	private List<IResizeable> resizeCallbacks;
	
	private void registerResizeable(IResizeable resize){
		resizeCallbacks.add(resize);
	}
	
	public void clearResizeList(){
		resizeCallbacks = new ArrayList<IResizeable>();
	}

	protected SpriteBatch getSpriteBatch()
	{
		return batch;
	}
	private void setSpriteBatch(SpriteBatch batch)
	{
		this.batch = batch;
	}
	
	
 	private ComponentManager getComponentManager()
	{
		return components;
	}
	private void setComponentManager(ComponentManager manager)
	{
		components = manager;
	}
	
	@Override
	public void create () {
		
		
		setSpriteBatch(new SpriteBatch());
		setComponentManager(new ComponentManager(new LibGDXGameTimer()));
		clearResizeList();

	}
	
	public void addComponent(Component gameComponent)
	{
		if(gameComponent != null)
		{
			components.registerComponent(gameComponent);
			if(gameComponent instanceof IResizeable){
				System.out.println("Gamecomponent registered as resizeable in componentManager");
				registerResizeable((IResizeable)gameComponent);
			}
		}
	}

	@Override
	public void render () {
		//System.currentTimeMillis() might not be available on all systems and would need a suitable alternative if not
		getComponentManager().process(System.currentTimeMillis());
		
		
	}
	
	@Override
	public void resize(int w, int h)
	{
		for(IResizeable res : resizeCallbacks){
			res.resize(w, h);
		}
	}
}

