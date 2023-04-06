#ifndef PSQL_H
#define PSQL_H

void* handle_connection(void* client_socket);
int check (int exp, const char *msg);
void* thread_function(void* args);
void closeSQL();
void insertSQL(char *query);
void querySQL (char *query);

#endif
