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
	//private boolean isSendMode;
	private int traitement; //Sert à définir le traitement voulu (via constant InterfaceLOTR)
	
	public ThreadEnvoiReception(Socket soc) throws IOException {
		super();
		this.out = new Emission(soc.getOutputStream());
		this.in = new Reception(soc.getInputStream());
	}
	
	public ThreadEnvoiReception(Emission out, Reception in) throws IOException {
		super();
		this.out = out;
		this.in = in;
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

	@Override
	public void run() 
	{
		if (traitement == PROCEDURE_JOUEURS)
			try {
				out.sendString("#OK"); //Averti le client que sa demande est bien reçue 
				this.listJoueur = getInfosJoueurs();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
				this.listJoueur = null;
			}
		else
			this.listJoueur = null;
	}
	
	/**
	 * Reçoit la liste des joueurs envoyée par l'application distante.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private ArrayList<Joueur> getInfosJoueurs() throws ClassNotFoundException, IOException 
	{
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
	
	/**
	 * Surcharge de la méthode pour pouvoir réexécuter la méthode run() du Thread.
	 */
	public ThreadEnvoiReception clone() 
	{
		try {
			ThreadEnvoiReception toReturn = new ThreadEnvoiReception(this.out, this.in);
			toReturn.setListJoueur(this.listJoueur);
			return toReturn;
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
