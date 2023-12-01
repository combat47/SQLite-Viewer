package viewer;

import java.sql.*;
import java.util.ArrayList;

public class DataBase {

    private final String url;
    private ArrayList<String> columnNames;
    private ArrayList<ArrayList<String>> contentTable;

    public DataBase(String fileName) {
        this.url = "jdbc:sqlite:" + fileName;
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public ArrayList<String> getTableNames() {
        String sql = "SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%';";
        ArrayList<String> result = new ArrayList<>();

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                result.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public void getResponse(String request) {
        columnNames = new ArrayList<>();
        int columnNumbs;
        contentTable = new ArrayList<>();

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(request)) {

            ResultSetMetaData columns = rs.getMetaData();
            columnNumbs = columns.getColumnCount();
            for (int i = 1; i <= columnNumbs; i++) {
                columnNames.add(columns.getColumnName(i));
            }

            while (rs.next()) {
                ArrayList<String> contentLine = new ArrayList<>();
                for (String columnName : columnNames) {
                    String content = rs.getString(columnName);
                    contentLine.add(content);
                }
                contentTable.add(contentLine);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<String> getColumnNames() {
        return columnNames;
    }

    public ArrayList<ArrayList<String>> getContentTable() {
        return contentTable;
    }
}
