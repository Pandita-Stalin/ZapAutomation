package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConfiguration {

    private final String url = "jdbc:postgresql://localhost/appsData";
    private final String user = "admin";
    private final String password = "root";

    public Connection tryConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL database server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    private void importDataFromCSVFile(){}
    private void readData(){}
    private void selectExpectedData(){}
    private void addNewData(){}



}
