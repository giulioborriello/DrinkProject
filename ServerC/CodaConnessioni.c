#include "CodaConnessioni.h"
#include <stdlib.h>

nodo_coda* head = NULL;
nodo_coda* tail = NULL;

/// @brief 
/// @param client_socket valore da metter nella coda
void accoda(int *client_socket){
    nodo_coda *newnode = malloc(sizeof(nodo_coda));
    newnode->client_socket = client_socket;
    newnode->next = NULL;
    if (tail==NULL)
    {
        head = newnode;
    }
    else
    {
        tail->next = newnode;
    }
    
    tail = newnode;
}

/// @brief 
/// @return valore in testa alla cosa
int * decoda(){
    if (head == NULL)
    {
        return NULL;

    }else
    {
        int *risultato = head->client_socket;
        nodo_coda *temp = head;
        head = head->next;
        if (head == NULL) { tail=NULL; }
        free(temp);
        return risultato;
    }
}
