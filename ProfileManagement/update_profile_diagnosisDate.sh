#!/bin/bash

email=$1
diagnosisDate=$2
file='user-data.csv'


awk -F, -v email="$email" -v diagnosisDate="$diagnosisDate" 'BEGIN {OFS=FS} $1 == email {$8 = diagnosisDate}1' "$file" > temp.csv && mv temp.csv "$file"

echo "true"