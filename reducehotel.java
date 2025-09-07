package bookingsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class reducehotel extends JFrame{
    public reducehotel(){
        setTitle("Reduce hotel");
        setSize(600,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel hname = new JLabel("Enter the hotel name :");
        hname.setBounds(30,50,400,30);
        hname.setFont(new Font("Tahoma",Font.BOLD,15));
        add(hname);

        JTextField tf = new JTextField();
        tf.setBounds(210,55,300,25);
        add(tf);

        JButton add = new JButton("Reduce");
        add.setBounds(120,100,120,25);
        add.setFont(new Font("Tahoma",Font.BOLD,15));
        add(add);

        JButton cancel = new JButton("Cancel");
        cancel.setBounds(300,100,120,25);
        cancel.setFont(new Font("Tahoma",Font.BOLD,15));
        add(cancel);

        getContentPane().setBackground(new Color(214, 234, 248));
        setVisible(true);

        add.addActionListener(e -> {
            String hotel = tf.getText().trim();
            if(hotel.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter the hotel.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PreparedStatement ps = null;
            String q = "delete from hotels where hname = ?";
            Connection con = null;

            try{
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hbs","root","sanjib");
                ps = con.prepareStatement(q);
                ps.setString(1,hotel);
                int rowaffect = ps.executeUpdate();
                if(rowaffect > 0){
                    JOptionPane.showMessageDialog(null,"Hotel reduced successfully"," ",JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(null,"Hotel not found"," ",JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null,"Error : "+e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
        });
        cancel.addActionListener(e -> new admininterface());
    }
}
