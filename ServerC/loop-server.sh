#!/bin/bash

# Nome del programma C da eseguire
program_name="./Server"

# Funzione per controllare se il programma è in esecuzione
check_process() {
  local process_name="$1"
  local is_running=$(pgrep -x "$process_name")

  if [ -n "$is_running" ]; then
    return 0  # Il programma è in esecuzione
  else
    return 1  # Il programma non è in esecuzione
  fi
}

# Ciclo principale
while true; do
  if ! check_process "$program_name"; then
    echo "Il programma non è in esecuzione. Riavvio..."
    "$program_name" &  # Esegui il programma in background
  fi

  sleep 1  # Attendi un secondo prima di effettuare il controllo successivo
done
