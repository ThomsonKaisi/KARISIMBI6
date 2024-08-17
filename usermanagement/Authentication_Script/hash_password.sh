#!/bin/bash
email=$1
decrypted_password=$2
user_data=$3
hashed_string=$(echo -n "$decrypted_password" | sha256sum | awk '{print $1}')
result=$(awk -F "," -v email="$email" '$1 == email {print $2}' "$user_data")

if [ $hashed_string == $result ]; then
    echo "true"
else
    echo "false"
fi
