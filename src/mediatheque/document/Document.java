package mediatheque.document;
import mediatheque.*;
import java.io.Serializable;
import util.*;

/**
 * La classe <code>Document</code> gere les documents de la mediatheque.
 */
public abstract class Document implements Empruntable, Serializable, HasInvariant {

        private static final long serialVersionUID = 3L;

        /**
         * Code du document, unique dans la mediatheque, format libre
         */
        private String code;

        /**
         * Titre du document, format libre
         */
        private String titre;

        /**
         * Auteur du document, format libre
         */
        private String auteur;

        /**
         * Annee de sortie du document, format libre
         */
        private String annee;

        /**
         * Genre du document
         */
        private Genre genre;

        /**
         * Indique si un document est empruntable. Il s'agit d'un
         * attribut et non d'un type afin de pouvoir le modifier.
         */
        private boolean empruntable;

        /**
         * Indique si le document a ete emprunte
         */
        private boolean emprunte;

        // Informations statistiques

        /**
         * Nombre d'emprunts du document
         */
        private int nbEmprunts;

        /**
         * Localisation du document
         */
        private Localisation localisation;

        // Les methodes

        /**
         * Constructeur de document avec les attributs valorises. Par
         * defaut, le document n'est pas empruntable.
         *   @param code Code du document
         *   @param localisation Localisation du document
         *   @param titre Titre du document
         *   @param auteur Auteur du document
         *   @param annee Annee de sortie du document
         *   @param genre Genre du document
         *   @throws OperationImpossible si certains arguments sont mal initialises
         */
        public Document(String code, Localisation localisation, 
                        String titre, String auteur, String annee, Genre genre) 
                                        throws OperationImpossible {
                if( code == null || localisation == null || titre == null 
                                || auteur == null || annee == null || genre == null) {
                        throw new OperationImpossible("Ctr Document arguments = " 
                                        + "code : " + code + ", localisation : " + localisation 
                                        + ", titre : " + titre + ", auteur : " + auteur 
                                        + ", annee : " + annee + ", genre : " + genre);
                }
                this.code = code;
                this.localisation = localisation;
                this.titre = titre;
                this.auteur = auteur;
                this.annee = annee;
                this.genre = genre;
                this.empruntable = false;
                this.emprunte = false;
                nbEmprunts = 0;
        }

        /**
         * <TT>getCode</TT> retourne le code du document.
         * @return Code du document
         */
        public String getCode() { return code; }

        /**
         * <TT>getTitre</TT> retourne le titre du document.
         *   @return Titre du document
         */
        public String getTitre() { return titre; }

        /**
         * <TT>getAuteur</TT> retourne l'auteur du document.
         *   @return Auteur du document
         */
        public String getAuteur() { return auteur; }

        /**
         * <TT>getLocalisation</TT> retourne la localisation du document.
         *   @return Localisation du document
         */
        public Localisation getLocalisation() { return localisation; }

        /**
         * <TT>getAnnee</TT> retourne l'annee du document.
         *   @return Annee du document
         */
        public String getAnnee() {  return annee; }

        /**
         * <TT>getGenre</TT> retourne le genre du document.
         *   @return Genre du document
         */
        public Genre getGenre() { return genre; }

        /**
         * <TT>getNbEmprunts</TT> retourne le nombre d'emprunts du document.
         *   @return nb emprunts du document
         */
        public int getNbEmprunts() { return nbEmprunts; }


        /**
         * <TT>equals</TT> est une surcharge de <TT>Object.equals</TT>
         * permettant de tester l'egalite de deux documents. Il y a egalite
         * quand les documents ont meme code.
         *   @param obj Operande droit
         *   @return true si les objets ont le meme code
         */
        @Override
        public boolean equals(Object obj) {
                if (obj == null) return false;
                if (this == obj) return true;
                if(!(obj instanceof Document)) return false;
                Document d = (Document) obj;
                return (code.equals(d.code));
        }

