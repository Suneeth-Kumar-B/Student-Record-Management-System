package app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import entities.Student;
import entities.StudentResult;
import exceptions.DatabaseException;
import helpers.RollNumberGenerator;
import service.*;

import java.util.List;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminHomePanel extends JPanel {
	
	static StudentService studentService = new StudentServiceImpl(); 
	static MarksReportService marksReportService = new MarksReportServiceImpl();
	
    public AdminHomePanel() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE); // Set background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components

        JButton newStudentButton = new JButton("New Student");
        JButton deleteStudentButton = new JButton("Delete Student");
        JButton searchStudentButton = new JButton("Search Student");
        JButton updateStudentButton = new JButton("Update Student");
        JButton addReportsButton = new JButton("Add Reports");
        JButton showStudentsButton = new JButton("Show All Students");
        JButton showAllResultsButton = new JButton("Display All Results");
        JButton resetRollNoButton = new JButton("Reset Roll Number to 1");
        JButton newAdminRegistrationButton = new JButton("New Admin Registration");

        Dimension buttonSize = new Dimension(200, 50);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Expand horizontally
        gbc.weighty = 1.0 / 6; // Divide equally into 6 rows
        gbc.ipady = 30; // Set vertical padding
        add(newStudentButton, gbc);

        gbc.gridy = 1;
        add(deleteStudentButton, gbc);

        gbc.gridy = 2;
        add(searchStudentButton, gbc);

        gbc.gridy = 3;
        add(updateStudentButton, gbc);

        gbc.gridy = 4;
        add(addReportsButton, gbc);

        gbc.gridy = 5;
        add(showStudentsButton, gbc);
        
        gbc.gridy = 6;
        add(showAllResultsButton, gbc);
        
        gbc.gridy = 7;
        add(resetRollNoButton, gbc);
        
        gbc.gridy = 8;
        add(newAdminRegistrationButton, gbc);

        // Set preferred button size
        newStudentButton.setPreferredSize(buttonSize);
        deleteStudentButton.setPreferredSize(buttonSize);
        searchStudentButton.setPreferredSize(buttonSize);
        updateStudentButton.setPreferredSize(buttonSize);
        addReportsButton.setPreferredSize(buttonSize);
        showStudentsButton.setPreferredSize(buttonSize);
        showAllResultsButton.setPreferredSize(buttonSize);
        resetRollNoButton.setPreferredSize(buttonSize);
        newAdminRegistrationButton.setPreferredSize(buttonSize);

        // Style buttons
        JButton[] buttons = {newStudentButton, deleteStudentButton, searchStudentButton, updateStudentButton,
                             addReportsButton, showStudentsButton, showAllResultsButton, resetRollNoButton,
                             newAdminRegistrationButton};
        for (JButton button : buttons) {
            button.setBackground(Color.CYAN); // Set button background color
            button.setForeground(Color.BLACK); // Set button text color
            button.setFocusPainted(false); // Remove focus border
        }

        resetRollNoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the resetNextNumber method from RollNumberGenerator class
                RollNumberGenerator.resetNextNumber(1); // Reset to 1 or provide the desired number
                // Refresh the UI or perform any other necessary actions
                Main.frame.getContentPane().removeAll();
                Main.frame.add(new AddStudentFormPanel(Main.frame));
                Main.frame.revalidate();
                Main.frame.repaint();
            }
        });

        newAdminRegistrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Create an instance of AdminRegistrationPanel
                    AdminRegistrationPanel adminRegistrationPanel = new AdminRegistrationPanel(Main.frame);
                    // Set the dialog as modal
                    adminRegistrationPanel.setModal(true);
                    // Make the dialog visible
                    adminRegistrationPanel.setVisible(true);
                } catch (Exception ex) {
                    // Handle any exception that might occur
                    ex.printStackTrace(); // Print the stack trace for debugging
                    JOptionPane.showMessageDialog(Main.frame, "Error: Failed to open Admin Registration Panel. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        showAllResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<StudentResult> results = null; // Initialize outside try-catch block

                try {
                    // Retrieve all results data
                    results = marksReportService.getAllResults();
                } catch (DatabaseException ex) {
                    ex.printStackTrace();
                    // Handle the exception appropriately, for example, show an error message
                    JOptionPane.showMessageDialog(Main.frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method if an exception occurs
                }

                // Create a new ShowAllResultsPanel with the results data
                ShowAllResultsPanel resultsPanel = new ShowAllResultsPanel(results);

                // Replace the current content pane with the results panel
                Main.frame.getContentPane().removeAll();
                Main.frame.add(resultsPanel);
                Main.frame.revalidate();
                Main.frame.repaint();
            }
        });

        newStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.frame.getContentPane().removeAll();
                Main.frame.add(new AddStudentFormPanel(Main.frame));
                Main.frame.revalidate();
                Main.frame.repaint();
            }
        });

        deleteStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.frame.getContentPane().removeAll();
                Main.frame.add(new DeleteStudentPanel());
                Main.frame.revalidate();
                Main.frame.repaint();
            }
        });

        searchStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.frame.getContentPane().removeAll();
                Main.frame.add(new SearchStudentPanel(Main.frame));
                Main.frame.revalidate();
                Main.frame.repaint();
            }
        });

        updateStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Main.frame.getContentPane().removeAll();
                Main.frame.add(new UpdateStudentPanel(Main.frame));
                Main.frame.revalidate();
                Main.frame.repaint();
            }
        });

        addReportsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new instance of StudentResultCalculator
                AddReports calculator = new AddReports();
                
                // Create a new JFrame
                JFrame calculatorFrame = new JFrame();
                calculatorFrame.setTitle("Student Result Calculator");
                calculatorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                calculatorFrame.setSize(600, 400);
                
                // Add the components of the StudentResultCalculator directly to the calculatorFrame
                calculatorFrame.add(calculator.getContentPane());
                
                // Display the calculator frame
                calculatorFrame.setVisible(true);
            }
        });

        showStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve all student data
                java.util.List<Student> students = studentService.getAllStudents();

                JFrame frame = new JFrame();
                frame.setTitle("All Students");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(800, 600);
                frame.getContentPane().setBackground(Color.WHITE); // Set background color

                // Create a JLabel for the heading
                JLabel headingLabel = new JLabel("All Students", SwingConstants.CENTER);
                headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
                headingLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add padding
                headingLabel.setBackground(Color.CYAN); // Set background color
                headingLabel.setForeground(Color.BLACK); // Set text color
                headingLabel.setOpaque(true); // Enable background color

                // Create a table model with columns
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Roll No");
                model.addColumn("Name");
                model.addColumn("Phone");
                model.addColumn("Address");
                model.addColumn("Date of Birth");
                model.addColumn("Email");

                // Populate the table model with student data
                for (Student student : students) {
                    Object[] rowData = {
                        student.getRollno(),
                        student.getName(),
                        student.getPhoneNumber(),
                        student.getAddress(),
                        student.getDateOfBirth(),
                        student.getEmail()
                    };
                    model.addRow(rowData);
                }

                // Create a JTable with the populated table model
                JTable table = new JTable(model);
                table.setBackground(Color.WHITE); // Set table background color
                table.setForeground(Color.BLACK); // Set table text color
                table.setFont(new Font("Arial", Font.PLAIN, 12)); // Set table font
                table.setRowHeight(30); // Set row height

                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setBackground(Color.WHITE); // Set scroll pane background color

                // Create a "Back" button
                JButton backButton = new JButton("Back");
                backButton.setBackground(Color.CYAN); // Set button background color
                backButton.setForeground(Color.BLACK); // Set button text color
                backButton.setFocusPainted(false); // Remove focus border
                backButton.setPreferredSize(new Dimension(100, 50)); // Set preferred button size
                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose(); // Close the frame when "Back" button is clicked
                    }
                });

                // Add the table, heading, and "Back" button to the JFrame
                frame.setLayout(new BorderLayout());
                frame.add(headingLabel, BorderLayout.NORTH);
                frame.add(scrollPane, BorderLayout.CENTER);
                frame.add(backButton, BorderLayout.SOUTH); // Add the "Back" button to the bottom
                frame.setVisible(true);
            }
        });

    }
}
