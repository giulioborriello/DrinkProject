#include <stdio.h>  
#include <stdlib.h>
#include <unistd.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <stdbool.h>
#include <pthread.h> 
#include "CodaConnessioni.h"
#include "SocketLib.h"

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


void* handle_connection(void* client_socket_input){
    int client_socket = *((int *)client_socket_input);
    free(client_socket_input);
    char buffer[BUFSIZ];
    char actualpath[BUFSIZ];
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

