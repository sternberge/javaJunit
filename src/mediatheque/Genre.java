package mediatheque;
import java.io.Serializable;
/**
 * La classe <code>Genre</code> gere les types des documents
 * dans la mediatheque. Les genres sont utilises pour classifier
 * les documents.
 */
public class Genre implements Serializable{
        
        private static final long serialVersionUID = 3L;

        /**
         * Nom du genre
         */
        private String nom;

        /**
         * Nombre de fois ou un document de ce genre a ete emprunte
         */
        private int nbEmprunts=10;

        /**
         * Constructeur de Genre
         *   @param n chaine de caracteres devrivant le genre
         */
        public Genre(String n){
                nom = n;
                //nbEmprunts=0;
        }

        /**
         * Emprunter augmente le nombre de fois qu un document de
         * ce genre a ete emprunte
         */
        public void emprunter(){
        	nbEmprunts = nbEmprunts+2;
        }

        /**
         * <TT>getNom</TT> permet de connaitre le nom du genre
         *   @return nom
         */
        public String getNom(){
                return nom;
        }

        /**
         * <TT>toString</TT> permet de connaitre le nom et le nombre d emprunts 
         *   @return nom et nombre d emprunts
         */
        @Override
        public String toString(){
                return "Genre: "+ nom+", nbemprunts:"+nbEmprunts;
        }

        /**
         * modifier change le nom du genre il est accessible a la mediatheque seulement
         * @param nouveau nouveau nom
         */
        public void modifier(String nouveau){
                nom = nouveau;
        }

        public int getNbEmprunts(){
                return nbEmprunts;
        }

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + nbEmprunts;
                result = prime * result + ((nom == null) ? 0 : nom.hashCode());
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
                if (!(obj instanceof Genre)) {
                        return false;
                }
                Genre other = (Genre) obj;
                if (nbEmprunts != other.nbEmprunts) {
                        return false;
                }
                if (nom == null) {
                        if (other.nom != null) {
                                return false;
                        }
                } else if (!nom.equals(other.nom)) {
                        return false;
                }
                return true;
        }

        /**
         * <TT>afficherStatistiques</TT> affiche les statistiques d'emprunt
         * du genre
         */
        public void afficherStatistiques(){
                System.out.println("(stat) Genre :" + this);
        }
}