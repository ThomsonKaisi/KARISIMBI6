#!/bin/bash

email=$1
firstName=$2
file='user-data.csv'

# updating user fields
awk -F, -v email="$email" -v firstName="$firstName" 'BEGIN {OFS=FS} $1 == email {$4 = firstName}1' "$file" > temp.csv && mv temp.csv "$file"

echo "true"