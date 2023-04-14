package model;

public class Drink {
    private String id;
    private String nome;
    private String categoria;
    private String descrizione;
    private double prezzo;
    private byte [] immagine;   

    public Drink(String idInput, String nomeInput, String categoriaInput, String descrizioneInput, double prezzoInput, byte [] immagineInput){
        this.id = idInput;
        this.nome = nomeInput;
        this.categoria = categoriaInput;
        this.descrizione = descrizioneInput;
        this.prezzo = prezzoInput;
        this.immagine = immagineInput;
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
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public double getPrezzo() {
        return prezzo;
    }
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
    public byte [] getImmagine() {
        return immagine;
    }
    public void setImmagine(byte [] immagine) {
        this.immagine = immagine;
    } 

}
