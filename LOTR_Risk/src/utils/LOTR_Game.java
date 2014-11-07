package utils;

import java.util.ArrayList;

import objects.Joueur;
import net.ThreadConnexion;



public class LOTR_Game {

	private int nb_joueurs;
	private ArrayList<Joueur> tabJoueur;
	private LOTR_Data data;
	private ThreadConnexion threadCon;
	
	public LOTR_Game() {
		this.data = new LOTR_Data(); //Données du plateau initiales
		this.threadCon = new ThreadConnexion();
		new Thread(threadCon).start();
	}
	
	/** 
	 * Main du serveur
	 * @param args
	 */
	public static void main(String [] args) {
		LOTR_Game jeu = new LOTR_Game();
		jeu.init_Joueurs();	
	}
	
	public void init_Joueurs() 
	{
		
	}
	
}
