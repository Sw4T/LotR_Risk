package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadConnexion extends Thread {

	private ServerSocket serveur;
	private ThreadEnvoiReception T_DATA;
	protected AtomicBoolean hasFinished; //Boolean pouvant �tre modifi� dans n'importe quelle classe li�e au package net
	
	public ThreadConnexion() throws IOException {
		super();
		this.serveur = new ServerSocket(9876);
		this.hasFinished = new AtomicBoolean(false); 
	}
	
	@Override
	public void run() {
		Socket socketEntree = null;
		while (!this.hasFinished.get()) //V�rifie si une connexion est achev�e ou non
		{
			try {
				socketEntree = this.serveur.accept();
				System.out.println("Un client avec l'adresse " + socketEntree.getInetAddress().getHostAddress() + " se connecte..");
				System.out.println("Serveur : Allo j'�coute ?");
				this.T_DATA = new ThreadEnvoiReception(socketEntree);
			} catch (IOException e) {e.printStackTrace();}
		}
		try {
			this.serveur.close();
			System.out.println("Arrêt du serveur...");
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public void close() {
		this.hasFinished.set(true);
	}
	
	public ThreadEnvoiReception getThreadDonnees() {
		return this.T_DATA;
	}

	
}
