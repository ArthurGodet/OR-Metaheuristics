/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package util;

/**
 * Utility class to get elapsed and remaining time.
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
