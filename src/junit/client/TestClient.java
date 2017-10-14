package junit.client;

import static org.junit.Assert.*;



import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

	// Constructeurs à tester
	
	@Test//(expected = OperationImpossible.class)
	public void testInitAttr() throws Exception{
		Class[] cArg = new Class[4];
        cArg[0] = String.class;
        cArg[1] = String.class;
        cArg[2] = String.class;
        cArg[3] = CategorieClient.class;
		Method method = monClient.getClass().getDeclaredMethod("initAttr", cArg);
		method.setAccessible(true);
		method.invoke(monClient,new Object[] {"Nom","jkds","qsdj", new CategorieClient("nom")}); // Erreur à regler
	}
	
	
	@Test
	public void testAdesEmpruntsEnCours() {
		
		assertFalse(monClient.aDesEmpruntsEnCours());
		monClient.getCategorie().modifierMax(50);
		monClient.emprunter();
		assertTrue(monClient.aDesEmpruntsEnCours());
	}
	
	@Test
	public void testPeutEmprunter () throws Exception{
		
		assertFalse(monClient.peutEmprunter());
		monClient.getCategorie().modifierMax(50);
		assertTrue(monClient.peutEmprunter());
		
	}
	
	
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
	public void testRestituer() throws Exception{
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
	public void testDateRetour() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d = sdf.parse("25/10/2017");
		Date dateExpected = sdf.parse("20/10/2017");
		monClient.getCategorie().modifierCoefDuree(0);
		int duree = 1;
		Date retour = monClient.dateRetour(d, duree);
		Calendar cal = Calendar.getInstance();
		cal.setTime(retour);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		System.out.println(day);
		assertEquals(0,retour.compareTo(dateExpected)); /// Probleme avec la date à regler
	}
	
	@Test
	public void testSommeDue() {
		monClient.getCategorie().modifierCoefTarif(2);
		assertEquals((int)monClient.sommeDue(100),200);
	}
	
	
	//setcategorie à faire
}
