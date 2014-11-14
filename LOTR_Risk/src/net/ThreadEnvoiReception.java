package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import objects.Joueur;
import utils.InterfaceLOTR;

public class ThreadEnvoiReception extends Thread implements InterfaceLOTR {

	private Reception in;
	private Emission out; //TODO
	private ArrayList<Joueur> listJoueur;
	private boolean isSendMode;
	private int traitement; //Sert à définir le traitement voulu (via constant InterfaceLOTR)
	
	public ThreadEnvoiReception(Socket soc) throws IOException {
		super();
		this.out = new Emission(soc.getOutputStream());
		this.in = new Reception(soc.getInputStream());
		this.isSendMode = true; //Boolean servant à vérifier si la classe peut emettre ou non
	}
	
	public ThreadEnvoiReception(Emission out, Reception in) throws IOException {
		super();
		this.out = out;
		this.in = in;
		this.isSendMode = true; 
	}
	
	public void definirTraitementEtExecuter(int traitement) {
		this.traitement = traitement;
		this.start();
	}

	@Override
	public void run() {
		if (traitement == PROCEDURE_JOUEURS)
			try {
				getOutput().sendString("#OKJOUEURS");
				this.listJoueur = getInfosJoueurs();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
				this.listJoueur = null;
			}
		else
			this.listJoueur = null;
	}
	
	public ThreadEnvoiReception clone() {
		try {
			ThreadEnvoiReception toReturn = new ThreadEnvoiReception(this.out, this.in);
			toReturn.setListJoueur(this.listJoueur);
			return toReturn;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private ArrayList<Joueur> getInfosJoueurs() throws ClassNotFoundException, IOException {
		Integer nbJoueurs = getInput().getInt();
		while (nbJoueurs == null) {
			System.out.println("Erreur de saisie, j'attend un ENTIER");
			nbJoueurs = getInput().getInt();
		}
		ArrayList<Joueur> toReturn = new ArrayList<Joueur>(nbJoueurs);
		for (int i = 0; i < nbJoueurs; i ++) {
			Joueur joueurRecu = getInput().getJoueur();
			if (!toReturn.contains(joueurRecu)) {
				toReturn.add(joueurRecu);
				System.out.println("Joueur ajouté : " + joueurRecu.getNom());
			}
		}
		return toReturn;
	}

	public Integer get_Constante_Jeu() {
		return (getInput().getInt());
	}
	
	public ArrayList<Joueur> getListJoueur() {
		return listJoueur;
	}
	
	public void setListJoueur(ArrayList<Joueur> listJoueur) {
		this.listJoueur = listJoueur;
	}
	
	private Emission getOutput() {
		return this.out;
	}
	
	private Reception getInput() {
		return this.in;
	}
}
