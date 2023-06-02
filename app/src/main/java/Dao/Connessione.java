package Dao;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;
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
    public static final String FAILURE = "FAILIURE";
    private final String ERROR = "ERROR";
    private final String INVALID_MESSAGE = "INVALID_MESSAGE";
    private final String TIMEOUT = "TIMEOUT";
    private final String CONNECTION_ERROR = "CONNECTION_ERROR";


    private Connessione() throws IOException {
        socket = new Socket(indirizzoServer, portaServer);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }


    public static Connessione getInstance() throws IOException {
        if (istanza == null) {
            istanza = new Connessione();
        }
        return istanza;
    }


    public void sendMessage(String message) {
        out.println(message);
    }


    public String readResponse() throws IOException {
        return in.readLine();
    }


    public List<String> readAllResponses() throws IOException {
        List<String> responses = new ArrayList<>();
        String response;
        while ((response = in.readLine()) != null) {
            responses.add(response);
        }
        close();
        return responses;
    }


    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
        istanza = null;
    }


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
        boolean b = risposta.equals(SUCCESS);
        return b;
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
               "FROM utente " +
               "WHERE email='"+ username + "' AND " +
               "password ='" + password + "' ORDER BY id";

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


    public byte[] getImmagineByID(String id ){
        String query ="SELECT immagine " +
                "FROM drink " +
                "WHERE id= "+id + " ORDER BY id";

        try {
            List<String> res=sendSelect(query);
            if (res.get(0).equals(FAILURE))
                return null;
            return  Base64.getDecoder().decode(res.get(0));
        } catch (IOException e) {
            return null;
        }


    }


    public ArrayList<Drink> getListaDrink(){
        String query="SELECT id, nome, categoria, descrizione, prezzo " +
                " FROM drink ORDER BY nome" ;
        List<String> res;
        try {
            res = sendSelect(query);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Drink> listaDrink=new ArrayList<>();
        for (int i=0;i<res.size()-1;i+=5) {
            String id = res.get(i);
            System.out.println("primo debug: " + id);
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
                " FROM drink ORDER BY categoria" ;
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
                " FROM raccomanda_drink ORDER BY id" ;
        List<String> res;
        try {
            res = sendSelect(query);
        } catch (IOException e) {
            return null;
        }
        if (res.get(0).equals(FAILURE)) return  null;
        return (ArrayList<String>) res;
    }


    public Utente signIn(String name, String surname, String username, String password){
        String queryMaxIdUtente="( select max(id)+1 from utente )";
        String query= " INSERT INTO utente " +
                " (id, nome, cognome, email, password) " +
                " VALUES (" + queryMaxIdUtente + "," +
                "'" + name+ "'," + "'" + surname+ "'," + "'" + username+ "'," + "'" + password+"'" +
                ") ";


        if (sendInsert(query))
            return new Utente(Integer.toString(0),name,surname,username,password);
        else
            return null;
    }


    public boolean effettuaPagamento(Utente utente){
        //CALL public.salva_ordine(9, ARRAY[3, 5], ARRAY[2, 1]);
        String query="CALL public.salva_ordine";
        String idUtente=utente.getId();
        String stringaPerArrayDeiDrink="Array[";
        String stringaPerArrayDellaQuantita="Array[";
        for (DrinkOrdine drinkOrdinato :utente.getDrinkOrdineList() ) {
            stringaPerArrayDeiDrink+=drinkOrdinato.getDrink().getId()+" ,";
            stringaPerArrayDellaQuantita+=drinkOrdinato.getQuantita()+" ,";
        }
        stringaPerArrayDeiDrink=rimuoviultimaVirgola(stringaPerArrayDeiDrink)+"]";
        stringaPerArrayDellaQuantita =rimuoviultimaVirgola(stringaPerArrayDellaQuantita)+"]";
        query+="("+idUtente+","+
                stringaPerArrayDeiDrink+","+
                stringaPerArrayDellaQuantita+")";
        return sendInsert(String.valueOf(query));
    }


    private String rimuoviultimaVirgola(String str) {
        return str.substring(0,str.lastIndexOf(","));
    }


    public List<String> getSuggerimentiInBaseAlleTendenze(String idUtente){
        List<String> listaDrinkSuggeriti = new ArrayList<>();
        try {
            listaDrinkSuggeriti = sendSelect("SELECT id FROM public.raccomanda_drink(" + idUtente + ") ");
            if (listaDrinkSuggeriti.get(0).equals(FAILURE)) return null;
        } catch (IOException e) {
            return null;
        }
        listaDrinkSuggeriti.remove(listaDrinkSuggeriti.size()-1);
        return listaDrinkSuggeriti;
    }
}


