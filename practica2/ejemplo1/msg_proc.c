#include <stdio.h>
#include "msg.h"

int *printmessage_1_svc(char **msg, struct svc_req *req) {
    static int result;
    
	printf ("Se ha recibido una nueva petición\n");
	printf("El primer argumento es el siguiente: %s\n", msg[0]);

    result = 1;
    
    printf("Result = %i\n", result);
    
    return (&result);
}
