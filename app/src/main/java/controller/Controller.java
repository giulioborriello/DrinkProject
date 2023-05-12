package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import model.*;

@SuppressWarnings("unused")
public class Controller {
    private static volatile boolean dumpEseguito;
    private static Controller controller;
    private Utente utente = null;
    private static final ArrayList<Drink> listaDeiDrink = new ArrayList<>();
    private static final ArrayList<String> categorie = new ArrayList<>();

    private Controller() {
        dumpEseguito=false;
        dump();
        dumpEseguito=true;
    }

    public boolean ilCarrelloeVuoto() {
        if (utente.getDrinkOrdineList().size() == 0)
            return false;
        return utente.getDrinkOrdineList().get(0).getDrink() != null;
    }


    public static Controller getInstance() {
        if (controller == null) {
            controller = new Controller();
        }
        waitdDump();
        return controller;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private static void waitdDump() {
        while(!dumpEseguito);

    }

    private byte[] getByteArray() {
        byte[] array;
        try {
            array = Files.readAllBytes(Paths.get("images.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return array;
    }


    private void fakeDump() {
        login("GLR", "pass");
        listaDeiDrink.add(new Drink("1",
                "Mojito",
                "Alcolici",
                "\tUn cocktail a base di rum, lime, zucchero di canna, soda e foglie di menta",
                7.5));

        listaDeiDrink.add(new Drink("2",
                "Spritz",
                "Alcolici",
                "Un cocktail a base di prosecco, aperol e soda\t",
                6.5));

        listaDeiDrink.add(new Drink("3",
                "Acqua",
                "Analcolici",
                "Acqua naturale o frizzante",
                1.5));


        listaDeiDrink.add(new Drink("4",
                "Coca-Cola",
                "Analcolici",
                "Bevanda gassata al gusto di cola\t",
                2));

        listaDeiDrink.add(new Drink("5",
                "Fullato di frutta",
                "Frullati",
                "Frullato di fragole, banane e yogurt\t",
                4.5));

        categorie.add("all");
        categorie.add("Frullati");
        categorie.add("Alcolici");
        categorie.add("Analcolici");
    }


    public void dump() {
        //TODO fare il dump dei drink
/*
  Connessione con;
        try {
            con = Connessione.getInstance();
            String querySelectForDrink = "SELECT * " +
                    "FROM drink";
            List<String> tabellaDrink = con.sendSelect(querySelectForDrink);
            for (String eleDrink : tabellaDrink
            ) {
                System.out.println(eleDrink);
            }

            String querySelectForCategorie = "SELECT DISTINCT categoria" +
                    "FROM drink";
            List<String> listaCategorie = con.sendSelect(querySelectForCategorie);
            categorie.add("all");
            categorie.addAll(listaCategorie);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

         try {
            con.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
*/

        fakeDump();
        System.out.println("drink 1: " + getDrink("1").toString());
        System.out.println("drink 2: " + getDrink("2"));

    }


    public boolean login(String username, String password) {
        //check if username and password are valid
        //TODO verificare login in c e ricevere i suoi dati

        /*
        Connessione conn;
        try {
            conn = Connessione.getInstance();
        } catch (IOException e) {
            System.err.println("Non sono riuscito a connettermi a Server");
            return false;
        }
        String querySelectUtente = "SELECT * " +
                "FROM utente" +
                "WHERE email=' " + username + "' AND" +
                "password ='" + password + "'";
        List<String> utenteRes;
        try {
            utenteRes = conn.sendSelect(querySelectUtente);
            //TODO estrapolare parametri e salvali nell'attributo utente
              String id;
             String nome;
              String cognome;
            String email;
            String password;
            utente = new Utente(id,nome,cognome,email,password);

        } catch (IOException e) {
            System.err.println("Non sono riuscito a fare il login");
            return false;
        }
        //TODO DATO LA LISTA PRENDERE I VALORI AL SUO INTERNO e creare oggetto utente
*/
        utente = new Utente("0", "GLR", "LSO", "GLR.unina.it", "pass");


        return true;

    }
    public ArrayList<Drink> getSugeritiInbaseAiTuoiGusti(){
        int idUtente= Integer.parseInt(utente.getId());
        //funzione di lorenzo che sceglie in base agli ingredienti
        /*
        SELECT public.raccomanda_drink_ingrediente(<id_utente integer>)
         */
        //fake

        ArrayList<Drink> listaSuggerita=new ArrayList<>();
        listaSuggerita.add(listaDeiDrink.get(2));

        return  listaSuggerita;
    }
    public ArrayList<Drink> getSugerimentiNuoviInBaseAiTuoiGusti(){
        //funzione di Rai
        int idUtente= Integer.parseInt(utente.getId());
        /*
            SELECT public.raccomada_drink(<id_utente integer>)
         */
        //fake
        ArrayList<Drink> listaSuggerita=new ArrayList<>();
        listaSuggerita.add(listaDeiDrink.get(1));

        return  listaSuggerita;
    }
    public boolean signIn(String name, String surname, String username, String password) {
        //TODO fare il signin
        /*
        INSERT INTO public.utente(
	        id, nome, cognome, email, password, "dati_Biometrici")
	        VALUES (?, ?, ?, ?, ?, ?);

        String queryInsertUtente = "INSERT INTO utente" +
                "(nome, cognome email, password)" +
                "VALUES (" +
                "'" + name+ "'," + "'" + surname+ "'," + "'" + username+ "'," + "'" + password+"'" +
                ");";
/*
        try {
            if( (Connessione.getInstance().sendInsert(queryInsertUtente)) ){
                return true;
                utente = new Utente("0",name,surname,username,password);

            }else{


                return false;
            }
        } catch (IOException e) {
            return  false;
        }

*/

        utente = new Utente("0", name, surname, username, password);
        return true;
    }


    public boolean logout() {
        //TODO fare il logout
        utente = null;
        return true;
    }

    public ArrayList<Drink> getDrinks() {
        return listaDeiDrink;
    }

    public Drink getDrink(String idDrink) {
        for (Drink drink : listaDeiDrink) {
            if (drink.getId().equals(idDrink)) {
                return drink;
            }

        }
        return null;
    }


    public ArrayList<Drink> cercaDrink(String parolaCercata) {
        ArrayList<Drink> listaDeiDrinkCercati = new ArrayList<>();
        parolaCercata = parolaCercata.toLowerCase();

        for (Drink drink : listaDeiDrink
        ) {
            if (drink.getNome().toLowerCase().contains(parolaCercata) || drink.getCategoria().toLowerCase().contains(parolaCercata))
                listaDeiDrinkCercati.add(drink);
        }

        return listaDeiDrinkCercati;
    }


    public ArrayList<Drink> FiltraDrinkPerCategoria(String categoriaSelezionata) {
        ArrayList<Drink> listaDeiDrinkCercati = new ArrayList<>();
        categoriaSelezionata = categoriaSelezionata.toLowerCase();
        for (Drink drink : listaDeiDrink
        ) {
            if (drink.getCategoria().toLowerCase().matches(categoriaSelezionata))
                listaDeiDrinkCercati.add(drink);
        }

        return listaDeiDrinkCercati;
    }


    public void addDrink(String idDrinkOrdinato, int quantita) {
        //controllo quantita '
        if (quantita <= 0) return;

        //recupera drink
        Drink drinkDaAggiungere = getDrink(idDrinkOrdinato);

        if (drinkDaAggiungere == null) return;

        utente.addDrink(drinkDaAggiungere, quantita);
    }


    public boolean removeDrink(String idDrink) {
        //recupera drink
        Drink idDrinkDaRimuovere = getDrink(idDrink);

        if (idDrinkDaRimuovere == null) return false;

        utente.removeDrink(idDrinkDaRimuovere);
        return true;
    }


    public boolean removeDrink(String idDrink, int quantity) {
        //recupera drink
        Drink idDrinkDaRimuovere = getDrink(idDrink);

        if (idDrinkDaRimuovere == null) return false;

        utente.removeDrink(idDrinkDaRimuovere, quantity);
        return true;
    }


    public String getIdDrinkByName(String name) {
        for (Drink drink : listaDeiDrink
        ) {
            if (drink.getNome().equals(name)) {
                return drink.getId();
            }

        }
        return null;
    }


    public void updateDrink(String idDrink, int quantita) {
        Drink drinkDaAggiornare = getDrink(idDrink);
        if (!esisteIlDrinkNelCarrello(idDrink))
            addDrink(idDrink, quantita);
        else
            utente.updateQuantita(drinkDaAggiornare, quantita);
    }


    private boolean esisteIlDrinkNelCarrello(String idDrink) {
        for (DrinkOrdine drinkOrdine : utente.getDrinkOrdineList()) {
            if (drinkOrdine.getDrink() == null) break;
            if (drinkOrdine.getDrink().getId().equals(idDrink)) return true;
        }
        return false;
    }

    public List<DrinkOrdine> getSummary() {
        return utente.getDrinkOrdineList();
    }


    public String getPrezzoTotale() {
        return String.valueOf(utente.getPrezzoTotale());
    }


    public boolean effettuaPagamento(String nome, String cognome, String numerocarta, String dataScadenza, String cvv) {
        String queryClone = "INSERT INTO public.drink_ordine(\n" +
                "\t drink_id, ordine_id, quantita, prezzo)\n" +
                "\tVALUES\n";
        //"\tVALUES ( ?, ?, ?, ?) "
        StringBuilder query = new StringBuilder();
        ArrayList<DrinkOrdine> carello = (ArrayList<DrinkOrdine>) utente.getDrinkOrdineList();
        for (DrinkOrdine drinkOrdinato : carello) {
            String drink_id = drinkOrdinato.getDrink().getId();
            String drink_ordine = "0";//TODO crea la query per creare ordine cosi da avere id del ordine
            int quantita = drinkOrdinato.getQuantita();
            double prezzo = drinkOrdinato.getPrezzo();
            String value = "\t(" + drink_id + "," + drink_ordine + "," + quantita + "," + prezzo + ");\n";
            query.append(queryClone).append(value);
        }
        System.out.println("sono nel metodo Controller.EffettuaPagamento()\n la query creata è:\n" + query);
        //TODO inserire logica comunicazione server c ;

        return true;
    }


    public String[] getCategorie() {
        String[] categorieArray = new String[categorie.size()];
        for (String category : categorie) {
            categorieArray[categorie.indexOf(category)] = category;
        }
        return categorieArray;
    }


        public String getQuantitaOrdinata(String id){
            for (DrinkOrdine drinkOrdine : utente.getDrinkOrdineList()) {
                if (drinkOrdine.getDrink().getId().equals(id)) {
                    return String.valueOf(drinkOrdine.getQuantita());
                }
            }
            return "0";
        }


        public void svuotaCarrello () {
            utente.svuotaCarrello();
        }



}
