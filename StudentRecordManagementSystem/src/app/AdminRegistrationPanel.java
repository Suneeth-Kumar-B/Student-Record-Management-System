package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import entities.Admin;
import service.AdminService;
import service.AdminServiceImpl;
import exceptions.DatabaseException;

public class AdminRegistrationPanel extends JDialog {
    AdminService adminService = new AdminServiceImpl();

    private JTextField nameField;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public AdminRegistrationPanel(Frame parent) {
        super(parent, "Register as Admin", true);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE); // Set panel background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        nameField = new JTextField(20);
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(Color.CYAN); // Set button background color
        submitButton.setForeground(Color.BLACK); // Set button text color
        submitButton.setFocusPainted(false); // Remove focus border
        submitButton.setPreferredSize(new Dimension(100, 30)); // Set button size
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submit();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, gbc);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding to the panel

        getContentPane().add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void submit() {
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        Admin admin = new Admin(name, username, password);

        if (name.length() < 3 || name.isEmpty()) {
            JOptionPane.showMessageDialog(AdminRegistrationPanel.this, "Name must be at least 3 characters long");
            return;
        }

        if (password.length() < 6 || password.isEmpty()) {
            JOptionPane.showMessageDialog(AdminRegistrationPanel.this, "Password must be at least 6 characters long");
            return;
        }

        try {
            adminService.register(admin);
            JOptionPane.showMessageDialog(AdminRegistrationPanel.this, "User registered successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Close the dialog after successful registration
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(AdminRegistrationPanel.this, "Unknown error occurred. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
