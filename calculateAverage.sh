#!/bin/bash

file="patient-life-expectancy.csv"

sum=0
count=0

while IFS=, read -r email number country; do
    # Skip the header row if present
    if [[ $email != "email" ]]; then
        # Ensure number is valid
        if [[ $number =~ ^[0-9]+(\.[0-9]+)?$ ]]; then
            sum=$(echo "$sum + $number" | bc)
            count=$((count + 1))
        fi
    fi
done < "$file"

# Calculate average
if [[ $count -gt 0 ]]; then
    average=$(echo "scale=2; $sum / $count" | bc)
    echo "$average"
else
    echo "No valid data to calculate average."
fi