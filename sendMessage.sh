#!/bin/bash
file1="patient-directory.csv"
file2="user-data.csv"

matching_phones=()

while IFS=, read -r email phone; do
    status=$(awk -F, -v email="$email" '$1 == email {print $7}' "$file2")

    if [ "$status" == "true" ]; then
        matching_phones+=("$phone")
    fi
done < "$file1"


for phone in "${matching_phones[@]}"; do
     phone=$phone
     message="Good Morning!! You are reminded to take Your ART therapy. Have a good day!!"
     URL="http://192.168.231.55:8082"
     AUTH_TOKEN="5cd61c82-692f-4865-b8ea-5252524ec2be"
     JSON_PAYLOAD='{
       "to": "'$phone'",
       "message": "'$message'"
     }'
     curl -X POST "$URL" \
          -H "Content-Type: application/json" \
          -H "Authorization: $AUTH_TOKEN" \
          -d "$JSON_PAYLOAD"
done
