package controller;
import java.util.ArrayList;

import  model.*;
public class Controller {
    private Utente utente=null;
    private ArrayList<Drink> listaDeiDrink=null;
    public void dump(){
        //TODO fare il dump dei drink

    }
    public boolean login(String username, String password) {
        //check if username and password are valid
        //TODO verificare login in c e ricevere i suoi dati


        utente = new Utente("0","GLR","LSO","GLR.unina.it","pass");
        return true;
    }

    public ArrayList<Drink> getDrinks(){
        return listaDeiDrink;
    }


    private Drink getDrink(String idDrink){
        for (Drink drink:listaDeiDrink
        ) {
            if(drink.getId().equals(idDrink)){
                return drink;
            }

        }
        return null;
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
    public boolean updateDrink(String idDrink, int quantita){
        Drink drinkDaAggiornare = getDrink(idDrink);
        utente.updateQuantita(drinkDaAggiornare,quantita);
    return true;
    }
}
