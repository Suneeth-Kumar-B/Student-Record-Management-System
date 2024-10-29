package app;

import javax.swing.*;

import entities.Admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import service.*;

public class AdminPanel extends JPanel {
    static AdminService adminService = new AdminServiceImpl();

    public AdminPanel() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE); // Set background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components

        JLabel headingLabel = new JLabel("ADMIN LOGIN");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Increase font size
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(headingLabel, gbc);

        JLabel loginLabel = new JLabel("Login ID:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(loginLabel, gbc);

        JTextField loginField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(loginField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.CYAN); // Set button background color
        loginButton.setForeground(Color.BLACK); // Set button text color
        loginButton.setFocusPainted(false); // Remove focus border
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.CYAN); // Set button background color
        backButton.setForeground(Color.BLACK); // Set button text color
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(backButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginField.getText();
                String password = new String(passwordField.getPassword());
                Admin admin = new Admin(username, password);
                if (adminService.login(admin)) {
                    Main.frame.getContentPane().removeAll();
                    Main.frame.add(new AdminHomePanel());
                    Main.frame.revalidate();
                    Main.frame.repaint();
                } else {
                    JOptionPane.showMessageDialog(Main.frame, "Invalid username or password!");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate back to the Admin Home panel
                Main.frame.getContentPane().removeAll();
                Main.frame.add(new UserSelectionPanel());
                Main.frame.revalidate();
                Main.frame.repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        // Add custom painting code here if needed
        g2d.dispose();
    }
}
