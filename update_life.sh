#!/bin/bash
email=$1
life=$2
file="patient-life-expectancy.csv"
file2="user-data.csv"
country=$(grep "$email," "$file2" | cut -d "," -f11)

#checking email
email_exist=$(grep "$email," "$file" | cut -d "," -f1)

if [ -z "$email_exist" ]; then
#adding email
    echo "$email,$life,$country" >> "$file"
else
#appending email
    sed -i "/$email,/s/.*/$email,$life,$country/" "$file"
fi