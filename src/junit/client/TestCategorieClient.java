package junit.client;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import mediatheque.client.CategorieClient;

public class TestCategorieClient {
	
	CategorieClient maCategorie;

	@Before
	public void setUp() throws Exception {
		maCategorie = new CategorieClient("nom");
	}
	
	@Test
	public void testConstructor() {
		maCategorie = new CategorieClient("nom",12,456,45,45,true);
		assertEquals(true,maCategorie.getCodeReducUtilise());
	}
	
	@Test
	public void testConstructor1() {
		maCategorie = new CategorieClient("nom");
		assertEquals(false,maCategorie.getCodeReducUtilise());
	}

	@Test
	public void testModifierNom() {
		String nouveauNom = "nvNom";
		maCategorie.modifierNom(nouveauNom);
		assertEquals(maCategorie.getNom(),nouveauNom);
	}

	
	@Test
	public void testModifierCodeReducActif() {
		boolean var1 = true;
		boolean var2 = false;
		maCategorie.modifierCodeReducActif(var1);
		assertEquals(maCategorie.getCodeReducUtilise(),var1);
		maCategorie.modifierCodeReducActif(var2);
		assertEquals(maCategorie.getCodeReducUtilise(),var2);
	}

}
