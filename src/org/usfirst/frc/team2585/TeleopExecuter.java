package org.usfirst.frc.team2585;

import org.impact2585.lib2585.RunnableExecuter;
import org.usfirst.frc.team2585.systems.Initializable;

public class TeleopExecuter extends RunnableExecuter implements Initializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TeleopExecuter(Environment environment) {
		init(environment);
	}
	
	@Override
	public void init(Environment environment) {
		getRunnables().add(environment.getWheelSystem());
	}

}
