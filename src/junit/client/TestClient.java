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
	
	@Test//(expected = OperationImpossible.class)
	public void testInitAttr() throws Exception {
		Class[] cArg = new Class[4];
        cArg[0] = String.class;
        cArg[1] = String.class;
        cArg[2] = String.class;
        cArg[3] = CategorieClient.class;
		Method method = monClient.getClass().getDeclaredMethod("initAttr", cArg);
		method.setAccessible(true);
		method.invoke(monClient,new Object[] {"Nom","jkds","qsdj", new CategorieClient("nom")}); // Erreur � regler
	}
	
	
	//pas cr�er d'instance de client avec parametre null
	@Test(expected = InvocationTargetException.class) // a voir 
	public void testInitAttr2() throws Exception {
		Class[] cArg = new Class[4];
        cArg[0] = String.class;
        cArg[1] = String.class;
        cArg[2] = String.class;
        cArg[3] = CategorieClient.class;
		Method method = monClient.getClass().getDeclaredMethod("initAttr", cArg);
		method.setAccessible(true);
		method.invoke(monClient,new Object[] {null,"jkds","qsdj", new CategorieClient("nom")}); // Erreur � regler
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
	
	
	//restituer � faire avec param ficheemprunt � faire
	
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
	
	// Probleme � regler 
	@Test
	public void testDateRetour() throws Exception{
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		//Date d = sdf.parse("19/10/2017");
		Calendar calendar = new GregorianCalendar(2017,10,15);
		Date d =  calendar.getTime();
		Calendar calendar2 = new GregorianCalendar(2017,10,17);
		//Date dateExpected = sdf.parse("20/10/2017");
		Date dateExpected = calendar2.getTime();
		monClient.getCategorie().modifierCoefDuree(1);
		int duree = 2;
		Date retour = monClient.dateRetour(d, duree);
		Calendar cal = Calendar.getInstance();
		cal.setTime(retour);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		System.out.println("jour :"+day+" mois : "+month+" annee : "+year);
		assertEquals(0,retour.compareTo(dateExpected)); /// Probleme avec la date � regler
	}
	
	@Test
	public void testSommeDue() {
		monClient.getCategorie().modifierCoefTarif(2);
		assertEquals((int)monClient.sommeDue(100),200);
	}
	
	
	//setcategorie � faire
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