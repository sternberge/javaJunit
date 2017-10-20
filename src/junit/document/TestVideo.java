package junit.document;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import mediatheque.Genre;
import mediatheque.Localisation;
import mediatheque.OperationImpossible;
import mediatheque.document.Livre;
import mediatheque.document.Video;

public class TestVideo {

	Video maVideo, impossibleVideo;
	
	@Before
	public void setUp() throws Exception {
		maVideo = new Video("658394", new Localisation("28", "3"), "Folies meurtrières", "Bonnot.J",
				"1973", new Genre("Thriller"), 128, "Ne pas recopier");
	}
	
	@Test
	public void constructorTest() throws Exception {
		assertEquals("658394", maVideo.getCode());
		assertEquals(new Localisation("28", "3"), maVideo.getLocalisation());
		assertEquals("Folies meurtrières", maVideo.getTitre());
		assertEquals("Bonnot.J", maVideo.getAuteur());
		assertEquals("1973", maVideo.getAnnee());
		assertEquals(new Genre("Thriller"), maVideo.getGenre());
		assertEquals(false, maVideo.estEmpruntable());
		assertEquals(false, maVideo.estEmprunte());
		assertEquals(0, maVideo.getNbEmprunts());
		assertEquals(128, maVideo.getDureeFilm());
		assertEquals("Ne pas recopier", maVideo.getMentionLegale());
	}
	
	@Test(expected=OperationImpossible.class)
	public void ConstructorExceptionTest() throws Exception {
		impossibleVideo = new Video("658394", new Localisation("28", "3"), "Folies meurtrières", "Bonnot.J",
				"1973", new Genre("Thriller"), 0, "Ne pas recopier");
	}
	
	@Test(expected=OperationImpossible.class)
	public void ConstructorExceptionTest2() throws Exception {
		impossibleVideo = new Video("658394", new Localisation("28", "3"), "Folies meurtrières", "Bonnot.J",
				"1973", new Genre("Thriller"), 128, null);
	}
	
	//Peut pas ajouter une video avec une duree negative
	@Test(expected=OperationImpossible.class)
	public void ConstructorExceptionTest3() throws Exception {
		impossibleVideo = new Video("658394", new Localisation("28", "3"), "Folies meurtrières", "Bonnot.J",
				"1973", new Genre("Thriller"), -10, "Ne pas recopier");
	}
	

}
