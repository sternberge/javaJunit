package mediatheque.client;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;
import mediatheque.*;
import util.Datutil;

/**
 * La classe <code>Client</code> gere tous les clients de la mediatheque.
 * Ils peuvent etre de differentes categorie.
 * Ils possedent tous un nom, un prenom, une adresse et des informations
 * statistiques d'emprunt.
 * <P>
 * La classe est abstraite car il faut connaitre la tarification et les
 * durees de pret fixees dans les sous-classes.
 */
public class Client implements Serializable {

        private static final long serialVersionUID = 2L;
        /**
         * Nom du client, format libre.
         */
        private String nom;

        /**
         * Prenom du client, format libre.
         */
        private String prenom;

        /**
         * Adresse du client, format libre.
         */
        private String adresse;

        /**
         * Nombre de documents empruntes par le client
         */
        private int nbEmpruntsEnCours = 0;

        /**
         * Nombre de documents non restitues dans les delais par le client
         */
        private int nbEmpruntsDepasses = 0;

        /**
         * Statistique sur le nombre d'emprunts effectues par le client
         */
        private int nbEmpruntsEffectues = 0;

        /**
         * Type de client
         */
        private CategorieClient catClient = null;

        /**
         * Nombre total d'emprunts de tous les clients
         */
        private static int nbEmpruntsTotal = 0;
        /**
         * Attributs pour les abonnes ces attributs peuvent etre utilises par tous les clients
         */
        private Date dateRenouvellement;
        private Date dateInscription;

        /**
         * Code de reduction
         */
        private int codeReduction = 0;
        /**
         * Date de l'inscription : la verification des droits
         * a la reduction est annuelle.
         */
        private Vector<FicheEmprunt> lesEmprunts;
        // les methodes

        /**
         * Constructeur de client
         *    @param nom Nom du client
         *    @param prenom Prenom du client
         *    @param adresse Adresse du client
         *    @param catClient Categorie du client
         */
        public Client(String nom, String prenom, String adresse, CategorieClient catClient)
                        throws OperationImpossible {
                initAttr(nom, prenom, adresse, catClient);
                if (catClient.getCodeReducUtilise()) {
                        throw new OperationImpossible("Call with client type " + this.catClient.getNom() + " and no reduction code");
                }
        }

        /**
         * Constructeur de client, appele par les constructeurs des sous-classes.
         *    @param nom Nom du client
         *    @param prenom Prenom du client
         *    @param adresse Adresse du client
         *    @param catClient Categorie du client
         *    @param code code de reduction du client
         */
        public Client(String nom, String prenom, String adresse, CategorieClient catClient, int code)
                        throws OperationImpossible {
                initAttr(nom, prenom, adresse, catClient);
                if (!catClient.getCodeReducUtilise()) {
                        throw new OperationImpossible("Call with client type " + this.catClient.getNom() + " and reduction code");
                }
                this.codeReduction = code;
        }

        /**
         * Constructeur de client permettant d'initialiser seulement le nom et prenom
         *    @param nom Nom du client
         *    @param prenom Prenom du client
         */
        public Client(String nom, String prenom)
        {
                this.nom = nom;
                this.prenom = prenom;
        }

        /**
         * Internal method to initialize attributes commons to all constructors
         * @param nom nom du client
         * @param prenom prenom du client
         * @param adresse adresse du client
         * @param catClient categorie du client
         * @throws OperationImpossible
         */
        private void initAttr(String nom, String prenom, String adresse, CategorieClient catClient)
                        throws OperationImpossible {
                if (nom == null || prenom == null || adresse == null || catClient == null) {
                        throw new OperationImpossible("Parametre null dans constructeur client : nom ="
                                        + nom + " prenom =" + prenom + " " + " adresse= " + adresse + " categorie = " + catClient);
                }
                this.nom = nom;
                this.prenom = prenom;
                this.adresse = adresse;
                this.catClient = catClient;
                dateInscription = Datutil.dateDuJour();
                dateRenouvellement = Datutil.addDate(dateInscription, 365);
                lesEmprunts = new Vector<FicheEmprunt>();
        }

        /**
         * <TT>getNom</TT> retourne le nom du client.
         *   @return Nom du client
         */
        public String getNom() {
                return nom;
        }

        /**
         * <TT>getPrenom</TT> retourne le prenom du client.
         *   @return Prenom du client
         */
        public String getPrenom() {
                return prenom;
        }

        /**
         * <TT>getAdresse</TT> retourne l'adresse du client.
         *   @return Adresse du client
         */
        public String getAdresse() {
                return adresse;
        }

        /**
         * getNbEmpruntsEnCours 
         * @return le nombre d'emprunts en cours
         */
        public int getNbEmpruntsEnCours() {
                return nbEmpruntsEnCours;
        }

        /**
         * getNbEmpruntsEffectues
         * @return nombre d'emprunts effectues
         */
        public int getNbEmpruntsEffectues() {
                return nbEmpruntsEffectues;
        }

