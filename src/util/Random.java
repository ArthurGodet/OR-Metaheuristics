/*
 * Copyright (c) 2017, Arthur Godet, Joachim Hotonnier and Marie Deur. All rights reserved.
 *
 * Licensed under the Revised BSD License.
 * See LICENSE.md file in the project root for full license information.
 */
package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Module class for generating randomness.
 * 
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 19/03/2017
 */
public class Random {

	/**
	 * Returns a random integer between from (inclusive) and to (exclusive).
	 *
	 * @param from from
	 * @param to to
	 * @return a random integer between from (inclusive) and to (exclusive)
	 */
	public static int randomInteger(int from, int to) {
		return from + (int)(Math.random()*(to - from));
	}

	/**
	 * Returns a couple of integers both between from (inclusive) and to (exclusive), and the first
	 * integer being strictly smaller than the second one.
	 *
	 * @param from from
	 * @param to to
	 * @return a couple of integers
	 */
	public static int[] randomCouple(int from, int to) {
		int i = randomInteger(from, to);
		int j = randomInteger(from, to-1);
		if(i <= j)
			j++;

		int[] couple = new int[]{i, j};
		Arrays.sort(couple);
		return couple;
	}

	/**
	 * Returns a triplet of integers each one between from (inclusive) and to (exclusive), 
	 * with integer being strictly smaller than the next.
	 *
	 * @param from from
	 * @param to to
	 * @return a triplet of integers
	 */
	public static int[] randomTriplet(int from, int to) {
		int i = randomInteger(from, to);
		int j = randomInteger(from, to-1);
		int k = randomInteger(from, to-2);
		if(i <= j)
			j++;
		if(Math.min(i, j) <= k)
			k++;
		if(Math.max(i, j) <= k)
			k++;

		int[] triplet = new int[]{i, j, k};
		Arrays.sort(triplet);
		return triplet;
	}

	/**
	 * Generates two random integers between from+1 (inclusive) and to (exclusive).
	 *
	 * @param from from
	 * @param to to
	 * @return a couple of integers.
	 */
	public static int[] randomTwoPoints(int from, int to) {
		return randomCouple(from+1, to);
	}

	/**
	 * Randomly generates a list of k integers (without repetition), each integer being between
	 * from (inclusive) and to (exclusive).
	 *
	 * @param from from
	 * @param to to
	 * @param k the number of integers
	 * @return a list of k integers 
	 */
	public static List<Integer> randomSample(int from, int to, int k) {
		List<Integer> l = new ArrayList<Integer>();
		for(int i = from; i < to; i++)
			l.add(i);
		Collections.shuffle(l);
		return l.subList(0, k);
	}

	/**
	 * Generates a random sequence of the integers between from and to.
	 *
	 * @param from from
	 * @param to to
	 * @return the list of integers
	 */
	public static List<Integer> randomShuffle(int from, int to) {
		return randomSample(from, to, to-from);
	}
}
