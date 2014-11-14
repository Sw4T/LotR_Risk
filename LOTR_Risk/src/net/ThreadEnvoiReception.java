package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import objects.Joueur;

public class ThreadEnvoiReception extends Thread {

	private ObjectInputStream obj_in;
	private Emission bufferSortie; //TODO
	private ArrayList<Joueur> listJoueur;
	private boolean isSendMode;
	private String traitement; //Sert à définir le traitement voulu
	
	public ThreadEnvoiReception(Socket soc) throws IOException {
		super();
		this.bufferSortie = new Emission(soc.getOutputStream(), this);
		this.obj_in = new ObjectInputStream(soc.getInputStream());
		this.isSendMode = true; //Boolean servant à vérifier si la classe peut emettre ou non
	}

	@Override
	public void run() {
		if (traitement == null || traitement.equals(""))
			return;
		if (traitement.equals("RecepJoueurs"))
			try {
				this.listJoueur = getInfosJoueurs();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
				this.listJoueur = null;
			}
		else
			this.listJoueur = null;
	}
	
	private ArrayList<Joueur> getInfosJoueurs() throws ClassNotFoundException, IOException {
		Integer nbJoueurs = getInt();
		while (nbJoueurs == null) {
			System.out.println("Erreur de saisie, j'attend un ENTIER");
			nbJoueurs = getInt();
		}
		ArrayList<Joueur> toReturn = new ArrayList<Joueur>(nbJoueurs);
		for (int i = 0; i < nbJoueurs; i ++) {
			Joueur joueurRecu = getJoueur();
			if (!toReturn.contains(joueurRecu)) {
				toReturn.add(joueurRecu);
				System.out.println("Joueur ajouté : " + joueurRecu.getNom());
			}
		}
		return toReturn;
	}
	
	public Joueur getJoueur()
	{
		try {
			Object objRecu = this.obj_in.readObject();
			if (!(objRecu instanceof Joueur))
				return null;
			return ((Joueur) objRecu);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getString() 
	{
		try {
			Object objRecu = this.obj_in.readObject();
			if (!(objRecu instanceof String))
				return null;
			return ((String) objRecu);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer getInt() {
		try {
			Object objRecu = this.obj_in.readObject();
			if (!(objRecu instanceof Integer))
				return null;
			return ((Integer) objRecu);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void definirTraitementEtExecuter(String traitement) {
		this.traitement = traitement;
		this.start();
	}
	
	public ArrayList<Joueur> getListJoueur() {
		return listJoueur;
	}

	public String getTraitement() {
		return traitement;
	}

	public boolean isSendMode() {
		return isSendMode;
	}
}
