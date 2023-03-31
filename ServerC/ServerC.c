#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <stdbool.h>
#include <pthread.h> 

//#include <libpq-fe.h>

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

void* handle_connection(void* client_socket);
int check (int exp, const char *msg);
void* thread_function(void* args);

int main (int argc , char **argv)
{
    int server_socket, client_socket, addr_size;
    
    /** 
     * PGconn *conn;
     * PGresult *res;
    */
   
    SA_IN server_addr, client_addr;

    for (int i = 0; i < THREAD_POOL_SIZE; i++)
    {
        pthread_create(&thread_pool[i], NULL, thread_function, NULL);
    }
    
    check((server_socket = socket(AF_INET , SOCK_STREAM , 0)), "Failed to create socket");

    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = INADDR_ANY;
    server_addr.sin_port = htons(SERVERPORT);

    check(bind(server_socket, (SA*)&server_addr, sizeof(server_addr)), "Bind Failed!");
    check(listen(server_socket, SERVER_BACKLOG), "Listen Failed!");

    while (true)
    {
        printf("Aspettando connessione...\n");
        
        addr_size = sizeof(SA_IN);
        check(client_socket = 
                accept(server_socket, (SA*)&client_addr, (socklen_t*)&addr_size),
                "accept failed");
                printf("Connected!\n");
                //processa connessione
        int *pclient = malloc(sizeof(int));
        *pclient = client_socket;
        
        //zona critica
        pthread_mutex_lock(&mutex);
         accoda(pclient);
         pthread_cond_signal(&condition_var);
        pthread_mutex_unlock(&mutex);

    }//fine while
    
    return 0;
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
