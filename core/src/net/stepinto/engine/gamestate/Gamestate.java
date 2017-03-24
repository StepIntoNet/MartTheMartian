package net.stepinto.engine.gamestate;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import net.stepinto.engine.component.IResizeable;

public class Gamestate implements IResizeable, InputProcessor{

	private AssetManager assets;

	// gamestatemanager that is currently managing the gamestate. used to pop
	// itself off the stack
	// or to push a new state on above it. eg.. a gamestate can push a
	// pausestate to suspend updating
	// and the pause state can pop itself off the stack to resume the suspended
	// gamestate
	private GameStateManager currentManager = null;

	// name of the current state
	private String name;

	public Gamestate() {
		this("nameless");
	}

	public Gamestate(String name) {
		this(name, new AssetManager());

	}

	/*
	 * Constructors
	 */
	public Gamestate(String name, AssetManager assets) {
		setAssets(assets);
		setName(name);
	}

	public AssetManager getAssets() {
		return assets;
	}

	private void setAssets(AssetManager assets) {
		this.assets = assets;
	}

	protected GameStateManager getCurrentManager() {
		return currentManager;
	}

	public void setCurrentManager(GameStateManager manager) {
		currentManager = manager;
	}

	public Gamestate switchState(Gamestate state) {
		if (state != null) {
			GameStateManager manager = getCurrentManager();
			if (manager != null) {
				Gamestate oldstate = manager.SwitchState(state);
				return oldstate;
			}
		}
		
		return null;
	}

	public void pushState(Gamestate state) {
		if (state != null) {
			GameStateManager manager = getCurrentManager();
			if (manager != null) {
				manager.PushState(state);
			}
		}
	}

	public Gamestate popState() {
		GameStateManager manager = getCurrentManager();
		if (manager != null) {
			return manager.PopState();
		} else {
			return null;
		}
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public void beginRender(Batch batch) {
		batch.begin();
	}

	public void endRender(Batch batch) {
		batch.end();
	}

	public void initialize() {

	}

	@Override
	public void resize(int w, int h) {

	}

	/*
	 * Unimplemented/optional methods all gamestates will have. Called mainly by
	 * GameStateManager
	 */
	public void update(float dt) {
	}

	public void render(float deltaT, SpriteBatch batch) {
	}

	/*
	 * Optional events to be triggered when a state is popped on or off the
	 * gamestatemanager stack. May be used to allocate or release resources or
	 * do effects like fading in or out.
	 */
	public void OnEnter() {
	}

	public void OnLeave() {
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
