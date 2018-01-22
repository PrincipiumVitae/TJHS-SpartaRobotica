package com.austininseattle.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//Drive Motor Jaguar PWM Controller
	public static final int LEFT_JAGUAR_DRIVE_MOTOR_1 = 0;
	public static final int LEFT_JAGUAR_DRIVE_MOTOR_2 = 1;
	public static final int RIGHT_REVERSED_JAGUAR_DRIVE_MOTOR_1 = 3;
	public static final int RIGHT_REVERSED_JAGUAR_DRIVE_MOTOR_2 = 4;
	public static final int STRAFE_JAGUAR_DRIVE_MOTOR_1 = 2; //Yeah, I don't know who would wire motors like this either ¯\_(ツ)_/¯
	public static final int STRAFE_JAGUAR_DRIVE_MOTOR_2 = 5;
	
	//DIO
	public static final int LEFT_ENCODER_1 = 0;
	public static final int LEFT_ENCODER_2 = 1;
	public static final int RIGHT_ENCODER_1 = 2;
	public static final int RIGHT_ENCODER_2 = 3;
	public static final int FRONT_ENCODER_1 = 4;
	public static final int FRONT_ENCODER_2 = 5;
	public static final int BACK_ENCODER_1 = 6;
	public static final int BACK_ENCODER_2 = 7;
	public static final int IDLER_WHEEL_1 = 11; //Expansion board starts at 10, see DIO for details
	public static final int IDLER_WHEEL_2 = 12;
	
	//CAN Talon addresses
	public static final int LIFT_CAN_MOTOR_1 = 9;
	public static final int LIFT_CAN_MOTOR_2 = 7;
	
	//DIO
	public static final int LIFT_ENCODER_1 = 8;
	public static final int LIFT_ENCODER_2 = 9;
	public static final int LIFT_SWITCH = 10; //DIO Expension DIO_0 maps to DIO_10
	
	//Pneumatic
	public static final int LIFT_BRAKE_1 = 6;
	public static final int LIFT_BRAKE_2 = 7;
	
	//HID = Human Interface Device
	//These are created & started automatically in OI
	public static final int XBOX_CONTROLLER_COUNT = 1; //This starts at 1, if you want to be able to run your robot AT ALL - with a controller that is.
}
