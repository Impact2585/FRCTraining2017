package org.usfirst.frc.team2585;

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

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser = new SendableChooser<AutonomousCommand>();
		chooser.addDefault("Straight Drive", new Commands.DriveStraight());
		chooser.addObject("Drive Left", new Commands.DriveLeft());
		chooser.addObject("Drive Right", new Commands.DriveRight());
		SmartDashboard.putData("Auton choices", chooser);
		
		environ = new Environment(this);
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

