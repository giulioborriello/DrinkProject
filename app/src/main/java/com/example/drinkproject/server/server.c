#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>

#define BUFFER_SIZE 1024

int main(int argc, char const* argv[])
{

    char buffer[BUFFER_SIZE];
    int server_socket, client_socket;
    struct sockaddr_in server_address, client_address;
    int client_address_lenght = sizeof(client_address);

     server_address.sin_family = AF_INET;
     server_address.sin_port = htons(5000);
     server_address.sin_addr.s_addr = INADDR_ANY;

    if ((server_socket = socket(AF_INET, SOCK_STREAM, 0)) == 0) {
            perror("Socket creation error");
            exit(EXIT_FAILURE);
    }

    if (bind(server_socket, (struct sockaddr *)&server_address, sizeof(server_address)) < 0) {
        perror("Bind error");
        exit(EXIT_FAILURE);
    }

    if(listen(server_socket, 1) != 0) {
        perror("Listen error");
        exit(EXIT_FAILURE);
    }

    if ((client_socket = accept(server_socket, (struct sockaddr *)&client_address, (socklen_t *)&client_address_lenght)) < 0) {
        perror("Accept error");
        exit(EXIT_FAILURE);
    }

    if(recv(client_socket, buffer, BUFFER_SIZE, 0) != 0) {
            perror("Accept error");
            exit(EXIT_FAILURE);
    }

    printf("%s", buffer);

    while(1) {

    }


    return 0;
}
