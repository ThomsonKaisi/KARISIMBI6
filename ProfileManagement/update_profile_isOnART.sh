#!/bin/bash

email=$1
isOnART=$2
file='user-data.csv'


awk -F, -v email="$email" -v isOnART="$isOnART" 'BEGIN {OFS=FS} $1 == email {$9 = isOnART}1' "$file" > temp.csv && mv temp.csv "$file"

echo "true"