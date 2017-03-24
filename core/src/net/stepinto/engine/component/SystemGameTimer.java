package net.stepinto.engine.component;

public class SystemGameTimer implements IGameTimer{

	private long previousTime;
	private long currentTime;
	
	public SystemGameTimer(){
		previousTime = System.currentTimeMillis();
		currentTime = System.currentTimeMillis();
	}
	
	@Override
	public float getDeltaTime() {
		previousTime = currentTime;
		currentTime = System.currentTimeMillis();
		return (currentTime - previousTime) / 1000.0f;
	}

}
