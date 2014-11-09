package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadConnexion implements Runnable {

	private ServerSocket serveur;
	private ThreadEnvoiReception T_DATA;
	protected static AtomicBoolean hasFinished; //Boolean pouvant �tre modifi� dans n'importe quelle classe li�e au package net
	
	public ThreadConnexion() throws IOException {
		this.serveur = new ServerSocket(9876);
		ThreadConnexion.hasFinished = new AtomicBoolean(false); 
	}
	
	@Override
	public void run() {
		Socket socketEntree = null;
		while (!ThreadConnexion.hasFinished.get()) //V�rifie si une connexion est achev�e ou non
		{
			try {
				socketEntree = this.serveur.accept();
				System.out.println("Un client avec l'adresse " + socketEntree.getInetAddress().getHostAddress() + " se connecte..");
				System.out.println("Serveur : Allo j'�coute ?");
				this.T_DATA = new ThreadEnvoiReception(socketEntree);
				new Thread(this.T_DATA).start(); //Lancement du Thread échange de données
			} catch (IOException e) {e.printStackTrace();}
		}
		try {
			this.serveur.close();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	/*public Object[] getInfosJoueurs() {
		return this.T_DATA.getInfosJoueurs();
	}*/

	
}
