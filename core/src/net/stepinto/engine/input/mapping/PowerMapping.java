package net.stepinto.engine.input.mapping;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.math.Vector2;

public class PowerMapping extends JoystickMapping {

	private float power;
	public float getPower(){
		return this.power;
	}
	
	public void setPower(float power){
		this.power = power;
	}
	
	public PowerMapping(float deadzone, boolean inverted, float power){
		super(deadzone, inverted);
		setPower(power);
	}
	
	@Override
	public Vector2 MapValue(Vector2 input){
		float inputlen = input.len();
		if(inputlen >= 1.0f){
			inputlen = 1.0f;
		}
		Vector2 norminput = input;
		norminput.nor();
		
		if(inputlen <= getDeadZone()){
			return new Vector2(0.0f, 0.0f);
		}
		
		if(isInverted()){
			norminput.y = -norminput.y;
		}
		
		float scaledAmount = (inputlen - getDeadZone()) / (1 - getDeadZone());
		double finalscale = Math.pow(scaledAmount, power);
		return new Vector2(norminput.x * (float)finalscale, norminput.y * (float)finalscale);
	}
}
