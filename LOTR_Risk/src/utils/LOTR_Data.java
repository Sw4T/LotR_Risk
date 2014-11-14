package utils;

import java.util.ArrayList;
import java.util.Random;

import objects.Joueur;
import objects.Region;
import objects.Territoire;
import objects.TypeTerritoire;

/**
 * Classe regroupant les données et les méthodes servant à utiliser la plateau <br>
 * <b> ATTENTION A LA CASSE ET AUX ACCENTS HEIN !! </b>
 * @author Swatosj
 */
public class LOTR_Data {
	
	private ArrayList<Region> mapRegion;
	
	public LOTR_Data() {
		this.mapRegion = new ArrayList<Region>(6);
		mapRegion.add(this.init_list_Territoire_Rohan()); 
		mapRegion.add(this.init_list_Territoire_Arnor()); 
		mapRegion.add(this.init_list_Territoire_Rhovanion()); 
		mapRegion.add(this.init_list_Territoire_Eriador()); 
		mapRegion.add(this.init_list_Territoire_Rhun()); 
		mapRegion.add(this.init_list_Territoire_Foret_Noire());  
	}
	
	/**
	 * Retourne <u>l'objet Region</u> associé au nom défini par <b>nomRegion</b>, retourne <u>null</u> sinon.
	 * @param str
	 */
	public Region getRegionByName(String nomRegion) 
	{
		for (Region r : this.mapRegion)
		{
			if (r.getNom().equals(nomRegion)) {
				return r;
			}
		}
		return null;
	}
	
	/**
	 * Génère une ArrayList comprenant <b>nbTerritoire</b> en fonction du type de territoire <b>type</b><br>
	 * La liste <b>listTerritoire</b> est mise à jour en enlevant les territoires ajoutés à la liste de retour.<br>
	 * (Un même territoire ne peut pas être dans <b>listTerritoire</b> et dans la <u>liste retournée</u>)
	 * @param type
	 * @param nbTerritoire
	 */
	public ArrayList<Territoire> generateRandomTerritoiresFromType(TypeTerritoire type, int nbTerritoire, ArrayList<Territoire> listTerritoire) {
		ArrayList<Territoire> toReturn = new ArrayList<Territoire>();
		ArrayList<Territoire> listTerritoireType = getListTerritoireFromType(type);
		int tailleListe = listTerritoireType.size();
		Territoire territoireCourant;
		if (nbTerritoire > listTerritoireType.size()) {
			return null;
		}
		Random random = new Random();
		int chiffreRandom;
		boolean trouve;
		for (int i = 0; i < nbTerritoire; i++) 
		{
			trouve = false;
			while (!trouve) {
				chiffreRandom = random.nextInt(tailleListe);
				territoireCourant = listTerritoireType.get(chiffreRandom);
				if (!toReturn.contains(territoireCourant) && listTerritoire.contains(territoireCourant)) {
						toReturn.add(territoireCourant);
						listTerritoire.remove(territoireCourant);
						trouve = true;
				}
			}
		}
		return toReturn;
	}

	/**
	 * Retourne <u>true</u> si le joueur <b>j</b> poss�de la r�gion de nom <b>nomRegion</b>.
	 * @param j
	 * @param nomRegion
	 */
	public boolean playerHasRegion(Joueur j, String nomRegion) {
		Region r = getRegionByName(nomRegion);
		if (r != null) {
			for (Territoire t : r.getListTerritoire()) {
				if (!j.getListTerritoire().contains(t))
					return false;
			}
		} else
			return false;
		return true;
	}
	
	/**
	 * Retourne le <u>nombre de renforts</u> disponibles du joueur <b>j</b> en fonction de la liste des territoires qu'il poss�de.
	 * @param j
	 */
	public int calculer_Renforts(Joueur j)
	{
		if (j.getNb_Territoire() < 12)
			return 3;
		int renforts = j.getNb_Territoire() / 3;
		for (Region r : this.mapRegion) {
			if (this.playerHasRegion(j, r.getNom()))
				renforts += r.getNb_Renforts();
		}
		return renforts;
	}
	
	/**
	 * Retourne la liste de tout les territoires possedant le type défini par <b>type</b>.
	 * @param type
	 */
	public ArrayList<Territoire> getListTerritoireFromType(TypeTerritoire type) {
		ArrayList<Territoire> toReturn = new ArrayList<Territoire>();
		for (Region r : this.mapRegion) {
			for (Territoire t : r.getListTerritoire()) {
				if (t.getType_T() == type) {
					toReturn.add(t);
				}
			}
		}
		return toReturn;
	}
	
	/**
	 * Retourne une ArrayList de tout les territoires.
	 * @param type
	 */
	public ArrayList<Territoire> getAllTerritoires() {
		ArrayList<Territoire> toReturn = new ArrayList<Territoire>();
		for (Region r : this.mapRegion) {
			for (Territoire t : r.getListTerritoire()) 
					toReturn.add(t);
		}
		return toReturn;
	}
	
