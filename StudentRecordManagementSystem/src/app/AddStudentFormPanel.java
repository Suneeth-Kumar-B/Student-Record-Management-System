package app;

import javax.swing.*;
import entities.Student;
import exceptions.DatabaseException;
import service.StudentService;
import service.StudentServiceImpl;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import com.toedter.calendar.JDateChooser;

public class AddStudentFormPanel extends JPanel {
    static StudentService studentService = new StudentServiceImpl();

    public AddStudentFormPanel(JFrame previousWindow) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // Set background color

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 10)); // 6 rows, 2 columns, horizontal and vertical gap
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField();
        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();
        JLabel dobLabel = new JLabel("Date of Birth:");
        JDateChooser dobChooser = new JDateChooser();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        Dimension labelSize = new Dimension(100, 30);
        Dimension fieldSize = new Dimension(200, 30);
        nameLabel.setPreferredSize(labelSize);
        nameField.setPreferredSize(fieldSize);
        phoneLabel.setPreferredSize(labelSize);
        phoneField.setPreferredSize(fieldSize);
        addressLabel.setPreferredSize(labelSize);
        addressField.setPreferredSize(fieldSize);
        dobLabel.setPreferredSize(labelSize);
        dobChooser.setPreferredSize(fieldSize); // Adjusting size for date picker
        emailLabel.setPreferredSize(labelSize);
        emailField.setPreferredSize(fieldSize);

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);
        formPanel.add(addressLabel);
        formPanel.add(addressField);
        formPanel.add(dobLabel);
        formPanel.add(dobChooser);
        formPanel.add(emailLabel);
        formPanel.add(emailField);

        JButton submitButton = new JButton("Submit");
        JButton backButton = new JButton("Back"); // Adding a Back button

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String phone = phoneField.getText().trim();
                String address = addressField.getText().trim();
                String email = emailField.getText().trim();
                LocalDate dob = dobChooser.getDate() != null ?
                        dobChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;

                // Validate fields
                if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || email.isEmpty() || dob == null) {
                    JOptionPane.showMessageDialog(AddStudentFormPanel.this, "All fields are required.");
                    return; // Don't proceed if any field is empty or date is not selected
                }

                // Validate age to be at least 5 years
                LocalDate today = LocalDate.now();
                LocalDate fiveYearsAgo = today.minusYears(5);
                LocalDate sixteenYearsAgo = today.minusYears(16); // Changed to subtract 16 years from today's date

                if (dob.isAfter(fiveYearsAgo) || dob.isBefore(sixteenYearsAgo)) { // Changed condition to check if the dob is after 5 years ago OR before 16 years ago
                    JOptionPane.showMessageDialog(AddStudentFormPanel.this, "Age should be between 5 and 16");
                    return;
                }

                // Additional validation for phone number and email format
                if (phone.length() != 10 || (phone.charAt(0) != '9' && phone.charAt(0) != '8' && phone.charAt(0) != '7' && phone.charAt(0) != '6')) {
                    JOptionPane.showMessageDialog(AddStudentFormPanel.this, "Not a Valid Phone");
                    return;
                }


                if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    JOptionPane.showMessageDialog(AddStudentFormPanel.this, "Invalid email format.");
                    return;
                }

                // All validations passed, proceed with creating the student object and adding it to the database
                String dateOfBirth = dob.toString();

                Student student = new Student(name, phone, address, dateOfBirth, email);
                try {
                    Long rollNumber = studentService.addStudent(student).getRollno();

                    if (rollNumber != null) {
                        JOptionPane.showMessageDialog(AddStudentFormPanel.this, "Student Added, Roll No: " + rollNumber);
                    } else {
                        JOptionPane.showMessageDialog(AddStudentFormPanel.this, "Failed to add student. Please check details.");
                    }
                    SwingUtilities.getWindowAncestor(AddStudentFormPanel.this).dispose();
                    previousWindow.setVisible(true);
                } catch (DatabaseException ex) {
                    JOptionPane.showMessageDialog(AddStudentFormPanel.this, "Error registering student: " + ex.getMessage());
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate back to the Admin Home panel
                Main.frame.getContentPane().removeAll();
                Main.frame.add(new AdminHomePanel());
                Main.frame.revalidate();
                Main.frame.repaint();
            }
        });

        // Style buttons
        JButton[] buttons = {submitButton, backButton};
        for (JButton button : buttons) {
            button.setBackground(Color.CYAN); // Set button background color
            button.setForeground(Color.BLACK); // Set button text color
            button.setFocusPainted(false); // Remove focus border
            button.setFont(new Font("Arial", Font.PLAIN, 12)); // Set button font
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE); // Set panel background color
        buttonPanel.add(submitButton);
        buttonPanel.add(backButton); // Adding the Back button

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
