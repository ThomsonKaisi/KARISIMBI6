#!/bin/bash

csv_file="user-store.txt"
email=$1

# Create a temporary file
temp_file=$(mktemp)

# Remove the line containing the email
grep -v "^$email," "$csv_file" > "$temp_file"

# Replace the original file with the temporary file
mv "$temp_file" "$csv_file"

echo "Entry with email $email has been deleted from $csv_file."
