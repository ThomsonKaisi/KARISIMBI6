

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Admin extends User {
  public Admin(String email){
      super(email);
  }
    public boolean registration(String email,String phone){
    String script= "./addUser.sh " +email;

    try {
        Process process = Runtime.getRuntime().exec(new String[]{"sh", "-c", script});
        InputStream stdout = process.getInputStream();
        boolean status = Boolean.parseBoolean(streamData(stdout));
        Process process2 = sendData("add_patient_directory.sh "+email+" "+phone);
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
    private String streamData(InputStream inputStream,int num) {
        StringBuilder result = new StringBuilder();
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (Exception e) {
            // Handle the exception or log it
        }
        return result.toString();
    }

public String getAverageLifeExpectancy(){
Process process=sendData("calculateAverage.sh");
InputStream inputStream = process.getInputStream();
String average = streamData(inputStream);
return average;

}
public String getMedian(){
        Process process=sendData("median.sh");
        InputStream inputStream = process.getInputStream();
        String average = streamData(inputStream);
        return average;

    }
private Process sendData(String script){
        Process process= null;
        try {
            process =Runtime.getRuntime().exec(new String[]{"sh","-c","./"+script});
            return process;
        }catch (Exception e){

        }
        return process;
    }
    public String getAverageByCountry(){
        Process process=sendData("averagebyCountry.sh");
        InputStream inputStream = process.getInputStream();
        String average = streamData(inputStream,1);
        return average;

    }
}
