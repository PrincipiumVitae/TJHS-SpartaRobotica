package com.austininseattle.robot.operator;

import java.util.Arrays;

import com.austininseattle.robot.Robot;
import com.austininseattle.robot.RobotMap;
import com.austininseattle.robot.Robot.State;
import com.austininseattle.robot.Robot.StateChangeEvent;
import com.austininseattle.robot.Robot.StateChangeListener;
import com.austininseattle.robot.chassis.Chassis;
import com.austininseattle.robot.operator.drive.ArcadeDrive;
import com.austininseattle.robot.operator.drive.Drive;
import com.austininseattle.robot.operator.drive.TankDrive;
import com.austininseattle.robot.operator.drive.Drive.DriveType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	//Instance handling done here:
	private static OI INSTANCE;
	
	/**
	 * Gets the currently active instance of OI
	 * @return returns the object, in memory, of the active OI instance.
	 */
	public static OI getInstance() {
		return INSTANCE == null ? (INSTANCE = new OI()) : INSTANCE;
	}
	
	//Start of OI code:
	private XboxController[] controllers = new XboxController[0];
	
	private OI() {
		for(int i = 0; i < RobotMap.XBOX_CONTROLLER_COUNT; i++) {
			createXboxController();
		}
		
		//if the driver's controller is at least attached
		if(controllers.length > 1) {
			setupDriveCommand();
		}
		
		//if there is only a driver's Controller attached
		if(controllers.length == 1) {
			
		}
		
		// --Call mechanisms during development in here, this section gets to be wonderfully messy while the other one holds the
		//		official driver and manipulator preferences. This is controlled in Robot.java under a variable called ISDEVELOPMENT on
		//		line 24.
		// button.whenPressed(new ExampleCommand());
		// button.whileHeld(new ExampleCommand());
		// button.whenReleased(new ExampleCommand());
		// i.e.
		//		controllers[0].getButtonA().whileHeld(new ExampleCommand());
		//		controllers[0].getButtonB().whenPressed(new ExampleCommand());
		//		controllers[0].getButtonX().whenPressed(new ExampleCommand());
	}
	
	/**
	 * Sets up the Drive Command during OI's initialization.
	 */
	private void setupDriveCommand() {
		//Get the left & right stick axis (makes things easier when we go to move this into our own code)
		Axis leftStickX = new Axis(controllers[0], 1);
		Axis leftStickY = new Axis(controllers[0], 2);
		Axis rightStickX = new Axis(controllers[0], 4);
		Axis rightStickY = new Axis(controllers[0], 5);
		
		//Define two methods to drive the robot
		TankDrive tankDrive = new TankDrive(DriveType.Standard, leftStickY, rightStickY);
		ArcadeDrive arcadeDrive = new ArcadeDrive(DriveType.Standard, leftStickY, leftStickX);
		
		//Send the method selection to the DriverStation (Current Dashboard)
		final SendableChooser<Drive> driveChooser = new SendableChooser<Drive>();
		driveChooser.addDefault(tankDrive.getName(), tankDrive);
		driveChooser.addObject(arcadeDrive.getName(), arcadeDrive);
		SmartDashboard.putData("Drive Chooser", driveChooser);
		
		//Use a statechangelistener to handle which of these options is selected, just makes things easier than having any possibility
		//		of having the right/wrong option selected.
		Robot.addStateChangeListener(new StateChangeListener() {
			@SuppressWarnings("static-access")
			public void onStateChange(StateChangeEvent changeEvent) {
				if(changeEvent.currentState == State.Teleoperated) {
					Chassis.getInstance().setDefaultCommand((Command)driveChooser.getSelected());
				}
			}
		});
	}
	
	/**
	 * Creates an XboxController for use with the OI class
	 */
	private void createXboxController() {
		controllers = Arrays.copyOf(controllers, controllers.length+1);
		controllers[controllers.length-1] = new XboxController(controllers.length-1);
	}
}
