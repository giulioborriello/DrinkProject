Questa sezione è necessaria per garantire un corretto set-up del sistema e delle sue funzionalità. 
Nella cartella inviata al docente vi sono tutti i codici sorgenti, permettendo così di testare le componenti in modo indipendente l'uno dall'altro.
Verranno incluse le versioni dell'applicazione usanti il server in localhost per una configurazione e avvio semplificati.

#Server

Per compilare il server in un eseguibile bisogna usare il comando: 

gcc CodaConnessioni.c PostgreSQLlib.c SocketLib.c MainServerC.c -o Server -lpq -I/usr/include/postgresql

Il server deve essere attivo prima che l'applicativo Android, quindi per garantire che il server sia sempre in esecuzione, è stato scritto un semplice script bash chiamato loop.sh nella cartella ServerC, usato per far partire il programma in un ciclo infinito.
Per avviare il server, quindi è necessario eseguire questo script dopo aver dato permessi di esecuzione al file con il comando chmod.

#Applicativo

Prima di lanciare l'applicativo, modificare la classe Connessione in riga 20 inserendo l'indirizzo IP della macchina che agirà da server.
L'applicativo va eseguito dopo che il server è stato messo in funzione. Si può accedere senza effettuare una registrazione tramite l'utente amministratore (username: admin, password: admin), oppure si può registrare un nuovo utente e utilizzare quello per accedere successivamente. Per procedere al carrello bisogna inserire almeno un prodotto, dopo il pagamento il carrello viene svuotato e si viene riportati alla pagina di scelta dei Drink.
