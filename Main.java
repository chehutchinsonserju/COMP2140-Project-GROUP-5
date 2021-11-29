import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String []args) {
        Customer c;
        int choice = 0;
        Scanner scan = new Scanner(System.in);
        ManageCustomer cm = new ManageCustomer();

        while(choice != 3){
            System.out.println("Select an Option:\n[1] Register a Customer\n[2] Search a Customer by ID\n[3] Close Program\n");
            int option = scan.nextInt();
            choice = option;
            //this was necessary to handle an error

            switch (choice) {
                case 1: 
                    c = cm.RegisterCustomer();
                    break;
                case 2: 
                    c = cm.SearchCustomer();
                    System.out.println(c.getfName()+" "+c.getlName()+"\n"+c.getEmail()+" - "+c.getPhone()+"\n"+c.getAddress()+"\nAge: "+c.getAge()+"\nID: "+c.getID());
                    break;
                case 3:
                        break;
                default:
                        break;
            }
        }
    }

    

   
}
