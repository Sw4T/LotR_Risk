package utils;

public class Main implements InterfaceLOTR {

	/** 
	 * Main du serveur
	 * @param args
	 */
	public static void main(String [] args) {
		LOTR_Game jeu = null;
		try {
			jeu = new LOTR_Game();	
			Integer constanteTraitement;
			jeu.attenteConnexionClient();
			do {
				constanteTraitement = jeu.recupererTraitement(); //Récupère l'entier définissant une constante
				if (constanteTraitement != null) {
					switch (constanteTraitement.intValue()) {
						case PROCEDURE_JOUEURS :
							if (jeu.init_joueurs_territoires()) { //Retourne true si la procédure a bien aboutie
								for (int i = 0; i < jeu.getTabJoueurs().size(); i++) {
									System.out.println(jeu.getTabJoueurs().get(i));
								}
							} else
								System.out.println("Erreur lors de l'initialisation des joueurs");
							break;
						case FERMER_SERVEUR : 
							jeu.getThreadConnexion().close(); //Ferme le serveur
							break;
						default : System.out.println("Constante non implémentée ou inconnue");
					}
				} else
					System.out.println("Format de données invalide");
			} while (!jeu.getThreadConnexion().isServeurFinished()); 
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
