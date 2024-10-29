package app;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import entities.Student;
import exceptions.DatabaseException;
import service.StudentService;
import service.StudentServiceImpl;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class UpdateStudentPanel extends JPanel {
    private JFrame previousWindow;
    private static StudentService helper = new StudentServiceImpl();
    private JTextField rollNoField;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField addressField;
    private JDateChooser dobChooser;
    private JTextField emailField;

    public UpdateStudentPanel(JFrame previousWindow) {
        this.previousWindow = previousWindow;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // Set background color

        JLabel instructionLabel = new JLabel("Enter Roll No:");
        rollNoField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        JButton backButton = new JButton("Back");

        // Apply styling to buttons
        JButton[] buttons = {searchButton, backButton};
        for (JButton button : buttons) {
            button.setBackground(Color.CYAN); // Set button background color
            button.setForeground(Color.BLACK); // Set button text color
            button.setFocusPainted(false); // Remove focus border
        }

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.setBackground(Color.WHITE); // Set panel background color
        inputPanel.add(instructionLabel);
        inputPanel.add(rollNoField);
        inputPanel.add(searchButton);

        add(inputPanel, BorderLayout.NORTH);

        JPanel studentDetailsPanel = new JPanel(new GridLayout(0, 2, 5, 5)); // 2 columns layout with spacing
        studentDetailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        studentDetailsPanel.setBackground(Color.WHITE); // Set panel background color

        studentDetailsPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        studentDetailsPanel.add(nameField);

        studentDetailsPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        studentDetailsPanel.add(phoneField);

        studentDetailsPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        studentDetailsPanel.add(addressField);

        studentDetailsPanel.add(new JLabel("Date of Birth:"));
        dobChooser = new JDateChooser();
        studentDetailsPanel.add(dobChooser);

        studentDetailsPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        studentDetailsPanel.add(emailField);

        add(studentDetailsPanel, BorderLayout.CENTER);

        // Add the update, delete, and back buttons
        addButtonPanel();

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform search action with roll number
                try {
                    long rollNo = Long.parseLong(rollNoField.getText());
                    if (rollNo >= 1 && rollNo <= 99999) {
                        Student student = helper.getStudentByRno(rollNo);
                        displayStudentDetails(student);
                    } else {
                        JOptionPane.showMessageDialog(UpdateStudentPanel.this, "Invalid Roll No! Roll No should be between 1 and 99999.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(UpdateStudentPanel.this, "Invalid Roll No!");
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
    }

    private void displayStudentDetails(Student student) {
        if (student == null) {
            JOptionPane.showMessageDialog(UpdateStudentPanel.this, "No students exist.");
            // Redirect back to the same UpdateStudentPanel
            Main.frame.getContentPane().removeAll();
            Main.frame.add(new UpdateStudentPanel(previousWindow));
            Main.frame.revalidate();
            Main.frame.repaint();
        } else {
            // Populate text fields with student details
            rollNoField.setText(Long.toString(student.getRollno()));
            nameField.setText(student.getName());
            phoneField.setText(student.getPhoneNumber());
            addressField.setText(student.getAddress());
            // Convert String date of birth to Date object
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dob = dateFormat.parse(student.getDateOfBirth());
                dobChooser.setDate(dob);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            emailField.setText(student.getEmail());
        }
    }


    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE); // Set panel background color
        JButton updateButton = new JButton("Update");
        JButton backButton = new JButton("Back");

        // Apply styling to buttons
        JButton[] buttons = {updateButton, backButton};
        for (JButton button : buttons) {
            button.setBackground(Color.CYAN); // Set button background color
            button.setForeground(Color.BLACK); // Set button text color
            button.setFocusPainted(false); // Remove focus border
        }

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the updated data from text fields
                long rollNo = Long.parseLong(rollNoField.getText());
                String name = nameField.getText();
                String phone = phoneField.getText();
                String address = addressField.getText();
                String email = emailField.getText();
                // Get the date of birth from the JDateChooser
                LocalDate dob = dobChooser.getDate() != null ?
                        dobChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
                
                LocalDate today = LocalDate.now();
                LocalDate fiveYearsAgo = today.minusYears(5);
                LocalDate sixteenYearsAgo = today.minusYears(16); // Changed to subtract 16 years from today's date

                if (dob.isAfter(fiveYearsAgo) || dob.isBefore(sixteenYearsAgo)) { // Changed condition to check if the dob is after 5 years ago OR before 16 years ago
                    JOptionPane.showMessageDialog(Main.frame, "Age should be between 5 and 16");
                    return;
                }
                
                if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(Main.frame, "All fields are required.");
                    return; // Don't proceed if any field is empty or date is not selected
                }
 
                // Additional validation for phone number and email format
                if (phone.length() != 10 || (phone.charAt(0) != '9' && phone.charAt(0) != '8' && phone.charAt(0) != '7' && phone.charAt(0) != '6')) {
                    JOptionPane.showMessageDialog(Main.frame, "Not a Valid Phone");
                    return;
                }

                if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    JOptionPane.showMessageDialog(Main.frame, "Invalid email format.");
                    return;
                }

                // Update the student record in the database
                Student updatedStudent = new Student(rollNo, name, phone, address, dob.toString(), email);
                try {
                    Student student = helper.updateStudent(rollNo, updatedStudent);
                    if (student != null) {
                        JOptionPane.showMessageDialog(Main.frame, "Student with Roll No: " + rollNo + " updated!");
                    }
                } catch (DatabaseException e1) {
                    JOptionPane.showMessageDialog(Main.frame, "Error updating student: " + e1.getMessage());
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

        buttonPanel.add(updateButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
