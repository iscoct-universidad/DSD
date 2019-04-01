#!/bin/sh -e

if [ $# -ne 4 ] && [ $# -ne 3 ]; then
	echo "$0 <programaAEjecutar> <puertoRmiregistryPropio> <numeroExportado> [<puertoRmiregistryOtro>]"
else
	echo "Lanzando rmiregistry"
	rmiregistry $2 &
	
	echo "Compilando con javac ..."
	javac *.java

	sleep 2

	echo "Lanzando  $1"

	if [ $# -eq 3 ]; then
	java -cp . -Djava.security.manager -Djava.rmi.server.codebase=file:./ -Djava.rmi.server.hostname=localhost -Djava.security.policy=server.policy $1 $2 $3 $4
	else
		java -cp . -Djava.security.manager -Djava.rmi.server.codebase=file:./ -Djava.rmi.server.hostname=localhost -Djava.security.policy=server.policy $1 $2 $3
	fi
fi
