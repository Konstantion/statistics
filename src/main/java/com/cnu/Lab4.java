package com.cnu;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lab4 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Histogram Generator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            frame.add(panel);

            JButton generateButton = new JButton("Generate Histogram");
            panel.add(generateButton);

            generateButton.addActionListener(e -> {
                String[] options = {"Normal Distribution", "Exponential Distribution"};
                int choice = JOptionPane.showOptionDialog(frame, "Choose the distribution type:",
                        "Distribution Type", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);

                if (choice == -1) return; // User canceled

                String input = JOptionPane.showInputDialog(frame, "Enter the sample size:");
                if (input == null) return; // User canceled

                int sampleSize;
                try {
                    sampleSize = Integer.parseInt(input);
                    if (sampleSize <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid sample size. Please enter a positive integer.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                List<Double> sample = choice == 0
                        ? generateNormalDistribution(sampleSize)
                        : generateExponentialDistribution(sampleSize);

                showHistogram(frame, sample, choice == 0 ? "Normal Distribution" : "Exponential Distribution");
            });

            frame.setVisible(true);
        });
    }

    private static List<Double> generateNormalDistribution(int size) {
        Random random = new Random();
        List<Double> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add(random.nextGaussian());
        }
        return data;
    }

    private static List<Double> generateExponentialDistribution(int size) {
        Random random = new Random();
        List<Double> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add(-Math.log(1 - random.nextDouble()));
        }
        return data;
    }

    private static void showHistogram(JFrame frame, List<Double> data, String title) {
        double[] values = data.stream().mapToDouble(Double::doubleValue).toArray();
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("Frequency", values, 10);

        JFreeChart histogram = ChartFactory.createHistogram(
                title,
                "Value",
                "Frequency",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        NumberAxis xAxis = (NumberAxis) histogram.getXYPlot().getDomainAxis();
        xAxis.setAutoRangeIncludesZero(false);

        JFrame chartFrame = new JFrame(title);
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.setSize(800, 600);
        chartFrame.setLocationRelativeTo(frame);
        chartFrame.add(new ChartPanel(histogram));
        chartFrame.setVisible(true);
    }
}
