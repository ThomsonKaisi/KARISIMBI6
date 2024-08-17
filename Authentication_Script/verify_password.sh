#!/bin/bash

# Define the encrypted password (ensure it's a single line)
ENCRYPTED_PASSWORD=$4

# Define the private key file
PRIVATE_KEY_FILE=$1
user_data=$2
email=$3

# Decode the encrypted password from Base64
echo "$ENCRYPTED_PASSWORD" | base64 -d > encrypted_password.bin

# Decrypt the password
decrypted_password=$(openssl pkeyutl -decrypt -inkey "$PRIVATE_KEY_FILE" -in encrypted_password.bin 2>/dev/null)

# Check if the decryption was successful
if [ -z "$decrypted_password" ]; then
    echo 'false'
else
    hashed_string=$(echo -n "$decrypted_password" | sha256sum | awk '{print $1}')
    result=$(grep "$email," "$user_data" | cut -d "," -f2)

    if [ "$hashed_string" == "$result" ]; then
        echo "true"
    else
        echo "false"
    fi
fi
