package junit.mediatheque;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import mediatheque.Genre;

public class TestGenre {
	
	Genre monGenre;

	@Before
	public void setUp() throws Exception {
		monGenre =  new Genre ("Nom");
	}

	@Test
	public void testEmprunter() {
		int expectedEmprunts = monGenre.getNbEmprunts() + 1;
		monGenre.emprunter();
		assertEquals(monGenre.getNbEmprunts(), expectedEmprunts);
	}

	@Test
	public void testModifier() {
		String nom = "nom";
		monGenre.modifier(nom);
		assertEquals(monGenre.getNom(),nom);
	}

}
