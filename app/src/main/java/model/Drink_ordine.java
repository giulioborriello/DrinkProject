package  model;

public class Drink_ordine {
    private Drink drink;
    private int quantita;
    private double prezzo;

    public Drink_ordine(Drink drinkInput, int quantitaInput){
        this.drink = drinkInput;
        this.quantita = quantitaInput;
        this.prezzo = drinkInput.getPrezzo() * quantitaInput;
    }

    public void updateQuantita(int quantitaInput){
        this.quantita = quantitaInput;
        this.prezzo = drink.getPrezzo() * quantitaInput;
    }
    public void removeDrink(){
        this.drink = null;
        this.quantita = 0;
        this.prezzo = 0;
    }


    //getters and setters
    public Drink getDrink() {
        return drink;
    }
    public void setDrink(Drink drink) {
        this.drink = drink;
    }
    public int getQuantita() {
        return quantita;
    }


    public void setQuantita(int quantitaInput) {
        this.quantita = quantita;
        try{
            if (quantitaInput <= 0){
                //invalid argument exception
                throw  new IllegalArgumentException("Quantita non valida");

            }
            this.prezzo = drink.getPrezzo() * quantitaInput;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public double getPrezzo() {
        return prezzo;
    }



}