package mediatheque.document;
import mediatheque.*;
import util.InvariantBroken;

/**
 * La classe Video gere les documents de type DVD video.
 * Elles possedent une duree de film, une mention legale
 * de diffusion, une duree de pret de 2 semaines et un
 * tarif de pret de 1.5 Euros.
 */
public final class Video extends Document {

        private static final long serialVersionUID = 4L;
        /**
         * Duree du film,  en minutes.
         */
        private int dureeFilm;

        /**
         * Mention legale de diffusion du film, format libre.
         */
        private String mentionLegale;

        /**
         * Duree de pret en nombre de jours.
         */
        public static final int DUREE = 2*7;

        /**
         * Tarif du pret
         */
        public final static double TARIF = 1.5;

        /**
         * Nombre d'emprunts total des films.
         */
        private static int nbEmpruntsTotal = 0;

        // Les methodes

        /**
         * Constructeur de Video avec les attributs valorises. 
         * Par defaut, le document n'est pas empruntable.
         *   @param code Code du document
         *   @param localisation Localisation du document
         *   @param titre Titre du document
         *   @param auteur Auteur du document
         *   @param annee Annee de sortie du document
         *   @param genre Genre du document
         *   @param dureeFilm Duree du film (format libre)
         *   @param mentionLegale Mention legale du film
         *   @throws Operation impossible si un argument est mal initialise
         */
        public Video(String code, Localisation localisation, String titre,
                        String auteur, String annee, Genre genre, int dureeFilm,
                        String mentionLegale) throws OperationImpossible, InvariantBroken{
                super(code, localisation, titre, auteur, annee, genre);
                if (dureeFilm == 0 || mentionLegale == null) {
                        throw new OperationImpossible("Ctr Video dureeFile = " + dureeFilm
                                        + " mentionLegale = " + mentionLegale);
                }
                this.dureeFilm = dureeFilm;
                this.mentionLegale = mentionLegale;
                if (!invariantVideo()) {
                        throw new InvariantBroken("Video -" + this);
                }
        }

        /**
         * <TT>getStat</TT> retourne le nombre d'emprunts (statistique) de la
         * classe.
         * @return Nombre d'emprunts total
         */
        public static int getStat() {
                return nbEmpruntsTotal;
        }

        /**
         * <TT>emprunter</TT> est appelee lors de l'emprunt d'un document.
         * Les statistiques sont mises a jour.
         */
        @Override
        public boolean emprunter() throws InvariantBroken, OperationImpossible {
                super.emprunter();
                nbEmpruntsTotal++;
                System.out.println("\tAttention: " + mentionLegale);
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
        
        public String getMentionLegale() { return mentionLegale; }
        
        public int getDureeFilm() { return dureeFilm; }
        
        /**
         *<TT>toString</TT> affiche les caracteristiques de la video
         * @return Caracteristiques d'une video
         */
        @Override
        public String toString() {
                return "[Video] " + super.toString()
                                + " " + dureeFilm + " " + mentionLegale;
        }

        /** Safety property - film length must be at least 1 minute
         *  @return if document in safe state(i.e.dureeFilm > 0)
         */
        public boolean invariantVideo() {
                return dureeFilm > 0 && super.invariant();
        }
}