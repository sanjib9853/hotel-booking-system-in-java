package bookingsystem;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class bookinghistory extends JFrame {
    public bookinghistory(int uid, String name) {
        setTitle("Booking History for " + name);
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 780, 350);
        add(scrollPane);

        JButton backButton = new JButton("Back");
        backButton.setBounds(10, 370, 100, 30);
        add(backButton);

        backButton.addActionListener(e -> {
            new user(uid,name);
        });

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // Connect to the database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hbs", "root", "sanjib");

            // Query joining booking_history, hotels, and rooms with filtering based on user id and name.
            String query = "SELECT bh.hid, h.hname, bh.room_no, r.bedtype, bh.date_booked, bh.date_unbooked " +
                    "FROM booking_history bh " +
                    "JOIN hotels h ON bh.hid = h.id " +
                    "JOIN rooms r ON bh.hid = r.hid AND bh.room_no = r.room_no " +
                    "WHERE bh.uid = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, uid);
            rs = ps.executeQuery();

            // Populate the table with the query results
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        setVisible(true);
    }

}

