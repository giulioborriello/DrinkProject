CFLAGS=-g
BINS=ServerC
OBJS=ServerC.o CodaConnessioni.o

all: $(BINS)

ServerC: $(OBJS)
	$(CC) $(CFLAGS) -o $@ $^

%.o: %.c
	$(CC) $(CFLAGS) -c -o $@ $^

clean:
	rm -rf *.dSYM $(BINS)
