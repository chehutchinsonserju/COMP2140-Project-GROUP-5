import java.util.*;
import java.util.List;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ManageCustomer {
    private Scanner scan = new Scanner(System.in);
    static File f = new File("Customers.txt");
    String input;

    public Customer RegisterCustomer(){
        String fName, lName, email, pNum, address, id;
        int age;

        do{
            System.out.println("Enter the customer's first name.");
            fName = scan.nextLine();
        }while(!(isName(fName)));
        do{
            System.out.println("Enter the customer's last name.");
            lName = scan.nextLine();
        }while(!(isName(lName)));
        do{
            System.out.println("Enter the customer's email.");
            email = scan.nextLine();
        }while(!(isEmail(email)));
        do{
            System.out.println("Enter the customer's phone number.");
            pNum = scan.nextLine();
            if(pNum.length()==7){
                pNum = "876"+pNum;
            }
        }while(!(isPhone(pNum)));
        do{
            System.out.println("Enter the customer's address.");
            address = scan.nextLine();
        }while(!(isAddress(address)));
        do{
            System.out.println("Enter the customer's age.");
            age = scan.nextInt();
        }while(!(isAge(age)));
        do{
            System.out.println("Assign an ID to the customer.");
            id = scan.nextLine();
        }while(!(isID(id)));

        Customer c = new Customer(fName, lName, email, pNum, address, age, id);
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(f,true));
            writer.write(c.getfName()+"!"+c.getlName()+"!"+c.getEmail()+"!"+c.getPhone()+"!"+c.getAddress()+"!"+c.getAge()+"!"+c.getID()+"\n");

            writer.close();
        }catch(Exception e){return c;}

        return c;
    }

    

    public static Customer SearchCustomer(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line;
            int i = 0;
            List<String> rdata = new ArrayList<String>();
            
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter the ID you want to search");
            String input = scan.nextLine();

            while((line = reader.readLine()) != null){
                
                rdata.add(line);
                
                String[] dataFound = rdata.get(i).split("!");
                if(input.equals(dataFound[6])){
                    return new Customer(dataFound[0],dataFound[1],dataFound[2],dataFound[3],dataFound[4],Integer.valueOf(dataFound[5]),dataFound[6]);
                    
                }
                i = i + 1;
            }
            System.out.println("Customer not found.");
            return new Customer("empty");
        }catch(Exception e){return new Customer("empty");}
    }

    public boolean isName(String name) {
        char[] chars = name.toCharArray();
        for (char c : chars) {
            if((!Character.isLetter(c) && !(c=='-') && !(c==' '))) {
            //if the name contains characters aside from letters, "-" or a space, it is invalid
                System.out.println("Invalid Name");
                return false;
            }
        }
        return true;
    }

    public boolean isEmail(String email) {
        int atCount = 0;
        int dotCount = 0;
        char[] chars = email.toCharArray();

        if (!Character.isLetter(email.charAt(0)) && !Character.isDigit(email.charAt(0))){
        //if the email does not begin with a letter or number, it is invalid
            System.out.println("Invalid Email");
            return false;
        }else{
            for (char c : chars){
                if((!Character.isLetter(c) && !(Character.isDigit(c)) && !(c=='@') && !(c=='.'))) {
                //if the email contains characters that are not letters, numbers, '@' or '.', it is invalid
                    System.out.println("Invalid Email");
                    return false;
                }
                if(c == '@'){
                //counting the number of '@' in the email
                    atCount = atCount + 1;
                }
                if(c == '.'){
                //counting the number of '.' in the email
                    dotCount = dotCount + 1;
                }
            }
            if((atCount != 1) || (dotCount < 1)){
            //if the email does not include only 1 '@' and at least 1 '.', it is invalid
                System.out.println("Invalid Email");
                return false;
            }

            return true;
        }
    }
    
    public boolean isAddress(String address) {
        boolean hasLetter = false;
        boolean hasNum = false;

        char[] chars = address.toCharArray();
        for (char c : chars) {
            if(Character.isLetter(c)){
            //checks if the address has letters
            
                hasLetter = true;
            }
            if(Character.isDigit(c)){
            //checks if the address has numbers
                hasNum = true;
            }
        }
        if((hasLetter == true) && (hasNum == true)){
        //if the address does not have both a number and letters, it is invalid
            return true;
        }else{
            System.out.println("Invalid Address");
            return false;
        }
    }
    
    public boolean isAge(int age){
    //returns true if the age is a valid human age between 0 and 130
        if((age >= 0) && (age <= 130)){
            return true;
        }else{
            System.out.println("Invalid Age");
            return false;
        }
    }

    public boolean isPhone(String pNum) {
        if (pNum.length() != 10){
            //a phone number must be 10 digits including its area code to be valid
            System.out.println("Invalid Phone Number");
            return false;
        }else{
            if ((pNum.charAt(3) == '0') || (pNum.charAt(3) == '1')){
            //if the number after the area code begins with 0 or 1, it is invalid
                System.out.println("Invalid Phone Number");
                return false;
            }else{
                char[] num = pNum.toCharArray();
                for (char n : num) {
                    if(!(Character.isDigit(n))){
                    //if any character is not a digit, the number is invalid
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isID(String id) {
        boolean hasLetter = false;
        boolean hasNum = false;

        char[] chars = id.toCharArray();
        for (char c : chars) {
        //if the id has neither a letter nor number, it is invalid
            if(Character.isLetter(c)){
                hasLetter = true;
            }
            if(Character.isDigit(c)){
                hasNum = true;
            }
        }
        if((hasLetter == true) || (hasNum == true)){
            return true;
        }else{
            System.out.println("Invalid ID");
            return false;
        }
    }

}
