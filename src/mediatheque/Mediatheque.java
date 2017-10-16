package mediatheque;

import java.io.*;
import java.util.*;

import mediatheque.client.*;
import mediatheque.document.*;
import util.*;

/**
 * La classe <code>Mediatheque</code> gere l'interface du systeme de
 * gestion de la mediatheque. Les principales fonctions sont :
 * <ul>
 * <li>ajouts et suppressions des clients et documents
 * <li>emprunts et restitution des documents empruntes
 * <li>verification et relance des clients
 * </ul>
 * Dans cette version, les donnees sont permanentes.
 */
public final class Mediatheque implements Serializable {

        private static final long serialVersionUID = 3L;

        /**
         * Nom de la mediatheque, format libre.
         */
        private String nom;
        /** Objets geres par la mediatheque : Genre, Localisation, Client
         * Documents, FicheEmprunt
         */
        private Vector<Genre> lesGenres;
        private Vector<Localisation> lesLocalisations;
        private Vector<CategorieClient> lesCatsClient;
        private Hashtable<String, Document> lesDocuments;
        private Hashtable<HashClient,Client> lesClients;
        private Vector<FicheEmprunt> lesEmprunts;

        /**
         * boolean pour debuger la classe par des affichages
         */
        private static boolean debug=false;

        /**
         * Constructeur de la mediatheque qui initialise les listes
         * de clients, de documents et d'emprunts. Dans cette version,
         * ces listes sont initialisees a vide a chaque lancement.
         *    @param nom Nom de la mediatheque
         */
        public Mediatheque(String nom) {
                this.nom = nom;
                if(debug)
                        System.out.println("Mediatheque \"" + nom + "\"");
                empty();
                initFromFile();
        }

        /**
         * Initialisation des collections a vide
         */
        public final void empty() {
                lesGenres = new Vector<Genre>();
                lesLocalisations = new Vector<Localisation>();
                lesDocuments = new Hashtable<String,Document>();
                lesClients = new Hashtable<HashClient,Client>();
                lesEmprunts = new Vector<FicheEmprunt>();
                lesCatsClient = new Vector<CategorieClient>();
        }

        // Methodes pour manipuler les genres
        // chercher, ajouter, supprimer, lister
        // + methodes pour l'interface graphique taille du vecteur et elementAt
        /**
         * <TT>chercherGenre</TT> cherche un Genre dans la liste des genres
         *    @param nom du Genre a chercher
         */
        public Genre chercherGenre(String nom){
                Genre searched=new Genre(nom);
                int index = lesGenres.indexOf(searched);
                if(index>=0){
                        return lesGenres.elementAt(index);
                } else {
                        return null;
                }
        }

        /**
         * <TT>supprimerGenre</TT> permet de supprimer un Genre dans la
         * liste des genres
         *    @param nom du Genre a supprimer
         *    @exception OperationImpossible genre inexistant
         */
        public void supprimerGenre(String nom) throws OperationImpossible {
                if(debug){
                        System.out.println("Mediatheque: suppression d'un genre.");
                        System.out.println("\t" + nom);
                }
                Genre g = chercherGenre(nom);
                if (g == null) {
                    throw new OperationImpossible("Suppression de genre impossible. Il existe au moins un document associe au genre " + g);
                } else {
                        if (existeDocument(g)) {
                                throw new OperationImpossible("Genre " + nom + " inexistant");

                        }
                        lesGenres.removeElement(g);
                        if(debug)
                                System.out.println("Mediatheque: Genre \"" + nom + "\" retire");
                }
        }

        /**
         * <TT>ajouterGenre</TT> permet d'ajouter un Genre dans la 
         * liste des genres
         *    @param nom du Genre a ajouter
         *    @exception OperationImpossible genre deja present
         */
        public void ajouterGenre(String nom) throws OperationImpossible{
                if(debug){
                        System.out.println("Mediatheque: ajouter un genre.");
                        System.out.println("\t" + nom);
                }		
                Genre g=chercherGenre(nom);
                if (g==null)lesGenres.addElement(new Genre(nom));
                else throw new OperationImpossible("ajouter Genre existant:"+nom);
        }

