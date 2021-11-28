import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.awt.*;
import javax.swing.*;

/**
 * Features of AdminLogin:
 *   provides security against un-authorized users   
 */
public class Lvl1Login extends JFrame{

    private JPasswordField  txtPass;    //entered password
    private String      password;       //correct password
    private JButton     cmdPass;
    private JButton     cmdClose;
    private JButton     cmdClearAll;
    private JPanel      pnlCommand;
    private JPanel      pnlDisplay;
    private JLabel      label;
    private JLabel      instructions;

    Lvl1Login(){
        password = "lvl1";
        setTitle("Administrator");
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();
        instructions = new JLabel("Please Enter Administrator Password");
        instructions.setForeground(Color.WHITE);
        pnlDisplay.add(instructions); 
        label = new JLabel("<html><br/Password: <html>");
        label.setForeground(Color.WHITE);
        pnlDisplay.add(label);


        pnlDisplay.setBackground(new Color(15,17,22));
        pnlCommand.setBackground(new Color(15,17,22));


        pnlDisplay.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        txtPass = new JPasswordField(10);
        txtPass.setEchoChar('*');
        pnlDisplay.add(txtPass);
        pnlDisplay.setLayout(new GridLayout(4,1));


        //Create Icons For Buttons
        Icon passicon = new ImageIcon("icons/unlockpasswordicon.png");
        Icon closeicon = new ImageIcon("icons/exiticon.png");


        //Create Buttons
        cmdPass    = new JButton("Enter", passicon);
        cmdClose   = new JButton("Back", closeicon);


        //Set Background colour of Buttons
        cmdPass.setBackground(new Color(226,228,233));
        cmdClose.setBackground(new Color(221,55,78));


        //Add Buttons to Screen
        pnlCommand.add(cmdPass);
        pnlCommand.add(cmdClose);


        //Give Buttons ActionListeners
        cmdPass.addActionListener(new PasswordButtonListener());
        cmdClose.addActionListener(new BackButtonListener());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setUndecorated(true); // <-- the title bar is removed here
        add(pnlDisplay, BorderLayout.CENTER);
        add(pnlCommand, BorderLayout.SOUTH);
        pack();
        setResizable(false);
        this.setLocationRelativeTo(null);
        setVisible(true);
    }


    public void playSound(String soundName)
    {
        try 
        {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile( ));
            Clip clip = AudioSystem.getClip( );
            clip.open(audioInputStream);
            clip.start( );
        }
        catch(Exception ex)
        {
            System.out.println("Error with playing sound.");
            ex.printStackTrace( );
        }
    }


    private class BackButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            playSound("sound_dir/button2.wav");
            new EntryScreen();
            setVisible(false);
        }
    }


    private class PasswordButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            playSound("sound_dir/button2.wav");

            //Check if password inputted is equal to correct password
            if (String.valueOf(txtPass.getPassword()).equals(password)){
                playSound("sound_dir/start2.wav");
                MainMenu.createAndShowGUI();
                setVisible(false);
            }else{
                playSound("sound_dir/error.wav");
                label.setText("<html><i><b></b></i>Password: <html>");
                ImageIcon image = new ImageIcon("icons/erroricon.png");
                JLabel label2 = new JLabel("<html><i><b>INCORRECT PASSWORD</b></i><html>", image, JLabel.NORTH_EAST);
                label2.setForeground(Color.WHITE);
                pnlDisplay.remove(label);
                pnlDisplay.remove(txtPass);
                pnlDisplay.add( label2, BorderLayout.WEST); 
                pnlDisplay.add(label);
                pnlDisplay.add(txtPass);
            }
        }
    }
}
