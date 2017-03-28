package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe module pour la génération d'aléatoire
 */
public class Random {

	/** @return un entier aléatoire entre from inclus et to exclus */
	public static int randomInteger(int from, int to) {
		return from + (int)(Math.random()*(to - from));
	}

	/**
	 * @return deux entiers aléatoires formant une segment semi-ouvert non vide
	 * contenu dans l'intervalle entre from inclus et to exclus en laissant
	 * au moins un élément avant et après.
	 */
	public static int[] randomTwoPoints(int from, int to) {
		int i = randomInteger(from+1, to);
		int j = randomInteger(from+2, to);
		if(i < j)
			return new int[]{i, j};
		else
			return new int[]{j-1, i};
	}

	/** @return une liste de k entiers aléatoires sans doublons entre from inclus et to exclus */
	public static List<Integer> randomSample(int from, int to, int k) {
		List<Integer> l = new ArrayList<Integer>();
		for(int i = from; i < to; i++)
			l.add(i);
		Collections.shuffle(l);
		return l.subList(0, k);
	}

	/** Affichage des valeurs calculées par randomTwoPoints pour se convaincre de son fonctionnement */
	public static void main(String[] args) {
		int from = 0, to = 5;
		for(int i = from+1; i < to; i++) {
			for(int j = from+2; j < to; j++)
				if(i < j)
					System.out.print(" "+i+","+j);
				else
					System.out.print(" "+(j-1)+","+i);
			System.out.println();
		}
	}
}
