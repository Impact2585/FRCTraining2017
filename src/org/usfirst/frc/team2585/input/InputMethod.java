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
	
	/**
	 * @return whether the wheel system should be inverted.
	 */
	public boolean shouldInvert() {
		return false;
	}
	
	/**
	 * @return boolean denoting whether the gear should be shifted
	 */
	public boolean shouldBoost() {
		return false;
	}
	
	/**
	 * @return boolean denoting whether the shooter should be toggled between on and off
	 */
	public boolean shouldToggleShooter() {
		return false;
	}
	
	/**
	 * @return whether the loader should load balls into the shooter
	 */
	public boolean shouldLoad() {
		return false;
	}
	
	/**
	 * @return whether the intake system should be running or not
	 */
	public boolean shouldIntake() {
		return false;
	}
	
	/**
	 * @return whether the lift system should lift up the robot
	 */
	public boolean shouldLiftUp() {
		return false;
	}
	
	/**
	 * @return whether the lift system should lower the robot
	 */
	public boolean shouldLiftDown() {
		return false;
	}
	
	/**
	 * @return whether the lift system should lift up the robot
	 */
	public boolean shouldLiftUp() {
		return false;
	}
	
	/**
	 * @return whether the lift system should lower the robot
	 */
	public boolean shouldLiftDown() {
		return false;
	}
}
