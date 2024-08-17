#!/bin/bash
country=$1
life_expectancy_file="life-expectancy.csv"


life_expectancy=$(grep "$country," "$life_expectancy_file" | cut -d "," -f7)

if [ -z "$life_expectancy" ]; then
  echo "0"
else
  echo "$life_expectancy"
fi
