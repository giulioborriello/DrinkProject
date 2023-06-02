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
//gcc -g CodaConnessioni.c SocketLib.c MainServerC.c PostgreSQLlib.c -I/usr/include/postgresql  -lpq -o Server
void* thread_function(void* args);


pthread_t thread_pool[THREAD_POOL_SIZE];
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t condition_var = PTHREAD_COND_INITIALIZER;

int main (int argc , char **argv)
{
	PGconn *conn;
	PGresult *res;
    int server_socket, client_socket, addr_size;
    SA_IN server_addr, client_addr;
	
    for (int i = 0; i < THREAD_POOL_SIZE; i++)
    {
        pthread_create(&thread_pool[i], NULL, thread_function, NULL);
    }
    
    check((server_socket = socket(AF_INET , SOCK_STREAM , 0)), "Failed to create socket");
     int option=1;
        setsockopt(server_socket, SOL_SOCKET, SO_REUSEADDR, &option, sizeof(option));

    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = INADDR_ANY;
    server_addr.sin_port = htons(SERVERPORT);

    check(bind(server_socket, (SA*)&server_addr, sizeof(server_addr)), "Bind Failed!");
    check(listen(server_socket, SERVER_BACKLOG), "Listen Failed!");

    while (true)
    {
        printf("Aspettando connessione...\n");
        
        addr_size = sizeof(SA_IN);
        check(client_socket =  accept(server_socket, (SA*)&client_addr, (socklen_t*)&addr_size), "accept failed");
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

/*
#!/bin/bash

# Nome del programma C da eseguire
program_name="./Server"

# Funzione per controllare se il programma è in esecuzione
check_process() {
  local process_name="$1"
  local is_running=$(pgrep -x "$process_name")

  if [ -n "$is_running" ]; then
    return 0  # Il programma è in esecuzione
  else
    return 1  # Il programma non è in esecuzione
  fi
}

# Ciclo principale
while true; do
  if ! check_process "$program_name"; then
    echo "Il programma non è in esecuzione. Riavvio..."
    "$program_name" &  # Esegui il programma in background
  fi

  sleep 1  # Attendi un secondo prima di effettuare il controllo successivo
done


*/