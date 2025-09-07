package bookingsystem;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class avlhotel extends JFrame {
    public avlhotel(int uid, String name) {
        setTitle("Available Hotels and Rooms");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        // Header Label (centered)
        JLabel headerLabel = new JLabel("Available Hotels and Rooms");
        headerLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        headerLabel.setBounds(150, 20, 300, 30);
        add(headerLabel);

        // Back Button
        JButton cancel = new JButton("Back");
        cancel.setBounds(240, 400, 120, 40);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        cancel.setFocusable(false);
        add(cancel);

        // Table to display hotels with available rooms
        JTable table = new JTable();
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 70, 500, 300);
        add(scrollPane);

        // Database connection and data fetching
        Connection con = null;
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;

        try {
            String query = "SELECT h.hname AS 'Hotel Name', COUNT(r.id) AS 'Rooms' " +
                    "FROM hotels h LEFT JOIN rooms r ON h.id = r.hid AND r.is_booked = false " +
                    "GROUP BY h.hname";

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hbs", "root", "sanjib");
            ps1 = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs1 = ps1.executeQuery();

            if (!rs1.isBeforeFirst()) { // Check if no records are available
                JOptionPane.showMessageDialog(null, "No available rooms in any hotel.", "Information", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                return;
            } else {
                table.setModel(DbUtils.resultSetToTableModel(rs1));
            }
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Back Button ActionListener
        cancel.addActionListener(e -> {
            new user(uid, name);
            dispose();
        });

        // Background color
        getContentPane().setBackground(new Color(214, 234, 248));
        setVisible(true);
    }
}
