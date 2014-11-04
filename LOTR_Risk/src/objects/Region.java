package objects;

import java.util.HashMap;

public class Region {

	private String nom;
	private int nb_territoire;
	private int nb_renforts;
	
	public Region(String name, int nb_terr, int nb_renf)
	{
		this.nom = name;
		this.nb_territoire = nb_terr;
		this.nb_renforts = nb_renf;
	}
	
	public static void init_Territoires() {
		
	}
	
	public String getNom() {
		return nom;
	}

	public int getNb_territoire() {
		return nb_territoire;
	}

	public int getNb_renforts() {
		return nb_renforts;
	}
}
