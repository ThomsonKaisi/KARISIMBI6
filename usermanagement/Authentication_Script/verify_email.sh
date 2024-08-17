#!/bin/bash
email=$1
file=$2

uuid=$(grep "$email," "$file" | cut -d "," -f1)

if [ -z "$uuid" ]; then
  echo "false"
else
  echo "true"

fi
