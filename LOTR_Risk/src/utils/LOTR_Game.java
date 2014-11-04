package utils;



public class LOTR_Game {

	private int nb_joueurs;
	private LOTR_Data data;
	
	public LOTR_Game(int nb_joueur) {
		this.nb_joueurs = nb_joueur;
		this.data = new LOTR_Data(); //Données du plateau initiales
	}
}
