package swingGUI;

import java.io.IOException;
import java.util.ArrayList;

import objects.Joueur;
import objects.Territoire;
import objects.TypeTerritoire;
import utils.InterfaceLOTR;
import utils.LOTR_Data;
import utils.LOTR_Game;

public class Main implements InterfaceLOTR {

	public static void main(String [] args) {
		System.out.println("Lancement GUI...");
		
		ArrayList<Joueur> j = init_joueurs();
		MainWindow serveurGui = new MainWindow(j);
		
		//j.add(new Joueur("test", "#00EECD"));
		//serveurGui.repaint();
		
	}
	
	public static ArrayList<Joueur> init_joueurs() {
		LOTR_Data donnees = new LOTR_Data();
		ArrayList<Joueur> test = new ArrayList<Joueur>(3);
		ArrayList<Territoire> randomTerritoires = new ArrayList<Territoire>();
		
		test.add(new Joueur("Seikomi", "#FF0000"));
		test.add(new Joueur("Swatisj", "#FF00AB"));
		test.add(new Joueur("lucas", "#0000CD"));
		for (Joueur j : test) {
			randomTerritoires = donnees.generateRandomTerritoiresFromType(TypeTerritoire.BIEN, 5, donnees.getAllTerritoires());
			j.add_Territoires_From_List(randomTerritoires);
		}
		return test;
		 //ET ME DIS PAS QUE CETAIT DIFFICILE
	}
}

