package org.usfirst.frc.team2585.systems;

import org.impact2585.lib2585.Toggler;
import org.usfirst.frc.team2585.Environment;
import org.usfirst.frc.team2585.RobotMap;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

/**
 * This system controls the movement of the robot
 */
public class WheelSystem extends RobotSystem implements Runnable {	
	private SpeedController rightUpperDrive;
	private SpeedController rightLowerDrive;
	private SpeedController leftDrive;
	
	protected double currentForward;
	protected double currentRotation;
	private double previousForward;
	
	public static final double DEADZONE = 0.15;
	public static final double RAMP = 0.6;
	public static final double ROTATION_EXPONENT = 2.0;
	
	private Toggler invertDirectionToggler;
	private Toggler boostToggler;
	
	private Solenoid gearShifter;
	
	/**
	 * constructor for the WheelSystem that initializes the togglers
	 */
	public WheelSystem() {
		invertDirectionToggler = new Toggler(false);
		boostToggler = new Toggler(false);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.systems.Initializable#init(org.usfirst.frc.team2585.Environment)
	 */
	@Override
	public void init(Environment environ) {
		super.init(environ);
		
		rightUpperDrive = new Victor(RobotMap.RIGHT_UPPER_DRIVE);
		rightLowerDrive = new Victor(RobotMap.RIGHT_LOWER_DRIVE);
		leftDrive = new Victor(RobotMap.LEFT_DRIVE);
				
		previousForward = 0;
		currentForward = 0;
		
		gearShifter = new Solenoid(RobotMap.SOLENOID);
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
		forward = applyDeadZone(rampedForward(forward));
		currentForward = forward;
		
		rotation = applyDeadZone(applyRotationExponent(rotation));
		
		arcadeControl(forward, rotation);
	}
	
	/**
	 * This passes the signals directly to the motors, so ramping should already be complete 
	 * @param forward the raw forward signal to send to the motors
	 * @param rotation the raw rotation to add to the forward
	 */
	public void arcadeControl(double forward, double rotation) {
		leftForward(forward + rotation);
		rightForward(forward - rotation);
	}
	
	/**
	 * @return the calculated forwardMovement value taken from the input and ramped based on the previous movement value
	 */
	public double rampedForward(double forwardIn) {
		// Go straight to 0 if the input is 0
		if (forwardIn == 0) {
			return 0;
		}
		
		// Negate forward if the inverted toggle is activated
		if (invertDirectionToggler.state() == true) {
			forwardIn *= -1;
		}
		
		double forwardDiff = forwardIn - previousForward;
		double forward;
		if (Math.abs(forwardDiff) < 0.05) {
			forward = input.forwardMovement();
		} else {
			forward = previousForward + RAMP * (forwardDiff);
		}
		return forward;
	}
	
	/**
	 * @param rotation the input rotation value
	 * @return the result of applying the exponent to the ROTATION_EXPONENT while maintaining its sign
	 */
	public double applyRotationExponent(double rotation) {
		double sign = Math.signum(rotation);
		
		return sign * Math.pow(Math.abs(rotation), ROTATION_EXPONENT);
	}
	
	/**
	 *  Update the values of the boost toggler and direction inversion toggler
	 *  Then perform the appropriate actions based on the resulting togler states
	 */	
	private void updateTogglers() {
		// Invert the drive train direction if necessary
		invertDirectionToggler.toggle(input.shouldInvert());
		boostToggler.toggle(input.shouldBoost());
	}
	
	/**
	 * Included so that the tests can ensure the gears are shifted appropriately
	 * @return boolean denoting the state of the gear shifter solenoid
	 */
	public boolean getBoostState() {
		return boostToggler.state();
	}
	
	/**
	 * @param val the value to apply the deadzone to
	 * @return either 0 or the value depending on whether it falls within the deadzone
	 */
	protected double applyDeadZone(double val) {
		if (Math.abs(val) < DEADZONE) {
			val = 0;
		}
		return val;
	}
	
	/**
	 * @param the state to set the gear shifter to
	 */
	public void setGearShifter(boolean state) {
		gearShifter.set(state);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		previousForward = currentForward;
		updateTogglers(); // Must be done before forward calculation

		driveWithRotation(input.forwardMovement(), input.rotationValue());
		setGearShifter(boostToggler.state());
	}

	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		// Called when the wheelsystem is destroyed
		((PWM) rightUpperDrive).free();
		((PWM) rightLowerDrive).free();
		((PWM) leftDrive).free();
	}
}
