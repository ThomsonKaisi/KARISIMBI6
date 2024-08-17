email=$1
file=$2
email_check=$(grep "$email," "$file" | cut -d "," -f4-12)

echo "$email_check"