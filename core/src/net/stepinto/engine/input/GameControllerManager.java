package net.stepinto.engine.input;

import java.util.HashMap;
import java.util.Map;

import net.stepinto.engine.component.Component;

public class GameControllerManager extends Component {

	private Map<String, GameController> controllers;
	
	public GameControllerManager(int priority){
		super("GameControllerManager", priority);
		controllers = new HashMap<String, GameController>();
	}
	
	public GameControllerManager(){
		this(100);
	}
	
	public void RegisterController(String name, GameController controller){
		controllers.put(name, controller);
	}
	
	public GameController getController(String name){
		return controllers.get(name);
	}
	
	public void removeController(String name){
		controllers.remove(name);
	}
	
	public Map<String, GameController> getControllers(){
		return controllers;
	}
	
	@Override
	public void update(float deltaT) {
		for(GameController gc : controllers.values()){
			gc.update(deltaT);
		}
	}

}
