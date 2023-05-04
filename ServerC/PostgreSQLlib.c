#include <libpq-fe.h>
#include <stdio.h>  
#include <stdlib.h>
#include <unistd.h>
#include "PostgreSQLlib.h"

/**
PGconn *conn;
PGresult *res;
*/
PGconn * connectSQL(){
	PGconn *conn;
	conn = PQsetdbLogin(SERVERSQL, PORTSQL, NULL, NULL, USERSQL, USERSQL, PASSWDSQL);

   if (PQstatus(conn) != CONNECTION_OK) {
      printf("Errore di connessione: %s", PQerrorMessage(conn));
      PQfinish(conn);
      exit(1);
   }
   return conn;
}

void insertSQL(char *query, PGconn *conn){
	PGresult *res;
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

void querySQL (char *query, PGconn *conn){
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
   int rows = PQntuples(res);
   int cols = PQnfields(res);
   
   printf("\nRisultati della query:\n");
   for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
         printf("%s\t", PQgetvalue(res, i, j));
      }
      printf("\n");
   }
   
   // Libera la memoria
   PQclear(res);
}


void closeSQL(PGconn *conn){
	PQfinish(conn);
}