#!/bin/bash
password=$1
key="public_key.pem"
encrypted_text=$(echo -n "$password" | openssl pkeyutl -encrypt -pubin -inkey "$key" | base64)

echo "$encrypted_text"
