package objects;

import java.util.ArrayList;

public class Region {

	private String nom;
	private ArrayList<Territoire> tabTerritoire;
	private int nb_renforts;
	
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
	
	public int getNb_Territoires() {
		return this.tabTerritoire.size();
	}
	
	public String getNom() {
		return nom;
	}

	public int getNb_Renforts() {
		return nb_renforts;
	}
}
