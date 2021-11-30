

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DeliverySchedule extends JPanel{
	private JButton		cmdEditDelivery;
    private JButton     cmdClose;
    private JButton     cmdSortDeliveryDate;
    private JButton     cmdSortDeliveryMethod;
    private JButton     cmdSortAddress;
    private JButton     cmdSortCusId;
    private JButton     cmdSortOrderCost;
  
    private JPanel      pnlCommand;
    private JPanel      pnlDisplay;
    private ArrayList<DeliveryRecord> deliverylist;
    private DeliverySchedule thisList;
    private JScrollPane scrollPane;

    private JTable table;
    private DefaultTableModel model;
    
    final File schedFile = new File("C:\\Users\\Peters\\eclipse-workspace\\T'Curly\\src\\deliverySched\\schedule.dat");
    //Creates a file object using a predetermined data file

    public DeliverySchedule() {
        super(new GridLayout(2,1));
        thisList = this;
        
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();

        deliverylist= loadSchedule(schedFile);
        String[] columnNames=  {"ID#",
        		"Delivery Date",
        		//"Delivery Method",
        		"Address",
        		"Customer ID#",
                "Order Cost"};
        model=new DefaultTableModel(columnNames,0);
        table = new JTable(model);
        showTable(deliverylist);

        table.setPreferredScrollableViewportSize(new Dimension(600, deliverylist.size()*15 +50));
        table.setFillsViewportHeight(true);

        scrollPane = new JScrollPane(table);
       
        add(scrollPane);
        
        cmdEditDelivery  = new JButton("Edit Delivery Record");
        cmdSortDeliveryDate  = new JButton("Sort by Delivery Date");
        //cmdSortDeliveryMethod  = new JButton("Sort by Delivery Method");
        cmdSortAddress  = new JButton("Sort by Address");
        cmdSortCusId  = new JButton("Sort by Customer ID");
        cmdSortOrderCost  = new JButton("Sort by Order Cost");
        cmdClose   = new JButton("Close");

        cmdEditDelivery.addActionListener(new EditDeliveryButtonListener());
        cmdSortDeliveryDate.addActionListener(new SortDeliveryDateButtonListener());
        //cmdSortDeliveryMethod.addActionListener(new SortDeliveryMethodButtonListener());
        cmdSortAddress.addActionListener(new SortAddressButtonListener());
        cmdSortCusId.addActionListener(new SortCustomerIDButtonListener());
        cmdSortOrderCost.addActionListener(new SortOrderCostButtonListener());
        cmdClose.addActionListener(new CloseButtonListener());
        
        cmdEditDelivery.setBackground(Color.yellow);
        cmdSortDeliveryDate.setBackground(Color.white);
        //cmdSortDeliveryMethod.setBackground(Color.white);
        cmdSortAddress.setBackground(Color.white);
        cmdSortCusId.setBackground(Color.white);
        cmdSortOrderCost.setBackground(Color.white);
        cmdClose.setBackground(Color.red);
        pnlCommand.setBackground(Color.white);
        
        pnlCommand.add(cmdEditDelivery);
        pnlCommand.add(cmdSortDeliveryDate);
        //pnlCommand.add(cmdSortDeliveryMethod);
        pnlCommand.add(cmdSortAddress);
        pnlCommand.add(cmdSortCusId);
        pnlCommand.add(cmdSortOrderCost);
        pnlCommand.add(cmdClose);
       
        add(pnlCommand);
    }

    private void showTable(ArrayList<DeliveryRecord> schedlist)
    {
    	for(int i = 0; i<schedlist.size(); i++) {
    		  addToTable(schedlist.get(i));
         }
    }
    private void addToTable(DeliveryRecord d)
    {
        String date= d.getDate();
        String address = d.getAddress();
        String[] item={""+ d.getId(), date, address,""+ d.getCusId(),"" + d.getOrderCost()};
        model.addRow(item);        

    }

    static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Delivery Schedule");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        DeliverySchedule newContentPane = new DeliverySchedule();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        
    }

    public static void main(String[] args) {
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();
                }
            });
    }

    public void addPromoter (DeliveryRecord d)
    {
        deliverylist.add(d);
        addToTable(d);

    }

    private ArrayList<DeliveryRecord> loadSchedule(File schedFile) //Extracts data as an arraylist
    {
        ArrayList<DeliveryRecord> schedlist = new ArrayList<DeliveryRecord>();
        try
        {
        	Scanner dscan = new Scanner(schedFile);
            while(dscan.hasNextLine())
            {
                String [] nextLine = dscan.nextLine().split(" ");
                String date = nextLine[0];
                String address = nextLine[1];
                int cusId = Integer.parseInt(nextLine[2]);
                double orderCost = Double.parseDouble(nextLine[3]);
                DeliveryRecord d = new DeliveryRecord(date, address,cusId , orderCost);
                schedlist.add(d);
            }

            dscan.close();
        }
        catch(NullPointerException | FileNotFoundException e)
        {}
        return schedlist;
    }
    
    public class EditDeliveryButtonListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) { //listener for EditDelivery button, initiates when button is clicked
    		new DeliveryScheduleEdit(deliverylist, thisList);
    	}
    }
    
    public class SortDeliveryDateButtonListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {//listener for SortDeliveryDate button, initiates when button is clicked
    		Collections.sort(deliverylist, new DeliveryDateSort());
    		refresh(deliverylist);
    	}
    }
    
    /*public class SortDeliveryMethodButtonListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {//listener for SortDeliveryMethod button, initiates when button is clicked
    		Collections.sort(deliverylist);
    		refresh(deliverylist);
    	}
    }*/
    
    public class SortAddressButtonListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {//listener for SortAddress button, initiates when button is clicked
    		Collections.sort(deliverylist);
    		refresh(deliverylist);
    	}
    }
    
    public class SortCustomerIDButtonListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {//listener for SortCustomerID button, initiates when button is clicked
    		Collections.sort(deliverylist, new CustomerIDSort());
    		refresh(deliverylist);
    	}
    }
    
    public class SortOrderCostButtonListener implements ActionListener{
    	public void actionPerformed(ActionEvent e) {//listener for SortOrderCost button, initiates when button is clicked
    		Collections.sort(deliverylist, new OrderCostSort());
    		refresh(deliverylist);
    	}
    }
    

    public class CloseButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)//listener for Close button, initiates when button is clicked
        {
        	System.exit(0);
        
        }

    }
    
    public void refresh(ArrayList<DeliveryRecord> schedlist) {
    	//method used to refresh the table after modifying it.
    	model.setRowCount(0);
		for(DeliveryRecord d: schedlist) {
			addToTable(d);
		}
    }

}

class DeliveryDateSort implements Comparator<DeliveryRecord>{//used to help sort deliveries based on date

	@Override
	public int compare(DeliveryRecord d1, DeliveryRecord d2) {
		return d1.getDate().compareTo(d2.getDate());
	}
	
}

class CustomerIDSort implements Comparator<DeliveryRecord>{//used to help sort deliveries based on customer ID

	@Override
	public int compare(DeliveryRecord d1, DeliveryRecord d2) {
		if (d1.getCusId()>d2.getCusId()) {
			return 1;
		}else {
			if(d2.getCusId()>d1.getCusId()) {
				return -1;
			}else {
				return 0;
			}
		}
	}
	
}

class OrderCostSort implements Comparator<DeliveryRecord>{//used to help sort deliveries based on order cost

	@Override
	public int compare(DeliveryRecord d1, DeliveryRecord d2) {
		if (d1.getOrderCost()<d2.getOrderCost()) {
			return 1;
		}else {
			if(d2.getOrderCost()<d1.getOrderCost()) {
				return -1;
			}else {
				return 0;
			}
		}
	}
	
}