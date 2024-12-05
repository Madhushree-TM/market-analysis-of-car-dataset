import java.awt.*;
import java.awt.event.*;

public class DataFormAWT extends Frame implements ActionListener {
    TextField nameField;
    Choice datasetChoice;
    Button submitButton, backButton, viewButton, reviewButton;
    Frame reviewFrame;
    TextField reviewField;

    public DataFormAWT() {
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
        for (int i = 1; i <= 6; i++) {
            datasetChoice.add("Dataset " + i);
        }
        add(datasetLabel);
        add(datasetChoice);

        // Submit button
        submitButton = new Button("Submit");
        add(submitButton);

        // Add action listener
        submitButton.addActionListener(this);

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
            Frame optionFrame = new Frame("Options");
            optionFrame.setLayout(new FlowLayout());
            optionFrame.setSize(300, 200);

            // Buttons for next options
            backButton = new Button("Back");
            viewButton = new Button("View Visualization");
            reviewButton = new Button("Submit Review");

            optionFrame.add(backButton);
            optionFrame.add(viewButton);
            optionFrame.add(reviewButton);

            // Add action listeners
            backButton.addActionListener(this);
            viewButton.addActionListener(this);
            reviewButton.addActionListener(this);

            optionFrame.setVisible(true);

            optionFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    optionFrame.dispose();
                }
            });
        } else if (command.equals("Back")) {
            // Handle the back action
            System.out.println("Back button clicked");
            // Logic to navigate back can be added here
        } else if (command.equals("View Visualization")) {
            // Handle the view visualization action
            System.out.println("Viewing visualization for " + datasetChoice.getSelectedItem());
            // Visualization logic can be added here
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
                    System.out.println("Review: " + review);
                    reviewFrame.dispose();
                }
            });

            reviewFrame.setVisible(true);

            reviewFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    reviewFrame.dispose();
                }
            });
        }
    }

    public static void main(String[] args) {
        new DataFormAWT();
    }
}
