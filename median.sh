#!/bin/bash

file="patient-life-expectancy.csv"

# Putting numbers into array
numbers=()
while IFS=, read -r email number country; do
    # Ensure number is valid
    if [[ $number =~ ^[0-9]+(\.[0-9]+)?$ ]]; then
        numbers+=("$number")
    fi
done < "$file"

# Sorting
sorted_numbers=($(printf '%s\n' "${numbers[@]}" | sort -n))

# Finding the middle number
length=${#sorted_numbers[@]}
if (( length % 2 == 1 )); then
    median=${sorted_numbers[$((length / 2))]}
else
    mid=$((length / 2))
    median=$(echo "scale=2; (${sorted_numbers[$mid-1]} + ${sorted_numbers[$mid]}) / 2" | bc)
fi

echo "$median"