        /**
         * <TT>modifierGenre</TT> permet de modifier un Genre 
         *    @param old ancien nom du Genre
         *    @param neuf nouveau nom du Genre
         *    @exception OperationImpossible genre deja present
         */
        public void modifierGenre(String old, String neuf) throws OperationImpossible {
                Genre g = chercherGenre(old);
                if (g != null) {
                        throw new OperationImpossible("Genre \""
                                        + old + "\" inexistant");
                } else {
                        g.modifier(neuf);
                }
        }

        public void listerGenres() {
                if(debug)
                        System.out.println("Mediatheque " + nom +
                                        "  listage des genres au " +
                                        Datutil.dateToString(Datutil.dateDuJour()));
                for (Genre g : lesGenres) {
                        System.out.println(g);
                }
        }

        public Genre getGenreAt(int n) {
                return lesGenres.elementAt(n);
        }

        public int getGenresSize() {
                return lesGenres.size();
        }

        // Methodes pour manipuler les localisations
        // chercher, ajouter, supprimer, lister
        /**
         * <TT>supprimerLocalisation</TT> permet de supprimer une Localisation
         *  dans la liste des Localisations
         *    @param salle
         *    @param rayon
         *    @exception OperationImpossible localisation inexistante
         */
        public void supprimerLocalisation(String salle, String rayon)
                        throws OperationImpossible
        {
                if(debug){
                        System.out.println("Mediatheque: suppression d'une localisation.");
                        System.out.println("\t" + salle + "\t" + rayon);
                }
                Localisation l = chercherLocalisation(salle, rayon);
                if (l == null){
                        throw new OperationImpossible("Localisation " + salle + " " +
                                        rayon + " inexistant");
                } else {
                        if(existeDocument(l))
                                throw new OperationImpossible("Suppression de localisation impossible. Il existe au moins un document � la localisation "+l);
                        lesLocalisations.removeElement(l);
                        if(debug){
                                System.out.println("Mediatheque: Localisation \"" + salle
                                        + "/" + rayon + "\" retiree");
                        }
                }
        }
        /**
         * <TT>chercherLocalisation</TT> cherche une localisation dans la
         * liste des localisations
         *    @param salle
         *    @param rayon
         *    @return localisation ou null
         */
        public Localisation chercherLocalisation(String salle, String rayon) {
                Localisation l = new Localisation(salle,rayon);
                int where = lesLocalisations.indexOf(l);
                if (where >= 0) {
                        return lesLocalisations.elementAt(where);
                } else {
                        return null;
                }
        }
        /**
         * <TT>ajouterLocalisation</TT> permet d'ajouter une localisation dans la 
         * liste 
         *    @param s salle
         *    @param r rayon
         *    @exception OperationImpossible localisation existante
         */
        public void ajouterLocalisation(String s, String r)
                        throws OperationImpossible {
                if(debug){
                        System.out.println("Localisation: ajouter une localisation.");
                        System.out.println("\t" + s + " " + r);
                }		
                if (chercherLocalisation(s, r) != null) {
                        throw new OperationImpossible("Localisation \""
                                        + s + " " + r + "\" deja existant");
                } else {
                        lesLocalisations.addElement(new Localisation(s, r));
                }
        }
        /**
         * <tt>modifierLocalisation</tt>
         * @param loc la localisation a modifier
         * @param s nouvelle salle
         * @param r nouveau rayon
         * @throws OperationImpossible
         */
        public void modifierLocalisation(Localisation loc, String s, String r) throws OperationImpossible {
                Localisation inVector = chercherLocalisation(loc.getRayon(), loc.getRayon());
                if (inVector == null) {
                        throw new OperationImpossible("Modifier Localisation inexistante");
                }
                if (!inVector.getSalle().equals(s)) {
                        inVector.setSalle(s);
                }
                if (!inVector.getRayon().equals(r)) {
                        inVector.setRayon(r);
                }
        }
        /**
         * <TT>ListerLocalisations</TT> permet d'afficher toutes les localisations 
         */
        public void listerLocalisations() {
                System.out.println("Mediatheque " + nom
                                + "  listage des localisations au "
                                + Datutil.dateToString(Datutil.dateDuJour()));
                Localisation l = null;
                for (int i = 0; i < lesLocalisations.size(); i++) {
                        l = (Localisation) lesLocalisations.elementAt(i);
                        System.out.println(l);
                }
        }

