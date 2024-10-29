package app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import entities.Marks;
import entities.MarksReport;
import entities.StudentResult;
import service.MarksReportService;
import service.MarksReportServiceImpl;
import exceptions.DatabaseException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class ShowAllResultsPanel extends JPanel {
    private MarksReportService marksReportService = new MarksReportServiceImpl();

    public ShowAllResultsPanel(List<StudentResult> results) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // Set background color

        // Create a table model with columns
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make only columns corresponding to subjects editable
                return column >= 4 && column <= 8;
            }
        };
        model.addColumn("Ranking");
        model.addColumn("Name");
        model.addColumn("Roll No");
        model.addColumn("Standard");
        model.addColumn("Mathematics");
        model.addColumn("Computers");
        model.addColumn("Astronomy");
        model.addColumn("General Science");
        model.addColumn("Social Studies");
        model.addColumn("Total");
        model.addColumn("Average");
        model.addColumn("Percentage");

        // Populate the table model with result data without using toArray()
        for (StudentResult row : results) {
            model.addRow(new Object[]{
                    row.getRank(),
                    row.getName(),
                    row.getReport().getRollno(),
                    row.getReport().getStd(),
                    row.getReport().getMarks().getMaths(),
                    row.getReport().getMarks().getComputers(),
                    row.getReport().getMarks().getAstronomy(),
                    row.getReport().getMarks().getGenscience(),
                    row.getReport().getMarks().getSocial(),
                    row.getReport().getTotal(),
                    row.getReport().getAverage(),
                    row.getReport().getPercentage()
            });
        }

        // Create a JTable with the populated table model
        JTable table = new JTable(model);
        table.setBackground(Color.WHITE); // Set table background color
        table.getTableHeader().setBackground(Color.YELLOW); // Set table header background color
        table.getTableHeader().setForeground(Color.BLACK);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only single row selection

        JScrollPane scrollPane = new JScrollPane(table);

        // Add the table to the panel
        add(scrollPane, BorderLayout.CENTER);

        // Create buttons
        JButton updateButton = new JButton("Update");
        JButton backButton = new JButton("Back");
        JButton deleteButton = new JButton("Delete");

        // Apply styling to the buttons
        for (JButton button : new JButton[]{updateButton, deleteButton, backButton}) {
            button.setBackground(Color.CYAN); // Set button background color
            button.setForeground(Color.BLACK); // Set button text color
            button.setFocusPainted(false); // Remove focus border
            button.setPreferredSize(new Dimension(100, 30)); // Set button size
        }

        // Add action listeners to buttons
        updateButton.addActionListener(e -> updateRecord(table));
        deleteButton.addActionListener(e -> deleteRecord(table));
        backButton.addActionListener(e -> {
            // Navigate back to the admin home panel
            Main.frame.getContentPane().removeAll();
            Main.frame.add(new AdminHomePanel());
            Main.frame.revalidate();
            Main.frame.repaint();
        });

        // Create panel for buttons and add buttons to it
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        // Add button panel to the bottom of the panel
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateRecord(JTable table) {
        int selectedRowIndex = table.getSelectedRow();

        if (selectedRowIndex != -1) {
            try {
                long rollNo = ((Number) table.getValueAt(selectedRowIndex, 2)).longValue();
                int standard = ((Number) table.getValueAt(selectedRowIndex, 3)).intValue();

                double mathematics = extractDoubleFromCell(table.getValueAt(selectedRowIndex, 4));
                double computers = extractDoubleFromCell(table.getValueAt(selectedRowIndex, 5));
                double astronomy = extractDoubleFromCell(table.getValueAt(selectedRowIndex, 6));
                double generalScience = extractDoubleFromCell(table.getValueAt(selectedRowIndex, 7));
                double socialStudies = extractDoubleFromCell(table.getValueAt(selectedRowIndex, 8));

                if (isValidMark(mathematics) && isValidMark(computers) &&
                        isValidMark(astronomy) && isValidMark(generalScience) &&
                        isValidMark(socialStudies)) {

                    double total = mathematics + computers + astronomy + generalScience + socialStudies;
                    double average = total / 5.0;
                    double percentage = (total / 500) * 100;

                    Marks marks = new Marks(mathematics, computers, astronomy, generalScience, socialStudies);
                    MarksReport report = new MarksReport(rollNo, standard, marks, total, average, percentage);

                    report = marksReportService.updateReport(report);
                    JOptionPane.showMessageDialog(Main.frame, "Student Marks Updated for Roll No: " + report.getRollno());

                } else {
                    JOptionPane.showMessageDialog(ShowAllResultsPanel.this, "Error: Marks must be between 0 and 100 (inclusive).");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ShowAllResultsPanel.this, "Error: Please enter valid numeric marks.");
            } catch (DatabaseException ex) {
                handleDatabaseException(ex);
            }
        } else {
            JOptionPane.showMessageDialog(ShowAllResultsPanel.this, "Please select a row to update.");
        }
    }

    private void deleteRecord(JTable table) {
        int selectedRowIndex = table.getSelectedRow();

        if (selectedRowIndex != -1) {
            try {
                long rollNo = ((Number) table.getValueAt(selectedRowIndex, 2)).longValue();
                int standard = ((Number) table.getValueAt(selectedRowIndex, 3)).intValue();

                double mathematics = extractDoubleFromCell(table.getValueAt(selectedRowIndex, 4));
                double computers = extractDoubleFromCell(table.getValueAt(selectedRowIndex, 5));
                double astronomy = extractDoubleFromCell(table.getValueAt(selectedRowIndex, 6));
                double generalScience = extractDoubleFromCell(table.getValueAt(selectedRowIndex, 7));
                double socialStudies = extractDoubleFromCell(table.getValueAt(selectedRowIndex, 8));
                double total = extractDoubleFromCell(table.getValueAt(selectedRowIndex, 9));
                double average = extractDoubleFromCell(table.getValueAt(selectedRowIndex, 10));
                double percentage = extractDoubleFromCell(table.getValueAt(selectedRowIndex, 11));

                Marks marks = new Marks(mathematics, computers, astronomy, generalScience, socialStudies);
                MarksReport report = new MarksReport(rollNo, standard, marks, total, average, percentage);

                if (report != null) {
                    report = marksReportService.deleteReport(report);
                    JOptionPane.showMessageDialog(Main.frame, "Student Marks Deleted for Roll Number: " + report.getRollno());
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ShowAllResultsPanel.this, "Error: Please enter valid numeric marks.");
            }
        }
    }

    private double extractDoubleFromCell(Object cellValue) throws NumberFormatException {
        if (cellValue == null) {
            return 0.0;
        }
        return Double.parseDouble(cellValue.toString());
    }

    private boolean isValidMark(double mark) {
        return mark >= 0 && mark <= 100;
    }

    private void handleDatabaseException(DatabaseException ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        JOptionPane.showMessageDialog(Main.frame, exceptionAsString);
    }
}
