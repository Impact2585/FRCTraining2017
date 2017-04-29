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
	
	@Before
	public void setUp() {
		input = new TestInput();
		
		driveTrain = new TestWheelSystem();
		driveTrain.setInput(input);
		
		forwardInput = 0;
		rotationInput = 0;
		currentForward = 0;
		currentRotation = 0;
	}
	
	public void newWheelSystem() {
		driveTrain = new TestWheelSystem();
		driveTrain.setInput(input);
	}

	@Test
	public void testStartAtZero() {
		// Test that the forward starts as 0
		driveTrain.run();
		Assert.assertTrue(currentForward == 0);
		Assert.assertTrue(currentRotation == 0);
	}
	
	@Test
	public void testPosRamp() {
		// Test that the forward properly ramps with a positive difference
		forwardInput = 1;
		driveTrain.run();
		Assert.assertTrue(currentForward == 0.6);
		// Assure that the ramping works twice in a row
		driveTrain.run();
		Assert.assertTrue(currentForward == 0.84);
	}
	
	@Test
	public void testNegRamp() {		
		// Test that the forward properly ramps with a negative difference
		newWheelSystem();
		forwardInput = -1;
		driveTrain.run();
		Assert.assertTrue(currentForward == -0.6);
	}
	
	@Test
	public void testDeadzone() {
		
		// Test that the forward is dampened with the deadzone
		newWheelSystem();
		forwardInput = 0.14;
		driveTrain.run();
		Assert.assertTrue(currentForward == 0);
		// Test that the rotation is dampened with the deadzone
		rotationInput = 0.14;
		driveTrain.run();
		Assert.assertTrue(currentRotation == 0);
	}
	
	@Test
	public void testDropToZero() {
		// Test that forward goes straight to 0 if input is 0
		forwardInput = 1;
		for(int i=0; i<5; i++) {
			driveTrain.run();
		}
		Assert.assertTrue(currentForward > 0);
		forwardInput = 0;
		driveTrain.run();
		Assert.assertTrue(currentForward == 0);
	}
	
	@Test
	public void testSkipToValue() {
		// Test that forward jumps to the value when within 0.01
		newWheelSystem();
		forwardInput = 1;
		for(int i=0; i<6; i++) {
			driveTrain.run();
		}
		Assert.assertTrue(currentForward == 1);
	}
	
	private class TestInput extends InputMethod {
		// Input that uses the values of the test class as input
		// This is meant to be given to the test wheelsystem to allow testing
		
		@Override
		public double forwardMovement() {
			return forwardInput;
		}
		
		@Override
		public double rotationValue() {
			return rotationInput;
		}
	}
	
	private class TestWheelSystem extends WheelSystem {
		@Override 
		public void driveWithRotation(double newForward, double newRotation) {
			currentForward = newForward;
			currentRotation = newRotation;
		}
	}
}
