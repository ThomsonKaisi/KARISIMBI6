email=$1
file=$2
email_check=$(grep "$email," "$file" | cut -d "," -f4-11)

echo "$email_check"