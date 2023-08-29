package DAO;

import Util.ConnectionUtil;
import java.sql.Connection;

// You will need to design and create your own DAO classes from scratch. 
// You should refer to prior mini-project lab examples and course material for guidance.

// Please refrain from using a 'try-with-resources' block when connecting to your database. 
// The ConnectionUtil provided uses a singleton, and using a try-with-resources will cause issues in the tests.

public class MessageDAO {
    
    // Create JDBC connection object to connect to database //
    Connection connection = ConnectionUtil.getConnection();

}