        public Localisation getLocalisationAt(int n) {
                return lesLocalisations.elementAt(n);
        }

        public int getLocalisationsSize() {
                return lesLocalisations.size();
        }

        // Methodes pour manipuler les categories de client
        // chercher, ajouter, supprimer, lister + interface graphique
        /**
         * <TT>chercherCatClient</TT> cherche une categorie de client dans la
         * liste des categories
         *    @param catName nom de categorie recherchee
         */
        public CategorieClient chercherCatClient(String catName) {
                CategorieClient searched = new CategorieClient(catName);
                int index = lesCatsClient.indexOf(searched);
                if (index == 0) {
                        return lesCatsClient.elementAt(index);
                } else {
                        return null;
                }
        }

        /**
         * <TT>supprimerCatClient</TT> permet de supprimer une categorie dans la
         * liste des categories
         *    @param catName nom du categorie a supprimer
         *    @exception OperationImpossible categorie inexistante, ou client de cette categorie existant
         */
        public void supprimerCatClient(String catName) throws OperationImpossible {
                if(debug){
                        System.out.println("Mediatheque: suppression d'une categorie.");
                        System.out.println("\t" + nom);
                }
                CategorieClient c = chercherCatClient(catName);
                if (c == null) {
                        throw new OperationImpossible("Categorie " + nom + " inexistante");
                } else {
                        if (existeClient(c)) {
                                throw new OperationImpossible("Il existe un client dans la categorie " + nom);
                        }
                        lesCatsClient.removeElement(c);
                        System.out.println("Mediatheque: Categorie \"" + nom + "\" retire");
                }
        }

        /**
         * <TT>ajouterCatClient</TT> permet d ajouter une categorie dans la
         * liste des categories
         *    @param name nom de la categorie a ajouter
         *    @param max nombre maximum d'emprunt
         * @param cot cotisation
         * @param coefDuree coefficient de duree
         * @param coefTarif coefficient de tarif
         * @param codeReducUsed categorie avec code de reduction
         * @return reference sur la categorie ajoutee
         * @throws OperationImpossible
         */
        
        public CategorieClient ajouterCatClient(String name, int max, double cot, double coefDuree, double coefTarif, boolean codeReducUsed) throws OperationImpossible {
                System.out.println("Mediatheque: ajouter un categorie de client.");
                System.out.println("\t" + name);
                CategorieClient c = chercherCatClient(name);
                if (c != null) {
                        throw new OperationImpossible("Categorie client \""
                                        + c + "\" deja existant");
                } else {
                        c = new CategorieClient(name, max, cot, coefDuree, coefTarif, codeReducUsed);
                        lesCatsClient.addElement(c);
                }
                return c;
        }

        /**
         * <TT>modifierCatClient</TT> permet de modifier une categorie dans la
         * liste des categories
         * @param co categorie a modifier
         * @param name nouveau nom
         * @param max nombre maximum d'emprunts
         * @param cot cotisation
         * @param coefDuree coefficient de duree
         * @param coefTarif coefficient sur le tarif
         * @param codeReducUsed code de reduction utilise
         * @return CategorieClient la categorie modifiee
         * @throws OperationImpossible
         */
        public CategorieClient modifierCatClient(CategorieClient co, String name, int max, double cot, double coefDuree, double coefTarif, boolean codeReducUsed)
                        throws OperationImpossible {
                CategorieClient c = chercherCatClient(co.getNom());
                if (c == null) {
                        throw new OperationImpossible("Categorie client \""
                                        + co.getNom() + "\" inexistante");
                } else {
                        if (!co.getNom().equals(name)) {
                                co.modifierNom(name);
                        }
                        if (co.getNbEmpruntMax() != max) {
                                co.modifierMax(max);
                        }
                        if (co.getCotisation() != cot) {
                                co.modifierCotisation(cot);
                        }
                        if (co.getCoefDuree() != coefDuree) {
                                co.modifierCoefDuree(coefDuree);
                        }
                        if (co.getCoefTarif() != coefTarif) {
                                co.modifierCoefTarif(coefTarif);
                        }
                        if (co.getCodeReducUtilise() != codeReducUsed) {
                                co.modifierCodeReducActif(codeReducUsed);
                        }
                }
                return c;
        }

