package app;

import javax.swing.*;

import entities.StudentResult;
import exceptions.DatabaseException;
import service.MarksReportService;
import service.MarksReportServiceImpl;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StudentPanel extends JPanel {

    private MarksReportService marksReportService = new MarksReportServiceImpl();

    public StudentPanel(JFrame parentFrame) {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE); // Set background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components

        JButton progressReportButton = new JButton("Progress Report");
        JButton toppersListButton = new JButton("Toppers List");

        // Set fixed size for buttons
        Dimension buttonSize = new Dimension(150, 30);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Expand horizontally
        gbc.weighty = 0.5; // Divide equally into 2 rows
        gbc.ipady = 20; // Set vertical padding
        add(progressReportButton, gbc);

        gbc.gridy = 1;
        add(toppersListButton, gbc);

        progressReportButton.setPreferredSize(buttonSize);
        toppersListButton.setPreferredSize(buttonSize);

        // Style buttons
        JButton[] buttons = {progressReportButton, toppersListButton};
        for (JButton button : buttons) {
            button.setBackground(Color.CYAN); // Set button background color
            button.setForeground(Color.BLACK); // Set button text color
            button.setFocusPainted(false); // Remove focus border
        }

        progressReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Prompt user for the roll number
                    String rollNoInput = JOptionPane.showInputDialog(StudentPanel.this, "Enter Roll Number:");

                    // Check if the user canceled the input dialog
                    if (rollNoInput == null) {
                        return;
                    }

                    // Parse the roll number
                    Long rollNo = Long.parseLong(rollNoInput);

                    // Retrieve progress report data
                    StudentResult progressReportData = marksReportService.getResultsByRollNo(rollNo);
                    
                    // Create and display progress report window
                    if(!(progressReportData==null)) {
                    ProgressReportWindow reportWindow = new ProgressReportWindow(progressReportData);
                    reportWindow.setVisible(true);
                    }
                    else {
                    	JOptionPane.showMessageDialog(null, "No students exist.");
                    }
                } catch (NumberFormatException ex) {
                    // Handle invalid roll number format
                    JOptionPane.showMessageDialog(StudentPanel.this, "Please enter a valid roll number.",
                            "Invalid Roll Number", JOptionPane.ERROR_MESSAGE);
                } catch (DatabaseException ex) {
                    // Handle database exception
                    ex.printStackTrace(); // or show error message to user
                }
            }
        });

        toppersListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<StudentResult> results = null; // Initialize outside try-catch block

                try {
                    // Retrieve all results data
                    results = marksReportService.getTop10();
                } catch (DatabaseException ex) {
                    ex.printStackTrace();
                    // Handle the exception appropriately, for example, show an error message
                    JOptionPane.showMessageDialog(Main.frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method if an exception occurs
                }

                // Create a new ShowAllResultsPanel with the results data
                ToppersListPanel resultsPanel = new ToppersListPanel(results);

                // Replace the current content pane with the results panel
                Main.frame.getContentPane().removeAll();
                Main.frame.add(resultsPanel);
                Main.frame.revalidate();
                Main.frame.repaint();
            }
        });

        // Add window listener to redirect to UserSelectionPanel when closed
        if (parentFrame != null) {
            parentFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    // Create and display UserSelectionPanel
                    JFrame frame = new JFrame("User Selection");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.getContentPane().add(new UserSelectionPanel());
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            });
        }
    }
}
