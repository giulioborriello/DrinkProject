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

void dividi_stringa(char * stringa, char * stringa1, char * stringa2){
	char * token;
	token = strtok(stringa, SEPARATOR);
	strcpy(stringa1, token);
	token = strtok(NULL, SEPARATOR);
	strcpy(stringa2, token);
}
		

void* handle_connection(void* client_socket_input){
	PGconn *conn;
	PGresult *res;
    char * SQLrequest = malloc(sizeof(char) * 10);
    int client_socket = *((int *)client_socket_input);
    free(client_socket_input);
    char buffer[BUFSIZE];
    char * message = malloc(sizeof(buffer) + 1);
	char * token = malloc(sizeof(buffer) + 1);
	char * domanda = malloc(sizeof(buffer) + 1);
    //char actualpath[BUFSIZE];
    size_t bytes_read;
    int msgsize = 0;

    //read client message -- the name of the file to read
    while ((bytes_read= read(client_socket, buffer+msgsize, sizeof(buffer)-msgsize-1)) > 0){
        msgsize += bytes_read;
        if (msgsize > BUFSIZE-1 || buffer[msgsize-1] == '\n') break;
    }
    check(bytes_read, "recv error");
    buffer[msgsize-1] = 0; //null termina il messaggio e rimuove \name

    printf("REQUEST: %s\n", buffer);
    printf("Flag 1\n");
    strcpy(message,buffer);
	printf("REQUEST: %s\n", message);
	printf("Flag 2\n");
    fflush(stdout);
    //char* rest = message;   
 	//SQLrequest = strtok_r(rest, SEPARATOR, &rest);
 	//printf ("%s\n",SQLrequest); 
    dividi_stringa(message, SQLrequest, token);
	printf("Richiesta: %s\n", SQLrequest);

    printf("Domanda: %s\n", token);

    //TODO sistemare errore SIGSEGV
    printf("%s\n", domanda);
	char * notaOut;
    conn = connectSQL();
	
    switch (atoi(SQLrequest))
    {
    case 0: //"select"
    	printf("Query da svolgere: %s\n",domanda);
        PGresult *table = querySQL(domanda, conn);
        sendDataTable(table, client_socket);
        PQclear(table);
        break;
    case 1: //"insert"
        insertSQL(token, conn);
        notaOut = "Inserimento avvenuto con successo";
        sendDataString(client_socket, message);
        break;
    case 2: //"update"
        updateSQL(token, conn);
        notaOut = "Aggiornamento avvenuto con successo";
        sendDataString(client_socket, message);
        break;
    case 3: //"delete"
        deleteSQL(token, conn);
        notaOut = "Cancellazione avvenuta con successo";
        sendDataString(client_socket, message);
        break;    
    default:
        break;
    }

    closeSQL(conn);
	free(SQLrequest);
	free(message);
	free(token);
	free(domanda);
    close(client_socket);
    printf("Closing Connection.\n");
}

void sendDataTable(PGresult *table, int client_socket){
    char buffer[BUFSIZE];
    char *field;
    printf("flag2");
    //size_t bytes_read;
    int rows = PQntuples(table);
    int columns = PQnfields(table);
    
    for(int i=0;i<=rows;i++){
    	for(int j=0;j<=columns;j++){
    		strcpy(field, PQgetvalue(table,i,j) );
    		strcpy(buffer, field);
    		write(client_socket,buffer,strlen(field));
		}
	}
}

void sendDataString(int client_socket, char* string){
    char buffer[BUFSIZE];
    strcpy(buffer, string);
    write(client_socket,buffer,strlen(string));
}

