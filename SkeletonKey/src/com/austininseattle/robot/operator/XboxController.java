package com.austininseattle.robot.operator;

import java.util.Arrays;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class XboxController {
	private JoystickButton[] button = new JoystickButton[0];
	private Joystick joystick;
	
	private double DEAD_BAND = 9.0 / 127.0; //Default DEAD_BAND value defined
	
	/**
	 * Defines a Controller with 10 buttons and the corresponding port, according to the FRC DriverStation.
	 * @param port Integer value of which port the FRC DriverStation recognizes the controller as.
	 */
	public XboxController(int port) {
		this(10,port);
	}
	
	/**
	 * Defines a Controller with a number of buttons and which USB port it's connected to according to the FRC DriverStation.
	 * @param numberOfButtons Integer value of how many buttons are on the controller.
	 * @param port Integer value of which port the FRC DriverStation recognizes the controller as.
	 */
	public XboxController(int numberOfButtons, int port) {
		joystick = new Joystick(port);
		
		for(int i = 0; i < numberOfButtons; i++) {
			button = Arrays.copyOf(button, button.length+1);
			button[button.length-1] = new JoystickButton(joystick,i);
		}
	}
	
	/**
	 * Gets the A button on the controller.
	 * @return A button, in memory, from the controller. Class: edu.wpi.first.wpilibj.buttons.JoystickButton
	 */
	public JoystickButton getButtonA() {
		return button[0];
	}
	
	/**
	 * Gets the B button on the controller.
	 * @return B button, in memory, from the controller. Class: edu.wpi.first.wpilibj.buttons.JoystickButton
	 */
	public JoystickButton getButtonB() {
		return button[1];
	}
	
	/**
	 * Gets the X button on the controller.
	 * @return X button, in memory, from the controller. Class: edu.wpi.first.wpilibj.buttons.JoystickButton
	 */
	public JoystickButton getButtonX() {
		return button[2];
	}
	
	/**
	 * Gets the Y button on the controller.
	 * @return Y button, in memory, from the controller. Class: edu.wpi.first.wpilibj.buttons.JoystickButton
	 */
	public JoystickButton getButtonY() {
		return button[3];
	}
	
	/**
	 * Gets the left bumper on the controller.
	 * @return Left bumper, in memory, from the controller. Class: edu.wpi.first.wpilibj.buttons.JoystickButton
	 */
	public JoystickButton getButtonLeftBumper() {
		return button[4];
	}
	
	/**
	 * Gets the right bumper on the controller.
	 * @return Right bumper, in memory, from the controller. Class: edu.wpi.first.wpilibj.buttons.JoystickButton
	 */
	public JoystickButton getButtonRightBumper() {
		return button[5];
	}
	
	/**
	 * Gets the back button on the controller.
	 * @return Back button, in memory, from the controller. Class: edu.wpi.first.wpilibj.buttons.JoystickButton
	 */
	public JoystickButton getButtonBack() {
		return button[6];
	}
	
	/**
	 * Gets the start button on the controller.
	 * @return Start button, in memory, from the controller. Class: edu.wpi.first.wpilibj.buttons.JoystickButton
	 */
	public JoystickButton getButtonStart() {
		return button[7];
	}
	
	/**
	 * Gets the left joystick as a button.
	 * @return When the left Joystick is clicked down.
	 */
	public JoystickButton getButtonLeftStick() {
		return button[8];
	}
	
	/**
	 * Gets the right joystick as a button.
	 * @return When the right Joystick is clicked down.
	 */
	public JoystickButton getButtonRightStick() {
		return button[9];
	}
	
	/**
	 * Gets the current value of the DPad on the controller.
	 * @return Current omni-directional value of the DPad on the controller in 45º increments, returns a value between 0 and 359.
	 */
	public int getPOV() {
		return joystick.getPOV();
	}
	
	/**
	 * Gets the value of the axis given which axis and a pre-defined deadband.
	 * @param axis Which axis to return the value for.
	 * @return Returns a value between deadband and 1.0.
	 */
	public double getAxisValue(int axis) {
		if (axis < 0)
			return 0;
		
		double sign = (axis == 1 || axis == 5) ? -1 : 1;
		double tempAxis = joystick.getRawAxis(axis);
		return sign * (Math.abs(tempAxis) > DEAD_BAND ? tempAxis : 0.0);
	}
	
	/**
	 * Sets the right motor for controller vibration given an intensity between 0.0f and 1.0f.
	 * @param intensity A range between 0.0f and 1.0f of vibration strength on the controller's right vibration motor.
	 */
	public void setRightRumble(float intensity) {
		joystick.setRumble(RumbleType.kRightRumble, intensity);
	}
	
	/**
	 * Sets the left motor for controller vibration given an intensity between 0.0f and 1.0f.
	 * @param intensity A range between 0.0f and 1.0f of vibration strength on the controller's left vibration motor.
	 */
	public void setLeftRumble(float intensity) {
		joystick.setRumble(RumbleType.kLeftRumble, intensity);
	}
	
	/**
	 * Changes the XboxController's range of values in case the joystick has some issues.
	 * @param deadband accepts values from 0.0 to 1.0
	 */
	public void changeDeadband(double deadband) {
		DEAD_BAND = deadband;
	}
}