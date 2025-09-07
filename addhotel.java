package bookingsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class addhotel extends JFrame {
    public addhotel() {
        setTitle("Add Hotel");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel hname = new JLabel("Enter Hotel Name:");
        hname.setBounds(30, 50, 200, 30);
        hname.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(hname);

        JTextField tf = new JTextField();
        tf.setBounds(230, 55, 300, 25);
        add(tf);

        JLabel singlePriceLabel = new JLabel("Single Bed Price:");
        singlePriceLabel.setBounds(30, 100, 200, 30);
        singlePriceLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(singlePriceLabel);

        JTextField singlePriceField = new JTextField();
        singlePriceField.setBounds(230, 105, 300, 25);
        add(singlePriceField);

        JLabel doublePriceLabel = new JLabel("Double Bed Price:");
        doublePriceLabel.setBounds(30, 150, 200, 30);
        doublePriceLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(doublePriceLabel);

        JTextField doublePriceField = new JTextField();
        doublePriceField.setBounds(230, 155, 300, 25);
        add(doublePriceField);

        JButton add = new JButton("Add Hotel");
        add.setBounds(120, 250, 120, 30);
        add.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(add);

        JButton cancel = new JButton("Cancel");
        cancel.setBounds(300, 250, 120, 30);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(cancel);

        JLabel statusLabel = new JLabel("");
        statusLabel.setBounds(200, 300, 500, 25);
        statusLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        statusLabel.setForeground(Color.BLACK);
        add(statusLabel);

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String hotel = tf.getText().trim();
                String singlePriceStr = singlePriceField.getText().trim();
                String doublePriceStr = doublePriceField.getText().trim();
                Connection con = null;
                PreparedStatement sm = null;

                if (hotel.isEmpty() || singlePriceStr.isEmpty() || doublePriceStr.isEmpty()) {
                    statusLabel.setText("All fields are required.");
                    return;
                }

                double singlePrice, doublePrice;
                try {
                    singlePrice = Double.parseDouble(singlePriceStr);
                    doublePrice = Double.parseDouble(doublePriceStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numeric prices.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hbs", "root", "sanjib");
                    String q = "INSERT INTO hotels(hname, single_bed_price, double_bed_price) VALUES(?, ?, ?)";
                    sm = con.prepareStatement(q);
                    sm.setString(1, hotel);
                    sm.setDouble(2, singlePrice);
                    sm.setDouble(3, doublePrice);
                    sm.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Hotel Added Successfully!", "", JOptionPane.INFORMATION_MESSAGE);
                    tf.setText("");
                    singlePriceField.setText("");
                    doublePriceField.setText("");
                    statusLabel.setText("");

                    int response = JOptionPane.showConfirmDialog(null, "Do you want to add rooms to this hotel?", "Add Rooms", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        new addroom();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancel.addActionListener(e -> {
            new admininterface();
            dispose();
        });

        getContentPane().setBackground(new Color(214, 234, 248));
        setVisible(true);
    }

    public static void main(String args[]) {
        new addhotel();
    }
}
