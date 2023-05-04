package controller;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import Dao.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import  model.*;
public class Controller {
    private static Controller controller;
    private static Utente utente=null;
    private static final ArrayList<Drink> listaDeiDrink=new ArrayList<Drink>();


    private Controller (){
        dump();
    }


    public static Controller getInstance(){
        if(controller==null){
            controller=new Controller();
        }
        return controller;
    }


    private byte[] getByteArray(){
        byte[] array=null;
            try {
                array = Files.readAllBytes(Paths.get("images.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        return array;
    }


    private void fakeDump(){
        login("GLR","pass");
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
    }


    public void dump(){
        //TODO fare il dump dei drink
        fakeDump();
        System.out.println("drink 1: "+getDrink("1").toString());
        System.out.println("drink 2: "+getDrink("2"));
    }


    public boolean login(String username, String password) {
        //check if username and password are valid
        //TODO verificare login in c e ricevere i suoi dati
        /*Connessione conn = null;
        try {
            conn = Connessione.getInstance();
        } catch (IOException e) {
            System.err.println("Non sono riuscito a connettermi a Server");
            throw new RuntimeException(e);
        }
        conn.sendMessage("salutare server c");
        */
        utente = new Utente("0","GLR","LSO","GLR.unina.it","pass");

        return true;
    }


    public boolean signIn(String name, String surname, String username, String password){
        //TODO fare il signin
        return true;
    }


    public boolean logout(){
        //TODO fare il logout
        utente=null;
        return true;
    }


    public ArrayList<Drink> getDrinks(){
        return listaDeiDrink;
    }



    public Drink getDrink(String idDrink){
        for (Drink drink:listaDeiDrink
        ) {
            if(drink.getId().equals(idDrink)){
                return drink;
            }

        }
        return null;
    }
    ArrayList<Drink> cercaDrink(String parolaCercata){
        ArrayList<Drink> listaDeiDrinkCercati = new ArrayList<>();
        parolaCercata= "*"+parolaCercata+"*";
        for (Drink drink: listaDeiDrink
             ) {
                if ( drink.getNome().matches(parolaCercata)) listaDeiDrinkCercati.add(drink);
        }

    return listaDeiDrinkCercati;
    }




    public boolean addDrink(String idDrinkOrdinato, int quantita){
        //controllo quantita '
        if(quantita<=0) return false;

        //recupera drink
        Drink drinkDaAggiungere=getDrink(idDrinkOrdinato);

        if(drinkDaAggiungere==null) return false;

        utente.addDrink(drinkDaAggiungere,quantita);
        return true;
    }


    public boolean removeDrink(String idDrink){
        //recupera drink
        Drink idDrinkDaRimuovere=getDrink(idDrink);

        if(idDrinkDaRimuovere==null) return false;

        utente.removeDrink(idDrinkDaRimuovere);
        return true;
    }


    public String getIdDrinkByName(String name){
        for (Drink drink:listaDeiDrink
        ) {
            if(drink.getNome().equals(name)){
                return drink.getId();
            }

        }
        return null;
    }


    public boolean updateDrink(String idDrink, int quantita){
        Drink drinkDaAggiornare = getDrink(idDrink);
        utente.updateQuantita(drinkDaAggiornare,quantita);
    return true;
    }


    public List<DrinkOrdine> getSummary(){
        return utente.getDrinkOrdineList();
    }

    public String getPrezzoTotale(){
        return String.valueOf(utente.getPrezzoTotale());
    }
}
