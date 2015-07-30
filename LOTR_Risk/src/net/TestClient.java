package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import core.InterfaceLOTR;
import objects.Joueur;

public class TestClient implements InterfaceLOTR {

	/**
	 * A lancer APRES le lancement du main du serveur (pour TESTER)
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost", 9875);
			s.setSoTimeout(4000); //Temps d'attente de 4s pour les lectures/écritures
			Scanner input = new Scanner(System.in);
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(s.getInputStream());
			System.out.println("Constantes actuellement implémentées : \r\tPROCEDURE_JOUEUR -> tapez \"joueur\""
							+ "\n\tRECEPTION_JOUEUR -> tapez \"reception\" (après avoir envoyé les joueurs)"
							+ "\n\tFERMER_SERVEUR -> tapez \"close\"");
			String entree = input.nextLine();
			String nomJoueur;
			Integer nombreJoueurs = null;
			do
			{
				if (entree != null) 
				{
					if (entree.equals("joueur")) 
					{
						envoyerObject(out, CREATION_JOUEURS); //Envoi de la constante pour l'envoi de joueurs
						System.out.println("Serveur : " + (String) in.readObject());
						System.out.println("Veuillez entrez un nombre de joueurs...");
						
						nombreJoueurs = Integer.parseInt(input.nextLine());
						envoyerObject(out, nombreJoueurs);
						
						for (int i = 0; i < nombreJoueurs; i++) {
							System.out.println("Entrez le nom du joueur n°" + (i + 1));
							nomJoueur = input.nextLine();
							envoyerObject(out, new Joueur(nomJoueur, "000000")); //Envoi de l'objet joueur
						}
					}
					else if (entree.equals("reception") && nombreJoueurs != null) 
					{
						envoyerObject(out, SERVEUR_ENVOI_JOUEURS); //Envoi de la constante pour la réception
						System.out.println("Serveur : " + (String) in.readObject());
						
						for (int i = 0; i < nombreJoueurs; i++) {
							Joueur joueurRecu = (Joueur) in.readObject();
							System.out.println(joueurRecu);
						}
						System.out.println("Fin de la réception des joueurs");
					}
					else if (entree.equals("close")) 
						envoyerObject(out, FERMER_SERVEUR);
					else if (!entree.equals(""))
						envoyerObject(out, entree);
				}
			} while (!(entree = input.nextLine()).equals("tg")); //"tg" ferme le client et la connexion liée
			input.close();
			s.close();
		} catch (UnknownHostException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); 
		} catch (ClassNotFoundException e) { e.printStackTrace();
		} 
	}
	
	public static synchronized void envoyerObject(ObjectOutputStream out, Object o) throws IOException {
		out.writeObject(o);
		out.flush();
	}

}
