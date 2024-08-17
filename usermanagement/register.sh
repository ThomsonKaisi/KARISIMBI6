#!/bin/bash

email=$1
login_status='false'
firstName=$2
lastName=$3
birthDate=$4
isHiv=$5
isOnART=$6
diagnosisDate=$7
artStartDate=$8
country=$9
encryptedPassword=${10}
file=${11}
user_data=${13}

#  private key file
PRIVATE_KEY_FILE=${12}
temp_file=$(mktemp)
user_store="user-store.txt"


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


echo "$email,$hashedPassword,$login_status,$firstName,$lastName,$birthDate,$isHiv,$diagnosisDate,$isOnART,$artStartDate,$country" >> $file


email_check=$(grep "$email," "$file" | cut -d "," -f1)

if [ -z "$email_check" ]; then
  echo "false"
else
  grep -v "^$email," "$user_store" > "$temp_file"
  mv "$temp_file" "$user_store"
  echo "true"
fi
