package org.usfirst.frc.team2585.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team2585.input.InputMethod;
import org.usfirst.frc.team2585.systems.WheelSystem;

/**
 * Unit tests for the WheelSystem
 */
public class WheelSystemTest {
	private TestWheelSystem driveTrain;
	private TestInput input;
	
	private double forwardInput;
	private double rotationInput;
	
	private double currentForwardOut;
	private double currentRotationOut;
	
	private boolean invertInput;
	private boolean boostInput;

	private boolean currentBoost;
	
	/**
	 * Set up the wheelSystem for testing and initialize inputs to 0
	 */
	@Before
	public void setUp() {
		input = new TestInput();
		
		newWheelSystem();
		
		resetInput();
	}
	
	/**
	 * Create a new wheel system with reset values of movement and rotation
	 */
	public void newWheelSystem() {
		driveTrain = new TestWheelSystem();
		driveTrain.setInput(input);
	}
	
	/**
	 * Set the inputs to their default values
	 */
	public void resetInput() {
		forwardInput = 0;
		rotationInput = 0;
		currentForwardOut = 0;
		currentRotationOut = 0;
		
		invertInput = false;
		boostInput = false;
		currentBoost = false;
	}

	/**
	 * Test that the forward starts as 0
	 */
	@Test
	public void testStartAtZero() {
		driveTrain.run();
		Assert.assertTrue(currentForwardOut == 0);
		Assert.assertTrue(currentRotationOut == 0);
	}
	
	/**
	 * Test that the forward properly ramps with a positive difference
	 */
	@Test
	public void testPosRamp() {
		forwardInput = 1;
		driveTrain.run();
		Assert.assertTrue(currentForwardOut == 0.6);
		// Assure that the ramping works twice in a row
		driveTrain.run();
		Assert.assertTrue(currentForwardOut == 0.84);
	}
	
	/**
	 * Test that the forward properly ramps with a negative difference
	 */
	@Test
	public void testNegRamp() {	
		forwardInput = -1;
		driveTrain.run();
		Assert.assertTrue(currentForwardOut == -0.6);
	}
	
	/**
	 * // Test that the forward and rotation are dampened with the deadzone
	 */
	@Test
	public void testDeadzone() {
		// Test forward dampening
		forwardInput = 0.14;
		driveTrain.run();
		Assert.assertTrue(currentForwardOut == 0);
		
		// Test rotation dampening
		rotationInput = 0.14;
		driveTrain.run();
		Assert.assertTrue(currentRotationOut == 0);
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
		Assert.assertTrue(currentForwardOut > 0);
		forwardInput = 0;
		driveTrain.run();
		Assert.assertTrue(currentForwardOut == 0);
	}
	
	/**
	 * Test that forward jumps to the value when within 0.01
	 */
	@Test
	public void testSkipToValue() {
		forwardInput = 1;
		for(int i=0; i<6; i++) {
			driveTrain.run();
		}
		Assert.assertTrue(currentForwardOut == 1);
	}
	
	/**
	 * Tests that even though the rotation is squared, negative rotation remains negative
	 */
	@Test
	public void testNegativeRotation() {
		rotationInput = -0.5;
		driveTrain.run();
		Assert.assertTrue(currentRotationOut == -0.25);
	}
	
	/**
	 * Test that the direction of the drivetrain is toggled appropriately
	 */
	@Test
	public void testDirectionToggle() {
		forwardInput = 1;
		invertInput = true;
		driveTrain.run();
		Assert.assertTrue(currentForwardOut == -0.6);
		// Assure it works multiple times
		driveTrain.run();
		Assert.assertTrue(currentForwardOut == -0.84);
		
		// Test that it can be toggled back
		newWheelSystem();
		forwardInput = 1;
		// Simulate button press to invert direction 
		invertInput = true; 
		driveTrain.run();
		Assert.assertTrue(currentForwardOut == -0.6);
		forwardInput = 0;
		invertInput = false;
		driveTrain.run();
		
		// Simulate second button press to return the toggle to the normal value
		invertInput = true;
		forwardInput = 1;
		driveTrain.run();

		Assert.assertTrue(currentForwardOut == 0.6);
	}
	
	/**
	 *  Test that the gear is toggled appropriately
	 */
	@Test
	public void testGearToggle() {
		// Simulate first button press to shift the gear
		boostInput = true;
		driveTrain.run();
		Assert.assertTrue(currentBoost == true);
		
		// Releasing button but the toggle state remains the same
		boostInput = false;
		driveTrain.run();
		Assert.assertTrue(currentBoost == true);
		
		// Pressing the button a second time to flip the gear state back
		boostInput = true;
		driveTrain.run();
		Assert.assertTrue(currentBoost == false);
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
		
		@Override
		public boolean shouldInvert() {
			return invertInput;
		}
		
		@Override
		public boolean shouldBoost() {
			return boostInput;
		}
	}
	
	/**
	 * A testable wheelSystem that sets the test class variables 
	 * rather than driving the motors
	 */
	private class TestWheelSystem extends WheelSystem {		
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.systems.WheelSystem#arcadeControl(double, double)
		 */
		@Override
		public void arcadeControl(double forward, double rotation) {
			currentForwardOut = forward;
			currentRotationOut = rotation;
		}
		
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.systems.WheelSystem#setGearShifter(boolean)
		 */
		@Override
		public void setGearShifter(boolean state) {
			currentBoost = state;
		}
	}
}
