package mediatheque;
/**
 * Une exception <TT>OperationImpossible</TT> est levee
 * en cas d'erreur. Par exemple,
 * <UL>
 * <LI>client deja inscrit
 * <LI>nombre maximal de documents atteint pour un client</LI>
 * <LI>document(s) non restitue(s) dans les delais</LI>
 * <LI>document inexistant</LI>
 * <LI>document non empruntable</LI>
 * <LI>...</LI>
 * </UL>
 */
public class OperationImpossible extends Exception {

  /**
         * 
         */
        private static final long serialVersionUID = 879987735426640041L;

/**
   * Constructeur de l'exception OperationImpossible.
   *   @param message Message d'erreur
   */
  public OperationImpossible(String message) {
    super(message);
  }
}