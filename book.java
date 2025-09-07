package bookingsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class book extends JFrame {
    public book(int uid, String name) {
        setTitle("Book Room");
        setSize(520, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel hotel = new JLabel("Enter the hotel:");
        hotel.setBounds(30, 50, 140, 30);
        hotel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(hotel);

        JTextField tf1 = new JTextField();
        tf1.setBounds(170, 55, 300, 25);
        add(tf1);

        JLabel room = new JLabel("Enter the room no.:");
        room.setBounds(30, 100, 160, 30);
        room.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(room);

        JTextField tf2 = new JTextField();
        tf2.setBounds(190, 105, 280, 25);
        add(tf2);

        JButton book = new JButton("Book");
        book.setBounds(120, 160, 120, 25);
        book.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(book);

        JButton cancel = new JButton("Cancel");
        cancel.setBounds(320, 160, 120, 25);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(cancel);

        book.addActionListener(e -> {
            String h = tf1.getText().trim();
            String r = tf2.getText().trim();

            if (h.isEmpty() || r.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter the data in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int rno;
            try {
                rno = Integer.parseInt(r);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Room number must be a valid integer", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Connection con = null;
            PreparedStatement ps1 = null, ps2 = null, ps3 = null, ps4 = null, ps5 = null;
            ResultSet rs = null, rs1 = null;

            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hbs", "root", "sanjib");

                String q1 = "SELECT id, single_bed_price, double_bed_price FROM hotels WHERE hname = ?";
                ps1 = con.prepareStatement(q1);
                ps1.setString(1, h);
                rs = ps1.executeQuery();

                if (rs.next()) {
                    int hotelId = rs.getInt("id");
                    double singleBedPrice = rs.getDouble("single_bed_price");
                    double doubleBedPrice = rs.getDouble("double_bed_price");

                    String q4 = "SELECT room_no, is_booked, bedtype FROM rooms WHERE hid = ? AND room_no = ?";
                    ps4 = con.prepareStatement(q4);
                    ps4.setInt(1, hotelId);
                    ps4.setInt(2, rno);
                    rs1 = ps4.executeQuery();

                    if (rs1.next()) {
                        boolean isBooked = rs1.getBoolean("is_booked");
                        String bedType = rs1.getString("bedtype");

                        if (isBooked) {
                            JOptionPane.showMessageDialog(null, "Room is already booked", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            double price = bedType.equals("Single") ? singleBedPrice : doubleBedPrice;

                            int choice = JOptionPane.showConfirmDialog(null,
                                    "Room Type: " + bedType + "\nPrice: Rs." + price + "\nDo you want to proceed with booking?",
                                    "Confirm Booking", JOptionPane.YES_NO_OPTION);

                            if (choice == JOptionPane.YES_OPTION) {
                                String q2 = "UPDATE rooms SET is_booked = True WHERE hid = ? AND room_no = ?";
                                String q3 = "INSERT INTO bookings(hid, room_no, uid) VALUES(?, ?, ?)";
                                // Updated query based on provided table schema:
                                String q6 = "INSERT INTO booking_history (hid, room_no, uid, uname, date_booked) VALUES (?, ?, ?, ?, ?)";

                                ps2 = con.prepareStatement(q2);
                                ps2.setInt(1, hotelId);
                                ps2.setInt(2, rno);
                                ps2.executeUpdate();

                                ps3 = con.prepareStatement(q3);
                                ps3.setInt(1, hotelId);
                                ps3.setInt(2, rno);
                                ps3.setInt(3, uid);
                                ps3.executeUpdate();

                                ps5 = con.prepareStatement(q6);
                                ps5.setInt(1, hotelId);
                                ps5.setInt(2, rno);
                                ps5.setInt(3, uid);
                                ps5.setString(4, name);
                                ps5.setDate(5, Date.valueOf(LocalDate.now()));
                                ps5.executeUpdate();

                                JOptionPane.showMessageDialog(null, "Room booked successfully", "Successful", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
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
