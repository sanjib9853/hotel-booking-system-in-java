package bookingsystem;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class viewbookings extends JFrame {
    public viewbookings(int uid, String name) {
        setTitle("View Bookings");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        // Header Label
        JLabel headerLabel = new JLabel("Your Bookings", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(headerLabel, BorderLayout.NORTH);

        // UID Display
        JLabel uidLabel = new JLabel("User ID: " + uid, SwingConstants.CENTER);
        uidLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(uidLabel, BorderLayout.CENTER);

        // Table for Bookings
        JTable table = new JTable();
        table.setFont(new Font("Tahoma", Font.PLAIN, 14)); // Table font size
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 150));
        add(scrollPane, BorderLayout.CENTER);

        // Database connection and query
        Connection con = null;
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;

        try {
            // Query to fetch hotel name and room number based on user ID
            String query = "SELECT h.hname, b.room_no " +
                    "FROM bookings b " +
                    "JOIN hotels h ON b.hid = h.id " +
                    "WHERE b.uid = ?";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hbs", "root", "sanjib");
            ps1 = con.prepareStatement(query);
            ps1.setInt(1, uid); // Use the actual UID from the user
            rs1 = ps1.executeQuery();

            if (!rs1.isBeforeFirst()) {
                JOptionPane.showMessageDialog(null, "You have not booked any hotel.", "Information", JOptionPane.INFORMATION_MESSAGE);
                new user(uid,name);
            } else {
                // Set table model with the fetched data
                table.setModel(DbUtils.resultSetToTableModel(rs1));
            }
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Back Button
        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        backButton.setFocusable(false);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            new user(uid, name);
            dispose();
        });

        // Background Color
        getContentPane().setBackground(new Color(214, 234, 248));
        setVisible(true);
    }
}
