package app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import entities.Student;
import exceptions.DatabaseException;
import service.StudentService;
import service.StudentServiceImpl;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchStudentPanel extends JPanel {
    private JFrame previousWindow;
    static StudentService helper = new StudentServiceImpl();
    private JTable table;
    private DefaultTableModel tableModel;
    

    public SearchStudentPanel(JFrame previousWindow) {
        this.previousWindow = previousWindow;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // Set background color

        JLabel instructionLabel = new JLabel("Enter Roll No:");
        JTextField rollNoField = new JTextField(15);
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
        
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make only certain columns editable
                return column != 0 && column != 4; // Make all columns except 0 (Roll No) and 4 (Date of Birth) editable
            }
        };

        // Initialize table with empty data
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Roll No");
        tableModel.addColumn("Name");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Address");
        tableModel.addColumn("Date of Birth");
        tableModel.addColumn("Email");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

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
                        JOptionPane.showMessageDialog(SearchStudentPanel.this, "Invalid Roll No! Roll No should be between 1 and 99999.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SearchStudentPanel.this, "Invalid Roll No!");
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
        // Clear existing table data
        tableModel.setRowCount(0);

        if (student == null) {
            JOptionPane.showMessageDialog(SearchStudentPanel.this, "No students exist.");
        } else {
            Object[] rowData = {
                student.getRollno(),
                student.getName(),
                student.getPhoneNumber(),
                student.getAddress(),
                student.getDateOfBirth(),
                student.getEmail()
            };
            tableModel.addRow(rowData);
        }
    }

    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE); // Set panel background color
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");

        // Apply styling to buttons
        JButton[] buttons = {updateButton, deleteButton, backButton};
        for (JButton button : buttons) {
            button.setBackground(Color.CYAN); // Set button background color
            button.setForeground(Color.BLACK); // Set button text color
            button.setFocusPainted(false); // Remove focus border
        }

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row index
                int selectedRowIndex = table.getSelectedRow();

                // Check if any row is selected
                if (selectedRowIndex != -1) {
                    // Get the roll number from the selected row
                    long rollNo = (long) table.getValueAt(selectedRowIndex, 0);

                    // Get the updated data from the table
                    String name = (String) table.getValueAt(selectedRowIndex, 1);
                    String phone = (String) table.getValueAt(selectedRowIndex, 2);
                    String address = (String) table.getValueAt(selectedRowIndex, 3);
                    String dob = (String) table.getValueAt(selectedRowIndex, 4);
                    String email = (String) table.getValueAt(selectedRowIndex, 5);
                    Student student = new Student(rollNo, name, phone, address, dob, email);
                    
                    try {
                        student = helper.updateStudent(rollNo, student);
                        JOptionPane.showMessageDialog(Main.frame, "Student with Roll No: " + rollNo + " updated!");
                    } catch (DatabaseException ex) {
                        JOptionPane.showMessageDialog(Main.frame, "Error updating student: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(SearchStudentPanel.this, "Please select a student to update.");
                }
            }
        });



        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row index
                int selectedRowIndex = table.getSelectedRow();
                
                // Check if any row is selected
                if (selectedRowIndex != -1) {
                    // Get the roll number from the selected row
                    long rollNo = (long) table.getValueAt(selectedRowIndex, 0);
                    
                    // Delete the student from the database using the roll number
                    try {
                    helper.deleteStudent(rollNo);
                    
                    // Remove the row from the table
                    tableModel.removeRow(selectedRowIndex);
                    
                    JOptionPane.showMessageDialog(Main.frame, "Student with Roll No: " + rollNo + " deleted!");
                    }
                    catch(DatabaseException ex){
                    	JOptionPane.showMessageDialog(Main.frame, "Error deleting student: " + ex.getMessage());                    	
                    }
                } else {
                    JOptionPane.showMessageDialog(SearchStudentPanel.this, "Please select a student to delete.");
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
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
