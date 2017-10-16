package junit.document;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import mediatheque.document.DocumentForTest;
import util.InvariantBroken;
import mediatheque.Genre;
import mediatheque.Localisation;
import mediatheque.OperationImpossible;

public class TestDocument {
	
	DocumentForTest myDocument, impossibleDocument;

	@Before
	public void setUp() throws Exception {
		myDocument = new DocumentForTest("012345", new Localisation("105", "22"),
				"Comment tester", "Sternberger.A", "2017", new Genre("Education"));
	}

	@Test
	public void constructorTest() {
		assertEquals("012345", myDocument.getCode());
		assertEquals(new Localisation("105", "22"), myDocument.getLocalisation());
		assertEquals("Comment tester", myDocument.getTitre());
		assertEquals("Sternberger.A", myDocument.getAuteur());
		assertEquals("2017", myDocument.getAnnee());
		assertEquals(new Genre("Education"), myDocument.getGenre());
		assertEquals(false, myDocument.estEmpruntable());
		assertEquals(false, myDocument.estEmprunte());
		assertEquals(0, myDocument.getNbEmprunts());
	}
	

	@Test(expected=OperationImpossible.class)
	public void ConstructorExceptionTest() throws Exception {
		impossibleDocument = new DocumentForTest(null, null, null, null, null, null);
	}
	
	@Test
	public void emprunterTest() throws Exception{
		myDocument.metEmpruntable();
		assertTrue(myDocument.emprunter());
	}
	
	//Peut pas emprunter un document non empruntable
	@Test(expected=OperationImpossible.class)
	public void emprunterTest2() throws Exception{
		myDocument.emprunter();
	}
	
	//Peut pas emprunter un document deja emprunter
	@Test(expected=OperationImpossible.class)
	public void emprunterTest3() throws Exception{
		myDocument.metEmpruntable();
		myDocument.emprunter();
		myDocument.emprunter();
	}
	
	@Test
	public void emprunterGenreEmpruntsIncrementCorrectementTest() throws Exception{
		myDocument.metEmpruntable();
		int avant = myDocument.getGenre().getNbEmprunts();
		myDocument.emprunter();
		int apres = myDocument.getGenre().getNbEmprunts();
		assertEquals(avant + 1, apres);
	}
	
	
	@Test
	public void metEmpruntableTest() throws Exception{
		myDocument.metEmpruntable();
		assertEquals(true, myDocument.estEmpruntable());
	}
	
	//Peut pas mettre consultable un document qui l'est deja
	@Test(expected=OperationImpossible.class)
	public void metConsultableTest() throws Exception {
		myDocument.metConsultable();
	}
	
	//Peut pas mettre consultable un document deja emprunte
	@Test(expected=OperationImpossible.class)
	public void metConsultableTest2() throws Exception {
		myDocument.metEmpruntable();
		myDocument.emprunter();
		myDocument.metConsultable();
	}
	
	//Peut pas restituer un document non empruntable
	@Test(expected=OperationImpossible.class)
	public void restituerTest() throws Exception {
		myDocument.restituer();
	}
	
	//Peut pas restituer un document non emprunte
	@Test(expected=OperationImpossible.class)
	public void restituerTest2() throws Exception {
		myDocument.metEmpruntable();
		myDocument.restituer();
	}
	
	//Pas empruntable, pas emprunte => Safe state
	@Test
	public void invariantTest() {
		assertTrue(myDocument.invariant());
	}
	
	//Empruntable, pas emprunte => Safe state
	@Test
	public void invariantTest2() throws Exception{
		myDocument.metEmpruntable();
		assertTrue(myDocument.invariant());
	}
	
	//Empruntable, emprunte => Safe state
	@Test
	public void invariantTest3() throws Exception{
		myDocument.metEmpruntable();
		myDocument.emprunter();
		assertTrue(myDocument.invariant());
	}
	

}
