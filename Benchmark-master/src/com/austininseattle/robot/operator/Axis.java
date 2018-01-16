package com.austininseattle.robot.operator;

import edu.wpi.first.wpilibj.XboxController;

public class Axis implements AxisInput {
	private final XboxController controller;
	private final int posAxis;
	private final int negAxis;
	
	/**
	 * Constructs a new Axis for an XboxController given a single axis.
	 * @param controller Which controller to construct the axis for.
	 * @param axis Which Axis to handle, passed as the positive axis.
	 */
	public Axis(XboxController controller, int axis) {
		this(controller, axis, -1);
	}
	
	/**
	 * Constructs a new Axis for an XboxController given a positive and negative axises.
	 * @param controller Which controller to get the value of.
	 * @param posAxis The positive joystick axis to get the value of.
	 * @param negAxis If the positive axis is different from the negative axis, otherwise any number less than 0 will suffice (or using the other constructor will work).
	 */
	public Axis(XboxController controller, int posAxis, int negAxis) {
		this.controller = controller;
		this.posAxis = posAxis;
		this.negAxis = negAxis;
	}
	
	/**
	 * Gets the value of the axis. If only one axis is definied, then only that axis is captured, or if the negative axis is less than 0 then only the positive axis will be assessed.
	 */
	public double get() {
		if(negAxis < 0)
			return controller.getRawAxis(posAxis);
		return controller.getRawAxis(posAxis) - controller.getRawAxis(negAxis);
	}
	
	/**
	 * Get the POV of the controller, possibly not the best location for this getPOV() method.
	 * @return Returns 0, 45, 90, 135, 180, 225, 270, or 335 from the directional-pad on the XboxController.
	 */
	public int getPOV() {
		return controller.getPOV();
	}
}
