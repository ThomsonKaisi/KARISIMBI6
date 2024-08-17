#!/bin/bash

email=$1
status=$2
user_data=$3

awk -F, -v email="$email" -v status="$status" 'BEGIN {OFS=FS} $1 == email {$3 = status} {print}' "$user_data" > temp && mv temp "$user_data"
