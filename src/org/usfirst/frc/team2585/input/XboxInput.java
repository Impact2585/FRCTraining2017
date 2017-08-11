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
	 * @see org.usfirst.frc.team2585.input.InputMethod#rotationValue()
	 */
	@Override 
	public double rotationValue() {
		// Right joystick left/right for rotational movement
		return controller.getRawAxis(XboxConstants.RIGHT_X_AXIS);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#invert()
	 */
	@Override
	public boolean shouldInvert() {
		return controller.getRawButton(XboxConstants.A_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#boost()
	 */
	@Override
	public boolean shouldBoost() {
		return controller.getRawButton(XboxConstants.B_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#shoot()
	 */
	@Override 
	public boolean shouldToggleShooter() {
		return controller.getRawButton(XboxConstants.RIGHT_BUMPER);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#shouldLoad()
	 */
	@Override
	public boolean shouldLoad() {
		return controller.getRawAxis(XboxConstants.RIGHT_TRIGGER) > 0.15;
	}

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#intake()
	 */
	public boolean shouldIntake() {
		return controller.getRawAxis(XboxConstants.LEFT_TRIGGER) > 0.2;
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#shouldLiftUp()
	 */
	public boolean shouldLiftUp() {
		return controller.getRawButton(XboxConstants.Y_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.input.InputMethod#shouldLiftDown()
	 */
	public boolean shouldLiftDown() {
		return controller.getRawButton(XboxConstants.X_BUTTON);
	}
	
}
