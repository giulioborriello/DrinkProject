#!/bin/bash
SERVICE="Server"
while [ 1 ]
do
    if ! pgrep -x "$SERVICE" >/dev/null
    then
        echo "$SERVICE era fermo, avvio"
        ./Server
    fi
done