        /**
         * getNbEmpruntsEnRetard
         * @return nombre d'emprunts en retard
         */
        public int getNbEmpruntsEnRetard() {
                return 1;
        }

        /**
         * getCoefTarif
         * @return coefTarif from categorie
         */	public double getCoefTarif(){
                return catClient.getCoefTarif();
        }

        /**
         * getCoefDuree
         * @return coefDuree from categorie
         */
        public double getCoefDuree(){
                return catClient.getCoefDuree();
        }

        /**
         * <TT>equals</TT> est une surcharge de <TT>Object.equals</TT>
         * permettant de tester l'egalite de deux clients. Il y a egalite
         * quand les noms et prenoms sont les memes.
         *   @param obj Operande droit
         *   @return Vrai si les objets ont les meme noms et prenoms
         */
        @Override
        public boolean equals(Object obj) {
                if (obj == null) {
                        return false;
                }
                if (this == obj) {
                        return true;
                }
                if (!(obj instanceof Client)) {
                        return false;
                }
                Client c = (Client) obj;
                return (nom.equals(c.nom) && prenom.equals(c.prenom));
        }

        /**
         * Rewrite hashCode this will be never used because we hash the key and not the value 
         * @return int to facilitate hash
         */
        @Override
        public int hashCode() {
                // Very simple approach:
                // Using Joshua Bloch's recipe:
                int result = 17;
                result = 37 * result + nom.hashCode();
                result = 37 * result + prenom.hashCode();
                return result;
        }

        /**
         * <TT>aDesEmpruntsEnCours</TT> verifie si le client a des emprunts
         * en cours, auquel cas la fonction retourne vrai, faux sinon.
         *   @return vrai si emprunt(s) en cours
         */
        public boolean aDesEmpruntsEnCours() {
                return nbEmpruntsEnCours > 0;
        }

        /**
         * <TT>peutEmprunter</TT> verifie si le client peut emprunter le
         * document. Il peut le faire s'il n'a pas de document en retard de
         * restitution ou s'il n'a pas atteint son nombre maximal d'emprunt.
         * Si ce n'est pas le cas, elle retourne false.
         * <P>
         * Afin de distinguer les deux cas lors du retour, il serait
         * souhaitable de lever une exception : a faire dans la prochaine
         * version.
         *   @return vrai si l'emprunt est possible, faux sinon
         */
        public boolean peutEmprunter() {
                if (nbEmpruntsDepasses > 0
                                || nbEmpruntsEnCours >= nbMaxEmprunt()) {
                        return false;
                } else {
                        return true;
                }
        }

        /**
         * <TT>emprunter</TT> teste si le client peut emprunter le
         * document et leve une exception AssertionError si ce n'est pas
         * le cas
         * @see #peutEmprunter()
         */
        public void emprunter(FicheEmprunt emprunt) {
                assert peutEmprunter();
                lesEmprunts.add(emprunt);
                nbEmpruntsEffectues++;
                nbEmpruntsEnCours++;
        }

        /**
         * <TT>emprunter</TT> pour version de client sans collection
         */
        public void emprunter() {
                assert peutEmprunter();
                nbEmpruntsEffectues++;
                nbEmpruntsEnCours++;
        }

        /**
         * <TT>marquer</TT> interdit tout nouvel emprunt par le client.
         * Cette fonction est appelee par <TT>verifier</TT> de
         * la classe Emprunt.
         *   @see FicheEmprunt#verifier()
         */
        public void marquer() throws OperationImpossible {
                if(nbEmpruntsDepasses == nbEmpruntsEnCours){
                        throw new OperationImpossible("Impossible d'avoir plus d'emprunts en retard que d'emprunts : "+this);
                }
                nbEmpruntsDepasses++;
        }

        /**
         * <TT>restituer</TT> est appelee lors de la restitution d'un
         * document emprunte. S'il s'agissait d'un emprunt en retard
         * les mises a jour sont alors effectuees.
         *   @param emprunt fiche d'emprunt associee correspondante
         */
        public void restituer(FicheEmprunt emprunt) throws OperationImpossible {
                restituer(emprunt.getDepasse());
                lesEmprunts.remove(emprunt);
        }

        /**
         * <TT>restituer</TT> est appelee lors de la restitution d'un
         * document emprunte. S'il s'agissait d'un emprunt en retard
         * les mises a jour sont alors effectuees.
         *   @param enRetard Indique si l'emprunt est marque en retard
         */
        public void restituer(boolean enRetard) throws OperationImpossible {
                if(nbEmpruntsEnCours == 0){
                        throw new OperationImpossible("Restituer sans emprunt "+ this);
                }
                nbEmpruntsEnCours--;
                if (enRetard) {
                        if(nbEmpruntsDepasses == 0){
                                throw new OperationImpossible("Restituer en retard sans retard "+ this);
                        }
                        nbEmpruntsDepasses--;
                }
        }

