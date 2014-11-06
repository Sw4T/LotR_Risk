package tests;

import static org.junit.Assert.assertEquals;
import objects.Joueur;
import objects.Region;
import objects.Territoire;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.LOTR_Data;

public class Test_Data {

	private LOTR_Data data;
	private Region region;
	private Territoire territoire;
	private Joueur joueur;
	//TU VAS TE
	
	@Before
	public void setUp() throws Exception {
		region = new Region("testRegion", 5);
		territoire = new Territoire("testTerritoire", false);
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
		region.setNom("Arnor");
		assertEquals(data.getRegionByName("Arnor").getNom(), region.getNom());
	}

}
