package net.stepinto.engine.input.mapping;

import com.badlogic.gdx.math.Vector2;

public class JoystickMapping {

	private float deadZone;
	private boolean inverted;
	
	
	public void setDeadZone(float deadZone){
		this.deadZone = deadZone;
	}
	
	public float getDeadZone(){
		return this.deadZone;
	}
	
	public void setInverted(boolean inverted){
		this.inverted = inverted;
	}
	
	public boolean isInverted(){
		return this.inverted;
	}
	
	public JoystickMapping(float deadZone, boolean inverted){
		setInverted(inverted);
		setDeadZone(deadZone);
		
	}
	
	public JoystickMapping(){
		this(.05f, false);
	}
	
	public Vector2 MapValue(Vector2 input){
		float inputlen = input.len();
		if(inputlen >= 1.0f){
			inputlen = 1.0f;
		}
		Vector2 norminput = input;
		norminput.nor();
		
		if(inputlen <= deadZone){
			return new Vector2(0.0f, 0.0f);
		}
		
		if(inverted){
			norminput.y = -norminput.y;
		}
		
		float scaledAmount = (inputlen - deadZone) / (1 - deadZone);
		return new Vector2(norminput.x * scaledAmount, norminput.y * scaledAmount);
	}
	
}
