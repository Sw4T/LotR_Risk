package objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Joueur implements Serializable {

	private static final long serialVersionUID = 1L;
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
	
	public void remove_Territoire_FromName(String str) {
		for (Territoire t : this.listTerritoire) {
			if (t.getNom().equals(str)) {
				this.remove_Territoire(t);
				return;
			}
		}
	}
	
	public boolean has_Territoire(Territoire t) {
		return this.listTerritoire.contains(t);
	}
	
	public int getNb_Territoire()
	{
		return this.listTerritoire.size();
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
	
	public ArrayList<Territoire> getListTerritoire() {
		return listTerritoire;
	}
	
}
