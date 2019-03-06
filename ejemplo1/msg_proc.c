#include <stdio.h>
#include "msg.h"

int *printmessage_1(char **msg, struct svc_req *req) {
    static int result;
    FILE *f;

    f = fopen("/dev/console", "w");

    if(f == (FILE *) NULL) {
        result = 0;

        return (&result);
    }

    fprintf(f, "%s\n", *msg);
    fclose(f);
    result = 1;
    
    return (&result);
}