        public void listerCatsClient() {
                if(debug)
                        System.out.println("Mediatheque " + nom +
                                        "  listage des categories de clients " +
                                        Datutil.dateToString(Datutil.dateDuJour()));
                for (CategorieClient c : lesCatsClient) {
                        System.out.println(c);
                }
        }
        public CategorieClient getCategorieAt(int n) {
                Enumeration<CategorieClient> e = lesCatsClient.elements();
                int i;
                CategorieClient c = null;
                for (i = 0; i <= n; i++) {
                        if (e.hasMoreElements()) {
                                c = e.nextElement();
                        } else {
                                return null;
                        }
                }
                return c;
        }

        public int getCategoriesSize() {
                return lesCatsClient.size();
        }
        // Methodes sur les documents :
        // chercherDocument, ajouterDocument, retirer, metEmpruntable,
        // metConsultable
        /**
         * <TT>chercherDocument</TT> cherche le document dont le code est
         * indique en parametre.
         *    @param code Code du document a chercher
         *    @return Le document ou <code>null</code> en cas d'echec
         */
        public Document chercherDocument(String code){
                return lesDocuments.get(code);
        }
        /**
         * <TT>ajouterDocument</TT> permet d'ajouter un document dans le
         * fond de la mediatheque.
         *    @param doc Document a ajouter
         *    @exception OperationImpossible Code deja attribue
         */
        public void ajouterDocument(Document doc) throws OperationImpossible {
                if(debug){
                        System.out.println("Mediatheque: ajouter un document.");
                        System.out.println("\t" + doc.getCode() + " \"" + doc.getTitre() +
                                        "\" de " + doc.getAuteur());
                }
                if (lesDocuments.containsKey(doc.getCode())) {
                        throw new OperationImpossible("Document \"" + doc.getCode() + "\" deja existant");
                } else {
                        boolean g = lesGenres.contains(doc.getGenre());
                        if(!g){
                                throw new OperationImpossible("Ajout d'un document avec un genre non inclus dans la mediatheque");
                        }
                        boolean l = lesLocalisations.contains(doc.getLocalisation());
                        if(!l){
                                throw new OperationImpossible("Ajout d'un document avec une localisation inexistante");
                        }
                        lesDocuments.put(doc.getCode(),doc);
                }
        }
        /**
         * <TT>retirer</TT> est appelee pour retirer un document
         * de la mediatheque donne par son code suppose unique.
         * L'exception <TT>OperationImpossible</TT> est levee si le document
         * est emprunte ou si le document n'appartient pas a la mediatheque.
         *    @param code Code du document a retirer
         *    @exception OperationImpossible En cas d'erreur (voir ci-dessus)
         */
        public void retirerDocument(String code) throws OperationImpossible {
                if (lesDocuments.containsKey(code)) {
                        Document doc = lesDocuments.get(code);
                        if (doc.estEmprunte()) {
                                throw new OperationImpossible("Document \"" + code + "\" emprunte");
                        }
                        lesDocuments.remove(code);
                } else {
                        throw new OperationImpossible("Document " + code + " inexistant");
                }
        }
        /**
         * <TT>metEmpruntable</TT> Autorise l'emprunt du document.
         *  @param code Code du document a rendre empruntable
         *  @exception OperationImpossible Documient inexistant
         */
        public void metEmpruntable(String code)
                        throws OperationImpossible, InvariantBroken{
                Document doc = chercherDocument(code);
                if (doc == null){
                        throw new OperationImpossible("MetEmpruntable code inexistant:"+code);
                }
                doc.metConsultable();
        }

        /**
         *  <TT>metConsultable</TT> Interdit l'emprunt du document.
         *	@param code Code du document a rendre consultable
         *	@exception OperationImpossible Document inexistant
         */
        public void metConsultable(String code)
                        throws OperationImpossible, InvariantBroken{
                Document doc = chercherDocument(code);
                if (doc == null) {
                        throw new OperationImpossible("MetConsultable code inexistant:"+code);
                }
                doc.metConsultable();
        }

