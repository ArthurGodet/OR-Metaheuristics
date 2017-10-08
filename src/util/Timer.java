/*
 * Copyright (c) 2017, Arthur Godet, Joachim Hotonnier and Marie Deur. All rights reserved.
 *
 * Licensed under the Revised BSD License.
 * See LICENSE.md file in the project root for full license information.
 */
package util;

/**
 * Utility class to get elapsed and remaining time.
 * 
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 19/03/2017
 */
public class Timer {

	/** The starting point. */
	private long startingPoint; // in milliseconds

	/** The time given. */
	private long timeGiven; // in milliseconds

	/**
	 * Instantiates a new timer.
	 *
	 * @param millisecondsGiven the milliseconds given
	 */
	public Timer(long millisecondsGiven) {
		startingPoint = System.currentTimeMillis();
		timeGiven = millisecondsGiven;
	}

	/**
	 * Gets the elapsed time.
	 *
	 * @return the elapsed time
	 */
	public long getElapsedTime() {
		return System.currentTimeMillis() - startingPoint;
	}

	/**
	 * Gets the remaining time.
	 *
	 * @return the remaining time
	 */
	public long getRemainingTime() {
		return timeGiven - getElapsedTime();
	}

	/**
	 * Checks if the timer is finished.
	 *
	 * @return true, if finished
	 */
	public boolean isFinished() {
		return getRemainingTime() <= 0;
	}
	
	/**
	 * Resets the starting point.
	 */
	public void reset() {
		this.startingPoint = System.currentTimeMillis();
	}
	
	/**
	 * Returns the time given to this Timer.
	 *
	 * @return the time given to this Timer.
	 */
	public long getTimeGiven() {
		return this.timeGiven;
	}
}
