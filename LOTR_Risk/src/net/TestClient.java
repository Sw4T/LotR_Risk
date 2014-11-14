package net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import objects.Joueur;

public class TestClient {

	/**
	 * A lancer APRES le lancement du main du serveur (pour TESTER)
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost", 9876);
			s.setSoTimeout(4000);
			Scanner input = new Scanner(System.in);
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			String entree = input.nextLine();
			String entreeJoueur;
			do
			{
				if (entree != null && !entree.equals("") && !entree.equals("obj")) {
					envoyerObject(oos, entree);
				}
				if (entree != null && entree.equals("obj")) 
				{
					Integer nbJoueurs = Integer.parseInt(input.nextLine());
					envoyerObject(oos, nbJoueurs);
					for (int i = 0; i < nbJoueurs; i++) {
						entreeJoueur = input.nextLine();
						envoyerObject(oos, new Joueur(entreeJoueur));
					}
				}
			} while (!(entree = input.nextLine()).equals("tg"));
			input.close();
			s.close();
		} catch (UnknownHostException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); 
		} 
		
	}
	
	public static synchronized void envoyerObject(ObjectOutputStream oos, Object o) throws IOException {
		oos.writeObject(o);
		oos.flush();
	}

}