        /**
         * <TT>listerDocuments</TT> affiche les documents en cours
         */
        public void listerDocuments() {
                if(debug){
                        System.out.println("Mediatheque " + nom +"  listage des documents au " +
                                        Datutil.dateToString(Datutil.dateDuJour()));
                }
                if (lesDocuments.isEmpty()) {
                        System.out.println("(neant)");
                } else {
                        Enumeration<Document> e = lesDocuments.elements();
                        Document d = null;
                        while (e.hasMoreElements()) {
                                d = e.nextElement();
                                System.out.println(d);
                        }
                }
        }

        /**
         * <TT>existeDocument</TT> cherche un document dont le genre est
         * indique au parametre.
         *    @param g Genre du document a chercher
         *    @return true s'il en existe un false sinon
         */
        private boolean existeDocument(Genre g) {
                Enumeration<Document> e = lesDocuments.elements();
                Document d = null;
                while (e.hasMoreElements()) {
                        d = e.nextElement();
                        if (d.getGenre().equals(g)) {
                                return true;
                        }
                }
                return false;
        }

        /**
         * <TT>existeDocument</TT> cherche un document dont la localisation est
         * indique au parametre.
         *    @param l Localisation du document a chercher
         *    @return true s'il en existe un false sinon
         */
        private boolean existeDocument(Localisation l) {
                Enumeration<Document> e = lesDocuments.elements();
                Document d = null;
                while (e.hasMoreElements()) {
                        d = e.nextElement();
                        if (d.getLocalisation().equals(l)) {
                                return true;
                        }
                }
                return false;
        }

        public Document getDocumentAt(int n) {
                Enumeration<Document> e = lesDocuments.elements();
                int i;
                Document d = null;
                for (i = 0; i <= n; i++) {
                        if (e.hasMoreElements()) {
                                d = e.nextElement();
                        } else {
                                return null;
                        }
                }
                return d;
        }

        public int getDocumentsSize() {
                return lesDocuments.size();
        }
        // Methodes qui concernent les fiches d'emprunts
        // emprunter, restituer, verifier

        /**
         * <TT>emprunter</TT> est appelee pour l'emprunt d'un document par un
         * client. L'exception <TT>OperationImpossible</TT> est levee si le
         * document n'existe pas, n'est pas empruntable ou s'il est deja
         * emprunte, ou si le client n'existe pas ou ne peut pas emprunter.
         * Le tarif du pret est retourne.
         *    @param nom Nom du client emprunteur
         *    @param prenom Prenom du client emprunteur
         *    @param code Code du document a emprunter
         *    @exception OperationImpossible En cas d'erreur (voir ci-dessus)
         */
        public void emprunter(String nom, String prenom, String code)
                        throws OperationImpossible, InvariantBroken {
                Client client = chercherClient(nom, prenom);
                if (client == null) {
                        throw new OperationImpossible("Client " + nom + " " + prenom
                                        + " inexistant");
                }
                if (!client.peutEmprunter()) {
                        throw new OperationImpossible("Client " + client.getNom()
                                        + " non autorise a emprunter");
                }
                Document doc = chercherDocument(code);

                if (doc == null) {
                        throw new OperationImpossible("Document " + code + " inexistant");
                }
                if (!doc.estEmpruntable()) {
                        throw new OperationImpossible("Document " + doc.getCode()
                                        + " non empruntable");
                }
                if (doc.estEmprunte()) {
                        throw new OperationImpossible("Document " + doc.getCode()
                                        + " deja emprunte");
                }
                FicheEmprunt emprunt = new FicheEmprunt(this, client, doc);
                lesEmprunts.addElement(emprunt);
                return;
        }

        /**
         * <TT>restituer</TT> est lancee lors de la restitution d'un ouvrage.
         * Elle appelle la methode de restitution sur l'emprunt. L'exception
         * <TT>OperationImpossible</TT> est levee si le client ou le
         * document n'existent pas.
         *    @param nom Nom du client emprunteur
         *    @param prenom Prenom du client emprunteur
         *    @param code Code du document a restituer
         *    @exception OperationImpossible Restitution impossible
         */
        public void restituer(String nom, String prenom, String code)
                        throws OperationImpossible, InvariantBroken {
                Client client = chercherClient(nom, prenom);
                if (client == null) {
                        throw new OperationImpossible("Client " + nom + " " + prenom
                                        + " inexistant");
                }
                Document doc = chercherDocument(code);
                if (doc == null) {
                        throw new OperationImpossible("Document " + code + " inexistant");
                }
                for (int i = 0; i < lesEmprunts.size(); i++) {
                        FicheEmprunt emprunt = lesEmprunts.elementAt(i);
                        if (emprunt.correspond(client, doc)) {
                                emprunt.restituer();
                                lesEmprunts.removeElementAt(i);
                                return;
                        }
                }
                throw new OperationImpossible("Emprunt par \"" + nom + "\" de \""
                                + code + "\" non trouve");
        }

