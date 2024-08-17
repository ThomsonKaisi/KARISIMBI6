#!/bin/bash

email=$1
lastName=$2
file='user-data.csv'

# updating user fields
awk -F, -v email="$email" -v lastName="$lastName" 'BEGIN {OFS=FS} $1 == email {$5 = lastName}1' "$file" > temp.csv && mv temp.csv "$file"

echo "true"