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
   printf("Sono dentro alla funzione connectSQL; sto per usare il metodo PQsetdbLogin\n");
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

void querySQL (char *query, PGconn *conn, PGresult **res){
	*res = PQexec(conn, query);
   
   // Controllo la query
   if (PQresultStatus(*res) != PGRES_TUPLES_OK) {
      printf("Errore nella query: %s", PQerrorMessage(conn));
      PQclear(*res);
      PQfinish(conn);
      exit(1);
   }
   
   // Stampa i risultati per tener traccia, da togliere
   int rows = PQntuples(*res);
   printf("Righe: %d\n",rows);
   
   int cols = PQnfields(*res);
   printf("Colonne: %d\n",cols);
   
   printf("\nRisultati della query:\n");
   for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
         printf("%s\t", PQgetvalue(*res, i, j));
      }
      printf("\n");
   }
   printf("Fine tabella\n");
}

/**
 * @brief Esegue una query di cancellazione nel database
 * 
 * @param query istruzione in sql
 * @param conn connnessione stabilita con il database
 */
void deleteSQL(char *query, PGconn *conn){
   PGresult *res;
   res = PQexec(conn, query);
   
   // Controllo la query
   if (PQresultStatus(res) != PGRES_COMMAND_OK) {
      printf("Errore nella query: %s", PQerrorMessage(conn));
      PQclear(res);
      PQfinish(conn);
      exit(1);
   }
   printf("\nIstruzione di cancellazione eseguita con successo.\n");
   
   // Libera la memoria
   PQclear(res);
}

/**
 * @brief Esegue una query di aggiornamento nel database
 * 
 * @param query istruzione in sql
 * @param conn connessione stabilita con il database
 */
void updateSQL(char *query, PGconn *conn){
   PGresult *res;
   res = PQexec(conn, query);
   
   // Controllo la query
   if (PQresultStatus(res) != PGRES_COMMAND_OK) {
      printf("Errore nella query: %s", PQerrorMessage(conn));
      PQclear(res);
      PQfinish(conn);
      exit(1);
   }
   printf("\nIstruzione di aggiornamento eseguita con successo.\n");
   
   // Libera la memoria
   PQclear(res);
}

/**
 * @brief Chiude la connessione con il database
 * 
 * @param conn connessione stabilita con il database
 */
void closeSQL(PGconn *conn){
	PQfinish(conn);
}
