package utils;

import java.io.EOFException;
import java.net.SocketException;

public class Main implements InterfaceLOTR {

	/** 
	 * Main du serveur
	 * @param args
	 */
	public static void main(String [] args) 
	{
		LOTR_Game jeu = null;
		try {
			jeu = new LOTR_Game();	
			Integer constanteTraitement;
			jeu.attenteConnexionClient();
			do {
				try 
				{
					constanteTraitement = jeu.recupererTraitementClient(jeu.getClient()); //Récupère l'entier définissant une constante
					if (constanteTraitement != null)  
					{
						switch (constanteTraitement.intValue()) 
						{
							case CREATION_JOUEURS :
								if (jeu.init_joueurs_territoires()) { //Retourne true si la procédure a bien aboutie
									for (int i = 0; i < jeu.getJoueurs().size(); i++) {
										System.out.println(jeu.getJoueurs().get(i)); //Affichage des territoires de chaque joueur
									}
								} else
									System.out.println("Erreur lors de l'initialisation des joueurs");
								break;
							case SERVEUR_ENVOI_JOUEURS :
								System.out.println("Envoi des joueurs à l'application distante...");
								jeu.sendJoueurs_ToRemote(); //Gestion exceptions TODO
								break;
							case FERMER_SERVEUR : 
								jeu.getThreadConnexion().close(); //Ferme le serveur
								break;
							default : System.out.println("Constante non implémentée ou inconnue");
						}
					} 
					else
						System.out.println("Format de données invalide");
				}
				catch (EOFException | SocketException sock) { //Fermeture de la connexion cliente en cas d'exception levée
					System.out.println("Erreur lors de la communication cliente, fermeture de la connexion...");
					jeu.fermerClient();
					jeu.attenteConnexionClient();
				}
			} while (!jeu.getThreadConnexion().isServeurFinished()); 
		} 
		catch (Throwable e) {depuis le client
			e.printStackTrace();
		}
	}
}
