#ifndef SOCK_H
#define SOCK_H

#define BUFSIZE 4096

typedef struct sockaddr_in SA_IN;
typedef struct sockaddr SA;

void send_string(int socket, char *str);
void recv_string(int socket, char *str);

#endif

