package org.usfirst.frc.team2585.systems;

import org.usfirst.frc.team2585.input.InputMethod;
import org.usfirst.frc.team2585.robot.Environment;
import org.usfirst.frc.team2585.robot.RobotMap;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

/**
 * A system of one motor that lifts and lowers the robot
 */
public class LiftSystem extends RobotSystem implements Runnable {
	private SpeedController liftMotor;
	private static final double RAMP = 0.6;
	private double previousMotorSpeed = 0;
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.systems.RobotSystem#init(org.usfirst.frc.team2585.Environment)
	 */
	@Override
	public void init(Environment environ) {
		super.init(environ);
		liftMotor = new Victor(RobotMap.INTAKE_MOTOR);
	}
	
	/**
	 * @param controller new input to control the intakeSystem
	 */
	public void setInput(InputMethod controller) {
		input = controller;
	}
	
	/**
	 * @param inSpeed the input speed to be ramped
	 * @return the value after ramping to be passed on to the motors
	 */
	private double rampedInput(double inLift) {		
		double liftDiff = inLift - previousMotorSpeed;
		double lift;
		if (Math.abs(liftDiff) < 0.05) {
			lift = inLift;
		} else {
			lift = previousMotorSpeed + RAMP * (liftDiff);
		}
		return lift;
	}
	
	/**
	 * @param speed the speed to set the intake motor to
	 */
	public void setMotorSpeed(double speed) {
		liftMotor.set(speed);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		double newSpeed;
		if (input.shouldLiftUp()) {
			newSpeed = rampedInput(1);
		} else if (input.shouldLiftDown()) {
			newSpeed = rampedInput(-1);
		} else {
			newSpeed = 0;
		}
		setMotorSpeed(newSpeed);
		previousMotorSpeed = newSpeed;
	}
	
	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		if (liftMotor instanceof PWM) {
			((PWM) liftMotor).free();
		}
	}
}
