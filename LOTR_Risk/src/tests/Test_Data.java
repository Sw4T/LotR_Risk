package tests;

import static org.junit.Assert.assertEquals;
import objects.Joueur;
import objects.Region;
import objects.Territoire;
import objects.TypeTerritoire;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.LOTR_Data;

public class Test_Data {

	private LOTR_Data data;
	private Region region;
	private Territoire territoire;
	private Joueur joueur;
	
	@Before
	public void setUp() throws Exception {
		region = new Region("Rhûn", 5);
		territoire = new Territoire("testTerritoire", false, TypeTerritoire.BIEN);
		joueur = new Joueur("Arthure");
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
	public void test_get_Region_ByName() {
		assertEquals(data.getRegionByName("Rhûn").getNom(), region.getNom());
	}
	
	@Test
	public void test_player_has_Region() {
		joueur.add_Territoire(new Territoire("Esgaroth", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Rhûn du Sud", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Rhûn du Nord", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Mithlond", false, TypeTerritoire.MAL));
		joueur.add_Territoire(new Territoire("Landes Desséchée", false, TypeTerritoire.MAL));
		assertEquals(data.playerHasRegion(joueur, "Rhûn"), true);
	}
	
	@Test
	public void test_player_has_not_Region() {
		joueur.add_Territoire(new Territoire("Esgaroth", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Rhûn du Sud", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Rhûn du Nord", false, TypeTerritoire.NEUTRE));
		joueur.add_Territoire(new Territoire("Mithlond", false, TypeTerritoire.MAL));
		assertEquals(data.playerHasRegion(joueur, "Rhûn"), false);
	}
	
	

}
