package mediatheque;
import java.util.Date;
import java.io.Serializable;
import mediatheque.client.Client;
import mediatheque.document.Document;
import util.Datutil;

/**
 * La classe <code>LettreRappel</code> gere les lettres de rappel
 * a envoyer aux clients retardataires. Elle est composee de trois parties :
 * <UL>
 * <LI>une entete qui contient les references du rappel : le client,
 * le document non restitue dans les delais par celui-ci et la date
 * a laquelle il aurait du etre restitue ;
 * <LI>le corps de la lettre, c'est a dire le message de la mediatheque
 * (un corps minimal est propose)
 * <LI>la fin de la lettre comprenant la signature, etc..
 * </UL>
 * Seul le corps est modifiable par ajout de texte.
 */
public class LettreRappel implements Serializable{

        private static final long serialVersionUID = 2L;
        /**
         * Corps minimal de la lettre (<em>Tout emprunt est desormais
         * impossible</em>)
         */
        private final static String DEFAUT =
                        "Tout emprunt est desormais impossible\n";
        private final static String dashLine = "\n----------------------------------------------------\n";
        private String nomMedia, entete, corps, fin;  
        /**
         * Date d'envoi de la lettre
         */
        private Date dateRappel;
        private FicheEmprunt enRetard;
        // Les methodes
        /**
         * Constructeur de lettre de rappel avec le corps minimal
         * le constructeur fait un affichage de la lettre de rappel
         */
        LettreRappel(String nomMedia, FicheEmprunt emprunt) {
                dateRappel = emprunt.getDateLimite();
                this.nomMedia = nomMedia;
                this.enRetard = emprunt;
                Client cl= enRetard.getClient();
                Document doc = enRetard.getDocument();
                entete = "\n\tA Monsieur " + cl.getNom() + " " + cl.getPrenom() +
                                "\n\t" + cl.getAdresse() +
                                "\n\tObjet: Lettre de rappel\n\n" +
                                "Document non restitue le " +
                                Datutil.dateToString(dateRappel) + " : \n\t" +
                                doc.getCode() + ": \"" + doc.getTitre() +
                                "\" de " + doc.getAuteur();
                corps = DEFAUT;
                fin = "\n\tLe chef,\n\n" + dashLine;
                System.out.println(debut());
                System.out.println(corps);
                System.out.println(fin);
        }

        /**
         * <TT>relancer</TT> renouvelle le rappel 
         * (affiche de nouveau la lettre de rappel)
         */
        void relancer() {
                dateRappel = Datutil.dateDuJour();
                System.out.println(debut());
                System.out.println("Rappel : ");
                System.out.println(corps);
                System.out.println(fin);
        }

        /**
         * Debut de la lettre entete + nom de la mediatheque + date
         */
        private String debut() {
                String deb = "Mediatheque \"" + nomMedia + "\"\tLe " +
                                Datutil.dateToString(Datutil.dateDuJour());
                return dashLine + deb + entete ;
        }
        /**
         * <TT>getDateRappel</TT>
         * @return Date
         */
        public Date getDateRappel(){
                return dateRappel;
        }
}