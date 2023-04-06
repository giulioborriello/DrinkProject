#include <libpq-fe.h>
#include <stdio.h>  
#include <stdlib.h>
#include <unistd.h>

void* handle_connection(void* client_socket){

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

/// @brief funzione che gestisce le connessioni (generica legge file da computer dato path)
/// @param p_client_socket puntatore alla socket del client richiedente
/// @return 
void* handle_connection(void* p_client_socket){
    int client_socket = *((int *)p_client_socket);
    free(p_client_socket);
    char buffer[BUFSIZ];
    size_t bytes_read;
    int msgsize = 0;
    char actualpath[PATH_MAX_LOCAL+1];

    //read client message -- the name pof the file to read
    while ((bytes_read= read(client_socket, buffer+msgsize, sizeof(buffer)-msgsize-1)) > 0){
        msgsize += bytes_read;
        if (msgsize > BUFSIZE-1 || buffer[msgsize-1] == '\n') break;
    }
    check(bytes_read, "recv error");
    buffer[msgsize-1] = 0; //null termina il messaggio e rimuove \name

    printf("REQUEST: %s\n",buffer);
    fflush(stdout);

    //TODO scrivere funzione che esegue query e restituisce risultato
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
   ss
   // Libera la memoria
   PQclear(res);
}