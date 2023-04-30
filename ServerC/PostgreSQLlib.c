#include <stdio.h>  
#include <stdlib.h>
#include <unistd.h>
#include "PostgreSQLlib.h"

PGconn *conn;
PGresult *res;

void connectSQL(){
   
   
   printf("inizio connessione\n");
   conn = PQsetdbLogin(SERVERSQL, PORTSQL, NULL, NULL, USERSQL, USERSQL, PASSWDSQL);
   printf("Verifica connessione\n");
   if (PQstatus(conn) != CONNECTION_OK) {
      printf("Errore di connessione: %s", PQerrorMessage(conn));
      PQfinish(conn);
      exit(1);
   }
   printf("Connessione avvenuta con successo\n");
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

void querySQL (char *query,int *rows, int *cols,PGresult *resQuery){
	PGresult *res;
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
   * rows = PQntuples(res);
   * cols = PQnfields(res);
   resQuery = res;
   printf("\nRisultati della query:\n");
   for (int i = 0; i < *rows; i++) {
      for (int j = 0; j < *cols; j++) {
         printf("%s\t", PQgetvalue(res, i, j));
      }
      printf("\n");
   }
   
   // Libera la memoria
   PQclear(res);
}


char * getValore(PGresult * res, int col, int row){
   return PQgetvalue(res, col, row);
}


void closeSQL(){
	PQfinish(conn);
}
