#!/bin/bash
email=$1
file=$2

is_login=$(grep "$email," "$file" | cut -d "," -f3)

if [ -z "$is_login" ]; then
  echo "false"
else
  echo "$is_login"

fi