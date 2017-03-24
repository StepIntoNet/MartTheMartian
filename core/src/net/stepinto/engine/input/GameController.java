package net.stepinto.engine.input;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import net.stepinto.engine.input.mapping.JoystickMapping;
import net.stepinto.engine.input.mapping.PowerMapping;
import net.stepinto.engine.input.mapping.UnitMapping;

public class GameController {

	// Maps between buttons and integers for Libgdx Controller values
	private Map<String, Integer> buttonMap;
	private Map<String, Integer> axisMap;
	private Map<String, PovDirection> povMap;
	private Map<String, Joystick> joysticks;

	// Button States Previous/Current
	private Map<String, Boolean> buttonsPrevState;
	private Map<String, Boolean> buttonsCurrentState;

	// Axis State Previous/Current
	private Map<String, Float> axisPrevState;
	private Map<String, Float> axisCurrentState;
	private float minDelta;

	// JoyStick States Previous/Current
	private Map<String, Vector2> stickPrevState;
	private Map<String, Vector2> stickCurrentState;

	private Controller controller;

	public GameController(Controller controller) {
		this.controller = controller;

		// Create Maps
		buttonMap = new HashMap<String, Integer>();
		axisMap = new HashMap<String, Integer>();
		povMap = new HashMap<String, PovDirection>();
		joysticks = new HashMap<String, Joystick>();

		// Create Current State Maps
		buttonsCurrentState = new HashMap<String, Boolean>();
		axisCurrentState = new HashMap<String, Float>();
		stickCurrentState = new HashMap<String, Vector2>();

		// Create Previous State Maps
		buttonsPrevState = new HashMap<String, Boolean>();
		axisPrevState = new HashMap<String, Float>();
		stickPrevState = new HashMap<String, Vector2>();

	}
	
	public GameController(Controller controller, String xmlFilePath){
		this(controller);
		LoadFromXML(xmlFilePath);
	}

	public void LoadFromXML(String xmlFileName) {

		XmlReader reader = new XmlReader();

		Element root;
		try {
			
			root = reader.parse(Gdx.files.internal(xmlFileName));
			Integer valueAttr;
			String nameAttr;

			buttonMap.clear();
			Element ButtonMaps = root.getChildByName("ButtonMaps");
			for (Element e : ButtonMaps.getChildrenByName("Button")) {
				nameAttr = e.getAttribute("name");
				valueAttr = Integer.parseInt(e.getAttribute("value"));
				
				buttonMap.put(nameAttr, valueAttr);
				buttonsCurrentState.put(nameAttr, false);
				buttonsPrevState.put(nameAttr, false);
				
			}

			axisMap.clear();
			Element AxisMaps = root.getChildByName("AxisMaps");
			for (Element e : AxisMaps.getChildrenByName("Axis")) {
				nameAttr = e.getAttribute("name");
				valueAttr = Integer.parseInt(e.getAttribute("value"));
				axisMap.put(nameAttr, valueAttr);
				axisCurrentState.put(nameAttr, 0f);
				axisPrevState.put(nameAttr, 0f);
			}

			povMap.clear();
			Element PovMaps = root.getChildByName("PovMaps");
			for (Element e : PovMaps.getChildrenByName("POV")) {
				nameAttr = e.getAttribute("name");
				PovDirection povValue = PovDirection.valueOf(e.getAttribute("value"));
				povMap.put(e.getAttribute("name"), povValue);
			}

			joysticks.clear();
			Element JoySticksElement = root.getChildByName("Joysticks");
			for (Element stick : JoySticksElement.getChildrenByName("Joystick")) {
				Element mappingElement = stick.getChildByName("JoystickMapping");
				JoystickMapping mapping;
				String mappingType = mappingElement.getAttribute("type");
				if(mappingType.equals("Power")){
				mapping = new PowerMapping(
						Float.parseFloat(mappingElement.getChildByName("Deadzone").getAttribute("value")),
						Boolean.parseBoolean(mappingElement.getChildByName("Inverted").getAttribute("value")),
						Float.parseFloat(mappingElement.getChildByName("Power").getAttribute("value"))); 
				} else if (mappingType.equals("Unit")){
				mapping = new UnitMapping(
						Float.parseFloat(mappingElement.getChildByName("Deadzone").getAttribute("value")),
						Boolean.parseBoolean(mappingElement.getChildByName("Inverted").getAttribute("value")));
				} else {
					mapping = null;
					System.out.println("ERROR: Invalid Joystick Mapping.");
				}
				
				joysticks.put(stick.getAttribute("name"),
						new Joystick(stick.getAttribute("name"),
								stick.getChildByName("Horizontal").getAttribute("value"),
								stick.getChildByName("Vertical").getAttribute("value"), mapping));
				
				stickPrevState.put(stick.getAttribute("name"), new Vector2(0f, 0f));
				stickCurrentState.put(stick.getAttribute("name"), new Vector2(0f, 0f));
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public void update(float dt){
		for (String key : buttonMap.keySet()){
			buttonsPrevState.put(key, buttonsCurrentState.get(key));
			buttonsCurrentState.put(key, controller.getButton(buttonMap.get(key)));
		}
		
		for (String key : axisMap.keySet()){
			axisPrevState.put(key, axisCurrentState.get(key));
			axisCurrentState.put(key, controller.getAxis(axisMap.get(key)));
		}
		
		for (String key : joysticks.keySet()){
			stickPrevState.put(key, stickCurrentState.get(key));
			stickCurrentState.put(key, getJoystickValue(key));
		}
	}
	
	public Vector2 getJoystickValue(String stick) {
		return joysticks.get(stick).getValue();
	} 
	
	public Vector2 getJoystickDelta(String stick){
		Vector2 prevStick = stickPrevState.get(stick);
		Vector2 currStick = stickCurrentState.get(stick);
		return new Vector2(currStick.x - prevStick.x, currStick.y - prevStick.y);
	}
	
	public boolean JoystickMoved(String stick){
		
		Vector2 stickDelta = getJoystickDelta(stick);
		
		if(stickDelta.len() >= minDelta){
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean ButtonDown(String button){
		return buttonsCurrentState.get(button);
	}
	
	public boolean ButtonPressed(String button){
		return buttonsCurrentState.get(button) && !buttonsPrevState.get(button);
	}
	
	public boolean ButtonReleased(String button){
		return !buttonsCurrentState.get(button) && buttonsPrevState.get(button);
	}
	
	private class Joystick {
		private String name;
		private String horizontalAxis;
		private String verticalAxis;
		private JoystickMapping mapping;

		private Joystick(String name, String horizontal, String vertical, JoystickMapping mapping) {
			this.name = name;
			this.horizontalAxis = horizontal;
			this.verticalAxis = vertical;
			this.mapping = mapping;
		}
		
		private Joystick(Element e){
			//To-Do clean this up a bit. 
		}

		private Vector2 getValue() {

			return mapping.MapValue(new Vector2(controller.getAxis(axisMap.get(horizontalAxis)),
					controller.getAxis(axisMap.get(verticalAxis))));

		}
		
	}
	
}
