package org.usfirst.frc.team2585.systems;

import org.usfirst.frc.team2585.Environment;
import org.usfirst.frc.team2585.RobotMap;
import org.usfirst.frc.team2585.input.InputMethod;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;

public class WheelSystem implements RobotSystem, Runnable {
	
	private RobotDrive drivebase;
	private InputMethod input;
	
	private Victor rightUpperDrive;
	private Victor rightLowerDrive;
	private Victor leftDrive;
	
	private double currentForward;
	private double previousForward;
	private double currentRotation;
	
	public static final double DEADZONE = 0.15;
	public static final double RAMP = 0.6;
	

	@Override
	public void init(Environment environ) {
		rightUpperDrive = new Victor(RobotMap.RIGHT_UPPER_DRIVE);
		rightLowerDrive = new Victor(RobotMap.RIGHT_LOWER_DRIVE);
		leftDrive = new Victor(RobotMap.LEFT_DRIVE);
		
		input = environ.getInput();
		
		previousForward = 0;
		currentForward = 0;
	}
	
	public synchronized void setInput(InputMethod newInput) {
		input = newInput;
	}
	
	public void leftForward(double amount) {
		leftDrive.set(amount);
	}
	
	public void rightForward(double amount) {
		rightUpperDrive.set(amount);
		rightLowerDrive.set(amount);
	}
	
	public void driveWithRotation(double forward, double rotation) {
		leftForward(forward + Math.pow(rotation, 2));
		rightForward(forward - Math.pow(rotation, 2));
	}
	
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
	
	@Override
	public void destroy() {
		drivebase.free();
	}

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
}
