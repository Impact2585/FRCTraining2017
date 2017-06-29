package org.usfirst.frc.team2585.systems;

import org.impact2585.lib2585.Toggler;
import org.usfirst.frc.team2585.robot.Environment;
import org.usfirst.frc.team2585.robot.RobotMap;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * This system controls the ball shooter of the robot
 */
public class ShooterSystem extends RobotSystem implements Runnable {	
	private SpeedController agitator;
	private SpeedController shooter;
	private SpeedController loader;
	
	private double prevShooterSpeed;
	private double prevLoaderSpeed;
	
	public final double shooterMultiplier = 0.8;
	public final double loaderMultiplier = 0.9;
	public final double agitatorMultiplier = 0.7;
	
	
	public final double RAMP = 0.6;
	
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
		agitator = new Spark(RobotMap.AGITATOR);
		shooter = new Spark(RobotMap.SHOOTER);
		loader = new Spark(RobotMap.LOADER);
	}
	
	/**
	 * Updates the toggler states based on the current input
	 */
	private void updateToggler() {
		shooterToggler.toggle(input.shouldToggleShooter());
	}
	
	/**
	 * @param shooterInput the unramped new input value for the shooter
	 */
	private void rampAndShoot(double shooterInput) {
		double newShooterSpeed = prevShooterSpeed + RAMP * (shooterInput - prevShooterSpeed);
		if (newShooterSpeed > 0.95) {
			newShooterSpeed = 1.0;
		}
		
		setShooter(newShooterSpeed * shooterMultiplier);
		
		prevShooterSpeed = newShooterSpeed;
	}
	
	/**
	 * Set the motors to the raw given speeds with no additional logic or processing performed on the numbers
	 * @param shooterSpeed the raw speed to be sent to the shooter motor
	 * @param agitatorSpeed the raw speed to be sent to the agitator motor
	 */
	protected void setShooter(double shooterSpeed) {
		shooter.set(-shooterSpeed);
		
	}
	
	/**
	 * @param loaderInput the unramped new input value for the loader
	 */
	private void rampAndLoad(double loaderInput) {
		double newLoaderSpeed = prevLoaderSpeed + RAMP * (loaderInput - prevLoaderSpeed);
		if (newLoaderSpeed > 0.95) {
			newLoaderSpeed = 1.0;
		}
		
		setLoader(newLoaderSpeed * loaderMultiplier);
		setAgitator(newLoaderSpeed * agitatorMultiplier);
		
		prevLoaderSpeed = newLoaderSpeed;
	}
	
	/**
	 * @param loaderSpeed the speed to set the loader motor to
	 */
	public void setLoader(double loaderSpeed) {
		loader.set(-loaderSpeed);
	}
	
	/**
	 * @param agitatorSpeed the speed to set the agitator motor to
	 */
	public void setAgitator(double agitatorSpeed) {
		agitator.set(agitatorSpeed);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		updateToggler();
		// Shooter
		if (shooterToggler.state() == true) {
			rampAndShoot(1.0);
		} else {
			setShooter(0);
			prevShooterSpeed = 0;
		}
		
		if (input.shouldLoad()) {
			rampAndLoad(1.0);
		} else {
			setLoader(0);
			prevLoaderSpeed = 0;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		if (agitator instanceof PWM) {
			((PWM) agitator).free();
		}
		if (shooter instanceof PWM) {
			((PWM) shooter).free();
		}
		if (loader instanceof PWM) {
			((PWM) loader).free();
		}
	}
}
