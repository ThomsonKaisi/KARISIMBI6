#!/bin/bash

email=$1
firstName=$2
lastName=$3
birthDate=$4
isHiv=$5
isOnART=$6
diagnosisDate=$7
artStartDate=$8
country=$9
file='user-data.csv'

# updating user fields
awk -F, -v email="$email" -v firstName="$firstName" -v lastName="$lastName" -v birthDate="$birthDate" -v isHiv="$isHiv" -v isOnART="$isOnART" -v diagnosisDate="$diagnosisDate" -v artStartDate="$artStartDate" -v country="$country" 'BEGIN {OFS=FS} $1 == email {$2 = firstName; $3 = lastName; $4 = birthDate; $5 = isHiv; $6 = isOnART; $7 = diagnosisDate; $8 = artStartDate; $9 = country}1' "$file" > temp.csv && mv temp.csv "$file"

echo "true"
