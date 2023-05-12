#ifndef SOCKET_LIB_H
#define SOCKET_LIB_H

#define THREAD_POOL_SIZE 20
#define SOCKETERROR (-1)
#define SEPARATOR "#"

#define PATH_MAX_LOCAL 1024
#define SERVERPORT 8080
#define SOCKETERROR (-1)
#define SERVER_BACKLOG 100
#define THREAD_POOL_SIZE 20

#define BUFSIZE 4096

typedef struct sockaddr_in SA_IN;
typedef struct sockaddr SA;

void* handle_connection(void* client_socket_input);
int check(int exp, const char *msg);
void sendDataTable(PGresult *table, int client_socket);
void sendDataString(int client_socket, char* string);
void dividi_stringa(char * stringaInput, char * stringaOutput1, char * stringaOutput2);

#endif