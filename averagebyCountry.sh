#!/bin/bash

file="patient-life-expectancy.csv"

# Declare associative arrays to store sums and counts for each country
declare -A sums
declare -A counts

# Initialize sums and counts to zero
for country in $(cut -d, -f3 "$file" | tail -n +2 | sort | uniq); do
    sums["$country"]=0
    counts["$country"]=0
done

while IFS=, read -r email number country; do
    # Skip the header row if present
    if [[ $email != "email" ]]; then
        # Ensure number is valid
        if [[ $number =~ ^[0-9]+(\.[0-9]+)?$ ]]; then
            # Safely handle unset or empty sums
            current_sum=${sums["$country"]:-0}
            sums["$country"]=$(echo "$current_sum + $number" | bc)
            counts["$country"]=$((counts["$country"] + 1))
        fi
    fi
done < "$file"

# Calculate and print the average for each country
for country in "${!sums[@]}"; do
    sum=${sums["$country"]}
    count=${counts["$country"]}
    if [[ $count -gt 0 ]]; then
        average=$(echo "scale=2; $sum / $count" | bc)
        echo "$country: $average"
    else
        echo "No valid data for $country."
    fi
done
