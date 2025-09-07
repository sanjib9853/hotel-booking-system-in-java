package bookingsystem;

import javax.swing.*;
import java.awt.*;

public class user extends JFrame {
    public user(int uid, String name) {
        setTitle("User");
        setSize(1000, 700);

        // Background Image
        ImageIcon backgroundIcon = new ImageIcon("src\\pexel.jpg");
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(1000, 700, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(backgroundImage));
        background.setBounds(0, 0, 1000, 700);
        background.setLayout(null);
        setContentPane(background);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);

        // Left-side buttons
        JButton book = new JButton("Book");
        book.setBounds(100, 200, 200, 40);
        book.setFont(new Font("Tahoma", Font.BOLD, 15));
        book.setFocusable(false);
        ImageIcon bookIcon = new ImageIcon("src\\book.png");
        book.setIcon(new ImageIcon(bookIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        add(book);

        JButton unbook = new JButton("Unbook");
        unbook.setBounds(100, 260, 200, 40);
        unbook.setFont(new Font("Tahoma", Font.BOLD, 15));
        unbook.setFocusable(false);
        ImageIcon unbookIcon = new ImageIcon("src\\cancel.png");
        unbook.setIcon(new ImageIcon(unbookIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        add(unbook);

        JButton available = new JButton("Available Rooms");
        available.setBounds(100, 320, 200, 40);
        available.setFont(new Font("Tahoma", Font.BOLD, 15));
        available.setFocusable(false);
        ImageIcon availableIcon = new ImageIcon("src\\available.png");
        available.setIcon(new ImageIcon(availableIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        add(available);

        // Right-side buttons
        JButton avhotel = new JButton("Available Hotels");
        avhotel.setBounds(700, 200, 200, 40);
        avhotel.setFont(new Font("Tahoma", Font.BOLD, 15));
        avhotel.setFocusable(false);
        ImageIcon avhotelIcon = new ImageIcon("src\\hotel.png");
        avhotel.setIcon(new ImageIcon(avhotelIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        add(avhotel);

        JButton viewBookings = new JButton("View Bookings");
        viewBookings.setBounds(700, 260, 200, 40);
        viewBookings.setFont(new Font("Tahoma", Font.BOLD, 15));
        viewBookings.setFocusable(false);
        ImageIcon viewBookingsIcon = new ImageIcon("src\\view.png");
        viewBookings.setIcon(new ImageIcon(viewBookingsIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        add(viewBookings);

        JButton ah = new JButton("All History");
        ah.setBounds(700, 320, 200, 40);
        ah.setFont(new Font("Tahoma", Font.BOLD, 15));
        ah.setFocusable(false);
        // No icon provided originally for "All History" (optional)
        add(ah);

        // Exit button at the bottom center
        JButton exit = new JButton("Exit");
        exit.setBounds(400, 500, 200, 40);
        exit.setFont(new Font("Tahoma", Font.BOLD, 15));
        exit.setFocusable(false);
        ImageIcon exitIcon = new ImageIcon("src\\exit.png");
        exit.setIcon(new ImageIcon(exitIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        add(exit);

        // Button Action Listeners
        book.addActionListener(e -> {
            new book(uid, name);
            dispose();
        });

        unbook.addActionListener(e -> {
            new unbook(uid, name);
            dispose();
        });

        available.addActionListener(e -> {
            new available(uid, name);
            dispose();
        });

        avhotel.addActionListener(e -> {
            new avlhotel(uid, name);
        });

        viewBookings.addActionListener(e -> {
            new viewbookings(uid, name);
            dispose();
        });

        ah.addActionListener(e -> {
            new bookinghistory(uid, name);
            dispose();
        });

        exit.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
}
