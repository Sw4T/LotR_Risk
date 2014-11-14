package com.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class DonneesConnexion {

	private ObjectInputStream obj_in;
	private Emission bufferSortie; //TODO
	private Socket socket;
	private boolean isSendMode;
	
	
	public DonneesConnexion(String servAddr, int port) {
		Boolean reussi = this.connexionServeur(servAddr, port);
		if (reussi)
			this.isSendMode = true;
		else
			this.isSendMode = false;
	}

	public Boolean connexionServeur(String servAddr, int port)
	{
		try {
			this.socket = new Socket();
			this.socket.connect(new InetSocketAddress(servAddr, port), 5000);
			this.bufferSortie = new Emission(this.socket.getOutputStream(), this);
			this.obj_in = new ObjectInputStream(this.socket.getInputStream());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean get_Connexion_Reussi() {
		return this.isSendMode;
	}
	/*
	public ArrayList<Joueur> getInfosJoueurs() throws ClassNotFoundException, IOException {
		int nbJoueurs = getInt();
		if (nbJoueurs == - 1)
			return null;
		ArrayList<Joueur> toReturn = new ArrayList<Joueur>(nbJoueurs);
		for (int i = 0; i < nbJoueurs; i ++) {
			Joueur joueurRecu = getJoueur();
			if (!toReturn.contains(joueurRecu)) {
				toReturn.add(joueurRecu);
				System.out.println("Joueur ajout� : " + joueurRecu.getNom());
			}
		}
		return toReturn;
	}
	
	public Joueur getJoueur() throws ClassNotFoundException, IOException {
		try {
			Object objRecu = this.obj_in.readObject();
			if (!(objRecu instanceof Joueur))
				return null;
			return ((Joueur) objRecu);
		} catch (IOException e) {
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
		} catch (IOException e) {e.printStackTrace();
		} catch (ClassNotFoundException e) {e.printStackTrace();}
		return null;
	}
	
	public Integer getInt() {
		try {
			Object objRecu = this.obj_in.readObject();
			if (!(objRecu instanceof Integer))
				return null;
			return ((Integer) objRecu);
		} catch (IOException e) {e.printStackTrace();
		} catch (ClassNotFoundException e) {e.printStackTrace();}
		return null;
	}
	*/
}
