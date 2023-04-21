package model;

public class Utente {
    private String id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private Carello carrello;

    public Utente(String idInput, String nomeInput, String cognomeInput, String emailInput, String passwordInput) {
        this.id = idInput;
        this.nome = nomeInput;
        this.cognome = cognomeInput;
        this.email = emailInput;
        this.password = passwordInput;
        carrello = new Carello(this);

    }

    public void addDrink(Drink drinkInput, int quantitaInput) {
        this.carrello.addDrink(drinkInput, quantitaInput);
    }

    public void removeDrink(Drink drinkInput) {
        this.carrello.removeDrink(drinkInput);
    }

    public void updateQuantita(Drink drinkInput, int quantitaInput) {
        this.carrello.updateQuantita(drinkInput, quantitaInput);
    }


    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

}