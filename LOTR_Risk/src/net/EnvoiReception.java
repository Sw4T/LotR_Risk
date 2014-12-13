package net;

import java.io.IOException;
import java.util.ArrayList;

import objects.Joueur;
import utils.InterfaceLOTR;

public class EnvoiReception extends Thread implements InterfaceLOTR{

	private ArrayList<Joueur> listJoueur; //Permet de stocker la liste recu ou voulante etre envoyée
	private int traitement; //Sert à définir le traitement voulu (via constantes InterfaceLOTR)
	private Reception in;
	private Emission out;
	
	public EnvoiReception(Reception in, Emission out, ArrayList<Joueur> listJoueurs, int traitement) {
		super();
		this.listJoueur = listJoueurs;
		this.traitement = traitement;
		this.in = in;
		this.out = out;
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
		if (this.listJoueur == null)
			this.listJoueur = new ArrayList<Joueur>(nbJoueurs);
		
		for (int i = 0; i < nbJoueurs; i ++) {
			Joueur joueurRecu = in.getJoueur();
			if (!listJoueur.contains(joueurRecu)) {
				listJoueur.add(joueurRecu);
				System.out.println("Joueur ajouté : " + joueurRecu.getNom());
			}
		}
		return this.listJoueur;
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
	
	public ArrayList<Joueur> getListJoueur() {
		return listJoueur;
	}
}
