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
	
	public Joueur(String name, String couleurRGB)
	{
		this.nom = name;
		this.nb_unites = 0;
		this.score = 0;
		this.listTerritoire = new ArrayList<Territoire>();
		this.couleur = couleurRGB;
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
		int index = 0;
		for (Territoire t : this.listTerritoire) {
			if (t.getNom().equals(str)) {
				this.listTerritoire.remove(index);
				return;
			}
			index++;
		}
	}
	
	public void update_Territoire(Territoire t) {
		this.remove_Territoire_FromName(t.getNom());
		this.add_Territoire(t);
	}
	
	public Territoire getTerritoireFromIndex(int index) {
		return this.listTerritoire.get(index);
	}
	
	public Territoire getTerritoire(Territoire t) {
		for (Territoire ter : this.listTerritoire) {
			if (t.equals(ter)) 
				return ter;
		}
		return null;
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
		str.append(this.nom + " possède : \n\t");
		for (int i = 0; i < this.listTerritoire.size(); i++) {
			str.append(this.listTerritoire.get(i).getNom() + " (" + 
			this.listTerritoire.get(i).getNB_Unite() + ")\n\t");
		}
		return str.toString();
	}
	
	public boolean has_Territoire(Territoire t) {
		return this.listTerritoire.contains(t);
	}
	
	public int getNB_Territoire() {
		return this.listTerritoire.size();
	}
	
	public int getNB_unites() {
		return nb_unites;
	}

	public void setNB_unites(int nb_unites) {
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
