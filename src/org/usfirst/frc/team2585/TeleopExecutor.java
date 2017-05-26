package org.usfirst.frc.team2585;

import org.impact2585.lib2585.RunnableExecuter;
import org.usfirst.frc.team2585.systems.Initializable;

/**
 * Executor for the teleop period that uses input from a controller to control the robot's actions
 */
public class TeleopExecutor extends RunnableExecuter implements Initializable {
	
	private static final long serialVersionUID = 7239880156928339903L;

	/**
	 * Calls init
	 * @param environment the environment to initialize with
	 */
	public TeleopExecutor(Environment environment) {
		init(environment);
	}
	
	/* (non-Javadoc)
	 * @see org.usfirst.frc.team2585.systems.Initializable#init(org.usfirst.frc.team2585.Environment)
	 */
	@Override
	public void init(Environment environment) {
		getRunnables().add(environment.getWheelSystem());
		getRunnables().add(environment.getShooterSystem());
		getRunnables().add(environment.getIntakeSystem());
	}

}
