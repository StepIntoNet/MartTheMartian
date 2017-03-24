package net.stepinto.engine.gamestate;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//NEEDS TO BE FINISHED, NOT NECESSARY YET!
public class LoadingState extends Gamestate {

	List<AssetManager> assetsToLoad;
	
	
	public LoadingState(AssetManager manager)
	{
		assetsToLoad = new ArrayList<AssetManager>();
		assetsToLoad.add(manager);
	}
	
	public LoadingState(List<AssetManager> assetsToBeLoaded){
		assetsToLoad = assetsToBeLoaded;
	}
	
	public void update(int dt)
	{
		
		
	}
	public void render(int deltaT, SpriteBatch batch)
	{
		
		
	}

	/*
	 * Optional events to be triggered when a state is popped on or off the gamestatemanager stack.
	 * May be used to allocate or release resources or do effects like fading in or out.
	 */
	public void OnEnter()
	{
		
	}
	public void OnLeave()
	{
		
	}
	
}
