package junit.document;

import static org.junit.Assert.*;

import org.junit.Test;

import mediatheque.Genre;
import mediatheque.Localisation;
import mediatheque.OperationImpossible;
import mediatheque.document.Video;

public class TestVideo {

	Video mavideo;
	
	// test constructeur à terminer
	@Test (expected=OperationImpossible.class)
	public void constructorTest() throws Exception{
		mavideo = new Video ("code",new Localisation ("salle","rayon"),"titre","auteur","annee",new Genre("n"),0,"mentionLegal");
	}
	
	@Test (expected=OperationImpossible.class)
	public void constructorTest2() throws Exception{
		mavideo = new Video ("code",new Localisation ("salle","rayon"),"titre","auteur","annee",new Genre("n"),123,null);
	}
	

}
