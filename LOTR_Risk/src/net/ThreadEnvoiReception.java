package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

import objects.Joueur;

public class ThreadEnvoiReception implements Runnable {

	private ObjectInputStream obj_in;
	private Emission bufferSortie;
	private boolean isSendMode;
	
	public ThreadEnvoiReception(Socket soc) throws IOException {
		this.bufferSortie = new Emission(soc.getOutputStream(), this);
		this.obj_in = new ObjectInputStream(soc.getInputStream());
		this.isSendMode = false; //Boolean servant à vérifier si la classe peut emettre ou non
	}

	@Override
	public void run() {
		String messageEntrant = "";
		while (!(messageEntrant = getString()).equals("tg")) 
		{
			switch (messageEntrant) {
				case "obj" : try {
					Joueur j = getJoueur();
					System.out.println("Nom du joueur reçu : " + j.getNom());
				} 
				catch (ClassNotFoundException e) {e.printStackTrace();} 
				catch (IOException e) {e.printStackTrace();}
						  break;
				default : System.out.println("Client : " + messageEntrant);
			}
		}
	}
	
	public Object[] getInfosJoueurs() {
		int nbJoueurs = getInt();
		if (nbJoueurs == - 1)
			return null;
		
		Object[] toReturn = new Object[nbJoueurs + 1];
		toReturn[0] = nbJoueurs;
		for (int i = 1; i <= nbJoueurs; i++) {
			toReturn[i] = getString();
			System.out.println("Joueur " + i + " : " + toReturn[i]);
		}
		return toReturn;
	}
	
	public Joueur getJoueur() throws ClassNotFoundException, IOException {
		return (Joueur) (this.obj_in.readObject());
	}
	
	public String getString() 
	{
		try {
			if (!isSendMode) 
				return (String) (this.obj_in.readObject());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return "Rien reçu";
	}
	
	public int getInt() {
		try {
			return (Integer.parseInt(getString()));
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public boolean isSendMode() {
		return isSendMode;
	}


}
