package com.austininseattle.robot.operator.drive;

import com.austininseattle.robot.chassis.Chassis;
import com.austininseattle.robot.operator.AxisInput;

public class TankDrive extends Drive {
	protected AxisInput inputA;
	protected AxisInput inputB;
	
	/**
	 * Creates a TankDrive Command via Drive.
	 * @param DRIVETYPE Defines if the drive is inverted or normal, can be null as well - the only time this is ever used is to check if it's an inverted Y-axis or not.
	 * @param inputA This should be the controller's Y-axis of the left joystick, can be interchanged with the right joystick. Must be different from inputB.
	 * @param inputB This should be the controller's Y-axis of the right joystick, can be interchanged with the left joystick. Must be different from inputA.
	 */
	public TankDrive(Drive.DriveType DRIVETYPE, AxisInput inputA, AxisInput inputB) {
		super(DRIVETYPE);
		this.inputA = inputA;
		this.inputB = inputB;
		requires(Chassis.getInstance());
	}
	
	/**
	 * Nothing is done here.
	 */
	public void initialize() {
	}
	
	/**
	 * Sets the speed of the motors given a controller value.
	 */
	public void execute() {
		Chassis.getInstance().setSpeed((DRIVETYPE == Drive.DriveType.YInverted ? -1 : 1) * desensitize(inputA.get(),2.0),
									  (DRIVETYPE == Drive.DriveType.YInverted ? -1 : 1) * desensitize(inputB.get(), 2.0));
	}
	
	/**
	 * Always returns false.
	 */
	public boolean isFinished() {
		return false;
	}
	
	/**
	 * Stops the robot.
	 */
	public void end() {
		Chassis.getInstance().setSpeed(0.0, 0.0);
	}
	
	/**
	 * Cancels the Command and sets the robot speed to 0.0.
	 */
	public void interrupted() {
		end();
		super.cancel();
	}
}
