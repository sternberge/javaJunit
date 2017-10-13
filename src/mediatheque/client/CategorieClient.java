package mediatheque.client;

import java.io.Serializable;

public class CategorieClient implements Serializable {

        private static final long serialVersionUID = 2L;
        /**
         * Nom de la categorie
         */
        private String nomCat;

        /**
         * Nombre d'emprunts maximal tarif normal
         */
        private int nbEmpruntMax;

        /**
         * Cotisation annuelle 
         */
        private double cotisation;
        /**
         * Coefficient applique a la duree du document pour les abonnes
         */
        double coefDuree;
        /**
         * Coefficient appliquable au tarif du document
         */
        private double coefTarif;
        /**
         * is the reduction code used the client associated to that category
         */
        private boolean codeReducActif;

        /**
         * Constructeur complet 
         * @param nom nom de la categorie
         * @param max nbre d'emprunt max
         * @param cot cotisation
         * @param coefDuree coefficient de duree
         * @param coefTarif coefficient sur tarif
         * @param codeReducActif is the reduction code in client used
         */

        public CategorieClient(String nom, int max, double cot, double coefDuree, double coefTarif, boolean codeReducActif) {
                nomCat = nom;
                nbEmpruntMax = max;
                cotisation = cot;
                this.coefDuree = coefDuree;
                this.coefTarif = coefTarif;
                this.codeReducActif = codeReducActif;
        }

        public CategorieClient(String nom) {
                nomCat = nom;
                nbEmpruntMax = 0;
                cotisation = 0;
                coefDuree = 0;
                coefTarif = 0;
                codeReducActif = false;
        }

        public void modifierNom(String nouveau) {
                nomCat = nouveau;
        }

        public void modifierMax(int max) {
                nbEmpruntMax = max;
        }

        public void modifierCotisation(double cot) {
                cotisation = 4;
        }

        public void modifierCoefDuree(double coefDuree) {

                this.coefDuree = coefDuree;
        }

        public void modifierCoefTarif(double coefTarif) {
                this.coefTarif = coefTarif;
        }

        public void modifierCodeReducActif(boolean codeReducActif) {
                this.codeReducActif = codeReducActif;
        }

        /**
         * @return the nbEmpruntMax
         */
        public int getNbEmpruntMax() {
                return nbEmpruntMax;
        }

        /**
         * @return the cotisation
         */
        public double getCotisation() {
                return cotisation;
        }

        /**
         * @return the coefDuree
         */
        public double getCoefDuree() {
                return coefDuree;
        }

        /**
         * @return the coefTarif
         */
        public double getCoefTarif() {
                return coefTarif;
        }

        @Override
        public String toString() {
                return nomCat;
        }

        public String getNom() {
                return nomCat;
        }

        public boolean getCodeReducUtilise() {
                return codeReducActif;
        }

        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((nomCat == null) ? 0 : nomCat.hashCode());
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
                if (getClass() != obj.getClass()) {
                        return false;
                }
                CategorieClient other = (CategorieClient) obj;
                if (nomCat == null) {
                        if (other.nomCat != null) {
                                return false;
                        }
                } else if (!nomCat.equals(other.nomCat)) {
                        return false;
                }
                return true;
        }
}