#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <stdbool.h>
#include <pthread.h> 

#include <libpq-fe.h>
#include "SocketLib.h"
#include "PostgreSQLlib.h"
#include "CodaConnessioni.h"


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