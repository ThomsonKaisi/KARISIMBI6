

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.io.InputStream;

public class Patient extends User {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private boolean hivPositive;
    private LocalDate diagnosisDate;
    private boolean onART;
    private LocalDate artStartDate;
    private String country;

    public Patient(String email){
        super(email);
    }

    public String getFirstName(){return firstName; }
    public void setFirstName(String firstName){this.firstName = firstName; }
    public String getLastName(){return lastName; }
    public void setLastName(String lastName){this.lastName = lastName; }
    public LocalDate getDateOfBirth(){ return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) {this.dateOfBirth = dateOfBirth; }
    public boolean isHivPositive(){ return hivPositive; }
    public void setHivPositive(boolean hivPositive) {this.hivPositive = hivPositive; }
    public LocalDate getDiagnosisDate(){return diagnosisDate; }
    public void setDiagnosisDate(LocalDate diagnosisDate) {this.diagnosisDate = diagnosisDate; }
    public boolean isOnART(){return onART; }
    public void setOnART(boolean onART){this.onART = onART; }
    public LocalDate getArtStartDate(){return artStartDate; }
    public void setArtStartDate(LocalDate artStartDate) {this.artStartDate = artStartDate; }
    public String getCountry(){return country; }
    public void setCountry(String country){this.country = country; }
    //Patient Complete Registration
    public boolean registration(String uuid,String firstName, String lastName, LocalDate birthDate, boolean isHiv, boolean isOnART, LocalDate diagnosisDate, LocalDate artStartDate, String country, String encryptedPassword) {
        if (checkRegistrationStatus(uuid)) {
            String script = "./register.sh " + this.email + " " + firstName + " " + lastName + " " + birthDate + " " + isHiv + " " + isOnART + " " + diagnosisDate + " " + artStartDate + " " + country+" \""+encryptedPassword+"\"";

            try {
                Process process = Runtime.getRuntime().exec(new String[]{"sh", "-c", script});
                InputStream input = process.getInputStream();
                InputStream error =process.getErrorStream();
                boolean status = Boolean.parseBoolean(streamData(input));
                calculateLifeExpectancy();
                return status;
            } catch (Exception e) {

            }

        }else {
            System.out.println("Not Registered Contact your Admin");
            return false;
        }
        return false;
    }
    //get profile
    public Object[] getProfile(){
        Object [] dataFields = new Object[9];
        //checking if the user is login
        if(checkLoginStatus()){
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"sh","-c", "./view_profile.sh " +this.email+ " user-data.csv"});
            InputStream inputStream =process.getInputStream();
            InputStream error = process.getErrorStream();
            String [] data = streamData(inputStream).split(",");
            for(int i=0;i<=data.length;i++){
                dataFields[i]=data[i];
            }
            return  dataFields;
        }catch (Exception e){

        }
    }else {
            return  null;
        }
        return dataFields;
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
    public boolean checkRegistrationStatus(String uuid){
        try{
            Process process = Runtime.getRuntime().exec(new String []{"sh","-c", "./check_registration.sh " +this.email+" "+uuid+" user-store.txt"});
            InputStream input = process.getInputStream();
            boolean status = Boolean.parseBoolean(streamData(input));
            return status;
        }catch (Exception e){

        }
        return false;
    }
    //updating profile all
    public boolean updateProfile(String firstName, String lastName, LocalDate birthDate, boolean isHiv, boolean isOnART, LocalDate diagnosisDate, LocalDate artStartDate, String country){
        if(checkLoginStatus()){
            String script = "./update_profile_all.sh " + this.email + " " + firstName + " " + lastName + " " + birthDate + " " + isHiv + " " + diagnosisDate + " " + isOnART + " " + artStartDate + " " + country;
            try{
                Process process = Runtime.getRuntime().exec(new String[]{"sh", "-c", script});
                InputStream input = process.getInputStream();
                boolean status = Boolean.parseBoolean(streamData(input));
                calculateLifeExpectancy();
                return status;
            }catch (Exception e){

            }
        }else{
            return false;
        }
        return false;
    }

    //updating profile with option
    public boolean updateProfile(int option, Object data) {
        if (checkLoginStatus()) {
            InputStream input;
            boolean status;

            if (option == 1) {
                String firstname = (String) data;
                input = sendData("ProfileManagement/update_profile_firstname.sh " + this.email + " " + firstname).getInputStream();
                status = Boolean.parseBoolean(streamData(input));
                return status;
            } else if (option == 2) {
                String lastname = (String) data;
                input = sendData("ProfileManagement/update_profile_lastname.sh " + this.email + " " + lastname).getInputStream();
                status = Boolean.parseBoolean(streamData(input));
                return status;
            } else if (option == 3) {
                LocalDate birthDate = (LocalDate) data;
                input = sendData("ProfileManagement/update_profile_birthdate.sh " + this.email + " " + birthDate).getInputStream();
                status = Boolean.parseBoolean(streamData(input));
                calculateLifeExpectancy();
                return status;
            } else if (option == 4) {
                boolean isHiv = (boolean) data;
                input = sendData("ProfileManagement/update_profile_isHIV.sh " + this.email + " " + isHiv).getInputStream();
                status = Boolean.parseBoolean(streamData(input));
                calculateLifeExpectancy();
                return status;
            } else if (option == 5) {
                boolean isOnART = (boolean) data;
                input = sendData("ProfileManagement/update_profile_isOnART.sh " + this.email + " " + isOnART).getInputStream();
                status = Boolean.parseBoolean(streamData(input));
                calculateLifeExpectancy();
                return status;
            } else if (option == 6) {
                LocalDate diagnosisDate = (LocalDate) data;
                String diagnosisDate2 = String.valueOf(diagnosisDate);
                input = sendData("ProfileManagement/update_profile_diagnosisDate.sh " + this.email + " " + diagnosisDate2).getInputStream();
                status = Boolean.parseBoolean(streamData(input));
                calculateLifeExpectancy();
                return status;
            } else if (option == 7) {
                LocalDate artStartDate = (LocalDate) data;
                input = sendData("ProfileManagement/update_profile_artStartDate.sh " + this.email + " " + artStartDate).getInputStream();
                status = Boolean.parseBoolean(streamData(input));
                calculateLifeExpectancy();
                return status;
            } else if (option == 8) {
                String country = (String) data;
                input = sendData("ProfileManagement/update_profile_country.sh " + this.email + " " + country).getInputStream();
                status = Boolean.parseBoolean(streamData(input));
                calculateLifeExpectancy();
                return status;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //sending data to a process
    private Process sendData(String script){
        Process process= null;
        try {
             process =Runtime.getRuntime().exec(new String[]{"sh","-c","./"+script});
             return process;
        }catch (Exception e){

        }
        return process;
    }

    //calculating Life expectancy

    public double calculateLifeExpectancy(){
        Object [] profile =getProfile();
        if(profile != null){
            LocalDate date_of_birth= LocalDate.parse((String)profile[2]) ;
            boolean hiv = Boolean.parseBoolean((String)profile[3]);
            boolean art = Boolean.parseBoolean((String)profile[5]);

            String country = (String) profile[7];
            //calculating age of a person
            Period period = Period.between(date_of_birth,LocalDate.now());
            int age = period.getYears();
            //years between art start date and diagonosis date
            double years;
            double life_expectancy = getLifeExpectancy(country);
            //checking if is hiv positive
            if(hiv){
                //calculating life expectancy
                LocalDate date_of_diagnosis = LocalDate.parse((String)profile[4]);
                if(art){
                    LocalDate art_start_date = LocalDate.parse((String)profile[6]);
                    //Calculate difference in years between ART startdate and dignosisdate
                    Period period2 = Period.between(date_of_diagnosis,art_start_date);
                    years=(double) period2.getYears()+1;

                    //calculate life expectancy based on years
                    double fraction =Math.pow(0.9,years);
                    double time_difference = life_expectancy-age;
                    double patient_life_expectancy = fraction*time_difference;
                    sendData("update_life.sh "+this.email+" "+patient_life_expectancy);
                    return patient_life_expectancy;

                }else{
                    Period period3 = Period.between(date_of_diagnosis,LocalDate.now());
                    int life = 5-period3.getYears();
                    if(life>0){
                        sendData("update_life.sh "+this.email+" "+life);
                        return life;
                    }else {
                        sendData("update_life.sh "+this.email+" 0");
                        return 0;
                    }

                }
            }else {
                //retur country's life expectancy - age
                double year2 = life_expectancy-age;
                sendData("update_life.sh "+this.email+" "+year2);
                return year2;
            }
        }

return 0.0;

    }
    private double getLifeExpectancy(String country){
        Process process = sendData("./country_life_expectancy.sh " +country);
        InputStream input = process.getInputStream();
        String response = streamData(input);
        if(response !=null) {
            double years = Double.parseDouble(response);
            return years;
        }else {
            return 0.0;
        }
    }


}
