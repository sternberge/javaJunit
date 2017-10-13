package mediatheque;

import java.io.Serializable;
import java.util.Date;

import mediatheque.client.*;
import mediatheque.document.*;
import util.Datutil;
import util.InvariantBroken;

/**
 * La classe FicheEmprunt gere les fiches d'emprunts de la mediatheque. Il y a
 * une fiche par document emprunte et par client emprunteur.
 */
public class FicheEmprunt implements Serializable {

        private static final long serialVersionUID = 2L;
        // Associations
        /**
         * Mediatheque
         */
        private Mediatheque mediatheque;
        /**
         * Emprunteur.
         */
        private Client client;
        /**
         * Document emprunte.
         */
        private Document document;
        /**
         * Lettre de rappel si emprunt depasse
         */
        private LettreRappel rappel;
        // Attributs
        /**
         * Date de l'emprunt.
         */
        private Date dateEmprunt;
        /**
         * Date limite de restitution.
         */
        private Date dateLimite;
        /**
         * Indicateur d'emprunt depasse.
         */
        private boolean depasse;

        /**
         * Nombre d'emprunts total de documents de la mediatheque.
         */
        private static int nbEmpruntsTotal = 0;

        // Les methodes
        /**
         * Constructeur
         */
        public FicheEmprunt(Mediatheque m, Client c, Document d)
                        throws OperationImpossible, InvariantBroken{
                mediatheque = m;
                client = c;
                document = d;
                dateEmprunt = Datutil.dateDuJour();
                int duree = document.dureeEmprunt();
                dateLimite = client.dateRetour(dateEmprunt, duree);
                depasse = false;
                document.emprunter();
                client.emprunter(this);
                nbEmpruntsTotal++;
                System.out.println("\tTarif = " + getTarifEmprunt() + " euros");
        }

        /**
         * <TT>verifier</TT> teste si une lettre a ete deja envoyee. 
         * Dans ce cas, on relance un rappel
         * sinon on teste si la date est maintenant depassee,
         * si oui on envoie un premier rappel
         */
        public void verifier() throws OperationImpossible {
                if (depasse) {
                        relancer();
                } else {
                        Date dateActuelle = Datutil.dateDuJour();
                        if (dateLimite.before(dateActuelle)) {
                                premierRappel();
                        }
                }
        }

        /**
         * <TT>premierRappel</TT> 
         * Le client est marque ; la lettre de rappel est envoyee
         */
        private void premierRappel() throws OperationImpossible {
                depasse = true;
                client.marquer();
                rappel = new LettreRappel(mediatheque.getNom(), this);
        }

        /**
         * <TT>relancer</TT> verifie si l'emprunt est depasse, auquel cas
         * il faudra relancer le client retardataire.
         */
        private void relancer() {
                Date dateActuelle = Datutil.dateDuJour();
                if (depasse && rappel != null) {
                        Date dateRelance = Datutil.addDate(rappel.getDateRappel(), 7);
                        if (dateRelance.before(dateActuelle)) {
                                rappel.relancer();
                        }
                }
                return;
        }

        /**
         * modifie le client associe a l'emprunt pour permettre les modifications
         * de nom et prenom dans la hashtable
         * @param newClient 
         */
        void modifierClient(Client newClient) {
                client = newClient;
        }

        /**
         * <TT>correspond</TT> verifie que l'emprunt correspond au document et
         * au client en retournant vrai.
         *   @param cli Emprunteur
         *   @param doc Document emprunte
         *   @return Vrai si l'emprunt correspond
         */
        public boolean correspond(Client cli, Document doc) {
                return (client.equals(cli) && document.equals(doc));
        }

        /**
         * <TT>restituer</TT> est lancee lors de la restitution d'un document.
         * Elle appelle les methodes de restitution sur le document et le client.
         */
        public void restituer() throws InvariantBroken, OperationImpossible {
                client.restituer(this);
                document.restituer();
        }

        public Client getClient() {
                return client;
        }

        public Document getDocument() {
                return document;
        }

        public Date getDateEmprunt() {
                return dateEmprunt;
        }

        public Date getDateLimite() {
                return dateLimite;
        }

        public boolean getDepasse() {
                return depasse;
        }

        public int getDureeEmprunt(){
                return (int) ((dateLimite.getTime () - dateEmprunt.getTime ()) / (1000 * 60 * 60 * 24));
        }

        public double getTarifEmprunt(){ 
                double tarifNominal = document.tarifEmprunt();
                return  client.sommeDue(tarifNominal);
        }

        /**
         * changementCategorie est appele apres un changement de categorie du client 
         * calcule si l'emprunt est en retard ou non 
         * @return boolean true si l'emprunt etait depasse
         */
        public boolean changementCategorie() throws OperationImpossible {
                boolean oldDepasse = depasse;
                if (depasse) {
                        rappel = null;
                        depasse = false;
                }
                int duree = document.dureeEmprunt();
                dateLimite = client.dateRetour(dateEmprunt, duree);
                verifier();
                return oldDepasse;
        }

        /**
         *<TT>toString</TT> affiche les caracteristiques de l'emprunt.
         *  @return Caracteristiques de l'emprunt
         */
        @Override
        public String toString() {
                String s = "\"" + document.getCode() + "\" par \"" + client.getNom()
                                + "\" le " + Datutil.dateToString(dateEmprunt) + " pour le "
                                + Datutil.dateToString(dateLimite);
                if (depasse) {
                        s = s + " (depasse)";
                }
                return s;
        }

        /**
         *<TT>afficherStatistiques</TT> affiche le nombre total d'emprunts.
         */
        public static void afficherStatistiques() {
                System.out.println("Nombre total d'emprunts = " + nbEmpruntsTotal);
        }
}