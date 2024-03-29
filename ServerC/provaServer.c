#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <libpq-fe.h>


//gcc provaServer.c PostgreSQLlib.c -I/usr/include/postgresql  -lpq -o provaServerc
//librerie proprietarie


#include "PostgreSQLlib.h"

#define PORT 8080

int main(int argc, char const *argv[]) {
    int server_fd, new_socket, valread;
    struct sockaddr_in address;
    int addrlen = sizeof(address);
    char buffer[1024] = {0};
    char *hello = "Hello from server";

    // Creazione del socket
    if ((server_fd = socket(AF_INET, SOCK_STREAM, 0)) == 0) {
        perror("socket failed");
        exit(EXIT_FAILURE);
    }

    // Assegnazione dell'indirizzo IP e della porta al socket
    address.sin_family = AF_INET;
    address.sin_addr.s_addr = INADDR_ANY;
    address.sin_port = htons(PORT);

    // Binding del socket all'indirizzo e alla porta specificati
    if (bind(server_fd, (struct sockaddr *)&address, sizeof(address)) < 0) {
        perror("bind failed");
        exit(EXIT_FAILURE);
    }

    // Ascolto delle richieste di connessione in ingresso
    if (listen(server_fd, 3) < 0) {
        perror("listen failed");
        exit(EXIT_FAILURE);
    }

    // Accettazione di una connessione in ingresso
    if ((new_socket = accept(server_fd, (struct sockaddr *)&address, (socklen_t*)&addrlen)) < 0) {
        perror("accept failed");
        exit(EXIT_FAILURE);
    }

    // Invio di una stringa al client
//    send(new_socket, hello, strlen(hello), 0);
//    printf("Hello message sent\n");

    // Ricezione di una stringa dal client
    valread = read(new_socket, buffer, 1024);
    printf("Client message: %s\n", buffer);

    // Invio di una seconda stringa al client
    hello = "Another message from server\n";
    send(new_socket, hello, strlen(hello), 0);
    printf("Another message sent %s \n",hello);

    // Ricezione di una seconda stringa dal client
    valread = read(new_socket, buffer, 1024);
    printf("Client message: %s\n", buffer);
    


    //TODO: inizio della prova dump 
    //inzio connessione database postgresql
    printf("Inizio connessione...\n");
    PGconn *con=connectSQL();
    printf("Connessione effettuata\n");
    //INIZIO di un dump
    int rows, cols;
    //char *** tabella;
    PGresult *resQuery;
   printf("invio Query...\n");
   resQuery= querySQL("select nome, prezzo from drink", con, &rows, &cols);
    printf("inizio stampa...\n");

    printf("rows %d",rows);
    printf("cols %d",cols);
    printf("\n");
    printf("stringa %s",PQgetvalue(resQuery,0,0));
  
    
	for(int i=0;i<rows;i++){
		for(int j=0;j<cols;j++){
			hello=PQgetvalue(resQuery,i,j);
			send(new_socket, hello, strlen(hello), 0);
            hello = " ";
			send(new_socket, hello, strlen(hello), 0);
		}
        hello = "\n";
		send(new_socket, hello, strlen(hello), 0);
	}

    hello=PQgetvalue(resQuery,0,0);
    send(new_socket, hello, strlen(hello), 0); 

    return 0;
}


