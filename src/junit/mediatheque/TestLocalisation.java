package junit.mediatheque;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import mediatheque.Localisation;

public class TestLocalisation {
	
	Localisation maLocalisation;

	@Before
	public void setUp() throws Exception {
		maLocalisation = new Localisation("5", "28");
	}

	@Test
	public void constructorTest() {
		assertEquals("5", maLocalisation.getSalle());
		assertEquals("28", maLocalisation.getRayon());
	}
	
	@Test
	public void settersTest() {
		maLocalisation.setSalle("6");
		assertEquals("6", maLocalisation.getSalle());
		
		maLocalisation.setRayon("29");
		assertEquals("29", maLocalisation.getRayon());
	}

}
