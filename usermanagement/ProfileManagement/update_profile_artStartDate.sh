#!/bin/bash

email=$1
artStartDate=$2
file='user-data.csv'

# updating user fields
awk -F, -v email="$email" -v artStartDate="$artStartDate" 'BEGIN {OFS=FS} $1 == email {$10 = artStartDate}1' "$file" > temp.csv && mv temp.csv "$file"

echo "true"