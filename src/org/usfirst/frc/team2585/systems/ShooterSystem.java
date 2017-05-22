package org.usfirst.frc.team2585.systems;

import org.impact2585.lib2585.Toggler;
import org.usfirst.frc.team2585.Environment;
import org.usfirst.frc.team2585.RobotMap;
import org.usfirst.frc.team2585.input.InputMethod;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

/**
 * This system controls the ball shooter of the robot
 */
public class ShooterSystem extends RobotSystem implements Runnable {	
	private SpeedController agitator;
	private SpeedController shooter;
	
	private double prevShooterSpeed;
	
	public static final double RAMP = 0.6;
	
	private Toggler shooterToggler;
	
	/**
	 * Constructor for the shooter system that initializes the togglers
	 */
	public ShooterSystem() {
		shooterToggler = new Toggler(false);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.systems.Initializable#init(org.usfirst.frc.team2585.Environment)
	 */
	@Override
	public void init(Environment environ) {
		super.init(environ);
		agitator = new Victor(RobotMap.AGITATOR);
	}
	
	/**
	 * Updates the toggler states based on the current input
	 */
	private void updateToggler() {
		shooterToggler.toggle(input.shouldShoot());
	}
	
	/**
	 * @param shooterInput the unramped new input value for the shooter
	 */
	private void rampAndShoot(double shooterInput) {
		double newShooterSpeed = prevShooterSpeed + RAMP * (shooterInput - prevShooterSpeed);
		if (newShooterSpeed > 0.95) {
			newShooterSpeed = 1.0;
		}
		
		// Run the agitator motor at half the speed of the shooter motor
		setMotors(newShooterSpeed, newShooterSpeed / 2);
		
		prevShooterSpeed = newShooterSpeed;
	}
	
	/**
	 * Set the motors to the raw given speeds with no additional logic or processing performed on the numbers
	 * @param shooterSpeed the raw speed to be sent to the shooter motor
	 * @param agitatorSpeed the raw speed to be sent to the agitator motor
	 */
	protected void setMotors(double shooterSpeed, double agitatorSpeed) {
		shooter.set(shooterSpeed);
		agitator.set(agitatorSpeed);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		updateToggler();
		if (shooterToggler.state() == true) {
			rampAndShoot(1.0);
		} else {
			setMotors(0, 0);
			prevShooterSpeed = 0;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		agitator = null;
		shooter = null;
	}

}
