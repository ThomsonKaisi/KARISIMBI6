#!/bin/bash

email=$1
country=$2
file='user-data.csv'

# updating user fields
awk -F, -v email="$email" -v country="$country" 'BEGIN {OFS=FS} $1 == email {$11 = country}1' "$file" > temp.csv && mv temp.csv "$file"

echo "true"