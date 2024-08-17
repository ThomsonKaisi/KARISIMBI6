email=$1
uuid=$(uuidgen)
echo "$email,$uuid" >> user-store.txt
echo "true"