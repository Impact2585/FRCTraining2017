package org.usfirst.frc.team2585.robot;

import java.util.Collection;
import java.util.HashMap;

import org.impact2585.lib2585.RobotEnvironment;
import org.usfirst.frc.team2585.input.InputMethod;
import org.usfirst.frc.team2585.input.XboxInput;
import org.usfirst.frc.team2585.systems.IntakeSystem;
import org.usfirst.frc.team2585.systems.LiftSystem;
import org.usfirst.frc.team2585.systems.RobotSystem;
import org.usfirst.frc.team2585.systems.ShooterSystem;
import org.usfirst.frc.team2585.systems.WheelSystem;

/**
 * This class contains the robot's systems and input
 */
public class Environment extends RobotEnvironment{
	
	private static final long serialVersionUID = 6320366174026889629L;
	private HashMap<String, RobotSystem> systems;
	private InputMethod input;

	
	public static final String WHEEL_SYSTEM = "wheelSystem";
	public static final String SHOOTER_SYSTEM = "shooterSystem";
	public static final String INTAKE_SYSTEM = "intakeSystem";
	public static final String LIFT_SYSTEM = "liftSystem";
	
	/**
	 * Initializes the systems
	 * @param robot the robot that belongs to the environment
	 */
	public Environment(Robot robot) {
		super(robot);
		input = new XboxInput();
		
		systems = new HashMap<String, RobotSystem>();

		systems.put(Environment.WHEEL_SYSTEM, new WheelSystem());
		systems.put(Environment.SHOOTER_SYSTEM, new ShooterSystem());
		systems.put(Environment.INTAKE_SYSTEM, new IntakeSystem());
		systems.put(Environment.LIFT_SYSTEM, new LiftSystem());
		
		for (RobotSystem system : systems.values()) {
			system.init(this);
		}
	}
	
	/**
	 * @param systemName the name of the system to return 
	 * @return the environment's instance of the RobotSystem with the given name
	 */
	public RobotSystem getSystem(String systemName) {
		return systems.get(systemName);
	}
	
	/**
	 * @return a collection of all of the environment's systems
	 */
	public Collection<RobotSystem> getAllSystems() {
		return systems.values();
	}
	
	/**
	 * @return the input
	 */
	public InputMethod getInput() {
		return input;
	}
	
	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		for (RobotSystem system : systems.values()) {
			system.destroy();
		}
	}
}
