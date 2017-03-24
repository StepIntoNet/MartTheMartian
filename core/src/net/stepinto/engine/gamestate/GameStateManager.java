package net.stepinto.engine.gamestate;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import net.stepinto.engine.component.Component;
import net.stepinto.engine.component.IResizeable;

public class GameStateManager extends Component implements IResizeable, InputProcessor {

	private SpriteBatch spriteBatch;	
	public SpriteBatch getSpriteBatch()
	{
		return spriteBatch;
	}
	
	private Stage stage;
	public Stage getStage(){
		return stage;
	}
	
	
	private ArrayList<Gamestate> states;
	public Gamestate getCurrentState()
	{
		if(!states.isEmpty()){
			return states.get(states.size() - 1);
		}
		else
		{
			return null;
		}
	}
	
	public GameStateManager(SpriteBatch spriteBatch, int priority)
	{
		super("GameStateManager", priority);
		states = new ArrayList<Gamestate>();
		
		stage = new Stage(new ScreenViewport());
		
		
		InputMultiplexer inputMulti = new InputMultiplexer();
		inputMulti.addProcessor(stage);
		inputMulti.addProcessor(this);
		Gdx.input.setInputProcessor(inputMulti);
		
		this.spriteBatch = spriteBatch;
	}
	
	public GameStateManager(SpriteBatch spriteBatch)
	{
		this(spriteBatch, 100);
	}
	
	public GameStateManager()
	{
		this(new SpriteBatch());
	}
	
	@Override
	public void update(float deltaT) 
	{	
		stage.act(deltaT);
		if(!states.isEmpty())
		{
			Gamestate state = getCurrentState();
			
			state.update(deltaT);
		}
	}
	
	@Override
	public void render(float deltaT)
	{
		Gdx.gl.glClearColor( 0, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(!states.isEmpty())
		{
			Gamestate state = getCurrentState();
			state.beginRender(getSpriteBatch());
			state.render(deltaT, getSpriteBatch());
			state.endRender(getSpriteBatch());
		}
		stage.getViewport().apply();
		stage.draw();
	}
	

	public void PushState(Gamestate state)
	{
		if(!states.isEmpty())
		{
			getCurrentState().OnLeave();
		}
		if(state != null)
		{
			state.OnEnter();
			state.setCurrentManager(this);
			states.add(state);
		}
	}
	
	public Gamestate SwitchState(Gamestate state)
	{
		Gamestate oldstate = null;
		if(state != null)
		{	
			oldstate = PopState();
			states.add(state);
			state.setCurrentManager(this);
			state.OnEnter();
		}
		
		return oldstate;
	}
	
	public Gamestate PopState()
	{
		if(!states.isEmpty())
		{
			Gamestate poppedState = states.get(states.size() - 1);
			states.remove(states.size()-1);
			poppedState.OnLeave();
			return poppedState;
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public void resize(int w, int h) {
		stage.getViewport().update(w,h,true);
		for(Gamestate state : states){
			state.resize(w, h);
		}
		
	}

	@Override
	public boolean keyDown(int keycode) {
		
		if(states.isEmpty()){
		return false;
		}
		return getCurrentState().keyDown(keycode);
		
	}

	@Override
	public boolean keyUp(int keycode) {
		if(states.isEmpty()){
			return false;
		}
		return getCurrentState().keyUp(keycode);
	}

	@Override
	public boolean keyTyped(char character) {
		if(states.isEmpty()){
			return false;
		}
		return getCurrentState().keyTyped(character);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(states.isEmpty()){
			return false;
		}
		return getCurrentState().touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(states.isEmpty()){
			return false;
		}
		return getCurrentState().touchUp(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(states.isEmpty()){
			return false;
		}
		return getCurrentState().touchDragged(screenX, screenY, pointer);
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		if(states.isEmpty()){
			return false;
		}
		return getCurrentState().mouseMoved(screenX, screenY);
	}

	@Override
	public boolean scrolled(int amount) {
		if(states.isEmpty()){
			return false;
		}
		return getCurrentState().scrolled(amount);
	}
}
