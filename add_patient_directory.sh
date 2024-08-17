#!/bin/bash
email=$1
phone=$2

file="patient-directory.csv"
file2="user-store.txt"
uuid=$(grep "$email," "$file2" | cut -d "," -f2)

email_exist=$(grep "$email," "$file" | cut -d "," -f1)
message="Dear patient,here are your credentials: Email: $email, UUID: $uuid, Please keep them safe."
if [ -z "$email_exist" ]; then
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
else
    sed -i "/$email,/s/.*/$email,$phone/" "$file"
    URL="http://192.168.94.172:8082"
    AUTH_TOKEN="5cd61c82-692f-4865-b8ea-5252524ec2be"
    JSON_PAYLOAD='{
      "to": "'$phone'",
      "message": "'$message'"
    }'

    curl -X POST "$URL" \
         -H "Content-Type: application/json" \
         -H "Authorization: $AUTH_TOKEN" \
         -d "$JSON_PAYLOAD"


fi