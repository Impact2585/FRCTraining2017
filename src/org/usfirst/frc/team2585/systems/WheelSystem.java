package org.usfirst.frc.team2585.systems;

import org.usfirst.frc.team2585.Environment;
import org.usfirst.frc.team2585.RobotMap;
import org.usfirst.frc.team2585.input.InputMethod;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

/**
 * This system controls the movement of the robot
 */
public class WheelSystem implements RobotSystem, Runnable {
	private InputMethod input;
	
	private SpeedController rightUpperDrive;
	private SpeedController rightLowerDrive;
	private SpeedController leftDrive;
	
	private double currentForward;
	private double previousForward;
	private double currentRotation;
	
	public static final double DEADZONE = 0.15;
	public static final double RAMP = 0.6;

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.systems.Initializable#init(org.usfirst.frc.team2585.Environment)
	 */
	@Override
	public void init(Environment environ) {
		rightUpperDrive = new Victor(RobotMap.RIGHT_UPPER_DRIVE);
		rightLowerDrive = new Victor(RobotMap.RIGHT_LOWER_DRIVE);
		leftDrive = new Victor(RobotMap.LEFT_DRIVE);
		
		input = environ.getInput();
		
		previousForward = 0;
		currentForward = 0;
	}
	
	/**
	 * @param newInput the input to set
	 */
	public synchronized void setInput(InputMethod newInput) {
		input = newInput;
	}
	
	
	/**
	 * Move the speed controllers on the left
	 * Positive means forward movement and negative means backward movement
	 * @param amount value between -1 and 1 representing how much to move the left speedControllers
	 */
	public void leftForward(double amount) {
		leftDrive.set(amount);
	}
	
	/**
	 * Move the speed controllers on the right
	 * Positive means forward movement and negative means backward movement
	 * @param amount value between -1 and 1 representing how much to move the left speedControllers
	 */
	public void rightForward(double amount) {
		rightUpperDrive.set(amount);
		rightLowerDrive.set(amount);
	}
	
	/**
	 * Move the robot forward with a rotation
	 * @param forward value between -1 and 1 representing the forward/backward movement
	 * @param rotation value between -1 and 1 representing the left/right rotation
	 */
	public void driveWithRotation(double forward, double rotation) {
		leftForward(forward + Math.pow(rotation, 2));
		rightForward(forward - Math.pow(rotation, 2));
	}
	
	/**
	 * @return the calculated forwardMovement value taken from the input and ramped based on the previous movement value
	 */
	public double rampedForward() {
		// Go straight to 0 if the input is 0
		if (input.forwardMovement() == 0) {
			return 0;
		}
		
		double forwardDiff = input.forwardMovement() - previousForward;
		double forward;
		if (Math.abs(forwardDiff) < 0.05) {
			forward = input.forwardMovement();
		} else {
			forward = previousForward + RAMP * (forwardDiff);
		}
		return forward;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		previousForward = currentForward;
		currentForward = rampedForward();
		
		currentRotation = input.rotationValue();
		
		if (Math.abs(currentRotation) < DEADZONE) {
			currentRotation = 0;
		}
		if (Math.abs(currentForward) < DEADZONE) {
			currentForward = 0;
		}
		
		driveWithRotation(currentForward, currentRotation);
	}

	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		// Called when the wheelsystem is destroyed
		rightUpperDrive = null;
		rightLowerDrive = null;
		leftDrive = null;
	}
}
