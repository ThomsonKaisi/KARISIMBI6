package usermanagement;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

class Authentication {
  private String email;

  public Authentication(String user_email){
      this.email = user_email;
  }


    public boolean login(String encrypted_password){
        if(verifyEmail()){
            if(verifyPassword(encrypted_password)){
                //change login status
                change_status("true");
                return true;
            }

        }
        System.out.println("Email");
        return false;

    }
    public boolean logout(){
        change_status("false");
        return true;
    }

    private boolean verifyEmail(){
        try{
            Process process = Runtime.getRuntime().exec(new String[]{"sh","-c", "usermanagement/Authentication_Script/verify_email.sh " +this.email+" "+ "usermanagement/user-data.csv"});
            process.waitFor();
            int e = process.exitValue();

            InputStream inputStream = process.getInputStream();
            InputStream sterr = process.getErrorStream();
            boolean status = Boolean.parseBoolean(streamData(inputStream));
            return status;

        }catch(Exception e){

        }
	System.out.println("inside email");
        return false;
    }

    private boolean verifyPassword(String encrypted_password){
        try{
            Process process = Runtime.getRuntime().exec(new String[]{"sh","-c","./Authentication_Script/verify_password.sh key.pem user-data.csv "+this.email+" "+encrypted_password});
            InputStream inputStream = process.getInputStream();
            boolean status = Boolean.parseBoolean(streamData(inputStream));
            return status;

        }catch(Exception e){

        }
	System.out.println("inside password func");
        return false;
    }
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
    //changing login or logout status

    private void change_status(String status){

        try{
            Process process = Runtime.getRuntime().exec(new String[]{"sh","-c", "usermanagement/Authentication_Script/change_login_status.sh " +this.email+" "+status+ "usermanagement/user-data.csv"});

        }catch(Exception e){

        }

    }
}
