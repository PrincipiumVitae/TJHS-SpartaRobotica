package com.austininseattle.robot.operator.drive;

import edu.wpi.first.wpilibj.command.Command;

public class Drive extends Command {
	public final DriveType DRIVETYPE;
	
	public enum DriveType {
		Standard,
		YInverted;
	}
	
	/**
	 * Always returns false.
	 */
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	/**
	 * Holds DriveType from the enum above.
	 * @param DRIVETYPE Can be Drive.DriveType: ArcadeDrive, TankDrive, YInvertedArcadeDrive, or YInvertedTankDrive.
	 */
	public Drive(DriveType DRIVETYPE) {
		this.DRIVETYPE = DRIVETYPE;
	}
	
	/**
	 * Desensitizes controller input (i.e. amplifies controller input using value * Math.pow(Math.abs(value),power-1)
	 * @param value The base to apply.
	 * @param power How many factors of the base to multiply by, subtracted by 1 since the result ends with value if power is 1.0.
	 * @return Returns the result of value * Math.pow(Math.abs(value),power-1)
	 */
	protected double desensitize(double value, double power) {
		return value * Math.pow(Math.abs(value), power-1);
	}
}
