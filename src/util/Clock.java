/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 30/03/2017
 */
package util;

// TODO: Auto-generated Javadoc
/**
 * Utility class to get elapsed and remaining time.
 */
public class Clock {
	
	/** The starting point. */
	private long startingPoint; // in milliseconds
	
	/**
	 * Instantiates a new clock.
	 */
	public Clock() {
		restart();
	}
	
	/**
	 * Restart.
	 */
	public void restart() {
		startingPoint = System.currentTimeMillis();
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
	 * @param millisecondsGiven the milliseconds given
	 * @return the remaining time
	 */
	public long getRemainingTime(long millisecondsGiven) {
		return millisecondsGiven - getElapsedTime();
	}
}
