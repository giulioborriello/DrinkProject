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
            if (drink_ordine.getDrink() == null) break;
            if(drink_ordine.getDrink().getId().equals( drinkInput.getId())){
                quantitaInput += drink_ordine.getQuantita();
                drink_ordine.updateQuantitaEPrezzo(quantitaInput);
                return;
            }
        }

        DrinkOrdine drink_ordine = new DrinkOrdine(drinkInput, quantitaInput);
        this.drink_ordineArrayList.add(drink_ordine);
    }


    public void removeDrink(Drink drinkInput){
        for (DrinkOrdine drink_ordine : this.drink_ordineArrayList) {
            if(drink_ordine.getDrink().getId().equals( drinkInput.getId())){
                drink_ordineArrayList.remove(drink_ordine);
                return;
            }
        }
    }

    public void removeDrink(Drink drinkInput, int quantitaInput){
        for (DrinkOrdine drink_ordine : this.drink_ordineArrayList) {
            if(drink_ordine.getDrink().getId().equals( drinkInput.getId())){
                drink_ordine.updateQuantitaEPrezzo(drink_ordine.getQuantita()-quantitaInput);
                return;
            }
        }
    }


    public void updateQuantita(Drink drinkInput, int quantitaInput) {
        for (int i = 0; i < this.drink_ordineArrayList.size(); i++) {
            DrinkOrdine drinkOrdine = this.drink_ordineArrayList.get(i);
            if (drinkOrdine.getDrink().getId().equals(drinkInput.getId())) {
                if (quantitaInput <= 0) {
                    this.drink_ordineArrayList.remove(i);
                } else {
                    drinkOrdine.updateQuantitaEPrezzo(quantitaInput);
                }
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

    public void svuotaCarrello() {
        this.drink_ordineArrayList.clear();
    }
}