package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import entities.Marks;
import entities.MarksReport;
import exceptions.DatabaseException;
import service.MarksReportService;
import service.MarksReportServiceImpl;

public class AddReports extends JFrame {
    private JLabel rollNumberLabel;
    private JTextField rollNumberField;
    private JLabel standardLabel;
    private JTextField standardField;
    private List<JLabel> subjectLabels;
    private List<JTextField> marksFields;
    private JButton submitButton;
    private JLabel totalLabel;
    private JLabel averageLabel;
    private JLabel percentageLabel;

    MarksReportService helper = new MarksReportServiceImpl();

    public AddReports() {
        setTitle("Student Result Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE); // Set background color
        

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE); // Set panel background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components
        gbc.anchor = GridBagConstraints.WEST;

        // Roll number input
        rollNumberLabel = new JLabel("Roll Number:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(rollNumberLabel, gbc);

        rollNumberField = new JTextField(10);
        gbc.gridx = 1;
        panel.add(rollNumberField, gbc);

        // Standard input
        standardLabel = new JLabel("Standard:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(standardLabel, gbc);

        standardField = new JTextField(10);
        gbc.gridx = 1;
        panel.add(standardField, gbc);

        // Fixed subject names and marks inputs
        String[] fixedSubjectNames = {"Mathematics", "Computers", "Astronomy", "General Science", "Social Studies"};
        subjectLabels = new ArrayList<>();
        marksFields = new ArrayList<>();
        for (int i = 0; i < fixedSubjectNames.length; i++) {
            JLabel subjectLabel = new JLabel(fixedSubjectNames[i] + ":");
            gbc.gridx = 0;
            gbc.gridy = i + 2; // Adjust the row to start after the Roll Number and Standard fields
            panel.add(subjectLabel, gbc);

            JTextField marksField = new JTextField(10);
            gbc.gridx = 1;
            panel.add(marksField, gbc);

            subjectLabels.add(subjectLabel);
            marksFields.add(marksField);
        }

        // Calculate button
        submitButton = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = fixedSubjectNames.length + 2; // Adjust the row for the Calculate button
        gbc.gridwidth = 1;
        panel.add(submitButton, gbc);

        // Result labels
        totalLabel = new JLabel("Total:");
        gbc.gridx = 0;
        gbc.gridy = fixedSubjectNames.length + 3; // Adjust the row for the Result labels
        gbc.gridwidth = 1;
        panel.add(totalLabel, gbc);

        averageLabel = new JLabel("Average:");
        gbc.gridx = 1;
        panel.add(averageLabel, gbc);

        percentageLabel = new JLabel("Percentage:");
        gbc.gridx = 2;
        panel.add(percentageLabel, gbc);

        // Apply styling to the button
        submitButton.setBackground(Color.CYAN); // Set button background color
        submitButton.setForeground(Color.BLACK); // Set button text color
        submitButton.setFocusPainted(false); // Remove focus border

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateResults();
            }
        });

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void calculateResults() {
        int totalMarks = 0;
        int numberOfSubjects = marksFields.size();
        for (int i = 0; i < numberOfSubjects; i++) {
            try {
                double marks = Double.parseDouble(marksFields.get(i).getText());
                if (marks < 0 || marks > 100) {
                    JOptionPane.showMessageDialog(this, "Marks should be between 0 and 100 inclusive.");
                    return;
                }
                totalMarks += marks;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid marks for all subjects.");
                return;
            }
        }

        // Additional validation for the standard field
        try {
            int standard = Integer.parseInt(standardField.getText());
            if (standard < 1 || standard > 10) {
                JOptionPane.showMessageDialog(this, "Standard should be between 1 and 10 inclusive.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid standard.");
            return;
        }

        Marks marks = new Marks(Double.parseDouble(marksFields.get(0).getText()),
                Double.parseDouble(marksFields.get(1).getText()),
                Double.parseDouble(marksFields.get(2).getText()),
                Double.parseDouble(marksFields.get(3).getText()),
                Double.parseDouble(marksFields.get(4).getText()));

        double average = (double) totalMarks / numberOfSubjects;
        double percentage = (double) totalMarks / (numberOfSubjects * 100) * 100;

        MarksReport report = new MarksReport(Long.parseLong(rollNumberField.getText()), Integer.parseInt(standardField.getText()), marks, totalMarks, average, percentage);

        try {
            helper.addReports(report);
            JOptionPane.showMessageDialog(this, "Report Added Successfully");
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, "Error adding report: " + e.getMessage());
        }

        totalLabel.setText("Total: " + totalMarks);
        averageLabel.setText("Average: " + String.format("%.2f", average));
        percentageLabel.setText("Percentage: " + String.format("%.2f", percentage) + "%");
    }
}
