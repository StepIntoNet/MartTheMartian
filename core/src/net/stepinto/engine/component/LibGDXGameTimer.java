package net.stepinto.engine.component;

import com.badlogic.gdx.Gdx;

public class LibGDXGameTimer implements IGameTimer{

	@Override
	public float getDeltaTime() {
		return Gdx.graphics.getDeltaTime();
	}

}
