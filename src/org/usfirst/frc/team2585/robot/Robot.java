package org.usfirst.frc.team2585.robot;

import org.impact2585.lib2585.ExecuterBasedRobot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends ExecuterBasedRobot {

	private static final long serialVersionUID = 5925353859431414919L;

	SendableChooser<AutonomousCommand> chooser; 
	
	private Environment environ;
	private Commands commands;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser = new SendableChooser<AutonomousCommand>();
		environ = new Environment(this);
		
		commands = new Commands(environ);
		chooser.addDefault("Straight Drive", commands.new DriveStraight());
		chooser.addObject("Drive Left", commands.new DriveLeft());
		chooser.addObject("Drive Right", commands.new DriveRight());
		SmartDashboard.putData("Auton choices", chooser);
	}

	/**
	 * This is run at the beginning of the autonomous period
	 * It gets the chosen executor from the chooser and starts its execution thread
	 */
	@Override
	public void autonomousInit() {
		AutonomousCommand autoSelected = chooser.getSelected();
		AutonomousExecutor executor = new AutonomousExecutor();
		executor.init(environ);
		executor.setTask(autoSelected);
		
		setExecuter(executor);
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.IterativeRobot#teleopInit()
	 */
	@Override 
	public void teleopInit() {
		setExecuter(new TeleopExecutor(environ));
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

