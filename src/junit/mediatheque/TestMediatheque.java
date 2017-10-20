package junit.mediatheque;

import static org.junit.Assert.*;
import mediatheque.*;
import mediatheque.client.CategorieClient;
import mediatheque.client.Client;
import mediatheque.document.Audio;

import org.junit.Before;
import org.junit.Test;

public class TestMediatheque {

	Mediatheque maMediatheque;
	Localisation maLocalisation;
	
	
	@Before
	public void setUp() throws Exception {
		maMediatheque = new Mediatheque("Mediatheque");
		maMediatheque.ajouterCatClient("Etudiant", 0, 0, 0, 0, true);
		maMediatheque.ajouterLocalisation("38", "4");
		maLocalisation = new Localisation ("38","4");
	}

	
	@Test
	public void testChercherGenre() {
		Genre genreSearch = maMediatheque.chercherGenre("Genre");
		assertEquals(genreSearch, null);
	}
	
	@Test
	public void testChercherGenrebis() throws Exception{
		maMediatheque.ajouterGenre("Fiction");
		Genre genreSearch = maMediatheque.chercherGenre("Fiction");
		assertEquals(genreSearch.getNom(),"Fiction");
	}
	
	@Test (expected = OperationImpossible.class)
	public void testSupprimerGenre() throws Exception{
		maMediatheque.supprimerGenre("Fiction");
	}
	
	@Test 
	public void testSupprimerGenre2() throws Exception{
		maMediatheque.ajouterGenre("Fiction");
		Genre genreSearch = maMediatheque.chercherGenre("Fiction");
		assertEquals(genreSearch.getNom(),"Fiction");
		maMediatheque.supprimerGenre("Fiction"); // Le print ne fonctionne pas

	}
	
	@Test (expected = OperationImpossible.class)
	public void testSupprimerGenre3() throws Exception{
		maMediatheque.ajouterGenre("Fiction");
		Audio monAudio =  new Audio("456789", new Localisation("38", "4"), "Perles de nuit", "Cynthia", "1998",
				new Genre("Fiction"), "Opera");
		
		maMediatheque.ajouterDocument(monAudio);
		Genre genreSearch = maMediatheque.chercherGenre("Fiction");
		assertEquals(genreSearch.getNom(),"Fiction");
		maMediatheque.supprimerGenre("Fiction"); // Le print ne fonctionne pas

	}
	
	@Test
	public void testChercherLocalisation() {
		Localisation locTest = maMediatheque.chercherLocalisation("38", "4");
		assertEquals(locTest.getSalle(),"38");
		assertEquals(locTest.getRayon(),"4");
	}
	
	@Test
	public void testChercherLocalisation2() {
		Localisation locTest = maMediatheque.chercherLocalisation("39", "5");
		assertEquals(locTest,null);
		
	}
	
	@Test (expected = OperationImpossible.class)
	public void testAjouterLocalisation() throws Exception{
		maMediatheque.ajouterLocalisation("38", "4");
		
	}
	
	@Test
	public void testAjouterLocalisation2 () throws Exception {
		maMediatheque.ajouterLocalisation("39", "5");
	}
	
	@Test
	public void testmodifierLocalisation () throws Exception {
		
		maMediatheque.modifierLocalisation(maLocalisation, "39", "5");
		Localisation loc = maMediatheque.chercherLocalisation("39","5");
		
		assertEquals(loc.getSalle(),"39");
		assertEquals(loc.getRayon(),"5");
	}
	
	@Test(expected = OperationImpossible.class)
	public void testmodifierLocalisationbis () throws Exception {
		
		maMediatheque.modifierLocalisation(new Localisation("45","45"), "39", "5");
		
	}
	
	@Test
	public void testlisterLocalisations() throws Exception{
		maMediatheque.ajouterLocalisation("20", "1");
		maMediatheque.listerLocalisations();
	}
	
	@Test
	public void testChercherCatClient() {
		CategorieClient maCat = maMediatheque.chercherCatClient("Professionnel");
		assertEquals(maCat,null);
	}
	
	@Test
	public void testChercherCatClientbis() {
		CategorieClient maCat = maMediatheque.chercherCatClient("Etudiant");
		assertEquals(maCat.getNom(),"Etudiant");
	}
	
	@Test (expected = OperationImpossible.class)
	public void testsupprimerCatClient () throws Exception {
		maMediatheque.supprimerCatClient("Prof");
	}
	
	
	@Test
	public void testsupprimerCatClient2 () throws Exception {
		maMediatheque.supprimerCatClient("Etudiant");
	}
	
	
	@Test (expected = OperationImpossible.class)
	public void testsupprimerCatClient3 () throws Exception {
		Client monClient = new Client("nom", "prenom", "adresse", new CategorieClient("Etudiant"));
		maMediatheque.inscrire("nom", "prenom", "adresse", "Etudiant");
		maMediatheque.supprimerCatClient("Etudiant");
	}
	
	
	@Test(expected = OperationImpossible.class)
	public void testajouterCatClient() throws Exception {
		maMediatheque.ajouterCatClient("Etudiant", 0, 0, 0, 0, true);
	}
	
	@Test
	public void testajouterCatClient2() throws Exception {
		CategorieClient c = maMediatheque.ajouterCatClient("Prof", 0, 0, 0, 0, true);
		assertEquals(c.getNom(),"Prof");
	}
	
	@Test (expected = OperationImpossible.class)
	public void testModifierCatClient() throws Exception {
		maMediatheque.modifierCatClient(new CategorieClient("Adolescent"), "nom", 0, 0, 0, 0, true);
	}
	
	@Test
	public void testModifierCatClient2() throws Exception {
		CategorieClient c = maMediatheque.modifierCatClient(new CategorieClient("Etudiant"), "EtudiantMaster", 0, 0, 0, 0, true);
		assertEquals(c.getNom(),"EtudiantMaster");
	
	}
	
	@Test
	public void testGetCategorieAt() throws Exception {
		
	}
	

}
