package junit.client;

import static org.junit.Assert.*;



import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import mediatheque.OperationImpossible;
import mediatheque.client.CategorieClient;
import mediatheque.client.Client;

public class TestClient {
	
	Client monClient;
	CategorieClient maCategorie;
	
	@Before
	public void setUp() throws Exception {
		maCategorie = new CategorieClient("Nom");
		
		monClient = new Client ("Nom", "Prenom","Adresse",maCategorie);
		
	}


	@Test (expected = OperationImpossible.class)
	public void testConstructeur1() throws Exception {
		maCategorie.modifierCodeReducActif(true);
		monClient = new Client ("Nom", "Prenom","Adresse",maCategorie);
	}
	
	@Test
	public void testConstructeur1bis() throws Exception {
		
		
		assertEquals("Nom",monClient.getNom());
		assertEquals("Prenom",monClient.getPrenom());
		assertEquals("Adresse",monClient.getAdresse());
		assertEquals(maCategorie,monClient.getCategorie());
	}
	
	@Test (expected = OperationImpossible.class)
	public void testConstructeur2() throws Exception {
		Client clientConstructeur2 = new Client("Nom", "Prenom","Adresse",maCategorie, 10);
		
	}
	
	@Test (expected = OperationImpossible.class)
	public void testConstructeur2bis() throws Exception {
		maCategorie.modifierCodeReducActif(false);
		Client clientConstructeur2 = new Client("Nom", "Prenom","Adresse",maCategorie, 10);
		
		assertEquals("Nom",clientConstructeur2.getNom());
		assertEquals("Prenom",clientConstructeur2.getPrenom());
		assertEquals("Adresse",clientConstructeur2.getAdresse());
		assertEquals(maCategorie,clientConstructeur2.getCategorie());
		
	}
	
	@Test
	public void testInitAttr() throws Exception {
		Class[] cArg = new Class[4];
        cArg[0] = String.class;
        cArg[1] = String.class;
        cArg[2] = String.class;
        cArg[3] = CategorieClient.class;
		Method method = monClient.getClass().getDeclaredMethod("initAttr", cArg);
		method.setAccessible(true);
		method.invoke(monClient,new Object[] {"Nom","jkds","qsdj", new CategorieClient("nom")}); // Erreur à regler
	}
	
	
	//pas créer d'instance de client avec parametre null
	@Test(expected = InvocationTargetException.class) // a voir 
	public void testInitAttr2() throws Exception {
		Class[] cArg = new Class[4];
        cArg[0] = String.class;
        cArg[1] = String.class;
        cArg[2] = String.class;
        cArg[3] = CategorieClient.class;
		Method method = monClient.getClass().getDeclaredMethod("initAttr", cArg);
		method.setAccessible(true);
		method.invoke(monClient,new Object[] {null,"jkds","qsdj", new CategorieClient("nom")}); // Erreur à regler
	}
	
	
	@Test
	public void testAdesEmpruntsEnCours() {
		
		assertFalse(monClient.aDesEmpruntsEnCours());
		monClient.getCategorie().modifierMax(50);
		monClient.emprunter();
		assertTrue(monClient.aDesEmpruntsEnCours());
	}
	
	
	// tester nbEmpruntsDepasses >0
	@Test
	public void testPeutEmprunter () throws Exception{
		
		assertFalse(monClient.peutEmprunter());
		monClient.getCategorie().modifierMax(1);
		assertTrue(monClient.peutEmprunter());
		monClient.emprunter();
		assertFalse(monClient.peutEmprunter());
		
	}
	
	//tester la premiere methode avec fiche emprunt en parma
	@Test
	public void testEmpruter() throws Exception{
		monClient.getCategorie().modifierMax(50);
		monClient.emprunter();
		assertEquals(monClient.getNbEmpruntsEnCours(),1);
		assertEquals(monClient.getNbEmpruntsEffectues(),1);
	}
	
	
	@Test
	public void testMarquer() throws Exception {
		monClient.getCategorie().modifierMax(50);
		monClient.emprunter();
		monClient.marquer();
		assertEquals(monClient.getNbEmpruntsEnRetard(),1);
	}
	
	
	@Test (expected = OperationImpossible.class)
	public void testMarquerbis() throws Exception {
		
		monClient.marquer();
	
	}
	
	
	//restituer à faire avec param ficheemprunt à faire
	
	@Test (expected = OperationImpossible.class)
	public void testRestituerbis() throws Exception{
		monClient.restituer(false);
	}
	
	@Test (expected = OperationImpossible.class)
	public void testRestituerbis1() throws Exception{
		monClient.getCategorie().modifierMax(50);
		monClient.emprunter();
		monClient.restituer(true);
	}
	
	
	@Test 
	public void testRestituerbis2() throws Exception{
		monClient.getCategorie().modifierMax(50);
		monClient.emprunter();
		assertEquals(monClient.getNbEmpruntsEnCours(),1);
		monClient.restituer(false);
		assertEquals(monClient.getNbEmpruntsEnCours(),0);
	}
	
	@Test 
	public void testRestituerbis3() throws Exception{
		monClient.getCategorie().modifierMax(50);
		monClient.emprunter();
		
		monClient.marquer();
		assertEquals(monClient.getNbEmpruntsEnRetard(),1);
		monClient.restituer(true);
		assertEquals(monClient.getNbEmpruntsEnRetard(),0);
	}
	
	
	// la methode mettreajourlesEmprunts à tester !!
	
	@Test
  	public void testDateRetour() throws Exception{
  		Date dateEmprunt = new GregorianCalendar(2017, Calendar.OCTOBER, 15).getTime();
 		Date dateExpected = new GregorianCalendar(2017, Calendar.OCTOBER, 20).getTime();
 		monClient.getCategorie().modifierCoefDuree(1.0);
 		Date retour = monClient.dateRetour(dateEmprunt, 5);
 		assertEquals(0,retour.compareTo(dateExpected));
 	}
	
	@Test
	public void testSommeDue() {
		monClient.getCategorie().modifierCoefTarif(2);
		assertEquals((int)monClient.sommeDue(100),200);
	}
	
	
	
	@Test (expected = OperationImpossible.class)
	public void testsetCategorie() throws Exception {
		maCategorie.modifierCodeReducActif(true);
		monClient.setCategorie(maCategorie);
	}
	
	@Test
	public void testsetCategoriebis() throws Exception {
		CategorieClient newCategorie = new CategorieClient("nvcat");
		monClient.setCategorie(newCategorie);
		assertEquals(monClient.getCategorie(),newCategorie);
	}
}