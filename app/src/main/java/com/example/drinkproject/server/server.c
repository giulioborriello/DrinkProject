#include <netinet/in.h> //structure for storing address information
#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h> //for socket APIs
#include <sys/types.h>

const unsigned int MAX_BUF_LENGHT = 4096;



int main(int argc, char const* argv[])
{

    // create server socket similar to what was done in
    // client program
    int servSockD = socket(AF_INET, SOCK_STREAM, 0);

    // define server address
    struct sockaddr_in servAddr;

    servAddr.sin_family = AF_INET;
    servAddr.sin_port = htons(5000);
    servAddr.sin_addr.s_addr = INADDR_ANY;

    // bind socket to the specified IP and port
    bind(servSockD, (struct sockaddr*)&servAddr, sizeof(servAddr));

    if(listen(servSockD, 1) != 0) {
        perror("Error to listening");
    }

    if (accept(servSockD, NULL, NULL) == -1) {
        perror("Error to accept");
    }

    while(1) {

    }


    return 0;
}
