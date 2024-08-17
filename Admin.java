package usermanagement;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Admin extends User {
  Admin(){
      super();
  }
    public boolean registration(String email){
    String script= "usermanagement/addUser.sh " +email;
    try {
        Process process = Runtime.getRuntime().exec(new String[]{"sh", "-c", script});
        InputStream stdout = process.getInputStream();
        boolean status = Boolean.parseBoolean(streamData(stdout));
        return status;
    }catch (Exception e){

    }
    return false;
}

//stream data
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
