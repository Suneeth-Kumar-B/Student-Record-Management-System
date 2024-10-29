package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class UserSelectionPanel extends JPanel {
    private Image backgroundImage;

    public UserSelectionPanel() {
        // Load the background image
//        try {
//            backgroundImage = ImageIO.read(new File("C:\\Users\\surya\\Adv Java\\StudentRecordManagementSystem\\StudentRecordManagementSystem\\srms.jpg")); // Replace "background.jpg" with your image file path
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        setLayout(new GridBagLayout());
        setBackground(Color.WHITE); // Set background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components

        JLabel headingLabel = new JLabel("STUDENT RECORD MANAGEMENT SYSTEM");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Increase font size
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3; // Updated to accommodate three buttons
        gbc.anchor = GridBagConstraints.CENTER;
        add(headingLabel, gbc);

        JButton adminButton = new JButton("Admin");
        JButton studentButton = new JButton("Student");

        // Set fixed size for buttons
        Dimension buttonSize = new Dimension(50, 50);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Expand horizontally
        gbc.weighty = 0.0; // Don't divide vertically
        gbc.ipady = 20; // Set vertical padding
        add(adminButton, gbc);

        gbc.gridy = 2;
        add(studentButton, gbc);

        adminButton.setPreferredSize(buttonSize);
        studentButton.setPreferredSize(buttonSize);

        adminButton.setBackground(Color.CYAN); // Set button background color
        adminButton.setForeground(Color.BLACK); // Set button text color
        adminButton.setFocusPainted(false); // Remove focus border

        studentButton.setBackground(Color.CYAN); // Set button background color
        studentButton.setForeground(Color.BLACK); // Set button text color
        studentButton.setFocusPainted(false); // Remove focus border

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.frame.getContentPane().removeAll();
                Main.frame.add(new AdminPanel());
                Main.frame.revalidate();
                Main.frame.repaint();
            }
        });

        studentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.frame.getContentPane().removeAll();
                Main.frame.add(new StudentPanel(Main.frame)); // Pass Main.frame as the parent frame
                Main.frame.revalidate();
                Main.frame.repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
