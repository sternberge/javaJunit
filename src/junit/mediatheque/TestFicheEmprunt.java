package junit.mediatheque;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import mediatheque.*;
import mediatheque.client.CategorieClient;
import mediatheque.client.Client;
import mediatheque.document.*;
import util.Datutil;


public class TestFicheEmprunt {
	
	FicheEmprunt maFiche;
	
	@Before
	public void setUp() throws Exception {
		CategorieClient catNormale = new CategorieClient("normale", 5, 0.0, 1, 1, false);
		Client monClient = new Client("Pierre", "Jean", "15 rue de la Volee 75016", catNormale);
		Document monAudio = new Audio("456789", new Localisation("38", "4"), "Perles de nuit", "Cynthia", "1998",
				new Genre("French Varieties"), "Opera");
		monAudio.metEmpruntable();
		Mediatheque maMediatheque = new Mediatheque("media");
		maFiche = new FicheEmprunt(maMediatheque, monClient, monAudio);
	}
	
	@Test
	public void constructorTest() throws Exception {
		assertEquals(new Audio("456789", new Localisation("38", "4"), "Perles de nuit", "Cynthia", "1998",
				new Genre("French Varieties"), "Opera"), maFiche.getDocument());
		assertEquals(new Client("Pierre", "Jean", "15 rue de la Volee 75016",
				new CategorieClient("normale", 5, 0.0, 1, 1, false)), maFiche.getClient());
		assertEquals(28 ,maFiche.getDureeEmprunt());
		assertTrue(maFiche.getDocument().estEmprunte());
	}
	
	@Test
	public void premierRappelTest() throws Exception {
		
	}
	
	//Depasse à false mais dateLimite after DateActuelle
	@Test
	public void verifierTest1() throws Exception {
		maFiche.verifier();
		assertFalse(maFiche.getDepasse());
		assertTrue(maFiche.getClient().peutEmprunter());
	}
	
	//Depasse à false et dateLimite before DateActuelle
	@Test
	public void verifierTest2() throws Exception {
		Datutil.addAuJour(29);
		maFiche.verifier();
		assertTrue(maFiche.getDepasse());
		assertFalse(maFiche.getClient().peutEmprunter());
	}
	
	//Depasse à true, mais pas de relance car inférieur à 7 jours, dateRappel inchangée
	@Test
	public void verifierTest3() throws Exception {
		Datutil.addAuJour(29);
		maFiche.verifier();
		Date date1 = maFiche.getLettreRappel().getDateRappel();
		maFiche.verifier();
		Date date2 = maFiche.getLettreRappel().getDateRappel();
		assertEquals(0, date1.compareTo(date2));
	}
	
	//Depasse à true, mais relance car supérieur à 7 jours, dateRappel changée
	@Test
	public void verifierTest4() throws Exception {
		Datutil.addAuJour(29);
		maFiche.verifier();
		Date date1 = maFiche.getLettreRappel().getDateRappel();
		Datutil.addAuJour(7);
		maFiche.verifier();
		Date date2 = maFiche.getLettreRappel().getDateRappel();
		assertNotEquals(0, date1.compareTo(date2));
	}
	
	//Renvoie true car client avec meme nom et prenom et document avec meme code
	@Test
	public void correspondTest() throws Exception{
		Document monAudio = new Audio("456789", new Localisation("38", "4"), "Perles de nuit", "Cynthia", "1998",
				new Genre("French Varieties"), "Opera");
		assertTrue(maFiche.correspond(new Client("Pierre", "Jean", "15 rue de la Volee 75016",
				new CategorieClient("normale", 5, 0.0, 1, 1, false)), monAudio));
	}
	
	//Renvoie false car meme client mais document avec code différent
	@Test
	public void correspondTest2() throws Exception{
		Document monAudio = new Audio("12345", new Localisation("38", "4"), "Perles de nuit", "Cynthia", "1998",
				new Genre("French Varieties"), "Opera");
		assertFalse(maFiche.correspond(new Client("Pierre", "Jean", "15 rue de la Volee 75016",
				new CategorieClient("normale", 5, 0.0, 1, 1, false)), monAudio));
	}
	
	//Renvoie false car client avec nom ou prenom différent meme si document avec meme code
	@Test
	public void correspondTest3() throws Exception{
		Document monAudio = new Audio("12345", new Localisation("38", "4"), "Perles de nuit", "Cynthia", "1998",
				new Genre("French Varieties"), "Opera");
		assertFalse(maFiche.correspond(new Client("Dupont", "Jean", "15 rue de la Volee 75016",
				new CategorieClient("normale", 5, 0.0, 1, 1, false)), monAudio));
	}
	
	//Document anciennement dépassé ne l'est plus car le client a une durée
	//d'emprunt 2 fois plus élevée
	@Test
	public void changementCategorieTest() throws Exception {
		Datutil.addAuJour(29);
		maFiche.verifier();
		assertTrue(maFiche.getDepasse());
		maFiche.getClient().setCategorie(new CategorieClient("normaleAvancee", 5, 20.0, 2, 1, false));
		assertFalse(maFiche.getDepasse());
		
	}
	
}