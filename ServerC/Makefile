CC=gcc
CFLAGS=-I/usr/include/postgresql
LDFLAGS=-L/usr/lib/x86_64-linux-gnu -lpq -lm -pthread
OBJ=CodaConnessioni.o SQL_Libreria.o ServerC.o

Server: $(OBJ)
	$(CC) $(LDFLAGS) $(OBJ) -o Server

CodaConnessioni.o: CodaConnessioni.c
	$(CC) $(CFLAGS) -c CodaConnessioni.c

SQL_Libreria.o: SQL_Libreria.c
	$(CC) $(CFLAGS) -c SQL_Libreria.c

ServerC.o: ServerC.c
	$(CC) $(CFLAGS) -c ServerC.c

clean:
	rm -f $(OBJ) Server

#in caso di mancato funzionamento del makefile, provare a compilare con il seguente comando:
#gcc CodaConnessioni.c SQL_Libreria.c ServerC.c -o Server -I/usr/include/postgresql -lpq