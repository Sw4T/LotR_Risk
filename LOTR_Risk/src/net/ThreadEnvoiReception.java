package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import objects.Joueur;

public class ThreadEnvoiReception implements Runnable {

	private ObjectInputStream obj_in;
	private Emission bufferSortie; //TODO
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
					getInfosJoueurs();
				} 
				catch (ClassNotFoundException e) {e.printStackTrace();} 
				catch (IOException e) {e.printStackTrace();}
						  break;
				default : System.out.println("Client : " + messageEntrant);
			}
		}
	}
	
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

	public boolean isSendMode() {
		return isSendMode;
	}


}
