package tests;

import static org.junit.Assert.*;
import objects.Region;
import objects.Territoire;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.LOTR_Data;

public class Test_Data {

	//private LOTR_Data data;
	private Region region;
	private Territoire territoire;
	
	@Before
	public void setUp() throws Exception {
		region = new Region("testRegion", 5);
		territoire = new Territoire("testTerritoire", false);
	}

	@After
	public void tearDown() throws Exception {
		region = null;
		territoire = null;
	}

	@Test
	public void test_add_simple_territoire() {
		region.add_Territoire(t);
	}

}
