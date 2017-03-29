/*
 * @author Arthur Godet
 * @author Joachim Hotonnier
 * @author Marie Deur
 * @since 29/03/2017
 */
package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * Classe module pour la génération d'aléatoire.
 */
public class Random {

	/**
	 * Random integer.
	 *
	 * @param from the from
	 * @param to the to
	 * @return un entier aléatoire entre from inclus et to exclus
	 */
	public static int randomInteger(int from, int to) {
		return from + (int)(Math.random()*(to - from));
	}

	/**
	 * Random couple.
	 *
	 * @param from the from
	 * @param to the to
	 * @return un couple aléatoire d'entiers (i,j) avec i < j entre from inclus et to exclus
	 */
	public static int[] randomCouple(int from, int to) {
		int i = randomInteger(from, to);
		int j = randomInteger(from, to-1);
		if(i <= j)
			return new int[]{i, j+1};
		else
			return new int[]{j, i};
	}

	/**
	 * Random two points.
	 *
	 * @param from the from
	 * @param to the to
	 * @return un couple aléatoire d'entiers (i,j) avec i < j entre from exclus et to exclus
	 */
	public static int[] randomTwoPoints(int from, int to) {
		return randomCouple(from+1, to);
	}

	/**
	 * Random sample.
	 *
	 * @param from the from
	 * @param to the to
	 * @param k the k
	 * @return une liste de k entiers aléatoires sans doublons entre from inclus et to exclus
	 */
	public static List<Integer> randomSample(int from, int to, int k) {
		List<Integer> l = new ArrayList<Integer>();
		for(int i = from; i < to; i++)
			l.add(i);
		Collections.shuffle(l);
		return l.subList(0, k);
	}
}