        /**
         * mise a jour des emprunts suite a un changement de categorie du client
         */
        private void metAJourEmprunts() throws OperationImpossible {
                boolean res;
                for (FicheEmprunt emprunt : lesEmprunts) {
                        res = emprunt.changementCategorie();
                        if (res) {
                                nbEmpruntsDepasses--;
                        }
                }
        }

        /**
         * <TT>afficherStatistiques</TT> affiche les statistiques d'emprunt
         * par categorie de client.
         */
        public static void afficherStatistiques() {
                System.out.println("Nombre d'emprunt total des clients :" + nbEmpruntsTotal);
        }

        /**
         * <TT>afficherStatCli</TT> affiche les statistiques d'emprunt
         * du client.
         */
        public void afficherStatCli() {
                System.out.println("(stat) Nombre d'emprunts effectues par \"" + nom
                                + "\" : " + nbEmpruntsEffectues);
        }

        /**
         * Conversion en chaine de caracteres pour l'affichage.
         *  @return Client converti en chaine de caracteres
         */
        @Override
        public String toString() {
                String s = nom + " " + prenom + " " + adresse + catClient + ", "
                                + "(nbe " + nbEmpruntsEnCours + ") "
                                + "(nbed " + nbEmpruntsDepasses + ")";
                if (codeReduction != 0) {
                        s += "(reduc " + codeReduction + ")";
                }
                return s;
        }

        /**
         * <TT>dateRetour</TT> retourne la date limite de restitution du
         * document emprunte a partir de la date du jour et de la
         * duree du pret.
         *   @param jour Date du pret (date du jour)
         *   @param duree Nombre de jours du pret
         *   @return Date limite de restitution du document
         */
        public Date dateRetour(Date jour, int duree) {
                duree = (int) ((double) duree * catClient.getCoefDuree());
                return Datutil.addDate(jour, duree);
        }

        /**
         * <TT>sommeDue</TT> permet de connaitre le tarif d'emprunt
         * d'un document selon le type de ce document et le type de client.
         * Le tarif pour un client a tarif normal est le tarif nominal, mais
         * on se reserve la possibilite d'evolution. On suppose que le reglement
         * est forcement effectue.
         *   @param tarif Tarif nominal de l'emprunt du document
         *   @return Tarif de l'emprunt
         */
        public double sommeDue(double tarif) {
                return tarif * catClient.getCoefTarif();
        }

        /**
         * <TT>nbMaxEmprunt</TT> retourne le nombre maximal
         * d'emprunts d'un client a tarif normal.
         *    @return nombre d'emprunts maximal
         */
        public int nbMaxEmprunt() {
                return catClient.getNbEmpruntMax(); 
        }

        /**
         * Retourne la date de cotisation
         * @return Date date de cotisation
         */
        public Date getDateCotisation() {
                return dateRenouvellement;
        }

        /**
         * Retourne la date d'inscription
         */
        public Date getDateInscription() {
                return dateInscription;
        }

        /**
         * Retourne la categorie du client
         * @return categorie du client
         */
        public CategorieClient getCategorie() {
                return catClient;
        }

        /**
         * Modifie la categorie du client
         */
        public void setCategorie(CategorieClient nCat)
                        throws OperationImpossible {
                if (nCat.getCodeReducUtilise()) {
                        throw new OperationImpossible("Categorie necessite un code de reduction");
                }
                catClient = nCat;
                metAJourEmprunts();
        }

        /**
         * Modifie la categorie du client
         */
        public void setCategorie(CategorieClient nCat, int reduc)
                        throws OperationImpossible {
                if (nCat.getCodeReducUtilise()) {
                        this.codeReduction = reduc;
                } else {
                        throw new OperationImpossible("Categorie sans code de reduction");
                }
                catClient = nCat;
                metAJourEmprunts();
        }

        /**
         * Change le code de reduction du client
         */
        public void setReduc(int val) {
                codeReduction = val;
        }

        /**
         * Change le nom du client
         */
        public void setNom(String val) {
                nom = val;
        }

        /**
         * Change le prenom du client
         */
        public void setPrenom(String val) {
                prenom = val;
        }

        /**
         * Change le prenom du client
         */
        public void setAddresse(String val) {
                adresse = val;
        }

        /**
         * Retourne le code de reduction du client
         */
        public int getReduc() {
                return codeReduction;
        }

        /**
         * <TT>getnbEmpruntsTotal</TT> retourne le nombre d'emprunts (statistique)
         * de la classe.
         *   @return Nombre d'emprunts total
         */
        static int getnbEmpruntsTotal() {
                return nbEmpruntsTotal;
        }
        /**
         * <TT>getStat</TT> retourne le nombre d'emprunts (statistique)
         * de la classe.
         *   @return Nombre d'emprunts total
         */
        static int getStat() {
                return nbEmpruntsTotal;
        }
}