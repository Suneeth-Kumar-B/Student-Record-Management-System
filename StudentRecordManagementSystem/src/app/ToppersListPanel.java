package app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import entities.StudentResult;

import java.awt.*;
import java.util.List;

public class ToppersListPanel extends JPanel {

    public ToppersListPanel(List<StudentResult> results) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // Set background color

        // Create a table model with columns
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Ranking");
        model.addColumn("Name");
        model.addColumn("Mathematics");
        model.addColumn("Computers");
        model.addColumn("Astronomy");
        model.addColumn("General Science");
        model.addColumn("Social Studies");
        model.addColumn("Total");
        model.addColumn("Average");
        model.addColumn("Percentage");

        // Populate the table model with result data
        for (StudentResult row : results) {
            Object[] rowData = {
                row.getRank(),
                row.getName(),
                row.getReport().getMarks().getMaths(), // Assuming methods to get subject marks are available in MarksReport
                row.getReport().getMarks().getComputers(),
                row.getReport().getMarks().getAstronomy(),
                row.getReport().getMarks().getGenscience(),
                row.getReport().getMarks().getSocial(),
                row.getReport().getTotal(),
                row.getReport().getAverage(),
                row.getReport().getPercentage()
            };
            model.addRow(rowData);
        }

        // Create a JTable with the populated table model
        JTable table = new JTable(model);
        table.setEnabled(false); // Make the table non-editable
        table.setBackground(Color.WHITE); // Set table background color
        table.setForeground(Color.BLACK); // Set table text color
        table.setFont(new Font("Arial", Font.PLAIN, 12)); // Set table font
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the table to the panel
        add(scrollPane, BorderLayout.CENTER);

        // Create button
        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.CYAN); // Set button background color
        backButton.setForeground(Color.BLACK); // Set button text color
        backButton.setFocusPainted(false); // Remove focus border
        backButton.setFont(new Font("Arial", Font.PLAIN, 12)); // Set button font

        // Add action listener to back button
        backButton.addActionListener(e -> {
            // Add action to back button
            // Navigate back to the previous panel (UserSelectionPanel in this case)
            Main.frame.getContentPane().removeAll();
            Main.frame.add(new UserSelectionPanel());
            Main.frame.revalidate();
            Main.frame.repaint();
        });

        // Create panel for button and add button to it
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE); // Set panel background color
        buttonPanel.add(backButton);

        // Add button panel to the bottom of the panel
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
