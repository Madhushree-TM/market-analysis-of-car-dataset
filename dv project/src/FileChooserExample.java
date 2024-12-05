import java.awt.*;
import java.awt.event.*;

public class FileChooserExample extends Frame {
    private Button openButton;
    private FileDialog fileDialog;

    public FileChooserExample() {
        // Create a Frame
        super("AWT File Chooser Example");

        // Create a Button
        openButton = new Button("Open Dataset");
        openButton.setBounds(50, 50, 150, 30);

        // Create a FileDialog
        fileDialog = new FileDialog(this, "Select Dataset", FileDialog.LOAD);

        // Add ActionListener to the button
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileDialog.setVisible(true);
                String file = fileDialog.getFile();
                if (file != null) {
                    System.out.println("Selected file: " + fileDialog.getDirectory() + file);
                }
            }
        });

        // Set up the Frame
        setLayout(null);
        add(openButton);
        setSize(300, 200);
        setVisible(true);

        // Add WindowListener to handle window close operation
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new FileChooserExample();
    }
}

