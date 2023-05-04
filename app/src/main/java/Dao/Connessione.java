package Dao;


import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Connessione {
    private static Connessione istanza = null;
    private static final String indirizzoServer = "localhost"; // Indirizzo IP o nome del server
    private static final int portaServer = 8080; // Porta del server
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;



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

}