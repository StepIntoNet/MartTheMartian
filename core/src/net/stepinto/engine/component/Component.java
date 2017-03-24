package net.stepinto.engine.component;

public abstract class Component {

		//Higher priority gets rendered/updated first
		private int priority;
		private String name;
		
		public int getPriority()
		{
			return priority;
		}
		
		public String getName()
		{
			return name;
		}
		
		public Component(String name, int priority)
		{
			this.name = name;
			this.priority = priority;
		}
		
		public Component()
		{
			this("nameless", 0);
		}
		
		public abstract void update(float deltaT);
		
		public void render(float deltaT)
		{
			
		}
		
	}

