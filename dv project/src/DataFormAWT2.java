import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class DataFormAWT2 extends Frame implements ActionListener {
    TextField nameField;
    Choice datasetChoice;
    Button submitButton, backButton, viewButton, reviewButton, uploadButton, storedReviewsButton;
    Frame optionFrame, reviewFrame, uploadFrame;
    TextField reviewField;
    List<String> datasets = new ArrayList<>(); // List to store dataset names
    List<String[]> reviews = new ArrayList<>(); // List to store reviews with names

    public DataFormAWT2() {
        // Set the layout for the Frame
        setLayout(new FlowLayout());

        // Name field
        Label nameLabel = new Label("Name:");
        nameField = new TextField(20);
        add(nameLabel);
        add(nameField);

        // Dataset choice dropdown
        Label datasetLabel = new Label("Choose Dataset:");
        datasetChoice = new Choice();
        add(datasetLabel);
        add(datasetChoice);

        // Upload button
        uploadButton = new Button("Upload Dataset");
        add(uploadButton);

        // Submit button
        submitButton = new Button("Submit");
        add(submitButton);

        // Add action listeners
        submitButton.addActionListener(this);
        uploadButton.addActionListener(this);

        // Frame settings
        setTitle("Data Entry Form");
        setSize(300, 200);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();

        if (command.equals("Submit")) {
            // Handle the submit action
            String name = nameField.getText();
            String dataset = datasetChoice.getSelectedItem();
            System.out.println("Name: " + name);
            System.out.println("Selected Dataset: " + dataset);

            // Create a new Frame for the next options
            optionFrame = new Frame("Options");
            optionFrame.setLayout(new FlowLayout());
            optionFrame.setSize(300, 250);

            // Buttons for next options
            backButton = new Button("Back");
            viewButton = new Button("View Visualization");
            reviewButton = new Button("Submit Review");
            storedReviewsButton = new Button("Stored Reviews");

            optionFrame.add(backButton);
            optionFrame.add(viewButton);
            optionFrame.add(reviewButton);
            optionFrame.add(storedReviewsButton);

            // Add action listeners
            backButton.addActionListener(this);
            viewButton.addActionListener(this);
            reviewButton.addActionListener(this);
            storedReviewsButton.addActionListener(this);

            optionFrame.setVisible(true);

            optionFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    optionFrame.dispose();
                }
            });

            // Hide the main form
            setVisible(false);

        } else if (command.equals("Back")) {
            // Handle the back action
            System.out.println("Back button clicked");
            optionFrame.dispose();
            setVisible(true); // Show the main form again

        } else if (command.equals("View Visualization")) {
            // Handle the view visualization action
            String selectedDataset = datasetChoice.getSelectedItem();
            if (selectedDataset != null) {
                displayVisualization(selectedDataset);
            } else {
                System.out.println("No dataset selected.");
                showMessageDialog("Please select a dataset first.");
            }

        } else if (command.equals("Submit Review")) {
            // Create a review frame
            reviewFrame = new Frame("Submit Review");
            reviewFrame.setLayout(new FlowLayout());
            reviewFrame.setSize(300, 150);

            Label reviewLabel = new Label("Enter your review:");
            reviewField = new TextField(25);
            Button submitReviewButton = new Button("Submit Review");

            reviewFrame.add(reviewLabel);
            reviewFrame.add(reviewField);
            reviewFrame.add(submitReviewButton);

            submitReviewButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String review = reviewField.getText();
                    String name = nameField.getText(); // Get the name from the name field
                    if (!review.isEmpty() && !name.isEmpty()) {
                        reviews.add(new String[]{name, review});
                        System.out.println("Review submitted by " + name + ": " + review);
                        reviewField.setText(""); // Clear the field after submission
                    } else {
                        showMessageDialog("Please enter both name and review.");
                    }
                }
            });

            reviewFrame.setVisible(true);

            reviewFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    reviewFrame.dispose();
                }
            });
        } else if (command.equals("Upload Dataset")) {
            // Handle dataset upload
            handleUpload();
        } else if (command.equals("Stored Reviews")) {
            // Display stored reviews
            showStoredReviews();
        }
    }

    private void handleUpload() {
        // Create a file dialog to select a dataset
        FileDialog fileDialog = new FileDialog(this, "Select Dataset File", FileDialog.LOAD);
        fileDialog.setVisible(true);

        String filePath = fileDialog.getFile();
        String directory = fileDialog.getDirectory();

        if (filePath != null) {
            File selectedFile = new File(directory + filePath);
            if (selectedFile.exists()) {
                String datasetName = selectedFile.getName();
                datasets.add(datasetName);
                datasetChoice.add(datasetName);
                System.out.println("Dataset uploaded: " + datasetName);
                showMessageDialog("Dataset uploaded successfully.");
            } else {
                System.out.println("File does not exist.");
                showMessageDialog("Failed to upload dataset.");
            }
        }
    }

    private void showStoredReviews() {
        if (reviews.isEmpty()) {
            showMessageDialog("No reviews submitted yet.");
            return;
        }

        StringBuilder reviewsMessage = new StringBuilder("Stored Reviews:\n");
        for (String[] review : reviews) {
            reviewsMessage.append(review[0]).append(": ").append(review[1]).append("\n");
        }

        showMessageDialog(reviewsMessage.toString());
    }

    // Method to display a visualization from an image file
    private void displayVisualization(String datasetName) {
        Frame visualizationFrame = new Frame("Visualization - " + datasetName);
        visualizationFrame.setSize(600, 400);
        visualizationFrame.setLayout(new BorderLayout());

        // Create a panel to display the image
        Panel visualizationPanel = new Panel() {
            @Override
            public void paint(Graphics g) {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Image image = toolkit.getImage("path/to/your/visualization/directory/" + datasetName); // Adjust the path as needed

                if (image != null) {
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(Color.RED);
                    g.drawString("Failed to load the visualization image.", 50, 50);
                }
            }
        };

        visualizationFrame.add(visualizationPanel, BorderLayout.CENTER);
        visualizationFrame.setVisible(true);

        visualizationFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                visualizationFrame.dispose();
            }
        });
    }

    // Helper method to display a dialog message
    private void showMessageDialog(String message) {
        Dialog dialog = new Dialog(this, "Message", true);
        dialog.setLayout(new FlowLayout());
        Label msgLabel = new Label(message);
        Button okButton = new Button("OK");

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });

        dialog.add(msgLabel);
        dialog.add(okButton);
        dialog.setSize(300, 200);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new DataFormAWT2();
    }
}
