package Dao;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import model.Drink;
import model.DrinkOrdine;
import model.Utente;

@SuppressWarnings("unused")
public class Connessione {

    private static Connessione istanza = null;
    private static final String indirizzoServer = "109.117.81.47"; // Indirizzo IP o nome del server
    private static final int portaServer = 8080; // Porta del server
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private final String SEPARATORE = "#";
    private final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";
    private final String ERROR = "ERROR";
    private final String INVALID_MESSAGE = "INVALID_MESSAGE";
    private final String TIMEOUT = "TIMEOUT";
    private final String CONNECTION_ERROR = "CONNECTION_ERROR";


    // Costruttore privato per impedire la creazione di oggetti Connessione

    private Connessione() throws IOException {
        socket = new Socket(indirizzoServer, portaServer);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    // Metodo pubblico per accedere all'istanza singleton di Connessione
    public static Connessione getInstance() throws IOException {
        if (istanza == null) {
            istanza = new Connessione();
        }
        return istanza;
    }


    // Metodo per inviare un messaggio al server
    public void sendMessage(String message) {
        out.println(message);
    }

    // Metodo per leggere la risposta del server
    public String readResponse() throws IOException {
        return in.readLine();
    }

    // Metodo per leggere tutti i messaggi inviati dal server
    public List<String> readAllResponses() throws IOException {
        List<String> responses = new ArrayList<>();
        String response;
        while ((response = in.readLine()) != null) {
            responses.add(response);
        }
        return responses;
    }

    // Metodo per chiudere la connessione al server
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

   /*
    0 SELECT
    1 INSERT
    2 UPDATE
    3 delete
*/
    public List<String> sendSelect(String query) throws IOException {
        sendMessage("0" + SEPARATORE + query);
        return readAllResponses();
    }

    public boolean sendInsert(String query) {
        sendMessage("1" + SEPARATORE + query);
        String risposta;
        try {
            risposta = readResponse();
        } catch (IOException e) {
            return false;
        }
        return risposta.equals(SUCCESS);


    }

    public boolean sendUpdate(String query) {
        sendMessage("2" + SEPARATORE + query);
        String risposta;
        try {
            risposta = readResponse();
        } catch (IOException e) {
            return false;
        }
        return risposta.equals(SUCCESS);

    }

    public boolean sendDelete(String query) {
        sendMessage("3" + SEPARATORE + query);
        String risposta;
        try {
            risposta = readResponse();
        } catch (IOException e) {
            return false;
        }
        return risposta.equals(SUCCESS);


    }

   public  Utente login(String username, String password) {
        String querySelectUtente = "SELECT * " +
                "FROM utente" +
                "WHERE email=' " + username + "' AND" +
                "password ='" + password + "'";
        List<String> utenteRes;

        try {
            utenteRes = sendSelect(querySelectUtente);
        } catch (IOException e) {
            return null;
        }
        if (utenteRes.get(0).equals(Connessione.FAILURE)) {
            return null;
        }
        //TODO estrapolare parametri e salvali nell'attributo utente
        String id = utenteRes.get(0);
        String nome = utenteRes.get(1);
        String cognome = utenteRes.get(2);
        String email = utenteRes.get(3);
        String pass = utenteRes.get(4);

        return new Utente(id, nome, cognome, email, pass);
    }


    public byte[] getImmagine(String id ){
        String query ="SELECT immagine" +
                "FROM drink" +
                "WHERE id="+id;

        try {
            List<String> res=sendSelect(query);
            if (res.get(0).equals(FAILURE)) return null;

            return res.get(0).getBytes();
        } catch (IOException e) {
            return null;
        }


    }
    public ArrayList<Drink> getListaDrink(){
        String query="SELECT id, nome, categoria, descrizione, prezzo " +
                " FROM drink " ;
        List<String> res;
        try {
            res = sendSelect(query);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Drink> listaDrink=new ArrayList<>();
        for (int i=0;i<res.size();i+=5) {
            String id = res.get(i);
            String nome= res.get(i+1);
            String categoria = res.get(i+2);
            String descrizione = res.get(i+3);
            double prezzo = Double.parseDouble(res.get(i+4));
            listaDrink.add(new Drink(id,nome,categoria,descrizione,prezzo ));
        }
        return listaDrink;
    }


    public ArrayList<String> getCategoria(){
        String query="SELECT DISTINCT categoria " +
                " FROM drink";
        List<String> res;
        try {
            res = sendSelect(query);
        } catch (IOException e) {
            return null;
        }
        if (res.get(0).equals(FAILURE))
            return  null;
        res.remove(res.size()-1);
        return (ArrayList<String>) res;
    }

    public ArrayList<String> getIDDrinkSuggeritiDiRaimondo(){
        String query="SELECT id " +
                " FROM raccomanda_drink ";
        List<String> res;
        try {
            res = sendSelect(query);
        } catch (IOException e) {
            return null;
        }
        if (res.get(0).equals(FAILURE)) return  null;
        return (ArrayList<String>) res;
    }
    public boolean signIn(String name, String surname, String username, String password){

        String query= " INSERT INTO utente " +
                " (nome, cognome email, password) " +
                " VALUES (" +
                "'" + name+ "'," + "'" + surname+ "'," + "'" + username+ "'," + "'" + password+"'" +
                ") ";
        return sendInsert(query);
    }

    public boolean effettuaPagamento(Utente utente){


        String queryMaxIdOrdine="select max(id) from ordine";
        List<String> resIdOrdine;
        try {
            resIdOrdine = sendSelect(queryMaxIdOrdine);
            if(resIdOrdine.get(0).equals(FAILURE)) return false;
        } catch (IOException e) {
            return false;
        }
        int idOrdine = Integer.parseInt(resIdOrdine.get(0)) +1;

        String queryIsertOrdine= " INSERT INTO ordine (id, utente_id) " +
                " VALUES("+idOrdine+", "+utente.getId()+") ";
        if (!sendInsert(queryIsertOrdine)){
            return false;
        }



        String queryClone = "INSERT INTO public.drink_ordine( \n" +
                "\t drink_id, ordine_id, quantita, prezzo) \n" +
                "\t  VALUES \n";
        //"\tVALUES ( ?, ?, ?, ?) "
        StringBuilder query = new StringBuilder();
        ArrayList<DrinkOrdine> carello = (ArrayList<DrinkOrdine>) utente.getDrinkOrdineList();
        for (DrinkOrdine drinkOrdinato : carello) {
            String drink_id = drinkOrdinato.getDrink().getId();
            String ordine_id = Integer.toString(idOrdine);
            int quantita = drinkOrdinato.getQuantita();
            double prezzo = drinkOrdinato.getPrezzo();
            String value = "\t (" + drink_id + "," + ordine_id + "," + quantita + "," + prezzo + ");\n";
            query.append(queryClone).append(value);
        }

        return sendInsert(String.valueOf(query));
    }
}

