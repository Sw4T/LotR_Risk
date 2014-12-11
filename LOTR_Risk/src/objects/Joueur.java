package objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Joueur implements Serializable {

	private static final long serialVersionUID = 3138516806578033883L;
	private String nom;
	private int nb_unites;
	private int score;
	private String couleur;
	private ArrayList<Territoire> listTerritoire;
	
	public Joueur(String name, String colorRGB)
	{
		this.nom = name;
		this.nb_unites = 0;
		this.score = 0;
		this.couleur = colorRGB;
		this.listTerritoire = new ArrayList<Territoire>();
	}
	
	public void add_Territoire(Territoire t) {
		if (!this.has_Territoire(t))
			this.listTerritoire.add(t);	
	}
	
	public void add_Territoires_From_List(ArrayList<Territoire> list) {
		for (Territoire t : list) {
			if (!this.has_Territoire(t)) {
				this.listTerritoire.add(t);
			}
		}
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

	public Territoire getTerritoire_FromIndex(int index) {
		return this.listTerritoire.get(index);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (!(o instanceof Joueur))
			return false;
		Joueur j = (Joueur) o;
		if (this.nom.equals(j.getNom()))
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append(this.nom + " (" + this.fixColorJava() + ") " + "possède : \n\t");
		for (int i = 0; i < this.listTerritoire.size(); i++) {
			str.append(this.listTerritoire.get(i).getNom() + "\n\t");
		}
		return str.toString();
	}
	
	//Retourne le format RGB nécessaire à l'utilisation de la classe Color Java
	public String fixColorJava() {
		return (this.couleur.substring(3));
	}
	
	public boolean has_Territoire(Territoire t) {
		return this.listTerritoire.contains(t);
	}
	
	public int getNb_Territoire() {
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
	
	public void setListTerritoire(ArrayList<Territoire> listTerritoire) {
		this.listTerritoire = listTerritoire;
	}
	
	public ArrayList<Territoire> getListTerritoire() {
		return listTerritoire;
	}	
	
	public String getCouleurRGB() {
		return couleur;
	}
}
