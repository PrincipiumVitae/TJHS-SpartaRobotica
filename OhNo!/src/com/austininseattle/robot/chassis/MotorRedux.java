package com.austininseattle.robot.chassis;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.Talon;

public class MotorRedux {
	private MotorType MOTORTYPE;
	private PWMSpeedController MOTOR;
	private TalonSRX SRX_MOTOR;
	private double storedSpeed;
	
	public enum MotorType {
		CANTalon, JAGUAR, TalonSRX
	}
	
	/**
	 * Motor Object for instantiating motors in Chassis via RobotMap.
	 * @param MOTORTYPE Can only be CANTalon, Jaguar, or a TalonSRX, as per the WPILIB.
	 */
	public MotorRedux(MotorType MOTORTYPE) {
		this.MOTORTYPE = MOTORTYPE;
	}
	
	/**
	 * Motor Object for instantiating motors in Chassis via RobotMap.
	 * @param MOTORTYPE Can only be CanTalon, Jaguar, or a TalonSRX, as per the WPILIB.
	 * @param PORT Which port the motor is plugged into.
	 */
	public MotorRedux(MotorType MOTORTYPE, int PORT) {
		this(MOTORTYPE);
		createMotor(PORT);
	}
	
	/**
	 * Creates the motor officially at the hardware-level, can be delayed by calling Motor(MotorType), otherwise this is called automatically.
	 * @param PORT Which port the motor is plugged into.
	 */
	public void createMotor(int PORT) {
		if(MOTORTYPE == MotorType.CANTalon)
			MOTOR = new Talon(PORT);
		else if(MOTORTYPE == MotorType.JAGUAR)
			MOTOR = new Jaguar(PORT);
		else if(MOTORTYPE == MotorType.TalonSRX)
			SRX_MOTOR = new TalonSRX(PORT);
		else {
			try {
				throw new Exception("Motor Type invalid, please change the variable name to hold CAN, SRX, or JAGUAR for Chassis.java");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sets the motor's speed.
	 * @param speed A double value between -1.0 and 1.0.
	 */
	public void setSpeed(double speed) {
		if(MOTOR != null)
			MOTOR.set(speed);
		else
			SRX_MOTOR.set(ControlMode.PercentOutput, speed);
		this.storedSpeed = speed;
	}
	
	/**
	 * Inverts the motor on the WPILIB-level (which is typically hardware-level).
	 * @param inverted Whether or not the motor operates in a clockwise motion or counter-clockwise. (When facing from the cables to the silver tip of the motor that holds gear(s) typically.
	 */
	public void setInverted(boolean inverted) {
		MOTOR.setInverted(inverted);
	}
	
	/**
	 * Returns Motor.get().
	 * @return Returns a double of Motor.get().
	 */
	public double get() {
		return storedSpeed;
	}
	
	/**
	 * Returns whether or not the motor is inverted.
	 * @return Returns true or false depending on whether or not the motor is inverted.
	 */
	public boolean getInverted() {
		if(MOTOR != null)
			return MOTOR.getInverted();
		return SRX_MOTOR.getInverted();
	}
}
