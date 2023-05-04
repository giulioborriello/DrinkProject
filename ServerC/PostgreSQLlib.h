#ifndef PSQL_H
#define PSQL_H

#define SERVERSQL "hattie.db.elephantsql.com"
#define PORTSQL "5432"
#define USERSQL "belwxpmt"
#define PASSWDSQL "XrwgzZ7ArJ9IHywR-xoWQAb1CGT-Govq"

PGconn * connectSQL();
void closeSQL(PGconn *conn);
void insertSQL(char *query, PGconn *conn);
void querySQL (char *query, PGconn *conn);
void updateSQL(char *query, PGconn *conn);
void deleteSQL(char *query, PGconn *conn);

#endif
