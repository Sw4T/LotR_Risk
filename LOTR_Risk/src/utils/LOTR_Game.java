package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import objects.Joueur;
import objects.Territoire;
import objects.TypeTerritoire;
import net.ThreadConnexion;



public class LOTR_Game {

	private ArrayList<Joueur> tabJoueur;
	private LOTR_Data data;
	private ThreadConnexion threadCon;
	
	public LOTR_Game() throws IOException {
		this.data = new LOTR_Data(); //Données du plateau initiales
		this.threadCon = new ThreadConnexion();
		this.threadCon.start();
	}
	
	/** 
	 * Main du serveur
	 * @param args
	 */
	public static void main(String [] args) {
		LOTR_Game jeu = null;
		try {
			jeu = new LOTR_Game();	
			Scanner scan = new Scanner(System.in);
			System.out.println("Entrez \"go\" pour démarrer la procédure après que le client se soit connecté");
			while (!scan.nextLine().equals("go"));
			scan.close();
			if (jeu.init_joueurs_territoires()) {
				for (int i = 0; i < jeu.getTabJoueurs().size(); i++) {
					System.out.println(jeu.getTabJoueurs().get(i));
				}
				jeu.getThreadConnexion().close();
			}
			else
				System.out.println("DER IS A PROBLEM");
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean init_joueurs_territoires() throws ClassNotFoundException, IOException, InterruptedException {
		System.out.println("Lancement de la procédure pour l'envoi de joueur");
		this.tabJoueur = getJoueurs();
		if (this.tabJoueur == null)
			return false;
		System.out.println("Fin de la réception de " + this.tabJoueur.size() + " joueurs");
		ArrayList<Territoire> listAllTerritoires = this.data.getAllTerritoires();
		switch (this.tabJoueur.size()) { //Changement de règles en fonction du nombre de joueur
			case 2 : 
				this.tabJoueur.get(0).setListTerritoire(this.data.getListTerritoireFromType(TypeTerritoire.BIEN));
				this.tabJoueur.get(1).setListTerritoire(this.data.getListTerritoireFromType(TypeTerritoire.MAL));
				this.tabJoueur.add(new Joueur("Neutre")); //Gestion du neutre TODO
				this.tabJoueur.get(2).setListTerritoire(this.data.getListTerritoireFromType(TypeTerritoire.NEUTRE));
				break;
			case 3 : 
				//Joueur 1
				this.tabJoueur.get(0).add_Territoires_From_List(this.data.generateRandomTerritoiresFromType(TypeTerritoire.MAL, 5, listAllTerritoires));
				this.tabJoueur.get(0).add_Territoires_From_List(this.data.generateRandomTerritoiresFromType(TypeTerritoire.NEUTRE, 4, listAllTerritoires));
				//Joueur 2
				this.tabJoueur.get(1).add_Territoires_From_List(this.data.generateRandomTerritoiresFromType(TypeTerritoire.MAL, 4, listAllTerritoires));
				this.tabJoueur.get(1).add_Territoires_From_List(this.data.generateRandomTerritoiresFromType(TypeTerritoire.NEUTRE, 5, listAllTerritoires));
				//Joueur 3
				this.tabJoueur.get(2).add_Territoires_From_List(this.data.getListTerritoireFromType(TypeTerritoire.BIEN));
				break;
			case 4 :
				this.tabJoueur.get(0).add_Territoires_From_List(this.data.generateRandomTerritoiresFromType(TypeTerritoire.MAL, 5, listAllTerritoires));
				this.tabJoueur.get(1).add_Territoires_From_List(this.data.generateRandomTerritoiresFromType(TypeTerritoire.BIEN, 4, listAllTerritoires));
				this.tabJoueur.get(2).add_Territoires_From_List(this.data.generateRandomTerritoiresFromType(TypeTerritoire.MAL, 4, listAllTerritoires));
				this.tabJoueur.get(3).add_Territoires_From_List(this.data.generateRandomTerritoiresFromType(TypeTerritoire.BIEN, 5, listAllTerritoires));
				break;
			default : return false;
		}
		return true;
	}
	
	public ThreadConnexion getThreadConnexion() {
		return threadCon;
	}

	public ArrayList<Joueur> getJoueurs() throws ClassNotFoundException, IOException, InterruptedException {
		this.threadCon.getThreadDonnees().definirTraitementEtExecuter("RecepJoueurs");
		this.threadCon.getThreadDonnees().join();
		return (this.threadCon.getThreadDonnees().getListJoueur());
	}
	
	public ArrayList<Joueur> getTabJoueurs() {
		return tabJoueur;
	}
	
}
