package bookingsystem;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class available extends JFrame {
    public available(int uid, String name) {
        setTitle("Available Rooms");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel hname = new JLabel("Enter the hotel name:");
        hname.setBounds(30, 50, 400, 30);
        hname.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(hname);

        JTextField tf = new JTextField();
        tf.setBounds(210, 55, 250, 25);
        add(tf);

        JButton show = new JButton("Show");
        show.setBounds(100, 110, 120, 25);
        show.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(show);

        JButton cancel = new JButton("Cancel");
        cancel.setBounds(300, 110, 120, 25);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(cancel);

        JLabel avl = new JLabel("Available rooms are:");
        avl.setBounds(160, 160, 200, 30);
        avl.setFont(new Font("Tahoma", Font.BOLD, 15));

        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(80, 180, 350, 100);  // Adjusted width for Price column
        add(scrollPane);

        show.addActionListener(e -> {
            String hotel = tf.getText().trim();

            if (hotel.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter the hotel name field", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Connection con = null;
            PreparedStatement ps1 = null;
            PreparedStatement ps2 = null;
            ResultSet rs1 = null;
            ResultSet rs2 = null;

            try {
                String q1 = "SELECT id, single_bed_price, double_bed_price FROM hotels WHERE hname = ?";
                String q2 = "SELECT room_no, bedtype, " +
                        "CASE WHEN bedtype = 'Single' THEN h.single_bed_price ELSE h.double_bed_price END AS price " +
                        "FROM rooms r JOIN hotels h ON r.hid = h.id " +
                        "WHERE r.hid = ? AND r.is_booked = FALSE";

                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hbs", "root", "sanjib");

                ps1 = con.prepareStatement(q1);
                ps1.setString(1, hotel);
                rs1 = ps1.executeQuery();

                if (rs1.next()) {
                    int id = rs1.getInt("id");

                    ps2 = con.prepareStatement(q2);
                    ps2.setInt(1, id);
                    rs2 = ps2.executeQuery();

                    if (!rs2.isBeforeFirst()) {  // Check if result set is empty
                        JOptionPane.showMessageDialog(null, "Room not available", "Information", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        add(avl);
                        table.setModel(DbUtils.resultSetToTableModel(rs2));
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Hotel not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (rs1 != null) rs1.close();
                    if (ps1 != null) ps1.close();
                    if (rs2 != null) rs2.close();
                    if (ps2 != null) ps2.close();
                    if (con != null) con.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        cancel.addActionListener(e -> {
            new user(uid, name);
            dispose();
        });

        getContentPane().setBackground(new Color(214, 234, 248));
        setVisible(true);
    }
}
