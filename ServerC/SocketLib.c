#include <stdio.h>  
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>
#include <libpq-fe.h>
#include <arpa/inet.h>
#include <stdbool.h>
#include <pthread.h> 
#include "CodaConnessioni.h"
#include "SocketLib.h"
#include "PostgreSQLlib.h"

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
	PGconn *conn;
	PGresult *res;
	char * message;
    char * token;
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
    message = buffer;
    fflush(stdout);
	char * SQLrequest = strsep(&message, SEPARATOR);
   // loop through the string to extract all other tokens
    while( token != NULL ) {
        printf( " %s\n", token ); //printing each token
        token = strsep(&message, SEPARATOR); //ora token contiene la query SQL e SQLrequest il tipo di query
    }

    conn = connectSQL();

    switch (atoi(SQLrequest))
    {
    case 0: //"select"
        querySQL(token, conn);
        break;
    case 1: //"insert"
        insertSQL(token, conn);
        break;
    case 2: //"update"
        updateSQL(token, conn);
        break;
    case 3: //"delete"
        deleteSQL(token, conn);
        break;    
    default:
        break;
    }

    closeSQL(conn);
    close(client_socket);
    printf("Closing Connection.\n");
}
