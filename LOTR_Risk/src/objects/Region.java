package objects;

import java.util.ArrayList;

public class Region {

	private String nom;
	private ArrayList<Territoire> tabTerritoire;
	private int nb_renforts; //Nombre de renforts lorsque région possédé entièrement
	
	public Region(String name, int nb_renf)
	{
		this.nom = name;
		this.nb_renforts = nb_renf;
		this.tabTerritoire = new ArrayList<Territoire>();
	}

	public void add_Territoire(Territoire t) {
		if (!this.tabTerritoire.contains(t))
			this.tabTerritoire.add(t);			
	}
	
	public Territoire get_Territoire(String nom) {
		for (Territoire t : this.tabTerritoire)
		{
			if (t.getNom().equals(nom))
				return t;
		}
		return null;
	}
	
	public int getNb_Territoires() {
		return this.tabTerritoire.size();
	}
	
	public String getNom() {
		return nom;
	}
	
	/* ONLY FOR TESTING */
	public void setNom(String s) {
		this.nom = s;
	}

	public int getNB_Renforts() {
		return nb_renforts;
	}
	
	public ArrayList<Territoire> getListTerritoire() {
		return tabTerritoire;
	}
}
