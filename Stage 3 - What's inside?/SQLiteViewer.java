package viewer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SQLiteViewer extends JFrame {

    final String TITLE_OF_PROGRAM = "SQLite Viewer";
    final String BTN_ENTER = "Open";
    final String BTN_EXEC = "Execute";
    DataBase dataBase;
    private ArrayList<String> tables;

    public SQLiteViewer() {
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 800);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JLabel nameLabel = new JLabel("Database: ");
        nameLabel.setBounds(10, 7, 70, 30);
        add(nameLabel);

        JTextField fileNameTextField = new JTextField();
        fileNameTextField.setName("FileNameTextField");
        fileNameTextField.setBounds(75, 10, 515, 30);
        add(fileNameTextField);

        JButton openFileButton = new JButton(BTN_ENTER);
        openFileButton.setName("OpenFileButton");
        openFileButton.setBackground(Color.lightGray);
        openFileButton.setBounds(595, 10, 80, 30);
        add(openFileButton);

        JLabel nameLabelCombo = new JLabel("Table: ");
        nameLabelCombo.setBounds(10, 47, 70, 30);
        add(nameLabelCombo);

        JComboBox<String> tablesComboBox = new JComboBox<>();
        tablesComboBox.setName("TablesComboBox");
        tablesComboBox.setBackground(Color.lightGray);
        tablesComboBox.setBounds(75, 50, 600, 30);
        add(tablesComboBox);

        JLabel nameLabelQuery = new JLabel("Query: ");
        nameLabelQuery.setBounds(10, 87, 70, 30);
        add(nameLabelQuery);

        JTextArea queryTextArea = new JTextArea();
        queryTextArea.setName("QueryTextArea");
        queryTextArea.setBounds(75, 90, 600, 90);
        add(queryTextArea);

        JButton executeQueryButton = new JButton(BTN_EXEC);
        executeQueryButton.setName("ExecuteQueryButton");
        executeQueryButton.setBackground(Color.lightGray);
        executeQueryButton.setBounds(575, 190, 100, 30);
        add(executeQueryButton);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        table.setName("Table");
        JScrollPane contentTable = new JScrollPane(table);
        contentTable.setBounds(10, 230, getWidth() - 33, 300);
        add(contentTable);

        openFileButton.addActionListener(e -> {
            String fileName = fileNameTextField.getText();
            if (!fileName.equals("")) {
                System.out.println(fileName);
                dataBase = new DataBase(fileName);
                tables = dataBase.getTableNames();
                tablesComboBox.removeAllItems();
                tables.forEach(tablesComboBox::addItem);
                queryTextArea.removeAll();
            }
        });

        tablesComboBox.addActionListener(e -> {
            String item = (String) tablesComboBox.getSelectedItem();
            queryTextArea.removeAll();
            String request = "SELECT * FROM " + item + ";";
            queryTextArea.setText(request);
        });

        executeQueryButton.addActionListener(e -> {
            model.setRowCount(0);

            String request = queryTextArea.getText();
            dataBase.getResponse(request);

            Object[] columns = dataBase.getColumnNames().toArray();
            model.setColumnIdentifiers(columns);

            ArrayList<ArrayList<String>> data = dataBase.getContentTable();
            for (ArrayList<String> row : data) {
                model.addRow(row.toArray());
            }
        });

        setVisible(true);
    }
}
