package model;

import java.util.ArrayList;

public class Carello {
    private Utente utente;
    private ArrayList<DrinkOrdine> drink_ordineArrayList = new ArrayList<>();


    public Carello(Utente utenteInput){
        this.utente = utenteInput;
    }


    public void addDrink(Drink drinkInput, int quantitaInput){
        //check if quantita is valid
        if(quantitaInput <= 0){
            return;
        }

        //check if drink is already in the cart
        for (DrinkOrdine drink_ordine : this.drink_ordineArrayList) {
            if(drink_ordine.getDrink().getId().equals( drinkInput.getId())){
                quantitaInput += drink_ordine.getQuantita();
                drink_ordine.updateQuantita(quantitaInput);
                return;
            }
        }

        DrinkOrdine drink_ordine = new DrinkOrdine(drinkInput, quantitaInput);
        this.drink_ordineArrayList.add(drink_ordine);
    }


    public void removeDrink(Drink drinkInput){
        for (DrinkOrdine drink_ordine : this.drink_ordineArrayList) {
            if(drink_ordine.getDrink().getId().equals( drinkInput.getId())){
                drink_ordine.removeDrink();
                return;
            }
        }
    }


    public void updateQuantita(Drink drinkInput, int quantitaInput){
        for (DrinkOrdine drink_ordine : this.drink_ordineArrayList) {
            if(drink_ordine.getDrink().getId().equals( drinkInput.getId())){
                if (quantitaInput <= 0)
                    drink_ordine.removeDrink();
                else
                    drink_ordine.updateQuantita(quantitaInput);
                return;
            }
        }
    }


    public void svuotaCarello(){
        this.drink_ordineArrayList.clear();
    }

    public double getPrezzoTotale(){
        double prezzoTotale = 0;
        for (DrinkOrdine drink_ordine : drink_ordineArrayList) {
            prezzoTotale += (drink_ordine.getPrezzo());
        }
        return prezzoTotale;
    }


    //getters and setters
    public Utente getUtente() {
        return utente;

    }

    public ArrayList<DrinkOrdine> getDrinkOrdineArrayList() {
        return drink_ordineArrayList;
    }
}