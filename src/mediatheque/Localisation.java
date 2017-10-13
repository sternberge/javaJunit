package mediatheque;
import java.io.Serializable;
/**
 * La classe <code>Localisation</code> gere la localisation des documents dans
 * la mediatheque. Il s'agit uniquement du stockage des informations necessaires
 * et non de la gestion des salles et rayonnages.
 */
public class Localisation implements Serializable{

    private static final long serialVersionUID = 3L;

    /**
     * Salle ou ranger le document.
     */
    private String salle;

    /**
     * Rayon ou ranger le document.
     */
    private String rayon;

    /**
     * Constructeur de localisation.
     * 
     * @param salle
     *            Salle ou ranger le document.
     * @param rayon
     *            Rayon ou ranger le document.
     */
    public Localisation(String salle, String rayon) {
        this.salle = salle;
        this.rayon = rayon;
    }
    /**
     * <tt>getSalle</tt> permet de connaitre la salle.
     * 
     * @return la salle.
     */
    public String getSalle() {
        return salle;
    }

    /**
     * <tt>setSalle</tt> permet de modifier la salle.
     * 
     * @param s la salle.
     */
    void setSalle(String s) {
        salle = s;
    }
    /**
     * <tt>getRayon</tt> permet de connaitre le rayon.
     * 
     * @return le rayon.
     */
    public String getRayon() {
        return rayon;
    }
    /**
     * <tt>setRayon</tt> permet de modifier le rayon.
     * 
     * @param r le rayon.
     */
    void setRayon(String r) {
        rayon = r;
    }
    /**
     * <TT>toString</TT> permet de connaitre la salle et le rayon.
     * 
     * @return Salle et rayon.
     */
    @Override
    public String toString() {
        return "Salle/Rayon : " + salle + "/" + rayon;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rayon == null) ? 0 : rayon.hashCode());
        result = prime * result + ((salle == null) ? 0 : salle.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Localisation)) {
            return false;
        }
        Localisation other = (Localisation) obj;
        if (rayon == null) {
            if (other.rayon != null) {
                return false;
            }
        } else if (!rayon.equals(other.rayon)) {
            return false;
        }
        if (salle == null) {
            if (other.salle != null) {
                return false;
            }
        } else if (!salle.equals(other.salle)) {
            return false;
        }
        return true;
    }
}