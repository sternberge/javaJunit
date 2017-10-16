package junit.document;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import mediatheque.Genre;
import mediatheque.Localisation;
import mediatheque.OperationImpossible;
import mediatheque.document.Audio;
import mediatheque.document.DocumentForTest;
import util.InvariantBroken;

public class TestAudio {
	
	Audio myAudio, impossibleAudio;
	
	@Before
	public void setUp() throws Exception {
		myAudio = new Audio("456789", new Localisation("38", "4"), "Perles de nuit", "Cynthia", "1998",
				new Genre("French Varieties"), "Opera");
	}
	
	@Test
	public void constructorTest() {
		assertEquals("456789", myAudio.getCode());
		assertEquals(new Localisation("38", "4"), myAudio.getLocalisation());
		assertEquals("Perles de nuit", myAudio.getTitre());
		assertEquals("Cynthia", myAudio.getAuteur());
		assertEquals("1998", myAudio.getAnnee());
		assertEquals(new Genre("French Varieties"), myAudio.getGenre());
		assertEquals(false, myAudio.estEmpruntable());
		assertEquals(false, myAudio.estEmprunte());
		assertEquals(0, myAudio.getNbEmprunts());
		assertEquals("Opera", myAudio.getClassification());
	}
	
	@Test(expected=OperationImpossible.class)
	public void ConstructorExceptionTest() throws Exception {
		impossibleAudio = new Audio("456789", new Localisation("38", "4"), "Perles de nuit", "Cynthia", "1998",
				new Genre("French Varieties"), null);
	}
	
	@Test
	public void emprunterTest() throws Exception{
		myAudio.metEmpruntable();
		assertTrue(myAudio.emprunter());
	}
	
	@Test
	public void VerificationIncrementationNbEmpruntAudio() throws Exception{
		int avant = myAudio.getNbEmprunts();
		myAudio.metEmpruntable();
		myAudio.emprunter();
		int apres = myAudio.getNbEmprunts();
		assertEquals(avant + 1, apres);
	}

	
	/*Audio mondoc;
	Localisation loc;
	Genre gen;
	
	//tests constructeur à finir
	@Before
	public void init() throws Exception {
		loc = new Localisation("nom","localisation");
		gen = new Genre("nom");
		mondoc =  new Audio ("qjqskldjlq",loc,"3eme","titre","auteur",gen,"classification");
	}
	

	
	@Test (expected = OperationImpossible.class)
	public void testConstructor() throws Exception{
		mondoc =  new Audio ("qjqskldjlq",loc,"3eme","titre","auteur",gen,null);
	}
	
	@Test
	public void testConstructor2() throws Exception{
		mondoc =  new Audio ("qjqskldjlq",loc,"3eme","auteur","auteur",gen,"");
		assertEquals(mondoc.getAuteur(),"auteur");
		assertEquals(mondoc.getLocalisation(),loc);
	}*/
	

}
