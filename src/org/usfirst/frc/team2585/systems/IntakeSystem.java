package org.usfirst.frc.team2585.systems;

import org.usfirst.frc.team2585.input.InputMethod;
import org.usfirst.frc.team2585.robot.Environment;
import org.usfirst.frc.team2585.robot.RobotMap;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * A system of one motor that controls the ball intake of the robot
 */
public class IntakeSystem extends RobotSystem implements Runnable {
	private SpeedController intakeMotor;
	private static final double RAMP = 0.6;
	private double previousMotorSpeed = 0;

	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.systems.Initializable#init(org.usfirst.frc.team2585.Environment)
	 */
	@Override
	public void init(Environment environ) {
		super.init(environ);
		intakeMotor = new Spark(RobotMap.LIFT_MOTOR);
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
	private double rampedInput(double inSpeed) {
		double currentSpeed = previousMotorSpeed + RAMP * (inSpeed - previousMotorSpeed);
		if (currentSpeed > 0.95) {
			currentSpeed = 1;
		}
		return currentSpeed;
	}
	
	/**
	 * @param speed the speed to set the intake motor to
	 */
	public void setMotorSpeed(double speed) {
		intakeMotor.set(speed);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		double newSpeed;
		if (input.intake()) {
			newSpeed = rampedInput(1);
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
		if (intakeMotor instanceof PWM) {
			((PWM) intakeMotor).free();
		}
	}
}
