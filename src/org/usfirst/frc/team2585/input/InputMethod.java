package org.usfirst.frc.team2585.input;

/**
 * Input for the robot
 */
public abstract class InputMethod {
	/**
	 * Positive means forward movement and negative means backward movement
	 * @return value between -1 and 1 telling how fast the robot should move forward
	 */
	public double forwardMovement() {
		return 0;
	}
	
	/**
	 * Positive means rotate right and negative means rotate left
	 * @return value between -1 and 1 telling how fast the robot should rotate
	 */
	public double rotationValue() {
		return 0;
	}
}
