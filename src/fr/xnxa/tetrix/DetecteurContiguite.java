package fr.xnxa.tetrix;

import java.util.Arrays;

/**
 * Classe qui fait l'analyse d'un tableau de numéros de lignes du Petrix
 *
 * Détermine les index de départ et le nombre de lignes à descendre
 *
 *
 */
public final class DetecteurContiguite {

	/**
	 * classe qui regroupe les données d'un mouvement de bloc de lignes à faire pour
	 * tasser la grille de Petrix suite à des lignes complètes
	 */
	public class CoupleContigu {
		/**
		 * l'index de départ dans la grille de la 1ere ligne du bloc à bouger
		 */
		public int index_depart;

		/**
		 * le nombre de lignes dont il faut décaler vers le bas le bloc à bouger
		 */
		public int decaler_vers_le_bas_de_x_lignes;

		/**
		 * la quantité de lignes du bloc à bouger
		 */
		public int quantite_bougeable;

		/**
		 * Construit un nouveau bloc de données
		 *
		 * @param index                 l'index de départ dans la grille de la 1ere
		 *                              ligne du bloc à bouger
		 * @param nb_lignes_vers_le_bas le nombre de lignes dont il faut décaler vers le
		 *                              bas le bloc à bouger
		 * @param taille                la quantité de lignes du bloc à bouger
		 */
		public CoupleContigu(int index, int nb_lignes_vers_le_bas, int taille) {
			index_depart = index;
			quantite_bougeable = nb_lignes_vers_le_bas;
			this.decaler_vers_le_bas_de_x_lignes = taille;
		}

		/**
		 * fournit une représentation sous forme de chaine de caractères d'une instance
		 * de cette classe
		 */
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for (int i = 0; i < quantite_bougeable; i++) {
				sb.append(index_depart + i);
				sb.append(",");
			}
			if (quantite_bougeable >= 1) {
				sb.deleteCharAt(sb.length() - 1);
			}
			sb.append("] to ");
			sb.append(index_depart + decaler_vers_le_bas_de_x_lignes);
			return sb.toString();
		}
	}

	private int myTab[];
	private int taille;

	private int nb_blocs_contigus = 0;
	private CoupleContigu analyse[];
	private int nb_de_lignes_a_supprimer = 0;

	/**
	 * Construit un nouveau détecteur de contiguité sur la base du tableau des
	 * lignes à éliminer.
	 *
	 * Il suffit alors d'utiliser getAnalyse
	 *
	 * @param tableau_de_lignes tableau indiquant les numéros des lignes à éliminer
	 */
	public DetecteurContiguite(int[] tableau_de_lignes) {
		myTab = tableau_de_lignes;
		taille = myTab.length;
		nb_blocs_contigus = determine_nb_contigus();
	}

	/**
	 * Fournit le nombre total de mouvements de lignes à faire pour éliminer les
	 * lignes indiquées dans le tableau fourni lors de l'instantiation de cette
	 * classe.
	 *
	 * Les mouvements à faire sont fournis par la fonction getMouvements()
	 *
	 * @return le nb de mouvements
	 */
	public int getNbMouvements() {
		return analyse.length;
	}

	/**
	 * Fournit le mouvement à effectuer sur la grille de Petrix
	 *
	 * @param num_mouvement le numéro du mouvement recherché
	 * @return le mouvement à faire
	 */
	public CoupleContigu getMouvements(int num_mouvement) {
		if (num_mouvement >= analyse.length || num_mouvement < 0) {
			throw new IllegalArgumentException("l'index fourni ne va pas avec le nombre de mouvements à faire.");
		}
		return analyse[num_mouvement];
	}

	/**
	 * indique le nombre de lignes qui seront supprimées suite à l'analyse de
	 * contiguité menée par cette classe
	 *
	 * @return le nombre de lignes qui seront supprimées
	 */
	public int getNombreLignesASupprimer() {
		return nb_de_lignes_a_supprimer;
	}

	public int getNombreDeBlocsContigus() {
		return nb_blocs_contigus;
	}

	private int determine_nb_contigus() {

		int last_value = -1;
		int current_value;

		analyse = new CoupleContigu[1];
		int idx_analyse = 0;

		int compteur = 1;
		int compteur_total_decalage = 0;

		for (int i = taille - 1; i >= 0; i--) {
			current_value = myTab[i];
			if (last_value != -1) {
				int delta = last_value - current_value;
				if (delta == 1) {
					// la valeur est contigue : on continue !
					compteur++;
				} else {
					// discontiguité détectée : on note les paramètres suivants :
					// le n° de la ligne du haut du bloc à descendre = current_value + 1
					// le nombre total de lignes à bouger = delta-1
					// le n° de ligne où il faut placer la ligne du haut du bloc = compteur
					// = nb de lignes contigues dans le tableau
					// = nb de lignes à effacer
					compteur_total_decalage += compteur;
					CoupleContigu cpl = new CoupleContigu(current_value + 1, delta - 1, compteur_total_decalage);
					analyse = Arrays.copyOf(analyse, analyse.length + 1);
					analyse[idx_analyse++] = cpl;
					// du coup, on remet le compteur à 1
					compteur = 1;
				}
			} else {
				compteur = 1;

			}
			last_value = current_value;
		}
		// à la fin, il faut décaler tout ce qui est au dessus de la
		// ligne "last_value" de "compteur_total_decalage + 1" lignes
		// vers le bas.
		if (last_value > 0) {
			compteur_total_decalage += compteur;
			CoupleContigu cpl = new CoupleContigu(0, last_value, compteur_total_decalage);
			analyse[idx_analyse++] = cpl;
		}
		nb_de_lignes_a_supprimer = compteur_total_decalage;
		return idx_analyse;
	}

}
