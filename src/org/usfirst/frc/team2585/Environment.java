package org.usfirst.frc.team2585;

import org.impact2585.lib2585.RobotEnvironment;
import org.usfirst.frc.team2585.input.InputMethod;
import org.usfirst.frc.team2585.input.XboxInput;
import org.usfirst.frc.team2585.systems.WheelSystem;


public class Environment extends RobotEnvironment{
	
	/**
	 * 
	 */
	//TODO: change to an appropriate value
	private static final long serialVersionUID = 1L;
	
	private InputMethod input;
	private WheelSystem wheels;
	
	public Environment(Robot robot) {
		super(robot);
		wheels = new WheelSystem();
		input = new XboxInput();
	}

	@Override
	public void destroy() {
		wheels.destroy();
		
	}
	
	public WheelSystem getWheelSystem() {
		return wheels;
	}
	
	public InputMethod getInput() {
		return input;
	}

}
