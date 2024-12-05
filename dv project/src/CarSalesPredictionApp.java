import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

class CarSalesPredictionApp extends JFrame {
    JTextField carIdField;
    JLabel infoLabel;
    JScrollPane scrollPane;
    JButton uploadButton;
    JLabel datasetLabel;
    List<String[]> dataset;
    String[] headers;
    String[] predictionColumns;
    JTextArea descriptionBox;  // For displaying dataset details
    JTextArea storyBox;  // For displaying story about the created graph

    public CarSalesPredictionApp() {
        createView();
        setTitle("CAR MARKET Form");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set the window to fullscreen
        setLocationRelativeTo(null);
        setResizable(false);
    }

    void createView() {
        JPanel panel = new JPanel(new GridBagLayout());
        getContentPane().add(panel);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Upload Dataset:"), constraints);

        constraints.gridx = 1;
        uploadButton = new JButton("Upload");
        panel.add(uploadButton, constraints);

        datasetLabel = new JLabel("No file chosen");
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(datasetLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(new JLabel("Car ID:"), constraints);

        constraints.gridx = 1;
        carIdField = new JTextField(20);
        panel.add(carIdField, constraints);

        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        JButton submitButton = new JButton("Submit");
        panel.add(submitButton, constraints);

        constraints.gridy = 4;
        JButton predictionButton = new JButton("Prediction Columns");
        panel.add(predictionButton, constraints);

        constraints.gridy = 5;
        JButton visualizeButton = new JButton("Generate Visualization");
        panel.add(visualizeButton, constraints);

        infoLabel = new JLabel("");
        scrollPane = new JScrollPane(infoLabel);
        scrollPane.setPreferredSize(new Dimension(800, 400));  // Set the preferred size for the scroll pane

        constraints.gridy = 6;
        constraints.gridwidth = 2;
        panel.add(scrollPane, constraints);

        // Add description box for dataset details
        constraints.gridy = 7;
        descriptionBox = new JTextArea(5, 40);
        descriptionBox.setLineWrap(true);
        descriptionBox.setWrapStyleWord(true);
        descriptionBox.setEditable(false);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionBox);
        panel.add(descriptionScrollPane, constraints);

        // Add story box for graph story
        constraints.gridy = 8;
        storyBox = new JTextArea(5, 40);
        storyBox.setLineWrap(true);
        storyBox.setWrapStyleWord(true);
        storyBox.setEditable(false);
        JScrollPane storyScrollPane = new JScrollPane(storyBox);
        panel.add(storyScrollPane, constraints);

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpload();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });

        predictionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAnalyze();
            }
        });

        visualizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGenerateVisualization();
            }
        });
    }

    private void handleUpload() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            datasetLabel.setText(selectedFile.getName());
            dataset = loadDataset(selectedFile);

            // Display dataset description
            descriptionBox.setText("Dataset: " + selectedFile.getName() + "\nNumber of Records: " + dataset.size() + "\nColumns: ");
            for (String header : headers) {
                descriptionBox.append(header + ", ");
            }
            descriptionBox.setText(descriptionBox.getText().replaceAll(", $", ""));
        }
    }

    private List<String[]> loadDataset(File file) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    headers = line.split(",");
                    isHeader = false;
                } else {
                    data.add(line.split(","));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private void handleSubmit() {
        String carId = carIdField.getText();
        StringBuilder info = new StringBuilder("<html>");

        if (dataset != null && !dataset.isEmpty()) {
            boolean found = false;
            for (String[] record : dataset) {
                if (record[0].equals(carId)) {  // Assuming the first column is the Car ID
                    found = true;
                    for (int i = 0; i < headers.length; i++) {
                        info.append(headers[i]).append(": ").append(record[i]).append("<br>");
                    }
                    break;
                }
            }
            if (!found) {
                info.append("Car ID not found.");
            }
        } else {
            info.append("Dataset not loaded.");
        }
        info.append("</html>");
        infoLabel.setText(info.toString());
    }

    private void handleAnalyze() {
        if (dataset == null || dataset.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Dataset not loaded.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField columnsField = new JTextField();
        Object[] message = {
                "Enter Column Names (comma-separated, e.g., Body Style,Price):", columnsField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Enter Columns for Prediction", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String columnsInput = columnsField.getText().trim();
            predictionColumns = columnsInput.split(",");

            if (predictionColumns.length != 2) {
                JOptionPane.showMessageDialog(this, "Please enter exactly two columns.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Integer> colIndices = new ArrayList<>();
            for (String col : predictionColumns) {
                col = col.trim();
                int colIndex = -1;
                for (int i = 0; i < headers.length; i++) {
                    if (headers[i].equalsIgnoreCase(col)) {
                        colIndex = i;
                        break;
                    }
                }
                if (colIndex == -1) {
                    JOptionPane.showMessageDialog(this, "Column " + col + " not found in dataset.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    colIndices.add(colIndex);
                }
            }

            // Display information for the selected columns in two columns
            StringBuilder analysisInfo = new StringBuilder("<html>");
            analysisInfo.append("<table>");
            analysisInfo.append("<tr><th>Column</th><th>Value</th></tr>");
            for (String[] record : dataset) {
                for (int colIndex : colIndices) {
                    analysisInfo.append("<tr>")
                            .append("<td>").append(headers[colIndex]).append("</td>")
                            .append("<td>").append(record[colIndex]).append("</td>")
                            .append("</tr>");
                }
                analysisInfo.append("<tr><td colspan='2'><hr></td></tr>");  // Add a separator between records
            }
            analysisInfo.append("</table>");
            analysisInfo.append("</html>");
            infoLabel.setText(analysisInfo.toString());
            scrollPane.getViewport().revalidate();  // Refresh the scroll pane view
        }
    }

    private void handleGenerateVisualization() {
        if (dataset == null || dataset.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Dataset not loaded.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (predictionColumns == null || predictionColumns.length != 2) {
            JOptionPane.showMessageDialog(this, "Please specify exactly two columns for prediction.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Determine indices for the prediction columns
        int xIndex = -1;
        int yIndex = -1;
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].equalsIgnoreCase(predictionColumns[0].trim())) {
                xIndex = i;
            }
            if (headers[i].equalsIgnoreCase(predictionColumns[1].trim())) {
                yIndex = i;
            }
        }

        if (xIndex == -1 || yIndex == -1) {
            JOptionPane.showMessageDialog(this, "Specified columns not found in dataset.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Aggregate data for the chart
        Map<String, Map<String, Integer>> dataMap = new HashMap<>();
        for (String[] record : dataset) {
            String xValue = record[xIndex];
            String yValue = record[yIndex];
            dataMap.computeIfAbsent(xValue, k -> new HashMap<>())
                    .merge(yValue, 1, Integer::sum);
        }

        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Map<String, Integer>> entry : dataMap.entrySet()) {
            String xValue = entry.getKey();
            for (Map.Entry<String, Integer> subEntry : entry.getValue().entrySet()) {
                String yValue = subEntry.getKey();
                int count = subEntry.getValue();
                barDataset.addValue(count, yValue, xValue);
            }
        }

        // Create the bar chart
        JFreeChart barChart = ChartFactory.createBarChart(
                "Car Sales Visualization",
                predictionColumns[0],
                "Count",
                barDataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        // Show the chart in a new window
        JFrame chartFrame = new JFrame("Bar Chart");
        chartFrame.setLayout(new GridLayout(1, 1));
        chartFrame.add(new ChartPanel(barChart));
        chartFrame.pack();
        chartFrame.setVisible(true);

        // Display the story about the created graph
        String story = "This bar chart visualizes the relationship between " + predictionColumns[0].trim() + " and " + predictionColumns[1].trim() +
                ". The chart provides insights into how these variables correlate, revealing trends in the dataset, such as which " +
                predictionColumns[0].trim() + " category tends to have higher or lower values in " + predictionColumns[1].trim() + ".";
        storyBox.setText(story);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CarSalesPredictionApp().setVisible(true);
            }
        });
    }
}
