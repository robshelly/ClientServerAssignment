package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ServerA2Thread extends Thread {
  private Thread thread;
  private Socket socket;
  private Connection conn;
  private Statement statement;
  
  
  // DB Info
  private final String userName = "root";
  private final String password = "";
  private final String serverName = "localhost";
  private final int portNumber = 3306;
  private final String dbName = "gradedatabase";
//  private final String tableName = "Employee";
  
  /**
   * Constructor for Server Thread.
   * 
   * @param socket Socket to connect to client.
   */
  public ServerA2Thread(Socket socket) {
    this.socket = socket;
    try {
      this.conn = getConnection();
    } catch (SQLException e) {
      System.out.println("Error connecting to database. " + e.getMessage());
    }
  }

  
  /* (non-Javadoc)
   * Method to start the Thread.
   */
  public void start() {
    thread = new Thread (this);
    thread.start ();
  }
  
  
  /* (non-Javadoc)
   * Main run method called by the thread when started.
   */
  public void run() {
    
    try {
      // Create data input and output streams
      DataInputStream inputFromClient = new DataInputStream(
        socket.getInputStream());
      DataOutputStream outputToClient = new DataOutputStream(
        socket.getOutputStream());
    
      while (true) {
        
        // Recieve request from client
        int studentId = inputFromClient.readInt();
        String moduleName = inputFromClient.readUTF();
        System.out.println("Request Received" );
        
        
        ResultSet rs = checkStudentRegistered(studentId);
        if (rs.next()) {
          outputToClient.writeUTF("Welcome "
              + rs.getString("FNAME") + " "
              + rs.getString("SNAME")
              + "... you are now connected to the Server");
        } else {
          outputToClient.writeUTF("Sorry " + studentId
              + ". You are not a registered student. Bye.");
        }
        
        
        // Send area back to the client
        outputToClient.writeUTF("Hello From Server");
    
       System.out.println("Message Recieved from Client: " + studentId);
       System.out.println("Response sent");
      }
    } catch (IOException e) {
      System.out.println("IO Error: " + e.getMessage());
    } catch (SQLException e) {
      System.out.println("Error reading rows. " + e.getMessage());
    }
  }

  
  /**
   * Get a new database connection
   * 
   * @return
   * @throws SQLException
   */
  public Connection getConnection() throws SQLException {
    Connection conn = null;
    Properties connectionProps = new Properties();
    connectionProps.put("user", this.userName);
    connectionProps.put("password", this.password);

    conn = DriverManager.getConnection("jdbc:mysql://" + this.serverName + ":" + this.portNumber + "/" + this.dbName,
        connectionProps);

    return conn;
  }
  
  
  /**
   * Run an SQL query against the database.
   * 
   * @param conn The connection to the Table.
   * @param command The query to run.
   * @return The result set from the query.
   * @throws SQLException
   */
  public ResultSet executeQuery(Connection conn, String command) {
    try {
      statement = conn.createStatement();
      statement.executeQuery(command); // This will throw a SQLException if it fails
      return statement.getResultSet();
    } catch (SQLException e) {
      System.out.println("Error executing query. " + e.getMessage());
    }
    return null;
  }
  
  /**
   * Queries the database for a single student.
   * 
   * @param studentId The ID of the student being queried
   * @return A ResultSet containing any rows returned by the query.
   */
  private ResultSet checkStudentRegistered(int studentId) {
    return executeQuery(conn, "SELECT * FROM `students` WHERE `STUD_ID` = " + studentId);
  }
}
