package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Dao.Connessione;
import model.Drink;
import model.DrinkOrdine;
import model.Utente;

@SuppressWarnings("unused")
public class Controller {
    private static volatile boolean dumpEseguito;
    private static Controller controller;
    private Utente utente = null;
    private static final ArrayList<Drink> listaDeiDrink = new ArrayList<>();
    private static ArrayList<String> categorie = new ArrayList<>();
    public static boolean pagamentoEffettuatoConSuccesso = false;

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
       // login("GLR", "pass");
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

        categorie.add("Tutti");
        categorie.add("Frullati");
        categorie.add("Alcolici");
        categorie.add("Analcolici");
        categorie.add("Consigliati in base ai tuoi gusti");
        categorie.add("Consigliati in base alle tendenze");


    }




    public void dumpCategoria (){
        List<String> res = null;
        try {
            res = Connessione.getInstance().getCategoria();
        } catch (IOException ex) {
            categorie= new ArrayList<>(Arrays.asList("Tutti","Frullati", "Alcolici", "Analcolici","Consigliati in base ai tuoi gusti","Consigliati in base alle tue tendenze"));
        }
        if (res==null) categorie= new ArrayList<>(Arrays.asList("Tutti","Frullati", "Alcolici", "Analcolici","Consigliati in base ai tuoi gusti","Consigliati in base alle tue tendenze"));
    else {
        categorie.add("Tutti");
        categorie.addAll(res); categorie.add("Consigliati in base ai tuoi gusti"); categorie.add("Consigliati in base alle tue tendenze");
    }

    }


    public void dump() {
        //TODO fare il dump dei drink
        try {
            listaDeiDrink.addAll(Connessione.getInstance().getListaDrink());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dumpCategoria();
    }


    public void login(String username, String password) {
        //check if username and password are valid
        try {
            utente = Connessione.getInstance().login(username,password);
        } catch (IOException e) {
            utente = null;
        }
    }


    public byte[] getImmagineByID(String id) throws IOException {
        Drink drink = getDrinkByID(id);
        if(drink.getImmagine() == null){
            drink.setImmagine(Connessione.getInstance().getImmagineByID(id));
        }
        return drink.getImmagine();
    }


    public ArrayList<Drink> getSuggerimentiInbaseAiTuoiGusti(){
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


    public ArrayList<Drink> getSuggerimentiInBaseAlleTendenze(){
        ArrayList<Drink> listaSuggerita=new ArrayList<>();
        List<String> listaId = new ArrayList<>();
        try {
            listaId = Connessione.getInstance().getSuggerimentiInBaseAlleTendenze(String.valueOf(utente.getId()));
        } catch (IOException e) {
            return null;
        }
        for (String id : listaId) {
            listaSuggerita.add(getDrinkByID(id));
        }
        return  listaSuggerita;
    }


    public boolean signIn(String name, String surname, String username, String password) {
        try {
            utente= Connessione.getInstance().signIn(name,surname,username,password);
            if (utente==null)
                return false;
        } catch (IOException e) {
            return false;
        }
        String id = null;
        try {
            Connessione.getInstance().close();
            id = Connessione.getInstance().sendSelect("SELECT id FROM utente WHERE email = '" + username + "' ORDER BY id").get(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        utente.setId(id);
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

    public Drink getDrinkByID(String idDrink) {
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
        Drink drinkDaAggiungere = getDrinkByID(idDrinkOrdinato);

        if (drinkDaAggiungere == null) return;

        utente.addDrink(drinkDaAggiungere, quantita);
    }


    public void removeDrink(String idDrink) {
        //recupera drink
        Drink idDrinkDaRimuovere = getDrinkByID(idDrink);

        if (idDrinkDaRimuovere == null) return;

        utente.removeDrink(idDrinkDaRimuovere);
    }


    public void ricompattaArray(){
        utente.ricompattaArray();
    }


    public boolean removeDrink(String idDrink, int quantity) {
        //recupera drink
        Drink idDrinkDaRimuovere = getDrinkByID(idDrink);

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
        Drink drinkDaAggiornare = getDrinkByID(idDrink);
        if (!esisteIlDrinkNelCarrello(idDrink))
            addDrink(idDrink, quantita);
        else
            utente.updateQuantita(drinkDaAggiornare, quantita);
    }


    private boolean esisteIlDrinkNelCarrello(String idDrink) {
        if (utente == null) return false;
        if (utente.getDrinkOrdineList() == null || utente.getDrinkOrdineList().size() == 0) return false;
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


    public void effettuaPagamento(String nome, String cognome, String numerocarta, String dataScadenza, String cvv) {
        pagamentoEffettuatoConSuccesso = false;
        try {
            if(!Connessione.getInstance().effettuaPagamento(utente)){
                pagamentoEffettuatoConSuccesso = false;
            }
        } catch (IOException e) {
            //ERROR DI COMUNICAZIONE SERVER
            pagamentoEffettuatoConSuccesso = false;
        }
        pagamentoEffettuatoConSuccesso = true;
    }


    public String[] getCategorie() {
        String[] categorieArray = new String[categorie.size()];
        for (String category : categorie) {
            categorieArray[categorie.indexOf(category)] = category;
        }
        return categorieArray;
    }


        public String getQuantitaOrdinata(String id){
            if (utente != null) {
                for (DrinkOrdine drinkOrdine : utente.getDrinkOrdineList()) {
                    if (drinkOrdine.getDrink().getId().equals(id)) {
                        return String.valueOf(drinkOrdine.getQuantita());
                    }
                }
                return "0";
            }
            return "0";
        }


        public void svuotaCarrello () {
            utente.svuotaCarrello();
        }


        public boolean eLoggato(){
            return utente != null;
        }
}
