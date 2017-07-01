package org.usfirst.frc.team2585.robot;

import org.usfirst.frc.team2585.systems.WheelSystem;

/**
 * A container class for the different commands that can be passed to the autonomous executor
 */
public class Commands {
	
	private static Environment environ;
	private static WheelSystem drivetrain;
	
	/**
	 * Constructor that sets the environment and the required systems
	 * @param e the environment of the robot
	 */
	public Commands(Environment env) {
		environ = env;
		drivetrain = (WheelSystem) environ.getSystem(Environment.WHEEL_SYSTEM);
	}
	
	/**
	 * Move the robot forward with no rotation
	 */
	private static void driveForward() {
		drivetrain.driveWithRotation(1.0, 0);
	}
	
	/**
	 * Turn the robot left with no forward movement
	 */
	private static void turnLeft() {
		drivetrain.driveWithRotation(0.0, -0.5);
	}
	
	/**
	 * Turn the robot right with no forward movement
	 */
	private static void turnRight() {
		drivetrain.driveWithRotation(0.0, 0.5);
	}
	
	/**
	 * Set the forward movement and rotation to 0
	 */
	private static void stop() {
		drivetrain.driveWithRotation(0, 0);
	}
	
	
	/**
	 * Autonomous command that that drives the robot to the left first and then turns back the same amount to go forward
	 * It drives to the left for 0.5 seconds and forward for 1.5 seconds
	 */
	public class DriveLeft implements AutonomousCommand {
		private static final int timeToTurnLeft = 500;
		private static final int timeToDriveLeft = timeToTurnLeft + 500;
		// Turn back the same amount
		private static final int timeToTurnRight = timeToDriveLeft + timeToTurnLeft; 
		private static final int timeToDriveForward = timeToTurnRight + 1500;
		
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.AutonomousCommand#execute(long)
		 */
		@Override
		public void execute(long timeElapsed) {
			if (timeElapsed < timeToTurnLeft) {
				turnLeft();
			} else if (timeElapsed < timeToDriveLeft) {
				driveForward();
			} else if (timeElapsed < timeToTurnRight) {
				turnRight();
			} else if (timeElapsed < timeToDriveForward) {
				driveForward();
			} else {
				stop();
			}
		}
	}
	
	/**
	 * Autonomous command that drives the robot to the right and then turns back an equal amount to drive forward
	 * It drives to the right for 0.5 seconds and forward for 1.5 seconds
	 */
	public class DriveRight implements AutonomousCommand {
		private static final int timeToTurnRight = 500;
		private static final int timeToDriveRight = timeToTurnRight + 500;
		// Turn back the same amount
		private static final int timeToTurnLeft = timeToDriveRight + timeToTurnRight; 
		private static final int timeToDriveForward = timeToTurnLeft + 1500;
		
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.AutonomousExecutor#execute()
		 */
		@Override
		public void execute(long timeElapsed) {
			if (timeElapsed < timeToTurnRight) {
				turnRight();
			} else if (timeElapsed < timeToDriveRight) {
				driveForward();
			} else if (timeElapsed < timeToTurnLeft) {
				turnLeft();
			} else if (timeElapsed < timeToDriveForward) {
				driveForward();
			} else {
				stop();
			}
		}
	}
	
	/**
	 * Autonomous command that drives the robot straight for 1.5 seconds and then stops
	 */
	public class DriveStraight implements AutonomousCommand {
		private static final int timeToDrive = 1500;

		@Override
		public void execute(long timeElapsed) {			
			if (timeElapsed < timeToDrive) {
				driveForward();
			} else {
				stop();
			}	
		}	
	}
}
