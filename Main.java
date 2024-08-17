
//import usermanagement.*;
//import usermanagement.Authentication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Scanner;
import java.math.BigDecimal;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[]args){

        Runnable mainTask = () -> {
            while (true) {
                    loginMenu();
                try {
                    Thread.sleep(1000); // Sleep for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Secondary task
        Runnable secondaryTask = () -> {
            try {
                Thread.sleep(2000); // Sleep for 1 minute (60000 milliseconds)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            reminder();
        };

        Thread mainThread = new Thread(mainTask);
        mainThread.start();
        Thread secondaryThread = new Thread(secondaryTask);
        secondaryThread.start();

    }
    private static void verifyEmailUUID() throws Exception{
        String email;
        String uuid;
        System.out.print(" Email: ");
        email =reader.readLine();
        //System.out.println(" ");
        System.out.print(" UUID: ");
        uuid =reader.readLine();
        //System.out.println("");
        Patient patient = new Patient(email);
        boolean status = patient.checkRegistrationStatus(uuid);
        if(status){
            //call registration
            registration(uuid,patient);
        }else {
            System.out.println("Wrong Credentials! Please Try again or Contact the Admin");
        }
    }
    private static void registration(String uuid,Patient patient) {
        try {
        String firstname, lastname, country, encrypted_password, password;
        LocalDate date_of_birth, diagnosis_date, art_date;
        boolean isHIV, isOnART;
        int year, month, day, hiv, art;
        String passwordRe;
        do{
            System.out.println(" Enter New Password");
            char [] pass=System.console().readPassword();
            password = String.valueOf(pass);
            char [] passReEntered=System.console().readPassword("Enter Again");
            passwordRe=String.valueOf(passReEntered);
            System.out.println("");
        }while(!password.equals(passwordRe));

        encrypted_password = encrypt(password);

        System.out.println(" First Name");
        firstname = reader.readLine();
        System.out.println("");
        System.out.println(" Last Name");
        lastname = reader.readLine();
        System.out.println("");

        System.out.println("Country");
        country = reader.readLine();
        System.out.println("Date of Birth: YY-MM-DD");
        date_of_birth = getDate();
        System.out.println(" Are you HIV Positive");
        System.out.println(" 1. Yes");
        System.out.println(" 2. No");
        hiv = scanner.nextInt();
        System.out.println("");
        if (hiv == 1) {
            isHIV = true;
            System.out.println(" Date of Diagnosis (YY-MM-DD)");
            diagnosis_date = getDate();
            System.out.println(" Are you on ART Treatment");
            System.out.println(" 1. Yes");
            System.out.println(" 2. No");
            art = scanner.nextInt();
            System.out.println("");
            if (art == 1) {
                isOnART = true;
                System.out.println(" When did you Started Treatment? (YY-MM-DD)");

                art_date = getDate();
                if(diagnosis_date.isAfter(art_date)){
                    System.out.println(" Start date can not be greater than Diagnosis date");
                    art_date = getDate();
                }
                boolean status = patient.registration(uuid, firstname, lastname, date_of_birth, isHIV, isOnART, diagnosis_date, art_date, country, encrypted_password);
                if (status) {
                    System.out.println("Registered Successfully");
                    login();
                } else {
                    System.out.println("An Error Occured Please Try Again:");
                    registration(uuid, patient);
                }


            }else {
                isOnART=false;
                art_date=null;
                boolean status = patient.registration(uuid, firstname, lastname, date_of_birth, isHIV, isOnART, diagnosis_date, art_date, country, encrypted_password);
                System.out.println("Registered Successfully");
                login();
            }

        } else if (hiv == 2) {
            isHIV = false;
            isOnART = false;
            diagnosis_date = null;
            art_date = null;
            boolean status = patient.registration(uuid, firstname, lastname, date_of_birth, isHIV, isOnART, diagnosis_date, art_date, country, encrypted_password);
            if (status) {
                System.out.println("Registered Successfully");
                login();
            } else {
                System.out.println("An Error Occured Please Try Again");
                registration(uuid, patient);
            }

        }
    }catch(Exception e){
            System.out.println(e.toString());
        }

    }
    private static void mainMenu(){
        System.out.println("1. Login");
        System.out.println("2. Registration");
        System.out.println("3. Exit");
        int choice = scanner.nextInt();
        System.out.println();
        if(choice==1){
            login();
        } else if (choice==2) {
            try {
                verifyEmailUUID();
            }catch (Exception e){
                System.out.println(e);
            }
        } else if (choice==3) {
            System.exit(0);
        } else {
            System.out.println("Invalid Input");
            mainMenu();
        }

    }
    private static void adminLogin() {
        try{
        System.out.println("Enter Email");
        String email = reader.readLine();
        System.out.println("Enter Password");
        char [] pass=System.console().readPassword();
        String password = String.valueOf(pass);
        String encrypted = encrypt(password);
        Authentication auth = new Authentication(email);
        boolean status = auth.login(encrypted);
        if (status) {
            Admin admin = new Admin(email);
            adminMenu(admin, auth);
        } else {
            System.out.println("Wrong Credentials");
            adminLogin();
        }
    }catch(Exception e){

    }
    }
    private static void patientMenu(Patient patient,Authentication auth){
        System.out.println("1. View Profile");
        System.out.println("2. Update Profile");
        System.out.println("3. Check Survival Rate");
        System.out.println("4. Logout");
        int choice = scanner.nextInt();
        System.out.println();
        if(choice==1){
            //view profile
            Object[] profile = patient.getProfile();
            if(profile!=null){
                patient.calculateLifeExpectancy();
                System.out.println("First Name: "+(String)profile[0] );
                System.out.println("Last Name: "+(String)profile[1] );
                System.out.println("Date of Birth: "+(String)profile[2] );
                if(Boolean.parseBoolean((String)profile[3])){
                    System.out.println("HIV Status: Positive");
                    System.out.println("Diagnosis Date: "+(String)profile[4] );
                    if(Boolean.parseBoolean((String)profile[5])){
                        System.out.println("ART Status: Active" );
                        System.out.println("ART Start Date: "+(String)profile[6] );
                    }else{
                        System.out.println("ART Status: InActive" );
                    }
                }else {
                    System.out.println("HIV Status: Negative");
                }
                System.out.println("Country     : "+(String)profile[7] );
                double life_expectancy = patient.calculateLifeExpectancy();
                BigDecimal bigDecimal = new BigDecimal(String.valueOf(life_expectancy));
                BigDecimal years = new BigDecimal(bigDecimal.intValue());
                BigDecimal decimalPart = bigDecimal.subtract(years);
                double months = decimalPart.doubleValue()*12;
                int month= (int)months;
                System.out.println("Estimated Years Remaining : "+years+" years, "+month+" Months");
                patientMenu(patient,auth);

            }else{
                System.out.println("Profile not found");
            }

        } else if (choice==2) {
            //update profile
            update_profile_prompt(patient,auth);
        } else if (choice==3) {
            //check life expectancy
            double life_expectancy = patient.calculateLifeExpectancy();
            BigDecimal bigDecimal = new BigDecimal(String.valueOf(life_expectancy));
            BigDecimal years = new BigDecimal(bigDecimal.intValue());
            BigDecimal decimalPart = bigDecimal.subtract(years);
            double months = decimalPart.doubleValue()*12;
            int month= (int)months;
            System.out.println("Estimated Years Remaining : "+years+" years, "+month+" Months");
            patientMenu(patient,auth);
        } else if(choice==4){
            auth.logout();
            loginMenu();
        }else {
            System.out.println("Invalid Input");
            patientMenu(patient,auth);
        }


    }
    private static void adminMenu(Admin admin,Authentication auth) {
        try{
        System.out.println("1. Register User");
            System.out.println("2. Download User Data");
        System.out.println("3. Get Analytics Data");
        System.out.println("4. Send a Reminder");
        System.out.println("5. Logout");
        int choice = scanner.nextInt();
        if (choice == 1) {
            System.out.println("Enter User Email");
            String email = reader.readLine();
            System.out.print("Phone: ");
            String phone = reader.readLine();
            admin.registration(email,phone);
            System.out.println("Operation Successful");
            adminMenu(admin,auth);
        } else if (choice == 3) {
            System.out.println("Analytics");
            System.out.println("Average: "+admin.getAverageLifeExpectancy());
            System.out.println("Median: "+admin.getMedian());
            System.out.println("----------Average By Nation ---------------");
            System.out.println(admin.getAverageByCountry());

            adminMenu(admin,auth);
        } else if (choice == 4) {
           reminder();
           System.out.println("ART Reminder Send Successfuly");
            adminMenu(admin,auth);
        }else if(choice ==5){
            auth.logout();
            loginMenu();
        } else if (choice ==2) {
            System.out.println("User Data Downloaded Successfuly");
            adminMenu(admin,auth);
        }
        }catch(Exception e){

    }


    }
    private static void register_user(){
        System.out.println("Enter Email");
        System.out.println("Enter Phone");

    }
    private static void loginMenu(){
        System.out.println("1. Continue as A Patient");
        System.out.println("2. Continue as Admin");
        int choice= scanner.nextInt();
        if(choice==1){
            mainMenu();
        } else if (choice==2) {
           adminLogin();
           loginMenu();
        }
    }
    private static void login(){
        try {
            String email;
            String password;
            String encrypted_password;
            System.out.println("Enter Email");
            email = reader.readLine();
            System.out.println();
            System.out.println("Enter password");
            char[] pass = System.console().readPassword();
            password = String.valueOf(pass);
            encrypted_password = encrypt(password);
            Authentication authentication = new Authentication(email);
            boolean auth =authentication.login(encrypted_password);
            if(auth){
                Patient patient =new Patient(email);
                patientMenu(patient,authentication);
            }else {
                System.out.println("Wrong Credentials!!");
                login();
            }


        }catch (Exception e){
            System.out.println(e.toString());
        }

    }

    private static String encrypt(String password){
        String special ="\"";
        Process process =sendData("encrypt.sh "+password);
        InputStream inputStream = process.getInputStream();
        String encrypted_password = streamData(inputStream);
        return encrypted_password;
    }

    private static Process sendData(String script){
        try {
           Process process =Runtime.getRuntime().exec(new String[]{"sh","-c","./"+script});
            return process;
        }catch (Exception e){

        }
        return null;
    }

//    private static String streamData(InputStream inputStream){
//        String line;
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//        try{
//            while((line = bufferedReader.readLine()) !=null){
//                line = bufferedReader.readLine();
//            }
//            return line;
//        }catch(Exception e){
//
//        }
//        return null;
//    }

    private static String streamData(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append(System.lineSeparator());
            }
        } catch (Exception e) {
            e.printStackTrace(); // Or handle the exception as needed
            return null; // Optionally return null or an error message
        } finally {
            try {
                bufferedReader.close(); // Ensure the BufferedReader is closed
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
private static LocalDate getDate(){
    int year,month,day;
    LocalDate diagnosis_date;
    System.out.println(" Year: ");
    year = scanner.nextInt();
    System.out.println("");
    System.out.println(" Month: ");
    month = scanner.nextInt();
    System.out.println("");
    System.out.println(" Day: ");
    day = scanner.nextInt();
    System.out.println("");
    diagnosis_date = LocalDate.of(year, month, day);
    LocalDate today = LocalDate.now();
    if(!diagnosis_date.isAfter(today)){
        return diagnosis_date;
    }else{
        System.out.println("The date can not be greater than today: "+today);
        getDate();
    }
return today;
}
    private static void update_profile_prompt(Patient patient,Authentication authentication) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Birth Date");
        System.out.println("4. HIV Status");
        System.out.println("5. Country of Residence");
        System.out.println("6. Exit");
        try{
        int option = scanner.nextInt();
        String input;
        int input2;

        switch (option) {
            case 1:
                System.out.println("Please enter your first name:");
                input = reader.readLine();
                patient.updateProfile(1, input);
                break;
            case 2:
                System.out.println("Please enter your last name:");
                input = reader.readLine();
                patient.updateProfile(2, input);
                break;
            case 3:
                System.out.println("Please enter your birth date:");
                LocalDate date_of_birth =getDate();
                patient.updateProfile(3,date_of_birth);
                break;
            case 4:
                System.out.println("Please enter your HIV status:");
                System.out.println("1. Positive");
                System.out.println("2. Negative");
                input2 = scanner.nextInt();
                if(input2==1){
                    System.out.println("When were you diagnosed: YY-MM-DD");
                    LocalDate date_of_diagnosis =getDate();
                    patient.updateProfile(6,date_of_diagnosis);
                    patient.updateProfile(4,true);
                System.out.println("Are you on ART?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                input2=scanner.nextInt();
                if(input2==1){
                    System.out.println("When did you start: YY-MM-DD");
                    LocalDate startDate=getDate();
                    patient.updateProfile(5, true);
                    patient.updateProfile(7, startDate);
                    System.out.println("Operation Successful!");
                    patientMenu(patient,authentication);
                } else{
                    patientMenu(patient,authentication);
                }
                } else if (input2==2) {
                    patient.updateProfile(4,false);
                    patientMenu(patient,authentication);
                }else {
                    System.out.println("Invalid choice");
                    update_profile_prompt(patient,authentication);
                }

                break;
            case 5:
                System.out.println("Please enter your country of residence:");
                input = reader.readLine();
                patient.updateProfile(8, input);
                patientMenu(patient,authentication);
            case 6:
                patientMenu(patient,authentication);
                break;
        }
        patientMenu(patient,authentication);
    }catch(Exception e){

    }
//        return option;

    }

    private static void reminder(){
        Process process=sendData("sendMessage.sh");
    }

}
