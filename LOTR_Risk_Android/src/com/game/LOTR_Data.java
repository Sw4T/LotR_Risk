package com.game;

import java.util.ArrayList;

import objects.Joueur;
import objects.Territoire;

public class LOTR_Data {

	public static int NB_TOUR = 0; //Tour courant de la partie
	
	/**
	 * Classe de méthodes statiques servant à manipuler les données du jeu
	 */
	public static Joueur getJoueurFromNomTerritoire(String nomTerritoire, ArrayList<Joueur> tabJoueurs)
	{
		for (Joueur j : tabJoueurs) {
			for (Territoire territoireJoueur : j.getListTerritoire()) {
				if (territoireJoueur.getNom().equals(nomTerritoire)) 
					return j;
			}	
		}
		return null;
	}
}
