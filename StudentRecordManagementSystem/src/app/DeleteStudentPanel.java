package app;

import javax.swing.*;

import entities.Student;
import exceptions.DatabaseException;
import service.StudentService;
import service.StudentServiceImpl;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteStudentPanel extends JPanel {
    static StudentService helper = new StudentServiceImpl();

    private JTextArea deletedStudentDetails; // Text area to display deleted student details

    public DeleteStudentPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // Set background color

        JPanel deletePanel = new JPanel();
        deletePanel.setBackground(Color.WHITE); // Set panel background color
        JLabel rollNoLabel = new JLabel("Enter Roll No:");
        JTextField rollNoField = new JTextField(15);
        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");

        // Apply styling to buttons
        JButton[] buttons = {deleteButton, backButton};
        for (JButton button : buttons) {
            button.setBackground(Color.CYAN); // Set button background color
            button.setForeground(Color.BLACK); // Set button text color
            button.setFocusPainted(false); // Remove focus border
        }

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long rollNo = Long.parseLong(rollNoField.getText());
                // Get the student details before deleting
                Student deletedStudent = helper.getStudentByRno(rollNo);
                // Delete the student
                try {
                    deletedStudent = helper.deleteStudent(rollNo);

                    // Append the deleted student details to the text area
                    if (deletedStudent != null) {
                        appendDeletedStudentDetails(deletedStudent);
                        JOptionPane.showMessageDialog(Main.frame, "Student with Roll No: " + rollNo + " deleted!");
                    } else {
                        JOptionPane.showMessageDialog(Main.frame, "No Student Found");
                    }
                    rollNoField.setText("");
                } catch (DatabaseException e1) {
                    JOptionPane.showMessageDialog(Main.frame, "Error deleting student: " + e1.getMessage());
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

        deletePanel.add(rollNoLabel);
        deletePanel.add(rollNoField);
        deletePanel.add(deleteButton);
        deletePanel.add(backButton);

        add(deletePanel, BorderLayout.CENTER);

        // Initialize the deleted student details text area
        deletedStudentDetails = new JTextArea(10, 30);
        deletedStudentDetails.setEditable(false); // Make it non-editable
        deletedStudentDetails.setBackground(Color.WHITE); // Set text area background color
        JScrollPane scrollPane = new JScrollPane(deletedStudentDetails);
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void appendDeletedStudentDetails(Student student) {
        // Append the student details to the text area
        deletedStudentDetails.append("Roll No: " + student.getRollno() + "\n");
        deletedStudentDetails.append("Name: " + student.getName() + "\n");
        deletedStudentDetails.append("Phone: " + student.getPhoneNumber() + "\n");
        deletedStudentDetails.append("Address: " + student.getAddress() + "\n");
        deletedStudentDetails.append("Date of Birth: " + student.getDateOfBirth() + "\n");
        deletedStudentDetails.append("Email: " + student.getEmail() + "\n\n");
    }
}
