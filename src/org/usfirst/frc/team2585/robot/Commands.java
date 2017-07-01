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
		drivetrain = (WheelSystem) environ.getSystem("wheelSystem");
	}
	
	/**
	 * Move the robot forward with no rotation
	 */
	private static void driveForward() {
		drivetrain.driveWithRotation(0.8, 0);
	}
	
	/**
	 * Turn the robot left with no forward movement
	 */
	private static void turnLeft() {
		drivetrain.driveWithRotation(0.0, -0.2);
	}
	
	/**
	 * Turn the robot right with no forward movement
	 */
	private static void turnRight() {
		drivetrain.driveWithRotation(0.0, 0.2);
	}
	
	/**
	 * Set the forward movement and rotation to 0
	 */
	private static void stop() {
		drivetrain.driveWithRotation(0, 0);
	}
	
	
	/**
	 * Autonomous command that that drives the robot forward and then turns left and continues driving forward
	 */
	public class RightSide implements AutonomousCommand {
		private static final int timeToDriveStraight = 3500;
		private static final int timeToTurnLeft = timeToDriveStraight + 400;
		private static final int timeToDriveLeft = timeToTurnLeft + 1000;
		
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.AutonomousCommand#execute(long)
		 */
		@Override
		public void execute(long timeElapsed) {
			if (timeElapsed < timeToDriveStraight) {
				driveForward();
			} else if (timeElapsed < timeToTurnLeft) {
				turnLeft();
			} else if (timeElapsed < timeToDriveLeft) {
				driveForward();
			} else {
				stop();
			}
		}
	}
	
	/**
	 * Autonomous command that drives the robot forward and then turns right and drives forward again
	 */
	public class LeftSide implements AutonomousCommand {
		private static final int timeToDriveStraight = 3500;
		private static final int timeToTurnRight = timeToDriveStraight + 400;
		private static final int timeToDriveRight = timeToTurnRight + 1000;
		
		/* (non-Javadoc)
		 * @see org.usfirst.frc.team2585.AutonomousCommand#execute(long)
		 */
		@Override
		public void execute(long timeElapsed) {
			if (timeElapsed < timeToDriveStraight) {
				driveForward();
			} else if (timeElapsed < timeToTurnRight) {
				turnRight();
			} else if (timeElapsed < timeToDriveRight) {
				driveForward();
			} else {
				stop();
			}
		}
	}
	
	/**
	 * Autonomous command that drives the robot straight for 1.5 seconds and then stops
	 */
	public class Center implements AutonomousCommand {
		private static final int timeToDrive = 3100;

		@Override
		public void execute(long timeElapsed) {			
			if (timeElapsed < timeToDrive) {
				driveForward();
			} else {
				stop();
			}	
		}	
	}
	
	public class None implements AutonomousCommand {
		@Override
		public void execute(long timeElapsed) {
			stop();
		}
	}
}
