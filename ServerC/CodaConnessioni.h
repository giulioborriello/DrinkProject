#ifndef CODA_H
#define CODA_H


struct nodo{
    struct nodo* next;
    int *client_socket;
};
typedef struct nodo nodo_coda;

void accoda(int *client_socket);
int * decoda();

#endif
