
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
import java.io.*;
import java.awt.event.*;

public class orderProcessing extends JFrame implements ActionListener{

    private Scanner scan = new Scanner(System.in);
    static File file = new File("Invoices.txt");

    private Container container;
    private JLabel custIDLabel;
    private JTextField  custID;
    private JLabel prodLabel;
    private JTextField  prod;

    private JButton submitBtn;
    private JButton resetBtn;
    private JButton backBtn;
    private Icon backIcon;
    private JTextArea notify;

    Color lpink = (new  Color(250, 200, 250));
    Color dblue = (new  Color(10, 10, 15));
    Color dblue2 = (new  Color(21, 21, 31));
    Color dpink = (new  Color(255, 30, 75));
    
    public void getDetails(){
        container = getContentPane();
        container.setBackground(dblue);
        container.setLayout(null);
        
        custIDLabel = new JLabel("Please enter the customer ID for the sale");
        custID.setFont(new Font("Arial", Font.BOLD, 20));
        custID.setSize(150, 20);
        custID.setLocation(100, 100);
        custID.setForeground(lpink);
        container.add(custID);
        
        custID = new JTextField();
        custID.setFont(new Font("Arial", Font.PLAIN, 15));
        custID.setBackground(Color.white);
        custID.setSize(190, 20);
        custID.setLocation(250, 100);
        container.add(custID);

        prodLabel = new JLabel("Please enter the product for the sale");
        prod.setFont(new Font("Arial", Font.BOLD, 20));
        prod.setSize(150, 20);
        prod.setLocation(100, 100);
        prod.setForeground(lpink);
        container.add(prod);

        prod = new JTextField();
        prod.setFont(new Font("Arial", Font.PLAIN, 15));
        prod.setBackground(Color.white);
        prod.setSize(190, 20);
        prod.setLocation(250, 100);
        container.add(prod);

        submitBtn = new JButton("Submit");
          submitBtn.setFont(new Font("Arial", Font.BOLD, 25));
          submitBtn.setForeground(dpink);
          submitBtn.setBackground(lpink);
          submitBtn.setSize(340, 50);
          submitBtn.setLocation(100, 450);
          submitBtn.addActionListener(this);
          container.add(submitBtn);
  
          resetBtn = new JButton("Reset");
          resetBtn.setFont(new Font("Arial", Font.BOLD, 25));
          resetBtn.setForeground(Color.white);
          resetBtn.setBackground(dpink);
          resetBtn.setSize(340, 50);
          resetBtn.setLocation(100, 520);
          resetBtn.addActionListener(this);
          container.add(resetBtn);

          notify = new JTextArea();
          notify.setFont(new Font("Arial", Font.PLAIN, 20));
          notify.setSize(300, 320);
          notify.setForeground(Color.white);
          notify.setBackground(dblue2);
          notify.setLocation(500, 100);
          notify.setLineWrap(true);
          notify.setEditable(false);
          notify.setBorder(BorderFactory.createLineBorder(lpink));
          container.add(notify);

          backIcon = new ImageIcon("icons/exit.png");
          backBtn= new JButton   ("", backIcon);
          backBtn.addActionListener(new CloseButtonListener());
          backBtn.setSize(100, 70);
          backBtn.setBackground(lpink);
          backBtn.setLocation(700, 500);
          container.add(backBtn);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent event){
        	  
        File file = new File("Customers.txt");


        if (event.getSource() == submitBtn){

            String ID = custID.getText();
            String pname = prod.getText();
            
            notify.setText("");
            
            if (validateID(ID) == false){
                notify.setText("The submitted ID is not valid. Only letters and certain symbols are permitted.");
                custID.setBackground(lpink);
            }else if (validateName(pname) == false){
                notify.setText("The submitted product name is not valid. Only letters and certain symbols are permitted.");
                prod.setBackground(lpink);
            }else{
                String blank ="";
                custID.setText(blank);
                prod.setText(blank);
                
            }
        }else if (event.getSource() == resetBtn){
            String blank ="";
            custID.setText(blank);
            prod.setText(blank);
            custID.setBackground(Color.white);
            prod.setBackground(Color.white);
        }
    }

    public static String SearchCustomer(String input){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            int i = 0;
            List<String> rdata = new ArrayList<String>();
            
            Scanner scan = new Scanner(System.in);

            while((line = reader.readLine()) != null){
                
                rdata.add(line);
                
                String[] custFound = rdata.get(i).split("!");
                if(input.equals(custFound[6])){
                    return ("Customer ID("+custFound[6]+") Found\n----------------------------\n["
                    +custFound[0]+" "+custFound[1]+
                    "]\nEmail:\n-"+custFound[2]+
                    "\nTelephone:\n-"+custFound[3]+
                    "\nAddress:\n-"+custFound[4]+
                    "\nAge:\n-"+Integer.valueOf(custFound[5]));
                }
                i = i + 1;
            }
            return ("No customer with this ID was found.");
        }catch(Exception e){return ("No customer with this ID was found.");}
    }
    
    public static String SearchProduct(String input){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            int i = 0;
            List<String> rdata = new ArrayList<String>();
            
            Scanner scan = new Scanner(System.in);

            while((line = reader.readLine()) != null){
                
                rdata.add(line);
                
                String[] prodFound = rdata.get(i).split("!");
                if(input.equals(prodFound[1])){
                    return ("Product Name:("+prodFound[1]+") Found\n----------------------------\n["
                    +prodFound[0]+" "+prodFound[1]+
                    "]\nBrand:\n-"+prodFound[2]+
                    "\nDescription:\n-"+prodFound[3]+
                    "\nPrice:\n-"+prodFound[4]+
                    "\nAmount in Stock:\n-"+Integer.valueOf(prodFound[5]));
                } 
                i = i+1;


            }
            return ("No product with this ID was found.");
        }catch(Exception e){return ("No product with this ID was found.");}
    }

    public boolean validateName(String name) {
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

    public boolean validateID(String id) {
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
            if(c == '!'){
                return false;
            }
        }
        if((hasLetter == true) || (hasNum == true)){
            return true;
        }else{
            System.out.println("Invalid ID");
            return false;
        }
    }

    public class CloseButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)//listener for Close button, initiates when button is clicked
        {
        	MainMenu.createAndShowGUI();
            container.setVisible(false);
            dispose();
        }

    }


}
    
 


    


