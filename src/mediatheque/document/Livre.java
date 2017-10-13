package mediatheque.document;
import mediatheque.*;
import util.*;
/**
 * La classe <code>Livre</code> gere les documents de type livre.
 * Ils possedent un nombre de pages, une duree de pret de 6
 * semaines et un tarif de pret de 0.5 Euros.
 */
public final class Livre extends Document {

        private static final long serialVersionUID = 4L;

        /**
         * Nombre de pages, format libre.
         */
        private int nombrePages;

        /**
         * Duree de pret en nombre de jours
         */
        public static final int DUREE = 6*7;

        /**
         * Tarif du pret
         */
        public static final double TARIF = 0.5;

        /**
         * Nombre d'emprunts total de Livre
         */
        private static int nbEmpruntsTotal = 0;

        // Les methodes

        /**
         * Constructeur de Livre avec les attributs valorises. Par
         * defaut, le livre n'est pas empruntable.
         *   @param code Code du document
         *   @param localisation Localisation du document
         *   @param titre Titre du document
         *   @param auteur Auteur du document
         *   @param annee Annee de sortie du document
         *   @param genre Genre du document
         *   @param nombrePage Nombre de pages du livre
         *   @throws Operation impossible si un argument est null
         */
        public Livre(String code, Localisation localisation,
                        String titre, String auteur, String annee, Genre genre,
                        int nombrePage
                        ) throws OperationImpossible, InvariantBroken 
        {
                super(code, localisation, titre, auteur, annee, genre);
                if (nombrePage < 0) {
                        throw new OperationImpossible("Ctr Livre nombrePage = "
                                        + nombrePage);
                }
                this.nombrePages = nombrePage;
                if (!invariantLivre()) {
                        throw new InvariantBroken("Livre invariant");
                }
        }

        /**
         * <TT>getStat</TT> retourne le nombre d'emprunts (statistique) de la
         * classe.
         * @return Nombre d'emprunts total
         */
        public static int getStat() { return nbEmpruntsTotal; }

        /**
         * <TT>emprunter</TT> est appelee lors de l'emprunt d'un document.
         * Les statistiques sont mises a jour.
         * @return true or throws OperationImpossible or InvariantBroken
         */
        @Override
        public boolean emprunter() throws InvariantBroken, OperationImpossible {
                super.emprunter();
                return true;
        }
        // Methodes de l'interface Empruntable

        /**
         * <TT>dureeEmprunt</TT> retourne la duree nominale de pret
         * du document en nombre de jours.
         *    @return Duree de pret
         */
        @Override
        public int dureeEmprunt() { return DUREE; }

        /**
         * <TT>tarifEmprunt</TT> retourne le tarif nominal du pret
         * du document.
         *    @return Tarif du pret
         */
        @Override
        public double tarifEmprunt() { return TARIF; }
        /**
         *<TT>toString</TT> affiche les caracteristiques du Livre
         *  @return Caracteristiques d'un livre
         */
        @Override
        public String toString() {
                return "[Livre] " + super.toString()
                                +  " " + nombrePages;
        }

        /**
         * Safety property - nombre de pages must be positive
         * @return if document in safe state (i.e. nombrePage>0)
         */
        public boolean invariantLivre() {
                return nombrePages > 0 && super.invariant();
        }
}