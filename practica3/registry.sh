#!/bin/sh -e

if [ $# -eq 1 ]; then
	rmiregistry $1 &
else
	rmiregistry &
fi
