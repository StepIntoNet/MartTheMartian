package net.stepinto.engine.component;

import java.util.ArrayList;
import java.util.List;

public class ComponentManager {

	private List<Component> components;
	IGameTimer timer;
	
	public ComponentManager(IGameTimer timer)
	{
		components = new ArrayList<Component>();
		//perhaps pass in the prevTime and currTime values as System.currentTimeMillis() might not be available on all systems. 
		this.timer = timer;
	}
	
	public void registerComponent(Component c)
	{
		if(c == null)
		{
			return;
		}
		
		for(int i = 0; i <= components.size(); i++)
		{
			if( i == components.size() || c.getPriority() > components.get(i).getPriority())
			{
				components.add(i, c);
				break;
			}
		}
	}
	
	//Make a new method for processing delta in a system like LibGDX where delta is provided. 
	public void process(long systemTime)
	{
	
		update(timer.getDeltaTime());
		render(timer.getDeltaTime());
	}
	
	public void render(float deltaT)
	{
		for(Component c : components)
		{
			c.render(deltaT);
		}
	}
	
	public void update(float deltaT)
	{
		for(Component c : components)
		{
			c.update(deltaT);
		}
	}
}
