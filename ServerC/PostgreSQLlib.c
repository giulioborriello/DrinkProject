#include <libpq-fe.h>
#include <stdio.h>  
#include <stdlib.h>
#include <unistd.h>


PGconn *conn;
PGresult *res;

void connectSQL(){
   
   
	
   conn = PQsetdbLogin(SERVERSQL, PORTSQL, NULL, NULL, USERSQL, USERSQL, PASSWDSQL);

   if (PQstatus(conn) != CONNECTION_OK) {
      printf("Errore di connessione: %s", PQerrorMessage(conn));
      PQfinish(conn);
      exit(1);
   }
}

void insertSQL(char *query){
	res = PQexec(conn, query);
   
   // Controllo la query
   if (PQresultStatus(res) != PGRES_COMMAND_OK) {
      printf("Errore nella query: %s", PQerrorMessage(conn));
      PQclear(res);
      PQfinish(conn);
      exit(1);
   }
   printf("\nIstruzione di inserimento eseguita con successo.\n");
   
   // Libera la memoria
   PQclear(res);
}

void querySQL (char *query, char ****results, int *rows, int *cols){
	res = PQexec(conn, query);
   
   // Controllo la query
   if (PQresultStatus(res) != PGRES_TUPLES_OK) {
      printf("Errore nella query: %s", PQerrorMessage(conn));
      PQclear(res);
      PQfinish(conn);
      exit(1);
   }
   
   // Stampa i risultati 
   //TODO rendere questi parametri di output
    *rows = PQntuples(res);
    *cols = PQnfields(res);
   //allocazione matrice di stringhe
   //allocazione matrice di stringhe  per i risultati
   *results = (char ***) malloc(*rows * sizeof(char **));
   for (int i = 0; i < *rows; i++) {
      *results[i] = (char **) malloc(*cols * sizeof(char *));
      for (int j = 0; j < *cols; j++) {
         *results[i][j] = (char *) malloc(100 * sizeof(char));
      }
   }
   

   printf("\nRisultati della query:\n");
   for (int i = 0; i < *rows; i++) {
      for (int j = 0; j < *cols; j++) {
         printf("%s\t", PQgetvalue(res, i, j));
         strcpy(*results[i][j], PQgetvalue(res, i, j));
      }
      printf("\n");
   }
   
   // Libera la memoria
   PQclear(res);
}



void closeSQL(){
	PQfinish(conn);
}