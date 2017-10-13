package mediatheque.client;

public class HashClient implements java.io.Serializable{

        private static final long serialVersionUID = 2L;

        /**
         * Nom du client, format libre.
         */
        private String nom;

        /**
         * Prenom du client, format libre.
         */
        private String prenom;
        public HashClient(String nom, String prenom){
                this.nom = nom;
                this.prenom = prenom;
        }
        @Override
        public boolean equals(Object obj) {
                if (obj == null) return false;
                if (this == obj) return true;
                if(!(obj instanceof HashClient)) return false;
                HashClient hc = (HashClient) obj;
                return (nom.equals(hc.nom) && prenom.equals(hc.prenom));
        }
        @Override
        public int hashCode(){
                // Very simple approach:
                // Using Joshua Bloch's recipe:
                int result = 17;
                result = 37*result + nom.hashCode();
                result = 37*result + prenom.hashCode();
                return result;
        }

        public String getNom() {
                return nom;
        }

        public String getPrenom() {
                return prenom;
        }

}