#include "/usr/include/postgresql/libpq-fe.h"


#ifndef PSQL_H
#define PSQL_H

#define SERVERSQL "hattie.db.elephantsql.com"
#define PORTSQL "5432"
#define USERSQL "belwxpmt"
#define PASSWDSQL "XrwgzZ7ArJ9IHywR-xoWQAb1CGT-Govq"

void connectSQL();
void closeSQL();
void insertSQL(char *query);
void querySQL (char *query, int *rows, int *cols, PGresult *resQuery);
char* getValore(PGresult *resQuery, int col,int row);

#endif