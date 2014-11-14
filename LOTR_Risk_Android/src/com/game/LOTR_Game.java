package com.game;

import java.util.ArrayList;

import com.objects.Joueur;

public class LOTR_Game {

	private ArrayList<Joueur> tabJoueurs;
	
	public LOTR_Game () {
		this.tabJoueurs = new ArrayList<Joueur>();
	}
	
	public ArrayList<Joueur> getListJoueurs() {
		return this.tabJoueurs;
	}
	
	public void updateJoueurs(ArrayList<Joueur> tab) {
		this.tabJoueurs = tab;
	}
}
