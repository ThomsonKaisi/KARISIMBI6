#!/bin/bash

email=$1
isHIV=$2
file='user-data.csv'


awk -F, -v email="$email" -v isHIV="$isHIV" 'BEGIN {OFS=FS} $1 == email {$7 = isHIV}1' "$file" > temp.csv && mv temp.csv "$file"

echo "true"