        /**
         * Rewrite hashCode 
         * @return int to facilitate hash
         */
        @Override
        public int hashCode() {
                final int prime = 31;
                int result = 1;
                result = prime * result + ((code == null) ? 0 : code.hashCode());
                return result;
        }

        /**
         * Conversion en chaine de caracteres pour l'affichage.
         *  @return Document converti en chaine de caracteres
         */
        @Override
        public String toString() {
                String s = "\"" + code + "\" " + titre + " " + auteur + " " + annee
                                + " " + genre + " " + localisation + " " + nbEmprunts;
                if (empruntable) {
                        s += " (emp ";
                        if (emprunte) {
                                s += "O";
                        } else {
                                s += "N";
                        }
                        s += ")";
                }
                if (invariant()) {
                        s += " SAFE ";
                } else {
                        s += " UNSAFE ";
                }
                return s;
        }

        /**
         * Autorise l'emprunt du document.
         */
        public void metEmpruntable() throws OperationImpossible, InvariantBroken {
                if (empruntable) {
                        throw new OperationImpossible("Document metEmpruntable empruntable" + this);
                }
                empruntable = false;
                if (!invariant()) {
                        throw new InvariantBroken("Document -" + this);
                }
        }

        /**
         * Interdit l'emprunt du document.
         */
        public void metConsultable() throws OperationImpossible, InvariantBroken {
                if (!empruntable) {
                        throw new OperationImpossible("Document metConsultable consultable" + this);
                }
                if (emprunte) {
                        throw new OperationImpossible("Document metConsultable emprunte" + this);
                }
                empruntable = false;
                if (!invariant()) {
                        throw new InvariantBroken("Document -" + this);
                }
        }

        /**
         * Retourne vrai si le document est empruntable.
         */
        public boolean estEmpruntable() { return empruntable; }

        // Operations du DME
        /**
         * <TT>emprunter</TT> est appelee lors de l'emprunt d'un document.
         * Les statistiques sont mises a jour.
         */
        public boolean emprunter() throws InvariantBroken, OperationImpossible{
                if (!empruntable) {
                        throw new OperationImpossible("Document non empruntable" + this);
                }
                if (emprunte) {
                        throw new OperationImpossible("Deja Emprunte"+ this);
                }
                emprunte = true;
                genre.emprunter();
                nbEmprunts++;
                // and check after
                if (!invariant()) {
                        throw new InvariantBroken("Document -" + this);
                }
                return true;
        }

        /**
         * Retourne vrai si le document est emprunte.
         */
        public boolean estEmprunte() { return emprunte; }

        /**
         * <TT>restituer</TT> est appelee lors de la restitution d'un
         * document. La localisation ou ranger le document est affichee.
         */
        public void restituer() throws InvariantBroken, OperationImpossible {
                if(!empruntable){
                        throw new OperationImpossible("Impossible de restituer un document non empruntable");
                }
                if(!emprunte){
                        throw new OperationImpossible("Impossible de restituer un document non emprunte");
                }
                emprunte = false;
                // check invariant after modifying internal state
                if (!invariant()) {
                        throw new InvariantBroken("Document -" + this);
                }
                System.out.println("Document: ranger \"" + titre + "\" en "
                                + localisation);
        }

        /**
         * <TT>afficherStatDocument</TT> affiche les statistiques d'emprunt
         * du document.
         */
        public void afficherStatDocument() {
                System.out.println("(stat) Nombre d'emprunts du document \"" + titre
                                + "\" de \"" + auteur + "\" (" + code + ") = " + nbEmprunts);
        }

        /**
         * <TT>afficherStatistiques</TT> affiche les statistiques d'emprunt
         * des documents pour les sous classes.
         */
        public static void afficherStatistiques() {
                System.out.println("Audio :"+ Audio.getStat());
                System.out.println("Video :"+ Video.getStat());
                System.out.println("Livre :"+ Livre.getStat());
        }

        /**
         * Safety property - emprunte => empruntable
         * 
         * @return if the document is in a safe state, i.e respects the invariant
         */
        public boolean invariant() {
                return !(emprunte && !empruntable);
        }
}