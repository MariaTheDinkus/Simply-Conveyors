package com.momnop.simplyconveyors.client;

/**
 * An interface that defines an instance of PlayerControllerMP with
 * the ability to modify reach.
 */
public interface IExtendedPlayerController {

	/**
	 * Sets the extra reach the player should have.
	 */
	public void setReachDistanceExtension(float f);

	/**
	 * Gets the current reach extension.
	 */
	public float getReachDistanceExtension();
}