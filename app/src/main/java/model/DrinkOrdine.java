package  model;

public class DrinkOrdine {
    private Drink drink;
    private int quantita;
    private double prezzo;

    public DrinkOrdine(Drink drinkInput, int quantitaInput){
        this.drink = drinkInput;
        this.quantita = quantitaInput;
        this.prezzo = drinkInput.getPrezzo() * quantitaInput;
    }


    public void removeDrink(){
        this.drink = null;
        this.quantita = 0;
        this.prezzo = 0;
    }


    public void updateQuantitaEPrezzo(int quantitaInput) {
        this.quantita = quantitaInput;
        try{
            if (quantitaInput <= 0){
                throw  new IllegalArgumentException("Quantità non valida");
            }
            this.prezzo = drink.getPrezzo() * quantitaInput;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    public Drink getDrink() {
        return drink;
    }
    public void setDrink(Drink drink) {
        this.drink = drink;
    }
    public int getQuantita() {
        return quantita;
    }
    public double getPrezzo() {
        return prezzo;
    }
}