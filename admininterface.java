package bookingsystem;

import javax.swing.*;
import java.awt.*;

public class admininterface extends JFrame {
    public admininterface(){
        setTitle("Admin");
        setSize(1000,700);

        ImageIcon i = new ImageIcon("src\\admin.jpg");
        Image r = i.getImage().getScaledInstance(1000, 800, Image.SCALE_SMOOTH);
        ImageIcon ri = new ImageIcon(r);
        JLabel background = new JLabel(ri);
        background.setBounds(0, 0, 1000, 800);
        setContentPane(background);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        JButton ahottel = new JButton("Add hotel");
        ahottel.setBounds(175, 390, 140, 30);
        ahottel.setFont(new Font("Tahoma",Font.BOLD,15));
        add(ahottel);

        ImageIcon i1 = new ImageIcon("src\\hotell.png");
        Image r1 = i1.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        ImageIcon ri1 = new ImageIcon(r1);
        JLabel addh = new JLabel(ri1);
        addh.setBounds(185, 290, 120, 120);
        add(addh);

        JButton aroom = new JButton("Add room");
        aroom.setBounds(345, 390, 140, 30);
        aroom.setFont(new Font("Tahoma",Font.BOLD,15));
        add(aroom);

        ImageIcon i2 = new ImageIcon("src\\room.png");
        Image r2 = i2.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        ImageIcon ri2 = new ImageIcon(r2);
        JLabel addr = new JLabel(ri2);
        addr.setBounds(367, 290, 90, 90);
        add(addr);

        JButton rhotel = new JButton("Reduce hotel");
        rhotel.setBounds(515, 390, 140, 30);
        rhotel.setFont(new Font("Tahoma",Font.BOLD,15));
        add(rhotel);

        ImageIcon i3 = new ImageIcon("src\\roomm.png");
        Image r3 = i3.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon ri3 = new ImageIcon(r3);
        JLabel rh = new JLabel(ri3);
        rh.setBounds(540, 290, 100, 100);
        add(rh);


        JButton exit = new JButton("Exit");
        exit.setBounds(685, 390, 140, 30);
        exit.setFont(new Font("Tahoma",Font.BOLD,15));
        add(exit);

        ImageIcon i4 = new ImageIcon("src\\exit.png");
        Image r4 = i4.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon ri4 = new ImageIcon(r4);
        JLabel Exit = new JLabel(ri4);
        Exit.setBounds(705, 305, 100, 100);
        add(Exit);

        ahottel.addActionListener(e -> new addhotel());
        aroom.addActionListener(e -> new addroom());
        rhotel.addActionListener(e -> new reducehotel());
        exit.addActionListener(e -> new userlogin());

        setVisible(true);

    }
}
