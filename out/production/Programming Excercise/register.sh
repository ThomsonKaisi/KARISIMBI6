#!/bin/bash

email=$1
login_status='false'
firstName=$2
lastName=$3
birthDate=$4
isHiv=$5
isOnART=$5
diagnosisDate=$6
artStartDate=$7
country=$8
encryptedPassword=$9
file='user-data.csv'


# Define the private key file
PRIVATE_KEY_FILE=${10}

# Decode the encrypted password from Base64
echo "$encryptedPassword" | base64 -d > encrypted_password.bin

# Decrypt the password
decrypted_password=$(openssl pkeyutl -decrypt -inkey "$PRIVATE_KEY_FILE" -in encrypted_password.bin 2>/dev/null)

# Check if the decryption was successful
if [ -z "$decrypted_password" ]; then
    echo 'false'
else
    hashedPassword=$(echo -n "$decrypted_password" | sha256sum | awk '{print $1}')
fi


echo "$email,$hashedPassword,$login_status,$lastname,$dateOfbirth,$isHiv,$diagnosisDate,$isOnArt,$artStartDate,$country" >> $file

email_check=$(grep "$email," "$file" | cut -d "," -f1)

if [ -z "$email_check" ]; then
  echo "false"
else
  echo "true"

fi
