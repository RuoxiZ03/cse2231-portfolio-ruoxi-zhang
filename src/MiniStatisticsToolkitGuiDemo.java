import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

/**
 * Simple GUI demo for MiniStatisticsToolkit.
 *
 * Users can enter a series of numbers and compute mean, min, max,
 * standard deviation, and a moving average over the last k values.
 *
 * This class is a "use case" that shows how the component might be
 * used in a real application.
 *
 * @author Ruoxi Zhang
 */
public final class MiniStatisticsToolkitGuiDemo {

    /**
     * Private constructor to prevent instantiation.
     */
    private MiniStatisticsToolkitGuiDemo() {
        // no instances
    }

    /**
     * Creates and shows the GUI.
     */
    private static void createAndShowGui() {
        JFrame frame = new JFrame("MiniStatisticsToolkit GUI Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ----- Input panel -----
        JPanel inputPanel = new JPanel(new GridLayout(3, 1, 4, 4));

        // Line 1: label + text field for values
        JPanel valuesRow = new JPanel(new BorderLayout(4, 4));
        JLabel valuesLabel = new JLabel("Values (comma or space separated): ");
        JTextField valuesField = new JTextField();
        valuesRow.add(valuesLabel, BorderLayout.WEST);
        valuesRow.add(valuesField, BorderLayout.CENTER);

        // Line 2: label + spinner for window size k
        JPanel windowRow = new JPanel(new BorderLayout(4, 4));
        JLabel kLabel = new JLabel("Moving average window k: ");
        // k starts from 1, max will be adjusted after parsing input
        SpinnerNumberModel kModel = new SpinnerNumberModel(1, 1, 1000, 1);
        JSpinner kSpinner = new JSpinner(kModel);
        windowRow.add(kLabel, BorderLayout.WEST);
        windowRow.add(kSpinner, BorderLayout.CENTER);

        // Line 3: button row
        JPanel buttonRow = new JPanel();
        JButton computeButton = new JButton("Compute statistics");
        buttonRow.add(computeButton);

        inputPanel.add(valuesRow);
        inputPanel.add(windowRow);
        inputPanel.add(buttonRow);

        // ----- Output area -----
        JTextArea outputArea = new JTextArea(8, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        frame.getContentPane().add(inputPanel, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // ----- Button action -----
        computeButton.addActionListener((ActionEvent e) -> {
            String raw = valuesField.getText().trim();
            if (raw.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please enter at least one numeric value.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Parse values (split by comma OR whitespace)
            String[] parts = raw.split("[,\\s]+");
            List<Double> values = new ArrayList<>();
            try {
                for (String p : parts) {
                    if (!p.isEmpty()) {
                        values.add(Double.parseDouble(p));
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame,
                        "Could not parse all values as doubles.\n"
                                + "Example: 1.0, 2, -3.5",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (values.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please enter at least one numeric value.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Ensure k is in [1, n]
            int n = values.size();
            int k = (Integer) kSpinner.getValue();
            if (k > n) {
                // automatically clamp k to n
                kModel.setMaximum(n);
                kModel.setValue(n);
                k = n;
            } else {
                kModel.setMaximum(n);
            }

            // Build toolkit
            MiniStatisticsToolkit toolkit = new MiniStatisticsToolkit1L();
            for (double v : values) {
                toolkit.addData(v);
            }

            // Compute statistics
            double mean = toolkit.mean();
            double min = toolkit.min();
            double max = toolkit.max();
            double stddev = toolkit.stddev();
            double movingAvg = toolkit.movingAverage(k);

            // Format output
            StringBuilder sb = new StringBuilder();
            sb.append("Input sequence: ").append(toolkit.toString()).append('\n');
            sb.append("Count n        = ").append(n).append('\n');
            sb.append(String.format("Mean           = %.4f%n", mean));
            sb.append(String.format("Min            = %.4f%n", min));
            sb.append(String.format("Max            = %.4f%n", max));
            sb.append(String.format("StdDev (pop.)  = %.4f%n", stddev));
            sb.append(
                    String.format("Moving avg (last %d) = %.4f%n", k, movingAvg));

            outputArea.setText(sb.toString());
        });

        frame.pack();
        frame.setLocationRelativeTo(null); // center on screen
        frame.setVisible(true);
    }

    /**
     * Entry point.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MiniStatisticsToolkitGuiDemo::createAndShowGui);
    }
}