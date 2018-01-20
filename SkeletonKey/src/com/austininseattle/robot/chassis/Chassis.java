package com.austininseattle.robot.chassis;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.logging.Level;

import com.austininseattle.robot.Robot;
import com.austininseattle.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Chassis extends Subsystem {
	
	private static final Chassis INSTANCE = new Chassis();
	private double left = 0;
	private double right = 0;
	private static MotorRedux[] motors = new MotorRedux[0];
	private static MotorRedux[] leftDriveMotors = new MotorRedux[0];
	private static MotorRedux[] rightDriveMotors = new MotorRedux[0];
	private static EncoderRedux[] encoders = new EncoderRedux[0];
	
	/**
	 * Gets the Chassis object in memory, should be called instead of "new Chassis();".
	 * @return Returns an object of Chassis.
	 */
	public static Chassis getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Finds and sorts motors and encoders from RobotMap.java using keywords in the variable names.
	 */
	private Chassis() {
		Field[] fields = RobotMap.class.getFields();
		for(Field f : fields) {
			if(containsString(f,"motor") && !containsString(f,"drive")) {
				Arrays.copyOf(motors, motors.length+1);
				motors[motors.length-1] = new MotorRedux(identifyMotor(f),getPORT(f));
				if(containsString(f,"reversed")) {
					rightDriveMotors[rightDriveMotors.length-1].setInverted(true);
				}
			} else if(containsString(f,"motor") && containsString(f,"drive") && containsString(f,"left")) {
				Arrays.copyOf(leftDriveMotors, leftDriveMotors.length+1);
				leftDriveMotors[leftDriveMotors.length-1] = new MotorRedux(identifyMotor(f),getPORT(f));
				if(containsString(f,"reversed")) {
					rightDriveMotors[rightDriveMotors.length-1].setInverted(true);
				}
			} else if(containsString(f,"motor") && containsString(f,"drive") && containsString(f,"right")) {
				Arrays.copyOf(rightDriveMotors, rightDriveMotors.length+1);
				rightDriveMotors[rightDriveMotors.length-1] = new MotorRedux(identifyMotor(f),getPORT(f));
				if(containsString(f,"reversed")) {
					rightDriveMotors[rightDriveMotors.length-1].setInverted(true);
				}
			} else if(containsString(f,"encoder")) {
				if(encoders.length == 0 || encoders[encoders.length-1].channelB > -1) {
					Arrays.copyOf(encoders, encoders.length+1);
					try {
						encoders[encoders.length-1] = new EncoderRedux(f.getInt(f),-1);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						Robot.LOGGER.log(Level.WARNING, e.getMessage());
					}
				} else if(encoders.length > 0 && encoders[encoders.length-1].channelB < 0) {
					try {
						encoders[encoders.length-1].channelB = f.getInt(f);
					} catch(IllegalArgumentException | IllegalAccessException e) {
						Robot.LOGGER.log(Level.WARNING, e.getMessage());
					}
				}
			}
		}
	}
	
	/**
	 * Returns if the variable name contains item.
	 * @param f Field from within a class object.
	 * @param item Part of the variable name to search for.
	 * @return Returns whether or not that section of the string exists in the variable name.
	 */
	private boolean containsString(Field f, String item) {
		return f.getName().toLowerCase().contains(item.toLowerCase());
	}
	
	/**
	 * Returns the type of motor that is being used
	 * @param f Field of the variable from the RobotMap class.
	 * @return Returns the correct Motor.MotorType enum for the type of the motor according to the variable name.
	 */
	private MotorRedux.MotorType identifyMotor(Field f) {
		if(containsString(f,"srx")) {
			return MotorRedux.MotorType.TalonSRX;
		} else if(containsString(f,"talon")) {
			return MotorRedux.MotorType.CANTalon;
		} else if(containsString(f,"jaguar")) {
			return MotorRedux.MotorType.JAGUAR;
		}
		return null;
	}
	
	/**
	 * Converts Field to integer to pass to Motor or Encoder.
	 * @param field Which variable from RobotMap to check.
	 * @return returns a value greater than -1, or -1 if the value cannot be found - if it is negative 1 then the WPILIB will through an exception relevant to the port defined.
	 */
	private int getPORT(Field field) {
		try {
			if(field.isAccessible() && field.getClass().getTypeName().toLowerCase().indexOf("int") > -1) {
				return (int) field.getInt(field);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			Robot.LOGGER.log(Level.WARNING, e.getMessage());
			System.out.println(e.getMessage());
		}
		return -1;
	}
	
	/**
	 * Gets the current forward-direction velocity of the robot.
	 * @return Returns (left+right)/2 of the current speed that has been set.
	 */
	public double getVelocity() {
		return (left+right)/2;
	}
	
	/**
	 * Sets the speed of the robot.
	 * @param left Defines the speed of the left motors of the robot.
	 * @param right Defines the speed of the right motors of the robot.
	 */
	public void setSpeed(double left, double right) {
		this.left = left;
		this.right = right;
		
		for(MotorRedux m : leftDriveMotors) {
			m.setSpeed(left);
		}
		
		for(MotorRedux m : rightDriveMotors) {
			m.setSpeed(right);
		}
	}
	
	/**
	 * Sets the default command of Chassis.
	 */
	public void setDefaultCommand(Command command) {
		Command current = getCurrentCommand();
		if(current != null) {
			current.cancel();
		}
		super.setDefaultCommand(command);
	}
	
	@Override
	protected void initDefaultCommand() { }
}
