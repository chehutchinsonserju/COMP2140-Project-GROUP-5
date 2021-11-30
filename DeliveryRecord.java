
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DeliveryRecord extends JFrame implements Comparable<DeliveryRecord>{
	private String address, delivDate;
	private double orderCost;
	private DecimalFormat df = new DecimalFormat("0.00");
	private int id, cusId, index;
	private static int nextid =0;
	
	private JTextField  txtAddress;       //address
	private JTextField  txtCusId;       //customer id
	private JTextField  txtDelivDate;       //delivery date
    private JTextField  txtOrderCost;        //order cost
    
    private JButton     cmdUpdate;
    private JButton     cmdClose;

    private JPanel      pnlCommand;
    private JPanel      pnlDisplay;
    
    private ArrayList<DeliveryRecord> schedlist;
    
    private DeliveryRecord d;
    private DeliverySchedule listing;
    private DeliveryScheduleEdit updater,updateWin;
    File schedFile = new File("schedule.txt");
	
	
	public DeliveryRecord (String date, String address, int cusId, double orderCost) {
		this.delivDate = date;
		this.address = address;
		this.cusId = cusId;
		this.orderCost = orderCost;
		id = nextid;
		nextid++;
  	}
	
	public DeliveryRecord (int id,String date, String address, int cusId, double orderCost) {
		this.delivDate = date;
		this.address = address;
		this.cusId = cusId;
		this.orderCost = orderCost;
		this.id = id;
  	}
	
	public int getId()
	{
		return id;
	}
	
	public int getCusId()
	{
		return cusId;
	}
	
	public double getOrderCost()
	{
		return orderCost;
	}
	
	public String getDate()
	{
		return delivDate;
	}

	public String getAddress()
	{
		return address;
	}
	
	public int compareTo(DeliveryRecord other)
	{
		return this.getAddress().compareTo(other.getAddress());
	}
	
	public void setAddress(String adr)
	{
		this.address = adr;
	}
	
	public void setDate(String date)
	{
		this.delivDate = date;
	}
	
	public void setName(int cusId)
	{
		this.cusId = cusId;
	}
	
	public void setOrderCost(double orderCost)
	{
		this.orderCost = orderCost;
	}
	
	public String toString()
	{
		return this.delivDate+" "+this.address +" "+this.cusId+" "+df.format(this.orderCost)+"\n";
	}
	
	public static void resetId()
	{
		nextid=0;
	}
	
	
	public void updateLocalData(int index, DeliveryScheduleEdit updater, DeliveryScheduleEdit updateWin, DeliverySchedule listing, ArrayList<DeliveryRecord> schedlist)
	{
		String currDate = getDate();
		String currAddress = getAddress();
		double currOrderCost = getOrderCost();
		this.schedlist = schedlist;
		this.updater = updater;
		this.updateWin = updateWin;
		this.listing = listing;
		this.index = index;
		d = this;
		
		setTitle("Updating Delivery Record");
		
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();
        
        pnlDisplay.add(new JLabel(("Current delivery date: "+currDate+""))); 
        pnlDisplay.add(new JLabel(("Enter a new delivery date or leave blank to keep current date"))); 
        txtDelivDate = new JTextField(10);
        pnlDisplay.add(txtDelivDate);
        
        pnlDisplay.add(new JLabel(("Current address: "+currAddress+""))); 
        pnlDisplay.add(new JLabel(("Enter a new address or leave blank to keep current address"))); 
        txtAddress = new JTextField(20);
        pnlDisplay.add(txtAddress);
        
        pnlDisplay.add(new JLabel("Current order cost: "+currOrderCost+""));
        pnlDisplay.add(new JLabel("Enter a new order cost or leave blank to keep current order cost"));
        txtOrderCost = new JTextField(9);
        pnlDisplay.add(txtOrderCost);
        
        pnlDisplay.setLayout(new GridLayout(0,1));
       
        cmdUpdate      = new JButton("Update");
        cmdClose   = new JButton("Close");
        
        cmdUpdate.setBackground(Color.green);
        cmdClose.setBackground(Color.red);
        
        cmdUpdate.addActionListener(new UpdateButtonListener());
        cmdClose.addActionListener(new CloseButtonListener()); 

        pnlCommand.add(cmdUpdate);
        pnlCommand.add(cmdClose);
        add(pnlDisplay, BorderLayout.CENTER);
        add(pnlCommand, BorderLayout.SOUTH);
        pack();
        setVisible(true);
	}
	
	
	
	private class CloseButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            d.setVisible(false);
        }

    }
	
	private class UpdateButtonListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) { //listener for Update button, initiates when button is clicked
    		String delivDate = txtDelivDate.getText(), address = txtAddress.getText(), text = txtOrderCost.getText(); // storing text field values
    		double orderCost;
        	try {
        		if (delivDate.equals(""))
        			delivDate = getDate();
        		if (address.equals(""))
            		address = getAddress();
            	if (text.equals(""))
            		orderCost = getOrderCost();
            	else
            		orderCost = Double.parseDouble(text);
            	txtAddress.setText("");
            	txtDelivDate.setText("");
            	txtOrderCost.setText("");
            	try {
            		BufferedWriter writer = new BufferedWriter(new FileWriter(schedFile,false));
            		schedlist.set(index, new DeliveryRecord(id, delivDate, address, getCusId(),orderCost));
            		listing.schedFile.delete();
            		for (int i = 0; i < schedlist.size(); i++) {
            			writer.write(schedlist.get(i).toString());
            		}
                    writer.close();
            	}catch (Exception e1) {}
            	listing.refresh(schedlist);
        		d.setVisible(false); // close current window
        		
        	}catch (NumberFormatException ex){}
        		
        }
    }
}
