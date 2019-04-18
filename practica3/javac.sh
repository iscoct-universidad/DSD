#!/bin/sh -e

javac *.java
java -cp . -Djava.security.manager -Djava.rmi.server.codebase=file:./ -Djava.rmi.server.hostname=localhost -Djava.security.policy=server.policy $1 $2 $3 $4 $5 $6 $7&
