package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class PinChange extends JFrame implements ActionListener {
    JPasswordField pin, repin;
    JButton change, back;
    String pinnumber;
    PinChange(String pinnumber) {

        this.pinnumber = pinnumber;

        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image img2 = img.getImage().getScaledInstance(700, 700, Image.SCALE_DEFAULT);
        ImageIcon img3 = new ImageIcon(img2);
        JLabel image = new JLabel(img3);
        image.setBounds(0,0,700,700);
        add(image);

        JLabel pintext = new JLabel("New PIN");
        pintext.setBounds(80,190,700,35);
        pintext.setForeground(Color.WHITE);
        pintext.setFont(new Font("System", Font.BOLD,14));
        image.add(pintext);

        pin = new JPasswordField();
        pin.setFont(new Font("Raleway",Font.BOLD,22));
        pin.setBounds(80,230,200,20);
        image.add(pin);

        JLabel repintext = new JLabel("Re-Enter New PIN");
        repintext.setBounds(80,250,700,35);
        repintext.setForeground(Color.WHITE);
        repintext.setFont(new Font("System", Font.BOLD,14));
        image.add(repintext);

        repin = new JPasswordField();
        repin.setFont(new Font("Raleway",Font.BOLD,22));
        repin.setBounds(80,290,200,20);
        image.add(repin);

        change = new JButton("Change");
        change.setBounds(226,330,120,30);
        change.addActionListener(this);
        image.add(change);

        back = new JButton("Back");
        back.setBounds(78,330,120,30);
        back.addActionListener(this);
        image.add(back);

        getContentPane().setBackground(Color.WHITE);
        setSize(600,510);
        setLocation(390,100);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == change) {
            try {
                String npin = pin.getText();
                String rpin = repin.getText();

                if (!npin.equals(rpin)) {
                    JOptionPane.showMessageDialog(null, "PIN does not match");
                    return;
                }

                if(npin.equals("")) {
                    JOptionPane.showMessageDialog(null,"Enter New PIN");
                    return;
                }

                if (rpin.equals("")) {
                    JOptionPane.showMessageDialog(null,"Re-Enter New PIN");
                    return;
                }

                Conn conn = new Conn();

                String query1 = "update bank set pin = '"+rpin+"' where pin = '"+pinnumber+"' ";
                String query2 = "update login set pin = '"+rpin+"' where pin = '"+pinnumber+"' ";
                String query3 = "update signupthree set pin = '"+rpin+"' where pin = '"+pinnumber+"' ";

                conn.s.executeUpdate(query1);
                conn.s.executeUpdate(query2);
                conn.s.executeUpdate(query3);

                JOptionPane.showMessageDialog(null,"PIN Changed");

                setVisible(false);
                new Transactions(rpin).setVisible(true);

            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            setVisible(false);
            new Transactions(pinnumber).setVisible(true);
        }
    }

    public static void main(String[] args) {
        new PinChange("").setVisible(true);
    }
}
