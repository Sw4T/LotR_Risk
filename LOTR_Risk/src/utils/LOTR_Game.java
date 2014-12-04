package utils;

import java.io.IOException;
import java.util.ArrayList;

import net.ThreadConnexion;
import net.ThreadEnvoiReception;
import objects.Joueur;
import objects.Territoire;
import objects.TypeTerritoire;

public class LOTR_Game implements InterfaceLOTR {

	private ArrayList<Joueur> tabJoueur;
	private LOTR_Data data;
	private ThreadConnexion threadCon; //Thread gérant les connexion entrantes
	
	public LOTR_Game() throws IOException {
		this.data = new LOTR_Data(); //Données du plateau initiales
		this.threadCon = new ThreadConnexion();
		this.threadCon.start();
	}

	public boolean init_joueurs_territoires() throws ClassNotFoundException, IOException, InterruptedException 
	{
		System.out.println("Lancement de la procédure pour la création des joueurs");
		this.tabJoueur = getJoueurs_FromRemote();
		if (this.tabJoueur == null)
			return false;
		System.out.println("Fin de la réception de " + this.tabJoueur.size() + " joueurs");
		ArrayList<Territoire> listAllTerritoires = this.data.getAllTerritoires();
		switch (this.tabJoueur.size()) { //Changement de règles en fonction du nombre de joueur
			case 2 : 
				this.tabJoueur.get(0).setListTerritoire(this.data.getListTerritoireAvecType(TypeTerritoire.BIEN));
				this.tabJoueur.get(1).setListTerritoire(this.data.getListTerritoireAvecType(TypeTerritoire.MAL));
				this.tabJoueur.add(new Joueur("Neutre", "000000")); //Gestion du neutre TODO
				this.tabJoueur.get(2).setListTerritoire(this.data.getListTerritoireAvecType(TypeTerritoire.NEUTRE));
				break;
			case 3 : 
				//Joueur 1
				this.tabJoueur.get(0).add_Territoires_From_List(this.data.generateRandomTerritoiresFromType(TypeTerritoire.MAL, 5, listAllTerritoires));
				this.tabJoueur.get(0).add_Territoires_From_List(this.data.generateRandomTerritoiresFromType(TypeTerritoire.NEUTRE, 4, listAllTerritoires));
				//Joueur 2
				this.tabJoueur.get(1).add_Territoires_From_List(this.data.generateRandomTerritoiresFromType(TypeTerritoire.MAL, 4, listAllTerritoires));
				this.tabJoueur.get(1).add_Territoires_From_List(this.data.generateRandomTerritoiresFromType(TypeTerritoire.NEUTRE, 5, listAllTerritoires));
				//Joueur 3
				this.tabJoueur.get(2).add_Territoires_From_List(this.data.getListTerritoireAvecType(TypeTerritoire.BIEN));
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

	/**
	 * Execute la procédure de récupération des joueurs, attend la fin de la procédure et retourne la liste associée.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public ArrayList<Joueur> getJoueurs_FromRemote() throws ClassNotFoundException, IOException, InterruptedException {
		this.threadCon.getThreadDonnees().definirTraitementEtExecuter(PROCEDURE_JOUEURS);
		this.threadCon.getThreadDonnees().join();
		ThreadEnvoiReception newThread = this.threadCon.getThreadDonnees().clone();
		this.threadCon.setThreadDonnees(newThread); //Copie l'ancienne classe pour pouvoir reéxécuter la méthode run()
		return (this.threadCon.getThreadDonnees().getListJoueur());
	}	
	
	void attenteConnexionClient() throws InterruptedException {
		while (this.getThreadConnexion().getThreadDonnees() == null) { //Attente de la connexion client
			Thread.sleep(1000); 
		}
	}
	/**
	 * Retourne la <b>constante</b> de type entier reçu depuis l'application distante, <b>-1</b> si la connexion n'existe pas.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	Integer recupererTraitement() throws ClassNotFoundException, IOException {
		if (this.threadCon.getThreadDonnees() != null)
			return this.threadCon.getThreadDonnees().get_Constante_Jeu();
		return null;
	}
	
	ThreadConnexion getThreadConnexion() {
		return threadCon;
	}
	
	public ArrayList<Joueur> getTabJoueurs() {
		return tabJoueur;
	}
}
