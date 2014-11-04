package objects;

import java.util.ArrayList;

public class Joueur {

	private String nom;
	private int nb_unites;
	private int score;
	private ArrayList<Territoire> listTerritoire;
	
	public Joueur(String name)
	{
		this.nom = name;
		this.nb_unites = 0;
		this.score = 0;
		this.listTerritoire = new ArrayList<Territoire>();
	}
	
	public void add_Territoire(Territoire t) {
		if (!this.has_Territoire(t))
			this.listTerritoire.add(t);	
	}
	
	public void remove_Territoire(Territoire t) {
		if (this.has_Territoire(t)) 
			this.listTerritoire.remove(t);
	}
	
	public boolean has_Territoire(Territoire t) {
		return this.listTerritoire.contains(t);
	}
	
	public int getNb_unites() {
		return nb_unites;
	}

	public void setNb_unites(int nb_unites) {
		this.nb_unites = nb_unites;
	}

	public int getScore() {
		return score;
	}

	public void add_Score(int scoreToAdd) {
		this.score += scoreToAdd;
	}

	public String getNom() {
		return nom;
	}
	
}
