package org.usfirst.frc.team2585.tests;

import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team2585.input.InputMethod;
import org.usfirst.frc.team2585.systems.ShooterSystem;

import org.junit.Assert;

/**
 * Unit tests for the shooter system
 */
public class ShooterSystemTest {
	private TestShooterSystem shooter;
	private TestInput input;
	
	private boolean shooterInput;
	
	private double currentAgitatorOut;
	private double currentShooterOut;
		
	/**
	 * Set up the input and shooter system to be ready for a test
	 */
	@Before
	public void setUp() {
		input = new TestInput();
		newShooterSystem();
		resetInput();
	}
	
	/**
	 * Set shooter to be a new TestShooterSystem object
	 */
	private void newShooterSystem() {
		shooter = new TestShooterSystem();
		shooter.setInput(input);
	}
	
	/**
	 * Set the input values to their default states
	 */
	private void resetInput() {
		shooterInput = false;
	}
	
	/**
	 * Test the ramping of the shooter motor output value
	 */
	@Test
	public void testShooterMotorRamps() {
		shooterInput = true;
		shooter.run();
		Assert.assertTrue(currentShooterOut == 0.6);
		shooter.run();
		Assert.assertTrue(currentShooterOut == 0.84);
	}
	
	/**
	 * Test that the motor speed drops to 0 when it the shooter is deactivated
	 */
	@Test
	public void testShooterDropsToZero() {
		shooterInput = true;
		shooter.run();
		shooterInput = false;
		shooter.run();
		shooterInput = true;
		shooter.run();
		Assert.assertTrue(currentShooterOut == 0);
	}
	
	/**
	 * Test that the agitator is being set to run at half the speed of the shooter
	 */
	@Test
	public void testAgitatorIsHalf() {
		shooterInput = true;
		shooter.run();
		Assert.assertTrue(currentAgitatorOut == currentShooterOut / 2);
	}
	
	
	/**
	 * Input for testing that uses the input fields of ShooterSystemTest as input rather than reading the input from a remote
	 */
	public class TestInput extends InputMethod {
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.input.InputMethod#shouldShoot()
		 */
		@Override
		public boolean shouldShoot() {
			return shooterInput;
		}
	}
	
	/**
	 * A testable shooter system
	 */
	public class TestShooterSystem extends ShooterSystem {
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.systems.ShooterSystem#setMotors(double, double)
		 */
		@Override
		public void setMotors(double shooterSpeed, double agitatorSpeed) {
			currentShooterOut = shooterSpeed;
			currentAgitatorOut = agitatorSpeed;
		}
	}
}
