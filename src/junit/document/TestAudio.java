package junit.document;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import mediatheque.Genre;
import mediatheque.Localisation;
import mediatheque.OperationImpossible;
import mediatheque.document.Audio;
import util.InvariantBroken;

public class TestAudio {

	
	Audio mondoc;
	Localisation loc;
	Genre gen;
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
	}
	

}
