package junit.mediatheque;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

import mediatheque.*;
import mediatheque.client.CategorieClient;
import mediatheque.client.Client;
import mediatheque.document.Audio;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestMediatheque {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	Mediatheque maMediatheque;
	Localisation maLocalisation;
	Genre monGenre;
	
	
	
	@Before
	public void setUp() throws Exception {
		 System.setOut(new PrintStream(outContent));
		maMediatheque = new Mediatheque("Mediatheque");
		maMediatheque.ajouterCatClient("Etudiant", 0, 0, 0, 0, true);
		maMediatheque.ajouterCatClient("Retraite", 0, 0, 0, 0, true);
		maMediatheque.ajouterLocalisation("38", "4");
		maLocalisation = new Localisation ("38","4");
		monGenre = new Genre("mongenre");
		maMediatheque.ajouterGenre("mongenre");
		Audio musique = new Audio("123",maLocalisation,"Titre","auteur","annee",monGenre,"Classification");
		maMediatheque.ajouterDocument(musique);
		maMediatheque.inscrire("Sternberger", "Aurelien", "adresse", "Etudiant");
	}
	
	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	   
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
		CategorieClient c = maMediatheque.chercherCatClient("Retraite");
		System.out.println("la categorie est : "+c);
		
		maMediatheque.supprimerCatClient("Retraite");
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
		CategorieClient c = maMediatheque.modifierCatClient(new CategorieClient("Etudiant",0,0,0,0,true), "EtudiantMaster", 0, 0, 0, 0, true);
		
		assertEquals(c.getNom(),"EtudiantMaster");
	
	}
	
	@Test
	public void testGetCategorieAt() throws Exception {
		CategorieClient c = maMediatheque.getCategorieAt(5);
		assertEquals(c,null);
		
	}
	
	@Test
	public void testGetCategorieAt2() throws Exception {
		maMediatheque.ajouterCatClient("Enfant", 0, 0, 0, 0, true);
		CategorieClient c = maMediatheque.getCategorieAt(2);
		assertEquals("Enfant", c.getNom());
	}
	
	@Test (expected = OperationImpossible.class)
	public void testAjouterDocument() throws Exception {
		// Test d'ajout doc déja existant
		Audio monAudio =  new Audio("MonDocument",maLocalisation,"titre","auteur","annee",monGenre,"classification");
		maMediatheque.ajouterDocument(monAudio);
		maMediatheque.ajouterDocument(monAudio);
	}
	
	@Test (expected = OperationImpossible.class)
	public void testAjouterDocument3() throws Exception {
		Audio monAudio =  new Audio("MonDocument",maLocalisation,"titre","auteur","annee",new Genre("GenreNonPresentDansMedia"),"classification");
		maMediatheque.ajouterDocument(monAudio);
	}
	
	@Test (expected = OperationImpossible.class)
	public void testAjouterDocument4() throws Exception {
		Audio monAudio =  new Audio("MonDocument",new Localisation("12", "12"),"titre","auteur","annee",monGenre,"classification");
		maMediatheque.ajouterDocument(monAudio);
	}
	
	
	@Test
	public void testAjouterDocument2() throws Exception {
		
		Audio monAudio =  new Audio("MonDocument",maLocalisation,"titre","auteur","annee",monGenre,"classification");
		maMediatheque.ajouterDocument(monAudio);
		Audio monAudio2 = (Audio)maMediatheque.getDocumentAt(1);
		assertEquals(monAudio2.getCode(),"MonDocument");
		assertEquals(monAudio2.getTitre(),"titre");
	}
	
	@Test (expected = OperationImpossible.class)
	public void testRetirerDocument() throws Exception {
		maMediatheque.retirerDocument("fakeCode");
		
	}
	
	@Test (expected = OperationImpossible.class)
	public void testRetirerDocument2() throws Exception {
		Audio monAudio =  new Audio("MonDocument",maLocalisation,"titre","auteur","annee",monGenre,"classification");
		maMediatheque.ajouterDocument(monAudio);
		monAudio.metEmpruntable();
		monAudio.emprunter();
		maMediatheque.retirerDocument("MonDocument");
	}
	
	@Test
	public void testRetirerDocument3() throws Exception {
		Audio monAudio =  new Audio("MonDocumentAudio",maLocalisation,"titre","auteur","annee",monGenre,"classification");
		maMediatheque.ajouterDocument(monAudio);
		Audio monAudio2 = (Audio)maMediatheque.chercherDocument("MonDocumentAudio");
		assertEquals(monAudio2.getCode(),"MonDocumentAudio");
		maMediatheque.retirerDocument("MonDocumentAudio");
		Audio monAudio3 = (Audio)maMediatheque.chercherDocument("MonDocumentAudio");
		assertEquals(monAudio3,null);
	}
	
	@Test (expected = OperationImpossible.class)
	public void testmetEmpruntable() throws Exception {
		maMediatheque.metEmpruntable("CodeInexistant");
	}
	
	@Test 
	public void testmetEmpruntable2() throws Exception {
		Audio monAudio =  new Audio("MonDocumentAudio",maLocalisation,"titre","auteur","annee",monGenre,"classification");
		monAudio.metEmpruntable();
		assertTrue(monAudio.estEmpruntable());
		assertTrue(monAudio.emprunter());
		
		
	}
	
	
	
	@Test
	public void testListerDocuments() throws Exception{
		maMediatheque.listerDocuments();
		
	}
	
	
	@Test
	public void testListerDocuments2() throws Exception{
		Audio monAudio =  new Audio("MonDocumentAudio",maLocalisation,"titre","auteur","annee",monGenre,"classification");
		maMediatheque.ajouterDocument(monAudio);
		maMediatheque.listerDocuments();
		
	}
	
	@Test
	public void testexisteDocument() throws Exception{
		Audio monAudio =  new Audio("MonDocumentAudio",maLocalisation,"titre","auteur","annee",monGenre,"classification");
		maMediatheque.ajouterDocument(monAudio);
		Class cArg[] = new Class[1];
		cArg[0] = Genre.class;
		Method method = maMediatheque.getClass().getDeclaredMethod("existeDocument", cArg);
		method.setAccessible(true);
		Object o = method.invoke(maMediatheque,new Object[] {new Genre("Faux Genre")});
		assertEquals(o,false);
		
		
	}
	
	@Test
	public void testexisteDocument2() throws Exception{
		Audio monAudio =  new Audio("MonDocumentAudio",maLocalisation,"titre","auteur","annee",monGenre,"classification");
		maMediatheque.ajouterDocument(monAudio);
		Class cArg[] = new Class[1];
		cArg[0] = Genre.class;
		Method method = maMediatheque.getClass().getDeclaredMethod("existeDocument", cArg);
		method.setAccessible(true);
		Object o = method.invoke(maMediatheque,new Object[] {monGenre});
		assertEquals(o,true);
		
	}
	
	@Test
	public void testexisteDocument3() throws Exception{
		Audio monAudio =  new Audio("MonDocumentAudio",maLocalisation,"titre","auteur","annee",monGenre,"classification");
		maMediatheque.ajouterDocument(monAudio);
		Class cArg[] = new Class[1];
		cArg[0] = Localisation.class;
		Method method = maMediatheque.getClass().getDeclaredMethod("existeDocument", cArg);
		method.setAccessible(true);
		Object o = method.invoke(maMediatheque,new Object[] {maLocalisation});
		assertEquals(o,true);
		
	}
	
	@Test
	public void testexisteDocument4() throws Exception{
		Audio monAudio =  new Audio("MonDocumentAudio",maLocalisation,"titre","auteur","annee",monGenre,"classification");
		maMediatheque.ajouterDocument(monAudio);
		Class cArg[] = new Class[1];
		cArg[0] = Localisation.class;
		Method method = maMediatheque.getClass().getDeclaredMethod("existeDocument", cArg);
		method.setAccessible(true);
		Object o = method.invoke(maMediatheque,new Object[] {new Localisation("fausseSalle", "fauxRayon")});
		assertEquals(o,false);
		
	}
	
	@Test
	public void testgetDocumentAt() throws Exception{
		Audio monAudio =  new Audio("MonDocument",maLocalisation,"titre","auteur","annee",monGenre,"classification");
		maMediatheque.ajouterDocument(monAudio);
		Audio monAudio2 = (Audio)maMediatheque.getDocumentAt(1);
		assertEquals("MonDocument", monAudio2.getCode());
	}
	
	@Test
	public void testgetDocumentAt2() throws Exception{
		Audio monAudio =  new Audio("MonDocument",maLocalisation,"titre","auteur","annee",monGenre,"classification");
		maMediatheque.ajouterDocument(monAudio);
		Audio monAudio2 = (Audio)maMediatheque.getDocumentAt(5);
		assertEquals(monAudio2,null);
	}
	
	@Test
	public void testGetDocumentSize() throws Exception{
		Audio monAudio =  new Audio("MonDocument",maLocalisation,"titre","auteur","annee",monGenre,"classification");
		maMediatheque.ajouterDocument(monAudio);
		assertEquals(2,maMediatheque.getDocumentsSize());
		Audio monAudio2 =  new Audio("MonDocument2",maLocalisation,"titre","auteur","annee",monGenre,"classification");
		maMediatheque.ajouterDocument(monAudio2);
		assertEquals(3,maMediatheque.getDocumentsSize());
	}
	
	
	@Test (expected = OperationImpossible.class)
	public void testEmprunter() throws Exception{
		maMediatheque.emprunter("FauxNom", "FauxPrenom", "FauxCode");
	}
	
	
	@Test (expected = OperationImpossible.class)
	public void testEmprunter2() throws Exception{
		maMediatheque.emprunter("Sternberger", "Aurelien", "Code");
	}
	
	@Test (expected = OperationImpossible.class)
	public void testEmprunter3() throws Exception{
		maMediatheque.getClientAt(0).getCategorie().modifierMax(20);
		maMediatheque.emprunter("Sternberger", "Aurelien", "CodeInexistant");
	}
	
	@Test (expected = OperationImpossible.class)
	public void testEmprunter4() throws Exception{
		maMediatheque.getClientAt(0).getCategorie().modifierMax(20);
		maMediatheque.emprunter("Sternberger", "Aurelien", "123");
	}
	
	@Test (expected = OperationImpossible.class)
	public void testEmprunter5() throws Exception{
		Audio musique1 = new Audio("1234",maLocalisation,"Titre","auteur","annee",monGenre,"Classification");
		musique1.metEmpruntable();
		musique1.emprunter();
		maMediatheque.ajouterDocument(musique1);
		maMediatheque.getClientAt(0).getCategorie().modifierMax(20);
		maMediatheque.emprunter("Sternberger", "Aurelien", "1234");
	}
	
	@Test 
	public void testEmprunter6() throws Exception{
		maMediatheque.getClientAt(0).getCategorie().modifierMax(20);
		Audio musique1 = new Audio("1234",maLocalisation,"Titre","auteur","annee",monGenre,"Classification");
		musique1.metEmpruntable();
		maMediatheque.ajouterDocument(musique1);
		maMediatheque.emprunter("Sternberger", "Aurelien", "1234");
		assertEquals("1234",maMediatheque.getFicheEmpruntAt(0).getDocument().getCode());
		assertEquals("Sternberger",maMediatheque.getFicheEmpruntAt(0).getClient().getNom());
	}
	
	@Test(expected = OperationImpossible.class)
	public void testRestituer() throws Exception{
		maMediatheque.restituer("nomInconnu", "prenomInconnu", "123");
	}
	
	@Test (expected = OperationImpossible.class)
	public void testRestituer2() throws Exception {
		maMediatheque.restituer("Sternberger", "Aurelien", "FauxCode");
	}
	
	@Test (expected = OperationImpossible.class)
	public void testRestituer3() throws Exception {
		maMediatheque.restituer("Sternberger", "Aurelien", "123");
	}
	
	@Test 
	public void testRestituer4() throws Exception {
		maMediatheque.getClientAt(0).getCategorie().modifierMax(20);
		Audio musique1 = new Audio("1234",maLocalisation,"Titre","auteur","annee",monGenre,"Classification");
		musique1.metEmpruntable();
		maMediatheque.ajouterDocument(musique1);
		maMediatheque.emprunter("Sternberger", "Aurelien", "1234");
		assertEquals("1234",maMediatheque.getFicheEmpruntAt(0).getDocument().getCode());
		assertEquals("Sternberger",maMediatheque.getFicheEmpruntAt(0).getClient().getNom());
		maMediatheque.restituer("Sternberger", "Aurelien", "1234");
		
		assertEquals(0,maMediatheque.getFicheEmpruntsSize());
		
	}
	
	@Test
	public void testverifier() throws Exception {
		maMediatheque.getClientAt(0).getCategorie().modifierMax(20);
		Audio musique1 = new Audio("1234",maLocalisation,"Titre","auteur","annee",monGenre,"Classification");
		musique1.metEmpruntable();
		maMediatheque.ajouterDocument(musique1);
		maMediatheque.emprunter("Sternberger", "Aurelien", "1234");
		maMediatheque.verifier();
	}
	
	
	/// A voir
	@Test
	public void testverifier2() throws Exception {
		maMediatheque.getClientAt(0).getCategorie().modifierMax(20);
		Audio musique1 = new Audio("1234",maLocalisation,"Titre","auteur","annee",monGenre,"Classification");
		musique1.metEmpruntable();
		maMediatheque.ajouterDocument(musique1);
		maMediatheque.emprunter("Sternberger", "Aurelien", "1234");
		Class cArg[] = null;
		Method method = maMediatheque.getFicheEmpruntAt(0).getClass().getDeclaredMethod("premierRappel", cArg);
		method.setAccessible(true);
		Object o = method.invoke(maMediatheque.getFicheEmpruntAt(0),new Object[] {});

		maMediatheque.verifier();
	}
	
	
	// atester
	@Test
	public void testListerFicheEmprunts() {
		maMediatheque.listerFicheEmprunts();
		
	}
	
	@Test (expected = OperationImpossible.class)
	public void testInscrire () throws Exception {
		maMediatheque.inscrire("Nom", "Prenom", "adresse", "categorieInexistante");
	}
	
	
	@Test
	public void testInscrire2 () throws Exception {
		maMediatheque.inscrire("Nom", "Prenom", "adresse", "Etudiant");
		assertEquals(2,maMediatheque.getClientsSize());
	}
	
	@Test (expected = OperationImpossible.class)
	public void testInscrire3 () throws Exception {
		maMediatheque.inscrire("Nom", "Prenom", "adresse", "categorieInexistante",2);
	}
	
	@Test
	public void testInscrire4 () throws Exception {
		maMediatheque.inscrire("Nom", "Prenom", "adresse", "Etudiant",2);
		assertEquals(2,maMediatheque.getClientsSize());
	}
	
	
	// A faire pour la methode private !
	/*@Test
	public void testInscrire5 () throws Exception {
		CategorieClient maCat = new CategorieClient("Etudiant");
		maMediatheque.inscrire("Nom", "Prenom", "adresse", maCat ,2);
		assertEquals(2,maMediatheque.getClientsSize());
	}*/
	
	@Test (expected = OperationImpossible.class)
	public void testResilier () throws Exception {
		maMediatheque.resilier("FauxNom", "FauxPrenom");
	}
	
	@Test  (expected = OperationImpossible.class)
	public void testResilier2 () throws Exception {
	maMediatheque.getClientAt(0).getCategorie().modifierMax(20);
	Audio musique1 = new Audio("1234",maLocalisation,"Titre","auteur","annee",monGenre,"Classification");
	musique1.metEmpruntable();
	maMediatheque.ajouterDocument(musique1);
	maMediatheque.emprunter("Sternberger", "Aurelien", "1234");
	maMediatheque.resilier("Sternberger", "Aurelien");
	}
	
	@Test
	public void testResilier3 () throws Exception {
		maMediatheque.resilier("Sternberger", "Aurelien");
		assertEquals(0,maMediatheque.getClientsSize());
	}
	
	@Test (expected = OperationImpossible.class)
	public void testModifierCLient() throws Exception {
		maMediatheque.modifierClient(new Client ("FauxNom","fauxPrenom"), "nom", "prenom", "adresse", "catnom", 0);
	}

}
