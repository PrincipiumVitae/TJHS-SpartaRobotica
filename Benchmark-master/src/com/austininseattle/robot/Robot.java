
package com.austininseattle.robot;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.austininseattle.robot.chassis.Chassis;
import com.austininseattle.robot.operator.OI;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot {
	
	static {
		final String LOG_CONFIG_FILENAME = "/home/lvuser/logging.properties";
		System.getProperties().setProperty("java.util.logging.config.file", LOG_CONFIG_FILENAME);
	}
	
	public static final Logger LOGGER = Logger.getLogger(Robot.class.getSimpleName());
	public static final boolean ISDEVELOPMENT = false;
	
	private static ArrayList<StateChangeListener> changeListeners = new ArrayList<StateChangeListener>();
	private static ArrayList<Object> instances = new ArrayList<Object>();
	
	@Override
	public void robotInit() {
		instances.add(OI.getInstance());
		instances.add(Chassis.getInstance());
		
		setState(State.Initialize);
	}
	
	@Override
	public void disabledInit() {
		setState(State.Disabled);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void autonomousInit() {
		setState(State.Autonomous);
	}
	
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		setState(State.Teleoperated);
	}
	
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testInit() {
		setState(State.Test);
	}
	
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	/* ------------------------------------------------------------------	*/
	/* The following functionality is related to the Robot State and		*/ 
	/* the notification of listeners of State changers.					*/
	/*-------------------------------------------------------------------	*/
	
	public enum State {
		Initialize, Disabled, Autonomous, Teleoperated, Test
	}
	
	/**
	 * Gets the current enum state of the robot.
	 * @return Returns Initialize, Disabled, Autonomous, Teleoperated, or Test depending on the current State that was set.
	 */
	public State getState() {
		return StateChangeEvent.currentState;
	}
	
	/**
	 * 
	 * @param state
	 */
	private void setState(State state) {
		StateChangeEvent changeEvent = new StateChangeEvent(this, state);
		fireStateChanged(changeEvent);
	}
	
	public static class StateChangeEvent {
		public final Robot robot;
		public static State currentState;
		public static State previousState;
		
		/**
		 * Sets the current state and updates the previous state.
		 * @param robot Not entirely sure why this is here.
		 * @param currentState The updated state to send out as an event.
		 */
		public StateChangeEvent(Robot robot, State currentState) {
			this.robot = robot;
			StateChangeEvent.previousState = StateChangeEvent.currentState;
			StateChangeEvent.currentState = currentState;
		}
	}
	
	public static interface StateChangeListener {
		void onStateChange(StateChangeEvent changeEvent);
	}
	
	/**
	 * Adds a listener to the ArrayList<> of listeners.
	 * @param l Listener to be added.
	 */
	public static void addStateChangeListener(StateChangeListener l) {
		changeListeners.add(l);
	}
	
	/**
	 * Removes a listener from the ArrayList<> of listeners.
	 * @param l Listener to be removed.
	 */
	public static void removeStateChangeListener(StateChangeListener l) {
		changeListeners.remove(l);
	}
	
	/**
	 * "Fires" off the updated state to each StateChangeListener.
	 * @param changeEvent Sends the current status of the robot in a Status form.
	 */
	private void fireStateChanged(StateChangeEvent changeEvent) {
		for(StateChangeListener l : changeListeners) {
			l.onStateChange(changeEvent);
		}
	}
}
