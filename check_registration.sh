#!/bin/bash
email=$1
uuid=$2
user_data=$3

result=$(grep "$email," "$user_data" | cut -d "," -f2)
if [ -z "$result" ]; then
    echo 'false'
else
  if [ "$uuid" = "$result" ];then
    echo "true"
  else
    echo "false"
  fi
fi