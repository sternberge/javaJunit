package junit.document;

import static org.junit.Assert.*;

import org.junit.Test;

import mediatheque.Genre;
import mediatheque.Localisation;
import mediatheque.OperationImpossible;
import mediatheque.document.Livre;

public class TestLivre {

	Livre monLivre;
	
	
	@Test(expected = OperationImpossible.class)
	public void testConstructor() throws Exception {
		monLivre = new Livre("code",new Localisation("salle","rayon"),"titre","auteur","annee",new Genre("n"),-120);
	}
	
	@Test
	public void testConstructor2() throws Exception {
		monLivre = new Livre("code",new Localisation("salle","rayon"),"titre","auteur","annee",new Genre("n"),120);
		assertEquals(monLivre.getAnnee(),"annee");
		assertEquals(monLivre.getAuteur(),"auteur");
	}
	
	@Test
	public void testConstructor3() throws Exception {
		assertFalse(monLivre !=null);
	}
	
	@Test
	public void testConstructor4() throws Exception {
		monLivre = new Livre("code",new Localisation("salle","rayon"),"titre","auteur","annee",new Genre("n"),120);
		assertTrue(monLivre !=null);
	}

}