        /**
         * <TT>verifier</TT> est lancee chaque jour afin de determiner
         * les emprunts non restitues dans les delais.
         * Chaque emprunt detecte depasse pour la premiere fois provoque
         * l'impression d'une lettre de rappel au client.
         */
        public void verifier(){
                if(debug){
                        System.out.println("Mediatheque: verification le " +
                                        Datutil.dateToString(Datutil.dateDuJour()));
                }
                for (int i = 0; i < lesEmprunts.size(); i++) {
                        FicheEmprunt emprunt = (FicheEmprunt) lesEmprunts.elementAt(i);
                        try{
                        emprunt.verifier();
                        } catch(OperationImpossible oi){
                                oi.printStackTrace();
                        }
                }
        }

        /**
         * <TT>listerFicheEmprunts</TT> affiche les emprunts en cours
         */
        public void listerFicheEmprunts() {
                if(debug)
                        System.out.println("Mediatheque " + nom +"  listage des empruts au " +
                                        Datutil.dateToString(Datutil.dateDuJour()));
                if (lesEmprunts.size() == 0) System.out.println("(neant)");
                else for (int i=0; i<lesEmprunts.size(); i++) {
                        FicheEmprunt emprunt = lesEmprunts.elementAt(i);
                        System.out.println(i + ": " + emprunt);
                }
        }

        public FicheEmprunt getFicheEmpruntAt(int n) {
                return lesEmprunts.elementAt(n);
        }

        public int getFicheEmpruntsSize() {
                return lesEmprunts.size();
        }

        // Methodes sur les clients
        // inscrire, resilier, chercher, lister
        /**
         * Inscription d'un client dans la mediatheque. L'exception
         * <TT>OperationImpossible</TT> est levee si le client existe
         * deja dans la mediatheque ou si la categorie du client n'existe pas ou
         * si la categorie du client necessite un code de reduction
         * @param nom nom du client
         * @param prenom preneom du client
         * @param adresse adresse
         * @param nomcat nom de la categorie du client
         * @return double tarif pour ce client
         * @exception OperationImpossible en cas d'erreur (voir ci-dessus)
         */
        public double inscrire(String nom, String prenom, String adresse, String nomcat) throws OperationImpossible {

                CategorieClient c = chercherCatClient(nomcat);
                if (c == null) {
                        throw new OperationImpossible("Pas de categorie client " + nomcat);
                }
                return inscrire(nom, prenom, adresse, c, 0);
        }
        /**
         * Inscription d'un client dans la mediatheque. L'exception
         * <TT>OperationImpossible</TT> est levee si le client existe
         * deja dans la mediatheque.
         * @param nom nom du client
         * @param prenom prenom du client
         * @param adresse adresse du client
         * @param nomcat nom de la categorie du client
         * @param code code de reduction de ce client
         * @return double tarif pour ce client
         * @exception OperationImpossible en cas d'erreur (voir ci-dessus)
         */
        public double inscrire(String nom, String prenom, String adresse, String nomcat, int code)
                        throws OperationImpossible {
                CategorieClient c = chercherCatClient(nomcat);
                if (c == null) {
                        throw new OperationImpossible("Pas de categorie client " + nomcat);
                }
                return inscrire(nom, prenom, adresse, c, code);
        }

