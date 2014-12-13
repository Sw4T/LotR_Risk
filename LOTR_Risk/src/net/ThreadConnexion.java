package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import objects.Client;
import utils.LOTR_Game;

public class ThreadConnexion extends Thread {

	private ServerSocket serveur;
	private LOTR_Game jeu;
	protected AtomicBoolean hasFinished; //Boolean pouvant �tre modifi� dans n'importe quelle classe li�e au package net
	
	public ThreadConnexion(LOTR_Game jeu) throws IOException {
		super();
		this.serveur = new ServerSocket(9875);
		this.hasFinished = new AtomicBoolean(false); 
		this.jeu = jeu;
	}
	
	@Override
	public void run() {
		Socket socketEntree = null;
		while (!this.hasFinished.get()) //V�rifie si une connexion est achev�e ou non
		{
			try {
				socketEntree = this.serveur.accept();
				System.out.println("Un client avec l'adresse " + socketEntree.getInetAddress().getHostAddress() + " se connecte..");
				System.out.println("Serveur : Allo j'écoute ?");
				this.jeu.ajouterNouveauClient(new Client(socketEntree));
			} catch (IOException e) { System.out.println("Le serveur ferme ses portes...");}
		}
	}
	
	public boolean isServeurFinished() {
		return (this.hasFinished.get());
	}
	
	/**
	 * Ferme le serveur de connexion et met les attributs de la classe en conséquence
	 * @throws Throwable
	 */
	public void close() throws Throwable {
		this.serveur.close();
		this.hasFinished.set(true);
		this.finalize();
	}
}
