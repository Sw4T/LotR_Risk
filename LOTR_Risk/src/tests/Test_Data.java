package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import objects.Joueur;
import objects.Region;
import objects.Territoire;
import objects.TypeTerritoire;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.LOTR_Data;

public class Test_Data {

	private LOTR_Data data;
	private Region region;
	private Territoire territoire;
	private Joueur joueur;
	
	@Before
	public void setUp() throws Exception {
		region = new Region("Rhûn", 5);
		territoire = new Territoire("testTerritoire", false, TypeTerritoire.BIEN);
		joueur = new Joueur("Arthure", "000000");
		data = new LOTR_Data();
	}

	@After
	public void tearDown() throws Exception {
		region = null;
		joueur = null;
		data = null;
		territoire = null;
	}
	
	@Test
	public void test_add_region_territoire() {
		region.add_Territoire(territoire);
		assertEquals(region.get_Territoire("testTerritoire"), territoire);
	}
	
	@Test
	public void test_add_joueur_territoire() {
		joueur.add_Territoire(territoire);
		assertEquals(joueur.has_Territoire(territoire), true);
	}
	
	@Test
	public void test_remove_joueur_territoire() {
		joueur.add_Territoire(territoire);
		joueur.remove_Territoire(territoire);
		assertEquals(joueur.has_Territoire(territoire), false);
	}
	
	@Test
	public void test_get_territoire_joueur() {
		territoire.setNB_Unite(3);
		joueur.add_Territoire(territoire);
		territoire.setNB_Unite(5);
		assertEquals(joueur.getTerritoire(territoire).getNB_Unite(), 5);
	}
	
	@Test
	public void test_update_territoire_joueur() {
		territoire.setNB_Unite(3);
		joueur.add_Territoire(territoire);
		territoire.add_Units(5);
		Territoire update = new Territoire("testTerritoire", false, TypeTerritoire.BIEN);
		update.setNB_Unite(8);
		joueur.update_Territoire(update);
		assertEquals(joueur.getTerritoire(update).getNB_Unite(), 8);
		assertEquals(joueur.getTerritoire(territoire).getNB_Unite(), 8);
	}
	
	@Test
	public void test_joueur_get_nb_territoire() {
		joueur.add_Territoire(territoire);
		joueur.add_Territoire(new Territoire("1", false, TypeTerritoire.BIEN));
		joueur.add_Territoire(new Territoire("2", false, TypeTerritoire.BIEN));
		assertEquals(joueur.getNB_Territoire(), 3);
		joueur.remove_Territoire_FromName("");
		assertEquals(joueur.getNB_Territoire(), 3);
		joueur.remove_Territoire_FromName("1");
		assertEquals(joueur.getNB_Territoire(), 2);
	}
	
	@Test
	public void test_get_Region_ByName() {
		assertEquals(data.getRegionByName("Rhûn").getNom(), region.getNom());
	}
	
	@Test
	public void test_joueur_has_Region() {
		joueur.add_Territoire(new Territoire("Esgaroth", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Rhûn du Sud", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Rhûn du Nord", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Mithlond", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Landes Desséchée", false, TypeTerritoire.MAL));
		assertEquals(data.playerHasRegion(joueur, "Rhûn"), true);
	}
	
	@Test
	public void test_joueur_has_not_Region() {
		joueur.add_Territoire(new Territoire("Esgaroth", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Rhûn du Sud", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Rhûn du Nord", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Mithlond", false, TypeTerritoire.MAL));
		assertEquals(data.playerHasRegion(joueur, "Rhûn"), false);
	}
	
	@Test
	public void test_liste_regions_joueur() {
		joueur.add_Territoire(new Territoire("Esgaroth", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Rhûn du Sud", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Rhûn du Nord", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Landes Desséchée", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Mithlond", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Pays des Vieux Biscornus", true, TypeTerritoire.BIEN));
		joueur.add_Territoire(new Territoire("Trouée du Rohan", false, TypeTerritoire.BIEN));
		joueur.add_Territoire(new Territoire("Fangorn", true, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Enedwaith", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Miniriath", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Eregion", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Pays de Dun", false, TypeTerritoire.NEUTRE));
		assertEquals(data.getRegionsJoueur(joueur).size(), 2);
	}
	
	@Test
	public void test_joueur_renforts_simple() {
		joueur.add_Territoire(new Territoire("Esgaroth", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Rhûn du Sud", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Rhûn du Nord", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Mithlond", false, TypeTerritoire.MAL));
		assertEquals(data.calculer_Renforts(joueur), 3);
	}	
	
	@Test
	public void test_joueur_renforts_14_territoire() {
		joueur.add_Territoire(new Territoire("Esgaroth", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Rhûn du Sud", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Rhûn du Nord", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("c", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("b", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("a", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("D", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("M", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Mi", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Mit", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Mith", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Mithl", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Mithlo", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Mithlon", false, TypeTerritoire.MAL));
		assertEquals(data.calculer_Renforts(joueur), 4);
	}
	
	@Test
	public void test_joueur_renforts_14_territoire_and_region() {
		joueur.add_Territoire(new Territoire("Esgaroth", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Rhûn du Sud", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Rhûn du Nord", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Landes Desséchée", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("b", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("a", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("D", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("M", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Mi", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Mit", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Mith", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Mithl", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Mithlo", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Mithlon", false, TypeTerritoire.MAL));
		assertEquals(data.calculer_Renforts(joueur), 6); //Rhun = 2
	}
	
	@Test
	public void test_joueur_renforts_14_territoire_and_2_region() {
		joueur.add_Territoire(new Territoire("Esgaroth", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Rhûn du Sud", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Rhûn du Nord", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Landes Desséchée", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Pays des Vieux Biscornus", true, TypeTerritoire.BIEN));
		joueur.add_Territoire(new Territoire("Trouée du Rohan", false, TypeTerritoire.BIEN));
		joueur.add_Territoire(new Territoire("Fangorn", true, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Enedwaith", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Miniriath", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Eregion", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Pays de Dun", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Mithl", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Mithlo", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Mithlon", false, TypeTerritoire.MAL));
		assertEquals(data.calculer_Renforts(joueur), 10); //Rohan + Rhun = 6
	}
	
	@Test
	public void test_generate_4_territoires() {
		ArrayList<Territoire> allTerritoires = data.getAllTerritoires();
		assertEquals(allTerritoires.size(), 42); //Nombre de territoires total
		ArrayList<Territoire> toTest = data.generateRandomTerritoiresFromType(TypeTerritoire.BIEN, 4, allTerritoires);
		assertEquals(toTest.size(), 4);
		assertEquals(allTerritoires.size(), 38);
	}
}
