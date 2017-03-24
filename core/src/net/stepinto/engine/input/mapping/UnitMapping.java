package net.stepinto.engine.input.mapping;

import com.badlogic.gdx.math.Vector2;

public class UnitMapping extends JoystickMapping{
	
	public UnitMapping(float deadzone, boolean inverted){
		super(deadzone, inverted);
	}
	
	@Override
	public Vector2 MapValue(Vector2 input){
		float inputLen = input.len();
		
		if(inputLen <= getDeadZone()){
			return new Vector2(0f, 0f);
		}
		
		input.nor();
		if(isInverted()){
			input.y = -input.y;
		}
		
		return input;
	}

}
