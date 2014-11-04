package utils;

import java.util.HashMap;

import objects.Region;
import objects.Territoire;


public class LOTR_Game {

	private int nb_joueurs;
	private HashMap<Region, Territoire> map;
	
	public LOTR_Game(int nb_joueur) {
		this.nb_joueurs = nb_joueur;
	}
	
	public  void init_Data()
	{
		//this.map.put(new Region("Arnor", 12, 7), value)
	}
}
