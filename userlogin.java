package bookingsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class userlogin extends JFrame {
    public userlogin(){
        setTitle("Login");
        setSize(1000,700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);


        JPanel panel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.CYAN);
                g.fillRect(0, 0, getWidth()-502, getHeight());

                g.setColor(Color.BLACK);
                g.setFont(new Font("Tahoma", Font.BOLD, 50));
                g.drawString("Hotel booking", 80, 300);

                g.setFont(new Font("Tahoma",Font.ITALIC,50));
                g.drawString("System", 160, 350);
            }
        };
        panel.setLayout(null);
        setContentPane(panel);

        ImageIcon i1 = new ImageIcon("src\\admin2.png");
        Image r1 = i1.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        ImageIcon ri1 = new ImageIcon(r1);
        JLabel admin = new JLabel(ri1);
        admin.setBounds(917,0, 70, 70);
        add(admin);

        JLabel l = new JLabel("Admin");
        l.setBounds(930,50,140,30);
        l.setFont(new Font("Tahoma",Font.BOLD,14));
        add(l);

        JLabel l1 = new JLabel("Username :");
        l1.setBounds(502,220,400,30);
        l1.setFont(new Font("Tahoma",Font.BOLD,15));
        add(l1);

        JTextField user= new JTextField();
        user.setBounds(590,220,300,30);
        user.setOpaque(false);
        user.setFont(new Font("Tahoma",Font.BOLD,14));
        user.setForeground(Color.DARK_GRAY);
        user.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
        add(user);

        JLabel l2 = new JLabel("Password :");
        l2.setBounds(502,280,400,30);
        l2.setFont(new Font("Tahoma",Font.BOLD,15));
        add(l2);

        JPasswordField pass= new JPasswordField();
        pass.setBounds(590,280,300,30);
        pass.setOpaque(false);
        pass.setFont(new Font("Tahoma",Font.BOLD,14));
        pass.setForeground(Color.DARK_GRAY);
        pass.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.DARK_GRAY));
        add(pass);

        JButton login = new JButton("Login");
        login.setBounds(580,340,140,30);
        login.setFont(new Font("Tahoma",Font.BOLD,15));
        add(login);

        JButton cancel = new JButton("Cancel");
        cancel.setBounds(750,340,140,30);
        cancel.setFont(new Font("Tahoma",Font.BOLD,15));
        add(cancel);

        JLabel l3 = new JLabel("or Don't a new account ?");
        l3.setBounds(522,390,220,30);
        l3.setFont(new Font("Tahoma",Font.BOLD,15));
        add(l3);

        JLabel l4 = new JLabel("Sign up");
        l4.setBounds(718,390,140,30);
        l4.setFont(new Font("Tahoma",Font.BOLD,15));
        l4.setForeground(Color.BLUE);
        add(l4);

        l.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Admin();
            }
        });

        login.addActionListener(e -> {
            String u = user.getText().trim();
            String psw =new String(pass.getPassword());

            if(u.isEmpty() || psw.isEmpty()){
                JOptionPane.showMessageDialog(null,"Enter the data in field","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }

            String name;
            int uid;
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hbs","root","sanjib");
                String query = "SELECT * FROM users WHERE pass = ? AND uname = ?";
                ps = con.prepareStatement(query);

                // Step 3: Set the values in the PreparedStatement
                ps.setString(1, psw);
                ps.setString(2, u);

                // Step 4: Execute the query
                rs = ps.executeQuery();

                // Step 5: Process the ResultSet
                if (rs.next()) {
                    name = rs.getString("uname");
                    uid = rs.getInt("id");
                    new user(uid,name);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Invalid username and password ! try again","Error",JOptionPane.ERROR_MESSAGE);
                    return;
                }

            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null,"Error :"+e1.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            dispose();

        });

        l4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new createac();
            }
        });

        cancel.addActionListener(e ->System.exit(0));

        getContentPane().setBackground(new Color(214, 234, 248));
        setVisible(true);
    }
    public static void main(String args[]){
        new userlogin();

    }
}
