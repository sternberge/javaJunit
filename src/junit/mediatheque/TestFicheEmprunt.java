package junit.mediatheque;

import static org.junit.Assert.*;



import org.junit.Before;
import org.junit.Test;

import mediatheque.*;
import mediatheque.Mediatheque;
import mediatheque.client.Client;
import mediatheque.document.*;


public class TestFicheEmprunt {

	FicheEmprunt maFiche;
	Client monClient;
	Mediatheque maMediatheque;
	Document monDoc;

	@Before
	public void setUp() throws Exception {
		monClient = new Client("Nom", "Prenom");
		maMediatheque = new Mediatheque("NomMedia");
		monDoc = (Document)new Audio("Code", new Localisation("salle", "rayon"), "Titre", "Auteur", "annee", new Genre("Nom"),
				"Classification");
		
		maFiche = new FicheEmprunt(maMediatheque,monClient, monDoc);
		
		
	}
	
	
	@Test
	 public void testConstructeur() throws Exception{
	 		//maFiche = new FicheEmprunt(maMediatheque,monClient, monDoc);
	 		assertEquals(maFiche.getDepasse(),false);
	  	}

}
