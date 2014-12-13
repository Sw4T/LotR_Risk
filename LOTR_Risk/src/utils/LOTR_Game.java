package utils;

import java.io.IOException;
import java.util.ArrayList;

import net.ThreadConnexion;
import net.EnvoiReception;
import objects.Client;
import objects.Joueur;
import objects.Territoire;
import objects.TypeTerritoire;

public class LOTR_Game implements InterfaceLOTR {

	private ArrayList<Joueur> tabJoueur; //Liste des joueurs jouant à la partie
	private LOTR_Data data; //Données utilisées pour gérer les règles du jeu
	private ThreadConnexion threadServeur; //Thread gérant les connexion entrantes
	private Client client; //Application monoclient actuellement
	
	public LOTR_Game() throws IOException {
		this.data = new LOTR_Data(); //Données du plateau initiales
		this.threadServeur = new ThreadConnexion(this);
		this.threadServeur.start();
	}

	/**
	 * Lance la procédure de création des joueurs avec un client <b>(monoclient actuellement)</b> et retourne <b>TRUE</b>
	 * si l'exécution de celle-ci c'est déroulée correctement. 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
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
				this.tabJoueur.add(new Joueur("Neutre", "#ff000000")); //Gestion du neutre TODO
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
		EnvoiReception threadTraitement = this.client.definirTraitementEtExecuter(CREATION_JOUEURS, tabJoueur);
		threadTraitement.join(); //Attend la fin du traitement éxécuté
		this.tabJoueur = threadTraitement.getListJoueur(); //Récupère la liste des joueurs mise à jour
		return this.tabJoueur;
	}	
	
	/**
	 * Envoi la liste des joueurs à l'application distante.
	 * @throws InterruptedException
	 */
	public void sendJoueurs_ToRemote() throws InterruptedException {
		Thread threadTraitement = this.client.definirTraitementEtExecuter(SERVEUR_ENVOI_JOUEURS, tabJoueur);
		threadTraitement.join();
	}	
	
	/**
	 * Retourne la liste des joueurs jouant à la partie.
	 */
	public ArrayList<Joueur> getJoueurs() {
		return this.tabJoueur;
	}
	
	/**
	 * Ajoute un nouveau client à la partie.
	 */
	public void ajouterNouveauClient(Client toAdd) {
		this.client = toAdd;
	}
	
	/**
	 * Retourne la <b>constante</b> de type entier reçu depuis l'application distante du client <b>c</b>, <b>-1</b> si la connexion n'existe pas.
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	Integer recupererTraitementClient(Client c) throws ClassNotFoundException, IOException {
		return (c.get_ConstanteJeu_FromClient());
	}
	
	ThreadConnexion getThreadConnexion() {
		return threadServeur;
	}
	
	//Attend une connexion client
	void attenteConnexionClient() throws InterruptedException {
		while (this.client == null) { //Attente de la connexion client
			Thread.sleep(1000); 
		}
	}
	
	//TODO Fermeture de la seule connexion cliente, multiclient non géré
	void fermerClient() throws Throwable {
		this.client.fermerConnexion();
		this.tabJoueur = null;
		this.client = null;
	}
	
	Client getClient() {
		return this.client;
	}
}
