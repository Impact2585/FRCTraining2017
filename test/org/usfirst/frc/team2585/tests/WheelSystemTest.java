package org.usfirst.frc.team2585.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team2585.input.InputMethod;
import org.usfirst.frc.team2585.systems.WheelSystem;

public class WheelSystemTest {
	
	private TestWheelSystem driveTrain;
	
	private TestInput input;
	private double forwardInput;
	private double rotationInput;
	
	private double currentForward;
	private double currentRotation;
	
	/**
	 * Set up the wheelSystem for testing and initialize inputs to 0
	 */
	@Before
	public void setUp() {
		input = new TestInput();
		
		newWheelSystem();
		
		forwardInput = 0;
		rotationInput = 0;
		currentForward = 0;
		currentRotation = 0;
	}
	
	
	/**
	 * Create a new wheel system with reset values of movement and rotation
	 */
	public void newWheelSystem() {
		driveTrain = new TestWheelSystem();
		driveTrain.setInput(input);
	}

	/**
	 * Test that the forward starts as 0
	 */
	@Test
	public void testStartAtZero() {
		driveTrain.run();
		Assert.assertTrue(currentForward == 0);
		Assert.assertTrue(currentRotation == 0);
	}
	
	
	/**
	 * Test that the forward properly ramps with a positive difference
	 */
	@Test
	public void testPosRamp() {
		forwardInput = 1;
		driveTrain.run();
		Assert.assertTrue(currentForward == 0.6);
		// Assure that the ramping works twice in a row
		driveTrain.run();
		Assert.assertTrue(currentForward == 0.84);
	}
	
	/**
	 * Test that the forward properly ramps with a negative difference
	 */
	@Test
	public void testNegRamp() {	
		newWheelSystem();
		forwardInput = -1;
		driveTrain.run();
		Assert.assertTrue(currentForward == -0.6);
	}
	
	/**
	 * // Test that the forward and rotation are dampened with the deadzone
	 */
	@Test
	public void testDeadzone() {
		// Test forward dampening
		newWheelSystem();
		forwardInput = 0.14;
		driveTrain.run();
		Assert.assertTrue(currentForward == 0);
		
		// Test rotation dampening
		rotationInput = 0.14;
		driveTrain.run();
		Assert.assertTrue(currentRotation == 0);
	}
	
	/**
	 * Test that forward goes straight to 0 if input is 0
	 */
	@Test
	public void testDropToZero() {
		forwardInput = 1;
		for(int i=0; i<5; i++) {
			driveTrain.run();
		}
		Assert.assertTrue(currentForward > 0);
		forwardInput = 0;
		driveTrain.run();
		Assert.assertTrue(currentForward == 0);
	}
	
	/**
	 * Test that forward jumps to the value when within 0.01
	 */
	@Test
	public void testSkipToValue() {
		newWheelSystem();
		forwardInput = 1;
		for(int i=0; i<6; i++) {
			driveTrain.run();
		}
		Assert.assertTrue(currentForward == 1);
	}
	
	/**
	 * Input that uses the values of the test class as input
	 * This is meant to be given to the test wheelSystem to allow testing
	 */
	private class TestInput extends InputMethod {
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.input.InputMethod#forwardMovement()
		 */
		@Override
		public double forwardMovement() {
			return forwardInput;
		}
		
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.input.InputMethod#rotationValue()
		 */
		@Override
		public double rotationValue() {
			return rotationInput;
		}
	}
	
	/**
	 * A testable wheelSystem that sets the test class variables 
	 * rather than driving the motors
	 */
	private class TestWheelSystem extends WheelSystem {
		@Override 
		public void driveWithRotation(double newForward, double newRotation) {
			currentForward = newForward;
			currentRotation = newRotation;
		}
	}
}
