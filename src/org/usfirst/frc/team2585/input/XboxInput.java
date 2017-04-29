package org.usfirst.frc.team2585.input;

import org.impact2585.lib2585.XboxConstants;

import edu.wpi.first.wpilibj.Joystick;

public class XboxInput extends InputMethod {
	private Joystick controller;
	
	public XboxInput() {
		// 0 is the port # of the driver station the joystick is plugged into
		controller = new Joystick(0); 
	}
	
	@Override
	public double forwardMovement() {
		// Right joystick up and down for forward/backward movement
		return controller.getRawAxis(XboxConstants.LEFT_Y_AXIS);
	}
	
	@Override public double rotationValue() {
		// Right joystick left/right for rotational movement
		return controller.getRawAxis(XboxConstants.RIGHT_Y_AXIS);
	}
}
