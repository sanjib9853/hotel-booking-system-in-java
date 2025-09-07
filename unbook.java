package bookingsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class unbook extends JFrame {
    public unbook(int uid, String name) {
        setTitle("Cancel Room");
        setSize(520, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel hotel = new JLabel("Enter the hotel to cancel:");
        hotel.setBounds(30, 50, 200, 30);
        hotel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(hotel);

        JTextField tf1 = new JTextField();
        tf1.setBounds(230, 55, 240, 25);
        add(tf1);

        JLabel room = new JLabel("Enter the room no.:");
        room.setBounds(30, 100, 160, 30);
        room.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(room);

        JTextField tf2 = new JTextField();
        tf2.setBounds(190, 105, 280, 25);
        add(tf2);

        JButton ubook = new JButton("Cancel");
        ubook.setBounds(120, 160, 120, 25);
        ubook.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(ubook);

        JButton cancel = new JButton("Exit");
        cancel.setBounds(320, 160, 120, 25);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(cancel);

        ubook.addActionListener(e -> {
            String h = tf1.getText().trim();
            String r = tf2.getText().trim();

            if (h.isEmpty() || r.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter the data in the field", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int rno;
            try {
                rno = Integer.parseInt(r);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Room number must be an integer", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ResultSet rs = null;
            ResultSet rs1 = null;
            PreparedStatement ps1 = null;
            PreparedStatement ps2 = null;
            PreparedStatement ps3 = null;
            PreparedStatement ps4 = null;
            PreparedStatement ps5 = null;
            Connection con = null;

            try {
                // Query to get hotel id based on hotel name.
                String q1 = "SELECT id FROM hotels WHERE hname = ?";
                // Query to update room status to available.
                String q2 = "UPDATE rooms SET is_booked = FALSE WHERE hid = ? AND room_no = ?";
                // Query to delete the booking from bookings table.
                String q3 = "DELETE FROM bookings WHERE hid = ? AND room_no = ?";
                // Query to check if the room is booked.
                String q4 = "SELECT is_booked FROM rooms WHERE hid = ? AND room_no = ?";
                // Query to update the booking_history table by setting the unbooking date.
                String q5 = "UPDATE booking_history SET date_unbooked = ? WHERE hid = ? AND room_no = ? AND uid = ? AND date_unbooked IS NULL";

                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hbs", "root", "sanjib");
                ps1 = con.prepareStatement(q1);
                ps1.setString(1, h);
                rs = ps1.executeQuery();

                if (rs.next()) {
                    int hotelId = rs.getInt("id");

                    ps4 = con.prepareStatement(q4);
                    ps4.setInt(1, hotelId);
                    ps4.setInt(2, rno);
                    rs1 = ps4.executeQuery();

                    if (rs1.next()) {
                        boolean isBooked = rs1.getBoolean("is_booked");

                        if (!isBooked) {
                            JOptionPane.showMessageDialog(null, "Room is already available and cannot be unbooked", "Information", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }

                        // Update room status to available.
                        ps2 = con.prepareStatement(q2);
                        ps2.setInt(1, hotelId);
                        ps2.setInt(2, rno);
                        ps2.executeUpdate();

                        // Update booking_history: set date_unbooked to current date.
                        ps5 = con.prepareStatement(q5);
                        ps5.setDate(1, Date.valueOf(LocalDate.now()));
                        ps5.setInt(2, hotelId);
                        ps5.setInt(3, rno);
                        ps5.setInt(4, uid);
                        ps5.executeUpdate();

                        // Delete the booking record from bookings table.
                        ps3 = con.prepareStatement(q3);
                        ps3.setInt(1, hotelId);
                        ps3.setInt(2, rno);
                        ps3.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Room canceled successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Room not found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Hotel not found", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (rs1 != null) rs1.close();
                    if (ps1 != null) ps1.close();
                    if (ps2 != null) ps2.close();
                    if (ps3 != null) ps3.close();
                    if (ps4 != null) ps4.close();
                    if (ps5 != null) ps5.close();
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

        setVisible(true);
    }
}
