#!/bin/bash

email=$1
birthDate=$2
file='user-data.csv'

# updating user fields
awk -F, -v email="$email" -v birthDate="$birthDate" 'BEGIN {OFS=FS} $1 == email {$6 = birthDate}1' "$file" > temp.csv && mv temp.csv "$file"

echo "true"