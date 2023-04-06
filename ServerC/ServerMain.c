#include <libpq-fe.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <stdbool.h>
#include <pthread.h> 

#include "CodaConnessioni.h"

#define PATH_MAX_LOCAL 1024
#define SERVERPORT 8989
#define BUFSIZE 4096
#define SOCKETERROR (-1)
#define SERVER_BACKLOG 100
#define THREAD_POOL_SIZE 20

pthread_t thread_pool[THREAD_POOL_SIZE];
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t condition_var = PTHREAD_COND_INITIALIZER;

typedef struct sockaddr_in SA_IN;
typedef struct sockaddr SA;
// sudo apt-get install libpq-dev
// gcc Servermain.c -o pippo -I/usr/include/postgresql -lpq

int main() {
   PGconn *conn;
   PGresult *res;
   char *port;
   char *address, *user, *password, *database, *query, *insert_query;
   /*
    // Alloca la memoria per le stringhe
    address = (char *) malloc(100 * sizeof(char));   
   printf("Inserisci l'indirizzo del server PostgreSQL: ");
   scanf("%s", address);
    
    port = (char *) malloc(100 * sizeof(char));
   printf("Inserisci la porta del server PostgreSQL: ");
   scanf("%s", port);
   
    database = (char *) malloc(100 * sizeof(char));
   printf("Inserisci il nome del database: ");
   scanf("%s", database);
   
    user = (char *) malloc(100 * sizeof(char));
   printf("Inserisci il nome utente: ");
   scanf("%s", user);
   
    password = (char *) malloc(100 * sizeof(char));
   printf("Inserisci la password: ");
   scanf("%s", password);//porva push
   
   // Connessione al database
   conn = PQsetdbLogin(address, port, NULL, NULL, database, user, password);
   
   // Controllo la connessione
   if (PQstatus(conn) != CONNECTION_OK) {
      printf("Errore di connessione: %s", PQerrorMessage(conn));
      PQfinish(conn);
      exit(1);
   }
   */
  //conn = PQsetdbLogin("localhost", "5432", NULL, NULL, "postgres", "postgres", "qwerty");
    conn = PQsetdbLogin("hattie.db.elephantsql.com", "5432", NULL, NULL, "belwxpmt", "belwxpmt", "XrwgzZ7ArJ9IHywR-xoWQAb1CGT-Govq");

   if (PQstatus(conn) != CONNECTION_OK) {
      printf("Errore di connessione: %s", PQerrorMessage(conn));
      PQfinish(conn);
      exit(1);
   }

   // Query per stampare tutti i drink
   query = "SELECT id, nome FROM public.drink";
   //query= "SELECT * FROM pg_catalog.pg_tables";
   res = PQexec(conn, query);
   
   // Controllo la query
   if (PQresultStatus(res) != PGRES_TUPLES_OK) {
      printf("Errore nella query: %s", PQerrorMessage(conn));
      PQclear(res);
      PQfinish(conn);
      exit(1);
   }
   
   // Stampa i risultati
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
   
   // Query per l'istruzione di inserimento fornita dall'utente
    insert_query = (char *) malloc(100 * sizeof(char));
   printf("\nInserisci la query per l'istruzione di inserimento: ");
   scanf(" %s", insert_query);
   
   // Esegue l'istruzione di inserimento
   res = PQexec(conn, insert_query);
   
   // Controllo l'istruzione di inserimento
   if (PQresultStatus(res) != PGRES_COMMAND_OK) {
      printf("Errore nell'istruzione di inserimento: %s", PQerrorMessage(conn));
      PQclear(res);
      PQfinish(conn);
      exit(1);
   }
   
   // Stampa il messaggio di conferma
   printf("\nIstruzione di inserimento eseguita con successo.\n");
   
   // Libera la memoria
   PQclear(res);
   
   // Chiude la connessione
   PQfinish(conn);
   
   return 0;
}
