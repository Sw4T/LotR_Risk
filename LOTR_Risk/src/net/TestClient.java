package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import objects.Joueur;
import utils.InterfaceLOTR;

public class TestClient implements InterfaceLOTR {

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
			ObjectInputStream in = new ObjectInputStream(s.getInputStream());
			System.out.println("Constantes actuellement implémentées : \r\tPROCEDURE_JOUEUR -> tapez \"joueur\""
			+ "\n\tFERMER_SERVEUR -> tapez \"close\"");
			String entree = input.nextLine();
			String entreeJoueur;
			do
			{
				if (entree != null && entree.equals("joueur")) 
				{
					envoyerObject(oos, PROCEDURE_JOUEURS);
					System.out.println("Serveur : " + (String) in.readObject());
					System.out.println("Veuillez entrez un nombre de joueurs...");
					Integer nbJoueurs = Integer.parseInt(input.nextLine());
					envoyerObject(oos, nbJoueurs);
					for (int i = 0; i < nbJoueurs; i++) {
						System.out.println("Entrez le nom du joueur n°" + (i + 1));
						entreeJoueur = input.nextLine();
						envoyerObject(oos, new Joueur(entreeJoueur));
					}
				} else if (entree != null && entree.equals("close")) {
					envoyerObject(oos, FERMER_SERVEUR);
				}
				else if (entree != null && !entree.equals("")) 
					envoyerObject(oos, entree);
			} while (!(entree = input.nextLine()).equals("tg"));
			input.close();
			s.close();
		} catch (UnknownHostException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); 
		} catch (ClassNotFoundException e) { e.printStackTrace();
		} 
	}
	
	public static synchronized void envoyerObject(ObjectOutputStream oos, Object o) throws IOException {
		oos.writeObject(o);
		oos.flush();
	}

}
