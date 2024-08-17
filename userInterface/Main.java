package userInterface;
import usermanagement.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Scanner;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[]args){
    do{
        mainMenu();
    }while (true );//choice !=3);
    }
    private static boolean verifyEmailUUID(){
        String email;
        String uuid;
        System.out.println(" Email");
        email =scanner.nextLine();
        scanner.close();
        System.out.println(" UUID");
        uuid =scanner.nextLine();
        scanner.close();
        Patient patient = new Patient(email);
        boolean status = patient.checkRegistrationStatus(uuid);
        if(status){
            //call registration
            registration(uuid,patient);
        }else {
            System.out.println("Wrong Credentials! Please Try again or Contact the Admin");
        }

    }
    private static void registration(String uuid,Patient patient){
        String firstname,lastname,country,encrypted_password,password;
        LocalDate date_of_birth,diagnosis_date,art_date;
        boolean isHIV,isOnART;
        int year,month,day,hiv;

        System.out.println(" Enter New Password");
        password=scanner.nextLine();
        scanner.close();
        encrypted_password=encrypt(password);

        System.out.println(" First Name");
        firstname=scanner.nextLine();
        scanner.close();
        System.out.println(" Last Name");
        lastname=scanner.nextLine();
        scanner.close();

        System.out.println("Country");
        country=scanner.nextLine();
        scanner.close();

        System.out.println(" Date of Birth (YY-MM-DD)");
        System.out.println(" Year: ");
        year =scanner.nextInt();
        scanner.close();
        System.out.println(" Month: ");
        month =scanner.nextInt();
        scanner.close();
        System.out.println(" Day: ");
        day =scanner.nextInt();
        scanner.close();
        date_of_birth=LocalDate.of(year,month,day);
        System.out.println(" Are you HIV Positive");
        System.out.println(" 1. Yes");
        System.out.println(" 2. No");
        hiv =scanner.nextInt();
        scanner.close();
        if(hiv==1){
            isHIV=true;
            System.out.println(" Date of Diagnosis (YY-MM-DD)");
            System.out.println(" Year: ");
            year =scanner.nextInt();
            scanner.close();
            System.out.println(" Month: ");
            month =scanner.nextInt();
            scanner.close();
            System.out.println(" Day: ");
            day =scanner.nextInt();
            scanner.close();
            diagnosis_date=LocalDate.of(year,month,day);
            System.out.println(" Are you on ART Treatment");
            System.out.println(" 1. Yes");
            System.out.println(" 2. No");

        }else if(hiv==2) {
            isHIV=false;
            isOnART=false;
            diagnosis_date=null;
            art_date =null;
            boolean status =patient.registration(uuid,firstname,lastname,date_of_birth,isHIV,isOnART,diagnosis_date,art_date,country,encrypted_password);
            if(status){
                System.out.println("Registered Successfully");
                login();
            }else {
                System.out.println("An Error Occured Please Try Again");
                registration(uuid,patient);
            }

        }


    }
    private static void mainMenu(){
        System.out.println("1. Login");
        System.out.println("2. Registration");
        System.out.println("3. Exit");

    }
    private static void patientMenu(){
        System.out.println("1. View Profile");
        System.out.println("2. Update Profile");
        System.out.println("3. Check Survival Rate");
        System.out.println("4. Logout");
    }
    private static void adminMenu(){
        System.out.println("1. Register User");
        System.out.println("2. Download User Profile");
        System.out.println("3. Logout");
    }
    private static void register_user(){
        System.out.println("Enter Email");
        System.out.println("Enter Phone");

    }
    private static void loginMenu(){
        System.out.println("1. Continue as A Patient");
        System.out.println("2. Continue as Admin");
    }
    private static void login(){
        System.out.println("Enter Email");
        System.out.println("Enter password");
        Authentication authentication = new Authentication("jwj");
        authentication.login("pass");

    }

    private static String encrypt(String password){
       InputStream inputStream = sendData("encrypt "+password).getInputStream();
       String encrypted_password = streamData(inputStream);
       return encrypted_password;
    }

    private static Process sendData(String script){
        Process process= null;
        try {
            process =Runtime.getRuntime().exec(new String[]{"sh","-c","./"+script});
            return process;
        }catch (Exception e){

        }
        return process;
    }

    private static String streamData(InputStream inputStream){
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
