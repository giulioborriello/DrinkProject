#ifndef PSQL_H
#define PSQL_H

typedef struct nodo nodo_coda;

void* handle_connection(void* client_socket);
int check (int exp, const char *msg);
void* thread_function(void* args);

#endif
