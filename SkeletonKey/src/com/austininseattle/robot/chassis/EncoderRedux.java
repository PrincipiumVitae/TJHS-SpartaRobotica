package com.austininseattle.robot.chassis;

import edu.wpi.first.wpilibj.Encoder;

public class EncoderRedux {
	protected int channelA;
	protected int channelB;
	private boolean reverseDirection;
	private Encoder encoder;
	
	/**
	 * Defines a new Encoder without the reversed direction.
	 * @param channelA The first of the two ports the encoder was connected to, physically.
	 * @param channelB The second of the two ports the encoder was connected to, physically.
	 */
	public EncoderRedux(int channelA, int channelB) {
		this.channelA = channelA;
		this.channelB = channelB;
		this.reverseDirection = false;
		if(channelA > -1 && channelB > -1)
			encoder = new Encoder(channelA, channelB);
	}
	
	/**
	 * Defines a new Encoder with the possibility of a reversed direction.
	 * @param channelA The first of the two ports the encoder was connected to, physically.
	 * @param channelB The second of the two ports the encoder was connected to, physically.
	 * @param reverseDirection Accepts true or false depending on whether or not the encoder needs to be reversed to encode positive values.
	 */
	public EncoderRedux(int channelA, int channelB, boolean reverseDirection) {
		this.channelA = channelA;
		this.channelB = channelB;
		this.reverseDirection = reverseDirection;
		if(channelA > -1 && channelB > -1)
			encoder = new Encoder(channelA, channelB, reverseDirection);
	}
	
	/**
	 * Sets the first physical channel location and automatically creates the Encoder instance if both channels are greater than -1.
	 * @param channelA The first physical connection of the Encoder that is plugged in.
	 */
	public void setChannelA(int channelA) {
		this.channelA = channelA;
		if(channelA > -1 && channelB > -1)
			encoder = new Encoder(channelA, channelB, reverseDirection);
	}
	
	/**
	 * Sets the second physical channel location and automatically creates the Encoder instance if both channels are greater than -1.
	 * @param channelB The second physical connection of the Encoder that is plugged in.
	 */
	public void setChannelB(int channelB) {
		this.channelB = channelB;
		if(channelB > -1 && channelA > -1)
			encoder = new Encoder(channelA, channelB, reverseDirection);
	}
	
	/**
	 * Creates a new Encoder Object, can be used to override the default creation of the encoder if one of the channels is less then 0.
	 */
	public void createEncoder() {
		encoder = new Encoder(channelA, channelB, reverseDirection);
	}
	
	/**
	 * Gets the Encoder object.
	 * @return Returns the Encoder object used to identify where the encoder was connected to.
	 */
	public Encoder getEncoder() {
		return encoder;
	}
}
