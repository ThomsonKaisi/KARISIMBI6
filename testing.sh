#!/bin/bash

csv_file="user-store.txt"
email=$1


temp_file=$(mktemp)


grep -v "^$email," "$csv_file" > "$temp_file"


mv "$temp_file" "$csv_file"

echo "Entry with email $email has been deleted from $csv_file."
