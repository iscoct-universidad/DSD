#!/bin/sh -e

./registry.sh

sleep 1

./javac.sh ProgramaBroker localhost 1099 Broker

sleep 1

./javac.sh ProgramaServidor localhost 1099 Servidor1 localhost 1099 Broker

sleep 1

./javac.sh ProgramaServidor localhost 1099 Servidor2 localhost 1099 Broker
