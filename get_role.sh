#!/bin/bash
email=$1
file="user-data.csv"

role=$(grep "$email," "$file" | cut -d "," -f13)

if [ -z "$role" ]; then
  echo "false"
else
  echo "$role"

fi