	private Region init_list_Territoire_Rohan()
	{
		Region r = new Region("Rohan", 4);
		r.add_Territoire(new Territoire("Pays des Vieux Biscornus", true, TypeTerritoire.BIEN));
		r.add_Territoire(new Territoire("Trouée du Rohan", false, TypeTerritoire.BIEN));
		r.add_Territoire(new Territoire("Fangorn", true, TypeTerritoire.MAL));
		r.add_Territoire(new Territoire("Enedwaith", false, TypeTerritoire.NEUTRE));
		r.add_Territoire(new Territoire("Miniriath", false, TypeTerritoire.NEUTRE));
		r.add_Territoire(new Territoire("Pays de Dun", false, TypeTerritoire.NEUTRE));
		r.add_Territoire(new Territoire("Eregion", false, TypeTerritoire.NEUTRE));
		return r;
	}
	
	private Region init_list_Territoire_Rhovanion()
	{
		Region r = new Region("Rhovanion", 5);
		r.add_Territoire(new Territoire("Marais des Morts", false, TypeTerritoire.MAL));
		r.add_Territoire(new Territoire("Le Plateau", false, TypeTerritoire.BIEN));
		r.add_Territoire(new Territoire("Terres Brunes", false, TypeTerritoire.MAL));
		r.add_Territoire(new Territoire("Collines de Rhûn", false, TypeTerritoire.NEUTRE));
		r.add_Territoire(new Territoire("Lorien", false, TypeTerritoire.BIEN));
		r.add_Territoire(new Territoire("Emyn Muil", false, TypeTerritoire.MAL));
		r.add_Territoire(new Territoire("Moria", true, TypeTerritoire.MAL));
		r.add_Territoire(new Territoire("Champs aux Iris", false, TypeTerritoire.NEUTRE));
		return r;
	}
	
	private Region init_list_Territoire_Eriador()
	{
		Region r = new Region("Eriador", 3);
		r.add_Territoire(new Territoire("Collines des Tours", false, TypeTerritoire.NEUTRE));
		r.add_Territoire(new Territoire("La Comté", false, TypeTerritoire.BIEN));
		r.add_Territoire(new Territoire("Les Collines d'Evendim", true, TypeTerritoire.BIEN));
		r.add_Territoire(new Territoire("Harlindon", false, TypeTerritoire.NEUTRE));
		r.add_Territoire(new Territoire("Forlindon", false, TypeTerritoire.NEUTRE));
		r.add_Territoire(new Territoire("Montagnes Bleues", false, TypeTerritoire.NEUTRE));
		r.add_Territoire(new Territoire("Mithlond", false, TypeTerritoire.NEUTRE));
		return r;
	}
	
	private Region init_list_Territoire_Arnor()
	{
		Region r = new Region("Arnor", 7);
		r.add_Territoire(new Territoire("Pays de Bouc", false, TypeTerritoire.NEUTRE));
		r.add_Territoire(new Territoire("Vieille Forêt", false, TypeTerritoire.NEUTRE));
		r.add_Territoire(new Territoire("Rhudaur", true, TypeTerritoire.BIEN));
		r.add_Territoire(new Territoire("Fornost", false, TypeTerritoire.BIEN));
		r.add_Territoire(new Territoire("Hauts du Nord", false, TypeTerritoire.NEUTRE));
		r.add_Territoire(new Territoire("Hauts du Sud", false, TypeTerritoire.NEUTRE));
		r.add_Territoire(new Territoire("Collines du Temps", false, TypeTerritoire.NEUTRE));
		r.add_Territoire(new Territoire("Landes d'Etten", false, TypeTerritoire.NEUTRE));
		r.add_Territoire(new Territoire("Angmar", false, TypeTerritoire.NEUTRE));
		r.add_Territoire(new Territoire("Est d'Angmar", false, TypeTerritoire.MAL));
		r.add_Territoire(new Territoire("Forodwaith", false, TypeTerritoire.NEUTRE));
		return r;
	}
	
	private Region init_list_Territoire_Foret_Noire()
	{
		Region r = new Region("Forêt Noire", 4);
		r.add_Territoire(new Territoire("Carrock", false, TypeTerritoire.NEUTRE));
		r.add_Territoire(new Territoire("Vallée de l'Anduin", false, TypeTerritoire.NEUTRE));
		r.add_Territoire(new Territoire("Sud de la Forêt Noire", true, TypeTerritoire.MAL));
		r.add_Territoire(new Territoire("Est de la Forêt Noire", false, TypeTerritoire.MAL));
		r.add_Territoire(new Territoire("Nord de la Forêt Noire", false, TypeTerritoire.BIEN));
		return r;
	}
	
	private Region init_list_Territoire_Rhun()
	{
		Region r = new Region("Rhûn", 2);
		r.add_Territoire(new Territoire("Esgaroth", false, TypeTerritoire.NEUTRE));
		r.add_Territoire(new Territoire("Rhûn du Sud", false, TypeTerritoire.NEUTRE));
		r.add_Territoire(new Territoire("Rhûn du Nord", false, TypeTerritoire.NEUTRE));
		r.add_Territoire(new Territoire("Landes Desséchée", false, TypeTerritoire.MAL));
		return r;
	}
}
