

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class User {
    protected String firstName;
    protected String lastName;
    protected String email;
    protected UserRole role;

    public User(String firstName, String lastName, String email, UserRole role){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    public User(){}
    public User(String email){this.email=email;}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getUserRole() {
        return this.role;
    }

    public void setUserRole(UserRole userRole) {
        this.role = userRole;
    }

    public boolean checkLoginStatus(){
        try{
            Process process = Runtime.getRuntime().exec(new String [] {"sh","-c", "./Authentication_Script/check_login.sh " +this.email+ " user-data.csv"});
            InputStream input = process.getInputStream();
            boolean status = Boolean.parseBoolean(streamData(input));
            return status;
        }catch (Exception e){

        }
        return false;
    };

    //public abstract boolean registration(String email);

    private String streamData(InputStream inputStream){
        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            line = bufferedReader.readLine();
            return line;
        }catch(Exception e){

        }
        return null;
    }
}
