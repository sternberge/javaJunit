package junit.document;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import mediatheque.Genre;
import mediatheque.Localisation;
import mediatheque.OperationImpossible;
import mediatheque.document.Audio;
import mediatheque.document.Livre;

public class TestLivre {

	Livre monLivre, impossibleLivre;
	
	@Before
	public void setUp() throws Exception {
		monLivre = new Livre("0164826", new Localisation("32", "6"), "Comment etre mechant",
				"Herboch.H", "2016", new Genre("Education"), 500);
	}
	
	@Test
	public void constructorTest() throws Exception {
		assertEquals("0164826", monLivre.getCode());
		assertEquals(new Localisation("32", "6"), monLivre.getLocalisation());
		assertEquals("Comment etre mechant", monLivre.getTitre());
		assertEquals("Herboch.H", monLivre.getAuteur());
		assertEquals("2016", monLivre.getAnnee());
		assertEquals(new Genre("Education"), monLivre.getGenre());
		assertEquals(false, monLivre.estEmpruntable());
		assertEquals(false, monLivre.estEmprunte());
		assertEquals(0, monLivre.getNbEmprunts());
	}
	
	@Test(expected=OperationImpossible.class)
	public void ConstructorExceptionTest() throws Exception {
		impossibleLivre = new Livre("0164826", new Localisation("32", "6"), "Comment etre mechant",
				"Herboch.H", "2016", new Genre("Education"), -1);
	}
	
	@Test
	public void emprunterTest() throws Exception{
		monLivre.metEmpruntable();
		assertTrue(monLivre.emprunter());
	}
	
	@Test
	public void VerificationIncrementationNbEmpruntDocument() throws Exception{
		int avant = monLivre.getNbEmprunts();
		monLivre.metEmpruntable();
		monLivre.emprunter();
		int apres = monLivre.getNbEmprunts();
		assertEquals(avant + 1, apres);
	}
	
	@Test
	public void VerificationIncrementationNbEmpruntLivre() throws Exception{
		int avant = Livre.getStat();
		monLivre.metEmpruntable();
		monLivre.emprunter();
		int apres = Livre.getStat();
		assertEquals(avant + 1, apres);
	}
	
	@Test
	public void dureeEmpruntTest() {
		assertEquals(42, monLivre.dureeEmprunt());
	}
	
	@Test
	public void tarifEmpruntTest() {
		assertEquals(0.5, monLivre.tarifEmprunt(), 0.0);
	}
	
	
	

}
