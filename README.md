README

Questa sezione è necessaria per garantire un corretto set-up del sistema e delle sue funzionalità. \\
Nella cartella inviata al docente vi sono tutti i codici sorgenti, permettendo così di testare le componenti in modo indipendente l'uno dall'altro.\\
Verranno incluse le versioni dell'applicazione usanti il server in localhost per una configurazione e avvio semplificati.
\section{Database}
Il databse PostegreSQL è stato hostato usando un servizio gratuito di \href{https://www.elephantsql.com/}{ElephantSQL} per uso privato. Il Server in questo modo può gestire fino ad un massimo di 5 connessioni concorrenti, quindi è stata allegato il file per creare la base di dati in privato: i file createDbms.sql e dump.sql\\
Per evitare problemi legati alla configurazione, bisgna per prima cosa creare il database con con il nome "catalogo\textunderscore drink", quindi usare gli statements definiti dal file createDbms.sql per costruire le varie tabelle, view e funzioni.\\
Una volta finito il file dump.sql contiene tutti i dati dei vari drink inseriti, necessari per far funzionare il sistema nella sua interezza.
\section{Server}
Il server deve essere attivo prima che l'applicativo Android, quindi per garantire che il server sia sempre in esecuzione, è stato scritto un semplice script bash chiamato \texttt{loop.sh} per eseguire il server in un ciclo infinito:
\begin{lstlisting}[language=bash]
#!/bin/bash

while true
do
    ./Server &
    sleep 1
done
\end{lstlisting}
Per avviare il server, quindi è necessario eseguire questo script dopo aver dato permessi di esecuzione al file con: 
\begin{lstlisting}[language=bash]
chmod 777 loop.sh
\end{lstlisting}
\section{Client}
L'applicativo va eseguito dopo che il server è stato messo in funzione si può accedere senza effettuare una registrazione tramite l'utente amministratore che ha username= admin password= admin, oppure si può registrare un nuovo utente e utilizzare quello per accedere successivamente.\\Per procedere al carrello bisogna inserire almeno un prodotto, dopo il pagamento il carrello viene svuotato e si viene riportati alla pagina di scelta dei drink.
