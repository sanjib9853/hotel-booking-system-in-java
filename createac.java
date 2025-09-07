package bookingsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class createac extends JFrame {
    public createac(){
        setTitle("Create account");
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);

        JLabel name = new JLabel("Enter the name :");
        name.setBounds(30,50,400,30);
        name.setFont(new Font("Tahoma",Font.BOLD,15));
        add(name);

        JTextField tf1 = new JTextField();
        tf1.setBounds(210,55,300,25);
        add(tf1);

        JLabel addr = new JLabel("Enter the address :");
        addr.setBounds(30,100,300,25);
        addr.setFont(new Font("Tahoma",Font.BOLD,15));
        add(addr);

        JTextField tf2 = new JTextField();
        tf2.setBounds(210,105,300,25);
        add(tf2);

        JLabel uid = new JLabel("Enter the new user id :");
        uid.setBounds(30,150,300,25);
        uid.setFont(new Font("Tahoma",Font.BOLD,15));
        add(uid);

        JTextField tf3 = new JTextField();
        tf3.setBounds(210,155,300,25);
        add(tf3);

        JLabel pass = new JLabel("Enter the password :");
        pass.setBounds(30,200,300,25);
        pass.setFont(new Font("Tahoma",Font.BOLD,15));
        add(pass);

        JTextField tf4 =new JTextField();
        tf4.setBounds(210,205,300,25);
        add(tf4);

        JButton create = new JButton("Create");
        create.setBounds(100,280,140,30);
        create.setFont(new Font("Tahoma",Font.BOLD,15));
        add(create);

        JButton cancel = new JButton("Cancel");
        cancel.setBounds(350,280,140,30);
        cancel.setFont(new Font("Tahoma",Font.BOLD,15));
        add(cancel);

        create.addActionListener(e -> {
            String name1 = tf1.getText().trim();
            String address = tf2.getText().trim();
            String id = tf3.getText().trim();
            String psw = tf4.getText().trim();
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try{
                String q = "insert into users(uname,pass,name,address) values(?,?,?,?)";
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hbs","root","sanjib");

                ps =con.prepareStatement(q);
                ps.setString(1,id);
                ps.setString(2,psw);
                ps.setString(3,name1);
                ps.setString(4,address);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLIntegrityConstraintViolationException es) {
                JOptionPane.showMessageDialog(this, "Enter another password", "Error", JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "Error :"+e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancel.addActionListener(e -> {
            new userlogin();
            dispose();
        });
        //getContentPane().setBackground(new Color(214, 234, 248));
        setVisible(true);
    }
    public static void main(String args[]){
        new createac();
    }
}
