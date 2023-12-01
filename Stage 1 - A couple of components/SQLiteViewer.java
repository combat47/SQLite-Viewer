package viewer;

import javax.swing.*;
import java.awt.*;

public class SQLiteViewer extends JFrame {

    public SQLiteViewer() {
        super("SQLite Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 900);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        info();
    }

    private void info() {
        JLabel nameLabel = new JLabel();
        nameLabel.setText("Database: ");
        nameLabel.setBounds(10, 7, 70, 30);
        add(nameLabel);

        JTextField nameTextField = new JTextField();
        nameTextField.setBounds(75, 10, 500, 30);
        nameTextField.setName("FileNameTextField");
        add(nameTextField);

        JButton nameButton = new JButton("Open");
        nameButton.setName("OpenFileButton");
        nameButton.setBounds(585, 10, 80, 30);
        nameButton.setBackground(Color.lightGray);
        add(nameButton);
    }
}
