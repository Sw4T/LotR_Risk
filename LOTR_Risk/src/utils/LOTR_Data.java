package utils;

import java.util.ArrayList;

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
	
	public Region getRegionByName(String str) 
	{
		for (Region r : this.mapRegion)
		{
			if (r.getNom().equals(str)) {
				return r;
			}
		}
		return null;
	}
	
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
