package org.usfirst.frc.team2585.tests;

import org.junit.Before;
import org.junit.Test;
import org.usfirst.frc.team2585.input.InputMethod;
import org.usfirst.frc.team2585.systems.LiftSystem;

import org.junit.Assert;

/**
 * Unittests for the LiftSystem
 */
public class LiftSystemTest {
	private TestInput input;
	private TestLiftSystem liftSystem;
	
	private boolean liftUpInput;
	private boolean liftDownInput;
	
	private double liftOutput;
	
	/**
	 * Creates a new LiftSystem controlled by the test input
	 */
	private void newLiftSystem() {
		liftSystem = new TestLiftSystem();
		liftSystem.setInput(input);
	}
	
	/**
	 * Creates new input and LiftSystem
	 */
	@Before
	public void setUp() {
		input = new TestInput();
		newLiftSystem();
	}
	
	/**
	 * Test that the output is 0 when neither liftUp nor liftDown are activated
	 */
	@Test
	public void defaultsToZero() {
		liftUpInput = false;
		liftDownInput = false;
		
		liftSystem.run();
		Assert.assertTrue(liftOutput == 0);
	}
	
	/**
	 * Test that the output ramps correctly when given a positive input
	 */
	@Test
	public void rampsForward() {
		liftUpInput = true;
		liftSystem.run();
		Assert.assertTrue(liftOutput == 0.6);
	}
	
	/**
	 * Test that the output ramps correctly when given a negative input
	 */
	@Test
	public void rampsBackward() {
		liftDownInput = true;
		liftSystem.run();
		Assert.assertTrue(liftOutput == -0.6);
	}
	
	/**
	 * Test that the output reaches the full thrust after running and ramping multiple times
	 */
	@Test
	public void reachesFull() {
		liftUpInput = true;
		for (int i=0; i<10; i++) {
			liftSystem.run();
		}
		Assert.assertTrue(liftOutput == 1.0);
	}
	
	/**
	 * Test that the output immediately drops to 0 when the inputs are set to false after being true
	 */
	@Test
	public void dropsToZero() {
		liftUpInput = true;
		for (int i=0; i<10; i++) {
			liftSystem.run();
		}
		liftUpInput = false;
		liftSystem.run();
		Assert.assertTrue(liftOutput == 0.0);
	}
	
	/**
	 * A testable input class where the inputs are set through the test class fields
	 */
	private class TestInput extends InputMethod {
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.input.InputMethod#shouldLiftUp()
		 */
		@Override
		public boolean shouldLiftUp() {
			return liftUpInput;
		}
		
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.input.InputMethod#shouldLiftDown()
		 */
		@Override
		public boolean shouldLiftDown() {
			return liftDownInput;
		}
	}
	
	/**
	 * A testable lift system that uses the test class fields to input and output values
	 */
	private class TestLiftSystem extends LiftSystem {
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.systems.LiftSystem#setMotorSpeed(double)
		 */
		@Override
		public void setMotorSpeed(double speed) {
			liftOutput = speed;
		}
	}
}
