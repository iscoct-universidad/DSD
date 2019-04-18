#include "hoja.h"
#include <string.h>

registro *baseDeDatos;
int tam = 0;

char **
baja_1_svc(registro *argp, struct svc_req *rqstp)
{
	static char * result;

	printf("Nombre recibido: %s\n", argp -> nombre);
	printf("IP recibido: %s\n", argp -> ip);
	
	registro *aux;
	int encontrado = 0;
	int i = 0;

	while(! encontrado && i < tam) {
		if(strcmp(argp -> ip, baseDeDatos[i].ip))
			encontrado = 1;
		else
			++i;
	}
	
	if(encontrado) {
		printf("Nombre de la tupla %i: %s\n", i, baseDeDatos[i].nombre);
		
		for(int j = i + 1; j < tam; ++j)
			baseDeDatos[j - 1] = baseDeDatos[j];
			
		result = "Se ha eliminado la tupla correctamente";
	} else
		result = "La tupla no se encontraba en este servidor";

	return &result;
}
