#!/bin/bash
password=$1
encrypted_text=$(echo -n "$password" | openssl pkeyutl -encrypt -pubin -inkey public_key.pem | base64)

echo "$encrypted_text"
