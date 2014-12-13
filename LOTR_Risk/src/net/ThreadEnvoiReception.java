package net;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import objects.Joueur;
import utils.InterfaceLOTR;

public class ThreadEnvoiReception extends Thread implements InterfaceLOTR {

	private Reception in;
	private Emission out;
	private ArrayList<Joueur> listJoueur;
	private int traitement; //Sert à définir le traitement voulu (via constantes InterfaceLOTR)
	
	public ThreadEnvoiReception(Socket soc) throws IOException {
		super();
		this.out = new Emission(soc.getOutputStream());
		this.in = new Reception(soc.getInputStream());
	}
	
	public ThreadEnvoiReception(Emission out, Reception in, ArrayList<Joueur> listJoueurs) throws IOException {
		super();
		this.out = out;
		this.in = in;
		this.listJoueur = listJoueurs;
	}
	
	@Override
	public void run() 
	{
		if (traitement == CREATION_JOUEURS) {
			try {
				this.listJoueur = getInfosJoueurs();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
				this.listJoueur = null;
			}
		}
		else if (traitement == SERVEUR_ENVOI_JOUEURS) {
			if (sendInfosJoueurs())
				System.out.println("Envoi terminé avec succès");
			else
				System.out.println("ERREUR lors de l'envoi des joueurs !");
		}
	}
	
	/**
	 * Exécute le traitement attendue par la constante de jeu passé en paramètre.
	 * @param traitement
	 * 		constante définie par l'interface <b>InterfaceLOTR</b>
	 */
	public void definirTraitementEtExecuter(int traitement) {
		this.traitement = traitement;
		this.start();
	}
	
	/**
	 * Reçoit la liste des joueurs envoyée par l'application distante.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private ArrayList<Joueur> getInfosJoueurs() throws ClassNotFoundException, IOException 
	{
		out.sendString("#OK"); //Averti le client que sa demande est bien reçue 
		Integer nbJoueurs = in.getInt();
		while (nbJoueurs == null) {
			System.out.println("Erreur de saisie, j'attend un ENTIER");
			nbJoueurs = in.getInt();
		}
		ArrayList<Joueur> toReturn = new ArrayList<Joueur>(nbJoueurs);
		for (int i = 0; i < nbJoueurs; i ++) {
			Joueur joueurRecu = in.getJoueur();
			if (!toReturn.contains(joueurRecu)) {
				toReturn.add(joueurRecu);
				System.out.println("Joueur ajouté : " + joueurRecu.getNom());
			}
		}
		return toReturn;
	}
	
	private boolean sendInfosJoueurs() 
	{
		try {
			if (listJoueur == null || listJoueur.size() == 0) {
				out.sendString("#ERROR");
				return false;
			}
			out.sendString("#OK");
			for (Joueur j : listJoueur) 
				out.sendJoueur(j);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Surcharge de la méthode pour pouvoir réexécuter la méthode run() du Thread.
	 */
	public ThreadEnvoiReception clone() 
	{
		try {
			return new ThreadEnvoiReception(this.out, this.in, this.listJoueur);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Integer get_Constante_Jeu() throws ClassNotFoundException, IOException {
		return (in.getInt());
	}
	
	public void close() throws IOException {
		out.close();
		in.close();
	}
	
	public ArrayList<Joueur> getListJoueur() {
		return listJoueur;
	}
	
	public void setListJoueur(ArrayList<Joueur> listJoueur) {
		this.listJoueur = listJoueur;
	}
}
