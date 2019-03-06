#include <stdio.h>
#include "msg.h"

int main (int argc, char **argv) {
    CLIENT *clnt;
    int *result;
    char *server;
    char *message;

    if(argc != 3) {
        fprintf(stderr, "uso: %s maquina mensaje\n", argv[0]);
        exit(1);
    }

    server = argv[1];
    message = argv[2];

    clnt = clnt_create(server, MESSAGEPROG, PRINTMESSAGEVERS, "tcp");

    if(clnt == (CLIENT *) NULL) {
        clnt_pcreateerror(server);
        exit(1);
    }

	printf("Este es el mensaje que se enviará: %s\n", message);
	printf("Este es el servidor al que se dirige la llamada: %s\n", server);
	
    result = printmessage_1(&message, clnt);

    if(result == (int *) NULL) {
        clnt_perror(clnt, server);
        exit(1);
    }

    if(*result == 0) {
        fprintf(stderr, "%s: no pudo imprimir el mensaje\n", argv[0]);
        exit(1);
    }

    printf("Mensaje enviado a %s\n", server);
    clnt_destroy(clnt);

    exit(0);
}
