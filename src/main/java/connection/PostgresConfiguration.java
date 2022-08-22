package connection;

import java.sql.*;

public class PostgresConfiguration {

    private final String url = "jdbc:postgresql://localhost/appsData";
    private final String user = "admin";
    private final String password = "root";
    DataConstruct dataOrganizer = new DataConstruct();

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private String SQL;

    public Connection tryConnection() throws SQLException {
        Connection conn = connect();
        try {
            connect();
            System.out.println("Connected to the PostgreSQL database server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    private void importDataFromCSVFile(){}
    private void readData(){

         SQL = "SELECT *\n" +
                 "from \"appsData\".public.\"appsDataZap\"\n" +
                 "WHERE \"Scanned\"='N' OR \"Scanned\" IS NULL LIMIT 1";

            try (Connection conn = connect();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(SQL)) {
                // display actor information
                selectExpectedData(rs);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    }
    private void displayInformation(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.println(rs.getString("App ID") + "\t"
                    + rs.getString("App Name") + "\t"
                    + rs.getString("Scanned"));

        }
    }

    public void readTheDataFromDB(){
        readData();
    }

    private void selectExpectedData(ResultSet rs) throws SQLException {
        while (rs.next()){

            dataOrganizer.setApplicationName(rs.getString("App Name"));
            System.out.println(dataOrganizer.getApplicationName());
        }
      }
    private void addNewData(){}


}
