package connections;

import connection.PostgresConfiguration;
import org.testng.annotations.Test;

import java.sql.SQLException;

public class ConnectionsTesting {

   @Test(groups = {"postgresConnection"}, testName = "Check the connection to the database")
   public void TryingConnectionDB() throws SQLException {
       PostgresConfiguration connectionDB = new PostgresConfiguration();
       connectionDB.tryConnection();
   }

   @Test(groups = {"wip"}, testName = "Read the data from the database")
   public void takeTheDataFromDB(){
       PostgresConfiguration connectionDB = new PostgresConfiguration();
       connectionDB.readTheDataFromDB();
   }


}

