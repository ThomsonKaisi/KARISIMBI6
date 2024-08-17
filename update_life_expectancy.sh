#!/bin/bash

email=$1
life_expectancy=$2
file='user-data.csv'


awk -F, -v email="$email" -v life_expectancy="$life_expectancy" 'BEGIN {OFS=FS} $1 == email {$12 = life_expectancy}1' "$file" > temp.csv && mv temp.csv "$file"

echo "true"