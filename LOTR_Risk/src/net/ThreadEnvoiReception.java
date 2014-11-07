package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadEnvoiReception implements Runnable {

	private BufferedReader bufferLecture;
	private Emission bufferSortie;
	private boolean isSendMode;
	
	public ThreadEnvoiReception(Socket soc) throws IOException {
		this.bufferLecture = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		this.bufferSortie = new Emission(soc.getOutputStream(), this);
		this.isSendMode = false; //Boolean servant à vérifier si la classe peut emettre ou non
	}

	@Override
	public void run() {
		String messageEntrant = "";
		while (!(messageEntrant = getString()).equals("tg")) 
		{
			switch (messageEntrant) {
				case "j" : getInfosJoueurs();
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
	
	public String getString() 
	{
		try {
			if (!isSendMode) 
				return (this.bufferLecture.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Rien reçu";
	}
	
	public int getInt() {
		try {
			return (Integer.parseInt(this.bufferLecture.readLine()));
		} catch (NumberFormatException | IOException e) {
			return -1;
		}
	}

	public boolean isSendMode() {
		return isSendMode;
	}


}
