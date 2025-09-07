package bookingsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class addroom extends JFrame {
    public addroom() {
        setTitle("Add Room");
        setSize(550, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel hname = new JLabel("Enter the hotel name to add room:");
        hname.setBounds(30, 50, 400, 30);
        hname.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(hname);

        JTextField tf = new JTextField();
        tf.setBounds(300, 55, 200, 25);
        add(tf);

        JButton addRoomBtn = new JButton("Add Room");
        addRoomBtn.setBounds(100, 150, 150, 30);
        addRoomBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(addRoomBtn);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBounds(280, 150, 150, 30);
        cancelBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(cancelBtn);

        addRoomBtn.addActionListener(e -> {
            String hotelName = tf.getText().trim();

            if (hotelName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a hotel name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hbs", "root", "sanjib")) {
                String q1 = "SELECT id FROM hotels WHERE hname = ?";
                PreparedStatement psh = con.prepareStatement(q1);
                psh.setString(1, hotelName);
                ResultSet rs = psh.executeQuery();

                if (rs.next()) {
                    int hotelId = rs.getInt("id");

                    while (true) {
                        // Create input panel with text field and combo box
                        JPanel panel = new JPanel(new GridLayout(2, 2));
                        JTextField roomField = new JTextField();
                        String[] bedTypes = {"Single", "Double"};
                        JComboBox<String> bedTypeCombo = new JComboBox<>(bedTypes);

                        panel.add(new JLabel("Enter Room No:"));
                        panel.add(roomField);
                        panel.add(new JLabel("Select Bed Type:"));
                        panel.add(bedTypeCombo);

                        int result = JOptionPane.showConfirmDialog(this, panel, "Add Room",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        if (result != JOptionPane.OK_OPTION) {
                            JOptionPane.showMessageDialog(this, "Room addition cancelled.", "Cancelled", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        String roomStr = roomField.getText().trim();
                        String bedType = (String) bedTypeCombo.getSelectedItem();

                        if (roomStr.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Room number cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                            continue;
                        }

                        int roomNumber;
                        try {
                            roomNumber = Integer.parseInt(roomStr);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Room number must be a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
                            continue;
                        }

                        // ** Check if the room already exists for the hotel **
                        String checkQuery = "SELECT COUNT(*) FROM rooms WHERE hid = ? AND room_no = ?";
                        PreparedStatement checkStmt = con.prepareStatement(checkQuery);
                        checkStmt.setInt(1, hotelId);
                        checkStmt.setInt(2, roomNumber);
                        ResultSet checkRs = checkStmt.executeQuery();

                        if (checkRs.next() && checkRs.getInt(1) > 0) {
                            JOptionPane.showMessageDialog(this, "Error: Room number already exists in this hotel.", "Duplicate Entry", JOptionPane.ERROR_MESSAGE);
                            continue;
                        }

                        // Insert new room
                        String insertQuery = "INSERT INTO rooms(hid, room_no, bedtype) VALUES(?, ?, ?)";
                        PreparedStatement psr = con.prepareStatement(insertQuery);
                        psr.setInt(1, hotelId);
                        psr.setInt(2, roomNumber);
                        psr.setString(3, bedType);
                        psr.executeUpdate();

                        int choice = JOptionPane.showConfirmDialog(this, "Room added successfully! Add another room?",
                                "Success", JOptionPane.YES_NO_OPTION);

                        if (choice == JOptionPane.NO_OPTION) {
                            break;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Hotel not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> {
            new admininterface();
            dispose();
        });

        getContentPane().setBackground(new Color(230, 230, 250));
        setVisible(true);
    }

    public static void main(String args[]) {
        new addroom();
    }
}
