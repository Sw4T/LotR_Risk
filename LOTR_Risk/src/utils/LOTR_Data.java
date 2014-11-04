package utils;

import objects.Region;
import objects.Territoire;

public class LOTR_Data {
	
	public LOTR_Data() {
		this.init_list_Territoire_Rohan(); 
		this.init_list_Territoire_Arnor(); 
		this.init_list_Territoire_Rhovanion(); 
		this.init_list_Territoire_Eriador(); 
		this.init_list_Territoire_Rhun(); 
		this.init_list_Territoire_Foret_Noire();  
	}
	
	public Region init_list_Territoire_Rohan()
	{
		Region r = new Region("Rohan", 4);
		r.add_Territoire(new Territoire("Pays des Vieux Biscornus", true));
		r.add_Territoire(new Territoire("Trouée du Rohan", false));
		r.add_Territoire(new Territoire("Fangorn", true));
		r.add_Territoire(new Territoire("Enedwaith", false));
		r.add_Territoire(new Territoire("Miniriath", false));
		r.add_Territoire(new Territoire("Pays de Dun", false));
		r.add_Territoire(new Territoire("Eregion", false));
		return r;
	}
	
	public Region init_list_Territoire_Rhovanion()
	{
		Region r = new Region("Rhovanion", 5);
		r.add_Territoire(new Territoire("Marais des Morts", false));
		r.add_Territoire(new Territoire("Le Plateau", false));
		r.add_Territoire(new Territoire("Terres Brunes", false));
		r.add_Territoire(new Territoire("Collines du Rhun", false));
		r.add_Territoire(new Territoire("Lorien", false));
		r.add_Territoire(new Territoire("Emyn Muil", false));
		r.add_Territoire(new Territoire("Moria", true));
		r.add_Territoire(new Territoire("Champs aux Iris", false));
		return r;
	}
	
	public Region init_list_Territoire_Eriador()
	{
		Region r = new Region("Eriador", 3);
		r.add_Territoire(new Territoire("Collines des Tours", false));
		r.add_Territoire(new Territoire("La Comté", false));
		r.add_Territoire(new Territoire("Les Collines d'Evendim", true));
		r.add_Territoire(new Territoire("Harlindon", false));
		r.add_Territoire(new Territoire("Forlindon", false));
		r.add_Territoire(new Territoire("Montagnes Bleues", false));
		r.add_Territoire(new Territoire("Mithlond", false));
		return r;
	}
	
	public Region init_list_Territoire_Arnor()
	{
		Region r = new Region("Arnor", 7);
		r.add_Territoire(new Territoire("Pays de Bouc", false));
		r.add_Territoire(new Territoire("Vieille Forêt", false));
		r.add_Territoire(new Territoire("Rhudaur", true));
		r.add_Territoire(new Territoire("Fornost", false));
		r.add_Territoire(new Territoire("Hauts du Nord", false));
		r.add_Territoire(new Territoire("Hauts du Sud", false));
		r.add_Territoire(new Territoire("Collines du Temps", false));
		r.add_Territoire(new Territoire("Landes d'Etten", false));
		r.add_Territoire(new Territoire("Angmar", false));
		r.add_Territoire(new Territoire("Est d'Angmar", false));
		r.add_Territoire(new Territoire("Forodwaith", false));
		return r;
	}
	
	public Region init_list_Territoire_Foret_Noire()
	{
		Region r = new Region("Forêt Noire", 4);
		r.add_Territoire(new Territoire("Carrock", false));
		r.add_Territoire(new Territoire("Vallée de l'Anduin", false));
		r.add_Territoire(new Territoire("Sud de la Forêt Noire", true));
		r.add_Territoire(new Territoire("Est de la Forêt Noire", false));
		r.add_Territoire(new Territoire("Nord de la Forêt Noire", false));
		return r;
	}
	
	public Region init_list_Territoire_Rhun()
	{
		Region r = new Region("Rhûn", 2);
		r.add_Territoire(new Territoire("Esgaroth", false));
		r.add_Territoire(new Territoire("Rhûn du Sud", false));
		r.add_Territoire(new Territoire("Rhûn du Nord", false));
		r.add_Territoire(new Territoire("Landes Déséchée", false));
		return r;
	}
}