        /**
         * Inscription d'un client dans la mediatheque. L'exception
         * <TT>OperationImpossible</TT> est levee si le client existe
         * deja dans la mediatheque.
         * @param nom nom du client
         * @param prenom prenom du client
         * @param adresse adresse du client
         * @param cat categorie du client
         * @param code code de reduction de ce client
         * @return double tarif pour ce client
         * @exception OperationImpossible en cas d'erreur (voir ci-dessus)
         */
        private double inscrire(String nom, String prenom, String adresse, CategorieClient cat, int code) throws OperationImpossible {

                double tarif = 0.0;
                if(debug)
                        System.out.println("Mediatheque: inscription de " + nom
                                        + " " + prenom);
                HashClient hc = new HashClient(nom,prenom);

                if (lesClients.containsKey(hc)) {
                        throw new OperationImpossible("Client " + nom + " " + prenom
                                        + " deja existant");
                } else {
                        Client client;
                        if (cat.getCodeReducUtilise()) {
                                client = new Client(nom, prenom, adresse, cat, code);
                        } else {
                                client = new Client(nom, prenom, adresse, cat);
                        }
                        tarif = cat.getCotisation();
                        lesClients.put(hc, client);
                }
                return tarif;
        }
        /**
         * <TT>resilier()</TT> est appelee pour retirer un client
         * de la mediatheque. L'exception <TT>OperationImpossible</TT> est levee
         * si le client n'appartient pas a la mediatheque ou s'il n'a pas
         * restitue tous ses documents empruntes.
         *   @param nom Nom du client
         *   @param prenom Prenom du client
         *   @exception OperationImpossible En cas d'erreur (voir ci-dessus)
         */
        public void resilier(String nom, String prenom)
                        throws OperationImpossible {
                HashClient hc = new HashClient(nom,prenom);
                Client client = null;
                if (lesClients.containsKey(hc)) {
                        client = lesClients.get(hc);
                } else {
                        throw new OperationImpossible("Client " + nom + " " + prenom
                                        + " inexistant");
                }
                if (client.aDesEmpruntsEnCours()) {
                        throw new OperationImpossible("Client " + nom + " " + prenom
                                        + " n'a pas restitue tous ses emprunts");
                }
                lesClients.remove(hc);
                if(debug){
                        System.out.println("Mediatheque: desinscrire le client \"" +
                                        nom + " " + prenom + "\".");
                }
                client.afficherStatCli();
        }

        public void modifierClient(Client client, String nom, String prenom, String adresse,
                        String catnom, int code) throws OperationImpossible {
                HashClient newHash, oldHash = new HashClient(client.getNom(), client.getPrenom());
                boolean needNewHash = false;
                if (!lesClients.containsKey(oldHash)) {
                        throw new OperationImpossible("Client " + nom + " " + prenom
                                        + " inexistant");
                }
                if (!adresse.equals(client.getAdresse())) {
                        client.setAddresse(adresse);
                }
                if (!nom.equals(client.getNom())) {
                        client.setNom(nom);
                        needNewHash = true;
                }
                if (!prenom.equals(client.getPrenom())) {
                        client.setPrenom(prenom);
                        needNewHash = true;
                }

                if (needNewHash) {
                        newHash = new HashClient(nom,prenom);
                        lesClients.remove(oldHash);
                        lesClients.put(newHash, client);
                }
                CategorieClient catcli = chercherCatClient(catnom);
                if (!catcli.equals(client.getCategorie())) {
                        if (catcli.getCodeReducUtilise()) {
                                client.setCategorie(catcli, code);
                        } else {
                                client.setCategorie(catcli);
                        }
                }
        }

        /**
         * changerCategorie modifie la categorie du client
         * @param nom
         * @param prenom
         * @param catName
         * @param reduc 
         * @throws OperationImpossible
         */
        public void changerCategorie(String nom, String prenom, String catName, int reduc)
                        throws OperationImpossible {
                Client c = chercherClient(nom, prenom);
                if (c == null) {
                        throw new OperationImpossible("Client " + nom + " " + prenom + " non trouve");
                }
                CategorieClient cat = chercherCatClient(catName);
                if (cat == null) {
                        throw new OperationImpossible("Categorie client " + catName + " non trouvee");
                }
                if (cat.getCodeReducUtilise()) {
                        c.setCategorie(cat, reduc);
                } else {
                        c.setCategorie(cat);
                }
        }

        /**
         * changerCodeReduction modifie le code de reduction d'un client
         * @param nom 
         * @param prenom
         * @param reduc 
         * @throws OperationImpossible
         */
        public void changerCodeReduction(String nom, String prenom, int reduc)
                        throws OperationImpossible {
                Client c = chercherClient(nom, prenom);
                if (c == null) {
                        throw new OperationImpossible("Client " + nom + " " + prenom + " non trouve");
                }
                CategorieClient cat = c.getCategorie();
                if (cat.getCodeReducUtilise()) {
                        c.setReduc(reduc);
                } else {
                        throw new OperationImpossible("Changement de code de reduction sur une categorie sans code");
                }
        }

