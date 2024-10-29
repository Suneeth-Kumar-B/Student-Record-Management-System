package app;

import javax.swing.*;
import entities.StudentResult;
import java.awt.*;

public class ProgressReportWindow extends JFrame {

    public ProgressReportWindow(StudentResult progressReportData) {
        setTitle("Progress Report");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 2)); // Use GridLayout for arranging components
        getContentPane().setBackground(Color.WHITE); // Set background color

        // Labels and text fields for each data point
        String[] labels = {"Name:", "Roll No:", "Standard:", "Maths:", "Computers:", "Astronomy:", "General Science:",
                "Social Studies:", "Total:", "Average:", "Percentage:", "Rank:"};
        JLabel[] jLabels = new JLabel[labels.length];
        JLabel[] jDataLabels = new JLabel[labels.length];

        // Create and add labels and data labels to the frame
        for (int i = 0; i < labels.length; i++) {
            jLabels[i] = new JLabel(labels[i]);
            jDataLabels[i] = new JLabel();
            add(jLabels[i]);
            add(jDataLabels[i]);
        }

        // Fill data labels with data
        jDataLabels[0].setText(progressReportData.getName());
        jDataLabels[1].setText(Long.toString(progressReportData.getReport().getRollno()));
        jDataLabels[2].setText(Integer.toString(progressReportData.getReport().getStd()));
        jDataLabels[3].setText(Double.toString(progressReportData.getReport().getMarks().getMaths()));
        jDataLabels[4].setText(Double.toString(progressReportData.getReport().getMarks().getComputers()));
        jDataLabels[5].setText(Double.toString(progressReportData.getReport().getMarks().getAstronomy()));
        jDataLabels[6].setText(Double.toString(progressReportData.getReport().getMarks().getGenscience()));
        jDataLabels[7].setText(Double.toString(progressReportData.getReport().getMarks().getSocial()));
        jDataLabels[8].setText(Double.toString(progressReportData.getReport().getTotal()));
        jDataLabels[9].setText(Double.toString(progressReportData.getReport().getAverage()));
        jDataLabels[10].setText(Double.toString(progressReportData.getReport().getPercentage()));
        jDataLabels[11].setText(Integer.toString(progressReportData.getRank()));

        // Set font for labels and data labels
        Font font = new Font("Arial", Font.PLAIN, 12);
        for (JLabel jLabel : jLabels) {
            jLabel.setFont(font);
        }
        for (JLabel jDataLabel : jDataLabels) {
            jDataLabel.setFont(font);
        }

        setVisible(true);
    }
}
