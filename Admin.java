package bookingsystem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import java.awt.*;

public class Admin extends JFrame {
    public Admin(){
        setTitle("Login");
        setSize(600,320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);

        JLabel l1 = new JLabel("Username");
        l1.setBounds(62,70,400,30);
        l1.setFont(new Font("Tahoma",Font.BOLD,15));
        add(l1);

        JTextField user= new JTextField();
        user.setBounds(150,70,400,30);
        add(user);

        JLabel l2 = new JLabel("Password");
        l2.setBounds(62,130,400,30);
        l2.setFont(new Font("Tahoma",Font.BOLD,15));
        add(l2);

        JPasswordField pass= new JPasswordField();
        pass.setBounds(150,130,400,30);
        add(pass);

        JButton login = new JButton("Login");
        login.setBounds(180,180,140,30);
        login.setFont(new Font("Tahoma",Font.BOLD,15));
        add(login);

        JButton cancel = new JButton("Cancel");
        cancel.setBounds(350,180,140,30);
        cancel.setFont(new Font("Tahoma",Font.BOLD,15));
        add(cancel);

        getContentPane().setBackground(new Color(214, 234, 248));
        setVisible(true);

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = user.getText();
                String password = new String(pass.getPassword());
                String name;
                Statement st  = null;
                Connection con = null;
                PreparedStatement  sm = null;
                ResultSet rs = null;
                try{
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hbs", "root", "sanjib");

                    // Step 2: Prepare query using PreparedStatement to avoid SQL injection
                    String query = "SELECT name FROM admin WHERE pass = ? AND uid = ?";
                    sm = con.prepareStatement(query);

                    // Step 3: Set the values in the PreparedStatement
                    sm.setString(1, password);
                    sm.setString(2, username);

                    // Step 4: Execute the query
                    rs = sm.executeQuery();

                    // Step 5: Process the ResultSet
                    if (rs.next()) {
                        name = rs.getString("name");
                        new admininterface();
                        dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Invalid username or password! Try again", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        cancel.addActionListener(e1->new userlogin());
    }
}
