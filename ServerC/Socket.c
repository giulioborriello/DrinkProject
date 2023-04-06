#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <stdbool.h>
#include <pthread.h> 
#include "CodaConnessioni.h"
#include "Socket.h"

/// @brief funzione che esegue una send attraverso socket
/// @param socket 
/// @param str 
void send_string(int socket, char *str){
    int len = strlen(str);
    send(socket, &len, sizeof(int), 0);
    send(socket, str, len, 0);
}

void recv_string(int client_socket){
    char buffer[BUFSIZ];
    size_t bytes_read;
    int msgsize = 0;

    while ((bytes_read= read(client_socket, buffer+msgsize, sizeof(buffer)-msgsize-1)) > 0){
        msgsize += bytes_read;
        if (msgsize > BUFSIZE-1 || buffer[msgsize-1] == '\n') break;
    }
    check(bytes_read, "recv error");
    buffer[msgsize-1] = 0; //null termina il messaggio e rimuove \name

    printf("REQUEST: %s\n",buffer);
    fflush(stdout);
}

