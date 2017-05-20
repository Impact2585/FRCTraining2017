package org.usfirst.frc.team2585.input;

import org.impact2585.lib2585.XboxConstants;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Input from an Xbox360 controller
 */
public class XboxInput extends InputMethod {
	private Joystick controller;
	
	/**
	 * Initializes a joystick
	 */
	public XboxInput() {
		// 0 is the port # of the driver station the joystick is plugged into
		controller = new Joystick(0); 
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#forwardMovement()
	 */
	@Override
	public double forwardMovement() {
		// Right joystick up and down for forward/backward movement
		return controller.getRawAxis(XboxConstants.LEFT_Y_AXIS);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#invert()
	 */
	@Override
	public boolean invert() {
		return controller.getRawButton(XboxConstants.A_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#boost()
	 */
	@Override
	public boolean boost() {
		return controller.getRawButton(XboxConstants.B_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#rotationValue()
	 */
	@Override 
	public double rotationValue() {
		// Right joystick left/right for rotational movement
		return controller.getRawAxis(XboxConstants.RIGHT_X_AXIS);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#shoot()
	 */
	@Override 
	public boolean shoot() {
		return controller.getRawButton(XboxConstants.RIGHT_BUMPER);
	}
}
