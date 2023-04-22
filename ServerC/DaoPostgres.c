#include <libpq-fe.h>
#include <stdio.h>  
#include <stdlib.h>
#include <unistd.h>
#include <sys/socket.h>
#include <arpa/inet.h>

#define SOCKETERROR (-1)

void* handle_connection(void* client_socket){
    int client_socket = *((int *)p_client_socket);
    free(p_client_socket);
    char buffer[BUFSIZ];
    size_t bytes_read;
    int msgsize = 0;

    //read client message -- the name of the file to read
    while ((bytes_read= read(client_socket, buffer+msgsize, sizeof(buffer)-msgsize-1)) > 0){
        msgsize += bytes_read;
        if (msgsize > BUFSIZE-1 || buffer[msgsize-1] == '\n') break;
    }
    check(bytes_read, "recv error");
    buffer[msgsize-1] = 0; //null termina il messaggio e rimuove \name

    printf("REQUEST: %s\n",buffer);
    fflush(stdout);

    //validity check
    if (realpath(buffer, actualpath) == NULL)
    {
        printf("ERROR(Bad path): %s\n", buffer);
        close(client_socket);
        return NULL;
    }
    
    //read file and send its contents to client 
    FILE *fp = fopen(actualpath, "r");
    if (fp == NULL)
    {
        printf("ERROR(open): %s\n",buffer);
        close(client_socket);
        return NULL;
    }

    while ((bytes_read = fread(buffer, 1, BUFSIZE, fp)) > 0)
    {
        printf("Sending %zu bytes\n",bytes_read);
        write(client_socket, buffer, bytes_read);
    }
    close(client_socket);
    fclose(fp);
    printf("Closing Connection.\n");
}

/// @brief controlla se l'operazione causa errori
/// @param exp operazione da controllare
/// @param msg messaggio di errore in caso di fallimento
/// @return 
int check(int exp, const char *msg) {
    if (exp == SOCKETERROR)
    {
        perror(msg);
        exit(1);
    }

    return exp;
}

/// @brief funzione che accoda le connessioni per poi essere processate
/// @param args argomenti della funzione 
/// @return 
void* thread_function(void* args){
    while (true)
    {
        int *pclient;
        pthread_mutex_lock(&mutex);
        if( (pclient = decoda()) == NULL){
            pthread_cond_wait(&condition_var, &mutex);
            pclient = decoda();
        }
        pthread_mutex_unlock(&mutex);
        
        if(pclient != NULL){
            handle_connection(pclient);
        }
    }
}


/// @brief funzione che esegue la query e restituisce il risultato  
/// @param query query da eseguire
/// @return
void querySQL (char *query){
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
}

//* funzione void che esegue una insert su postgres database
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

//*funzione void che chiude la connessione con il database
void closeSQL(){
    PQfinish(conn);
}