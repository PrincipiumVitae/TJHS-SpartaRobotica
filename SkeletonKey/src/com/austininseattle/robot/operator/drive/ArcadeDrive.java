package com.austininseattle.robot.operator.drive;

import com.austininseattle.robot.chassis.Chassis;
import com.austininseattle.robot.operator.AxisInput;

public class ArcadeDrive extends Drive {
	protected AxisInput inputA;
	protected AxisInput inputB;
	private double LEFTMOTORSPEED;
	private double RIGHTMOTORSPEED;
	
	/**
	 * Creates a new ArcadeDrive Command for driving the robot.
	 * @param DRIVETYPE Whether or not the Y-axis is inverted or not, can be null.
	 * @param inputA Which AxisIput to treat as the Y-axis.
	 * @param inputB Which AxisInput to treat as the X-axis.
	 */
	public ArcadeDrive(Drive.DriveType DRIVETYPE, AxisInput inputA, AxisInput inputB) {
		super(DRIVETYPE);
		this.inputA = inputA;
		this.inputB = inputB;
		LEFTMOTORSPEED = 0.0;
		RIGHTMOTORSPEED = 0.0;
		requires(Chassis.getInstance());
	}
	
	/**
	 * Does absolutely nothing.
	 */
	protected void initialize() {
	}
	
	/**
	 * Uses inputA as the Y axis of the controller, uses inputB as the X axis of the controller.
	 */
	protected void execute() {
		if((inputA.get() > 0.0 && DRIVETYPE == Drive.DriveType.Standard) || (inputA.get() < 0.0 && DRIVETYPE == Drive.DriveType.YInverted)) {
			if(inputB.get() > 0.0) {
				LEFTMOTORSPEED = Math.abs(inputA.get()) - inputB.get();
				RIGHTMOTORSPEED = Math.max(Math.abs(inputA.get()), inputB.get());
			} else {
				LEFTMOTORSPEED = Math.max(Math.abs(inputA.get()), -inputB.get());
				RIGHTMOTORSPEED = Math.abs(inputA.get()) + inputB.get();
			}
		} else {
			if(inputB.get() > 0.0) {
				LEFTMOTORSPEED = -Math.max(inputA.get(), inputB.get());
				RIGHTMOTORSPEED = inputA.get() + inputB.get();
			} else {
				LEFTMOTORSPEED = inputA.get() - inputB.get();
				RIGHTMOTORSPEED = Math.min(inputA.get(), inputB.get());
			}
		}
		Chassis.getInstance().setSpeed(LEFTMOTORSPEED, RIGHTMOTORSPEED);
	}
	
	/**
	 * Always returns false.
	 */
	protected boolean isFinished() {
		return false;
	}
	
	/**
	 * Does absolutely nothing.
	 */
	protected void end() {
	}
	
	/**
	 * Cancels the Drive command.
	 */
	protected void interrupted() {
		super.cancel();
	}
}