        /**
         * <TT>chercherClient</TT> cherche le client dont les nom et
         * prenom sont donnes en parametre.
         *    @param nom Nom du client a chercher
         *    @param prenom Prenom du client a chercher
         *    @return Le client ou <code>null</code> en cas d'echec
         */
        public Client chercherClient(String nom, String prenom) {
                HashClient hc = new HashClient(nom,prenom);
                if (lesClients.containsKey(hc)) {
                        return lesClients.get(hc);
                }
                return null;
        }

        /**
         * <TT>listerClients</TT> affiche les Clients en cours
         */
        public void listerClients() {
                System.out.println("Mediatheque " + nom + "  listage des clients au "
                                + Datutil.dateToString(Datutil.dateDuJour()));
                if (lesClients.isEmpty()) {
                        System.out.println("(neant)");
                } else {
                        for (Client c : lesClients.values() ) {
                                System.out.println(c);
                        }
                }
        }

        /**
         * existeClient teste s'il existe un client de cette categorie
         * pour eviter de detruire une categorie lorsqu'un client la reference encore
         * @param cat la categorie du client
         * @return true si elle existe false sinon
         */
        public boolean existeClient(CategorieClient cat) {
                for (Client c : lesClients.values()) {
                        if (c.getCategorie().equals(cat)) {
                                return true;
                        }
                }
                return false;
        }

        public Client getClientAt(int n) {
                int i;
                Collection<Client> colClient = lesClients.values();
                Iterator<Client> ic = colClient.iterator();
                Client cl = null;
                for (i = 0; i <= n; i++) {
                        if (ic.hasNext()) {
                                cl = ic.next();
                        } else {
                                break;
                        }
                }
                return cl;
        }

        public int getClientsSize() {
                return lesClients.size();
        }

        public Client findClient(String nom, String prenom) {
                return chercherClient(nom, prenom);
        }

        //Affichage du contenu des vecteurs
        /**
         * <TT>afficherStatistiques</TT> affiche les statistiques
         * globales des classes.
         */
        public void afficherStatistiques() {
                if(debug)
                        System.out.println("Statistiques globales de la mediatheque \""
                                        + nom +"\" :");
                FicheEmprunt.afficherStatistiques();
                System.out.println("Par type de document :");
                Document.afficherStatistiques();
                Client.afficherStatistiques();
        }

        // Accesseur de l'attribut nom
        public String getNom() {
                return nom;
        }

        public boolean initFromFile() {
                FileInputStream fin;
                Mediatheque media = null;

                try {
                        fin = new FileInputStream(nom + ".data");
                } catch (FileNotFoundException fe) {
                        System.out.println(fe);
                        return false;
                }
                ObjectInputStream ois;

                try {
                        ois = new ObjectInputStream(fin);
                        media = (Mediatheque) ois.readObject();
                        lesCatsClient = media.lesCatsClient;
                        lesGenres = media.lesGenres;
                        lesLocalisations = media.lesLocalisations;
                        lesClients = media.lesClients;
                        lesDocuments = media.lesDocuments;
                        lesEmprunts = media.lesEmprunts;

                        ois.close();
                        fin.close();
                } catch (IOException ioe) {
                        System.out.println(ioe);
                        System.out.println("Error reading mediatheque data");
                } catch (ClassNotFoundException cnfe) {
                        System.out.println(cnfe);
                        System.out.println("Error finding mediatheque class");
                }
                return true;
        }

        public final boolean saveToFile() {
                FileOutputStream fout;

                try {
                        fout = new FileOutputStream(nom + ".data");
                } catch (FileNotFoundException fe) {
                        System.out.println(fe);
                        return false;
                }
                ObjectOutputStream oos;

                try {
                        oos = new ObjectOutputStream(fout);
                        oos.writeObject(this);
                        oos.close();
                        fout.close();
                } catch (IOException ioe) {
                        System.out.println(ioe);
                        System.out.println("Error writing mediatheque data");
                        return false;
                }
                return true;
        }
}

