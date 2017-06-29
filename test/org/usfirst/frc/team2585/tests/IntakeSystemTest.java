package org.usfirst.frc.team2585.tests;

import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team2585.input.InputMethod;
import org.usfirst.frc.team2585.systems.IntakeSystem;

import org.junit.Assert;

/**
 * Unittests for the IntakeSystem
 */
public class IntakeSystemTest {
	private TestInput input;
	private TestIntakeSystem intakeSystem;
	private boolean intakeTriggered;
	private double intakeOutput;
	
	/**
	 * Creates a new IntakeSystem and pass it the input
	 */
	private void newIntakeSystem() {
		intakeSystem = new TestIntakeSystem();
		intakeSystem.setInput(input);
	}

	/**
	 * Create a new input and new intakeSysetm
	 */
	@Before
	public void setUp() {
		input = new TestInput();
		newIntakeSystem();
	}
	
	/**
	 * Ensure that the intake is defaulted to being off
	 */
	@Test
	public void defaultsToZero() {
		intakeTriggered = false;
		intakeSystem.run();
		Assert.assertTrue(intakeOutput == 0);
	}
	
	/**
	 * Ensure that the ramping works after a single call of run()
	 */
	@Test
	public void testSingleRamping() {
		intakeTriggered = true;
		intakeSystem.run();
		Assert.assertTrue(intakeOutput == 0.6);
	}
	
	/**
	 * Ensure that the ramping works after multiple calls of run()
	 */
	@Test
	public void testMultipleRamping() {
		intakeTriggered = true;
		intakeSystem.run();
		intakeSystem.run();
		Assert.assertTrue(intakeOutput == 0.84);
	}
	
	/**
	 * Assure that the motor speed drops directly back to 0 when the input is 0
	 */
	@Test
	public void dropsToZero() {
		intakeTriggered = true;
		intakeSystem.run();
		intakeSystem.run();
		intakeTriggered = false;
		intakeSystem.run();
		Assert.assertTrue(intakeOutput == 0);
	}
	
	/**
	 * Assures that when the ramped value approaches 1 it skips directly to 1 rather than approaching endlessly
	 */
	@Test
	public void skipsToOne() {
		intakeTriggered = true;
		for (int i=0; i<10; i++) {
			intakeSystem.run();
		}
		Assert.assertTrue(intakeOutput == 1);
	}
	
	/**
	 * A testable input class where the inputs are set through changing the fields in the test class
	 */
	private class TestInput extends InputMethod {
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.input.InputMethod#intake()
		 */
		@Override 
		public boolean shouldIntake() {
			return intakeTriggered;
		}
	}
	
	/**
	 * A testable intakeSystem that sets the values of output variables rather than actually driving motors
	 */
	private class TestIntakeSystem extends IntakeSystem {
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.systems.IntakeSystem#setMotorSpeed(double)
		 */
		@Override
		public void setMotorSpeed(double speed) {
			intakeOutput = speed;
		}
	}
}
