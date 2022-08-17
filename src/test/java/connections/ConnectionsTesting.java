package connections;

import connection.PostgresConfiguration;
import org.testng.annotations.Test;

public class ConnectionsTesting {



   @Test
   public static void main(String args[]) throws Exception{

       PostgresConfiguration postgresConn = new PostgresConfiguration();
       postgresConn.tryConnection();
   }

}
