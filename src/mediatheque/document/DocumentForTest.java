package mediatheque.document;

import mediatheque.Genre;
import mediatheque.Localisation;
import mediatheque.OperationImpossible;

public class DocumentForTest extends Document {
	
	private static final long serialVersionUID = 4L;

	public DocumentForTest(String code, Localisation localisation, String titre, String auteur, String annee,
			Genre genre) throws OperationImpossible {
		super(code, localisation, titre, auteur, annee, genre);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int dureeEmprunt() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double tarifEmprunt() {
		// TODO Auto-generated method stub
		return 0;
	}

}
