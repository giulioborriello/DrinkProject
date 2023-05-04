#ifndef SOCKET_LIB_H
#define SOCKET_LIB_H

#define THREAD_POOL_SIZE 20
#define SOCKETERROR (-1)
#define SEPARATOR "#"

typedef struct sockaddr_in SA_IN;
typedef struct sockaddr SA;

void* handle_connection(void* client_socket_input);

int check(int exp, const char *msg);

void* thread_function(void* args);

#endif