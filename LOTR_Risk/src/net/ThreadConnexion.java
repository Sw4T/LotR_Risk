package net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadConnexion implements Runnable {

	private ServerSocket serveur;
	protected static AtomicBoolean hasFinished; //Boolean pouvant �tre modifi� dans n'importe quelle classe li�e au package net
	
	public ThreadConnexion() {
		try {
			this.serveur = new ServerSocket(9876);
		} catch (IOException e) {e.printStackTrace();}
		ThreadConnexion.hasFinished = new AtomicBoolean(false);
	}
	
	@Override
	public void run() {
		Socket socketEntree = null;
		while (!ThreadConnexion.hasFinished.get()) //V�rifie si une connexion est achev�e ou non
		{
			try {
				socketEntree = this.serveur.accept();
				new Thread(new ThreadReception(socketEntree.getInputStream())).start();
				System.out.println("Un client avec l'adresse " + socketEntree.getInetAddress().getHostAddress() + " se connecte..");
				System.out.println("Serveur : Allo j'�coute ?");
				/*PrintWriter pr = new PrintWriter(socketEntree.getOutputStream());
				pr.println("tgapo");
				pr.flush();*/
				
			} catch (IOException e) {e.printStackTrace();}
		}
		try {
			this.serveur.close();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public static void main(String[] args) {
		new Thread(new ThreadConnexion()).start();
	}

	
}
