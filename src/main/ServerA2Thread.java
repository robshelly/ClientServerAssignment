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

import gui.ServerGui;

public class ServerA2Thread extends Thread {
  private Thread thread;
  
  // IO
  private Socket socket;
  private DataInputStream inputFromClient;
  private DataOutputStream outputToClient;
  
  // JDBC
  private Connection conn;
  
  // DB
  private final String userName = "root";
  private final String password = "";
  private final String serverName = "localhost";
  private final int portNumber = 3306;
  private final String dbName = "gradedatabase";
  
  // Client Info
  private String studentFirstName;
  private String studentSurname;
  
  // Gui
  private ServerGui gui;
  
  /**
   * Constructor for Server Thread.
   * 
   * @param socket Socket to connect to client.
   */
  public ServerA2Thread(Socket socket, ServerGui gui) {
    
    this.socket = socket;
    this.gui = gui;
    try {
      createIoStreams();    
      getConnection();
    } catch (IOException e1) {
      gui.appendToLog("Error creating  IO Streams. " + e1.getMessage());
    } catch (SQLException e2) {
      gui.appendToLog("Error connecting to database. " + e2.getMessage());
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
    
      while (true) {
        
        // Receive request from client
        int studentId = inputFromClient.readInt();
        String moduleName = inputFromClient.readUTF();
        
        gui.appendToLog("Processing ... Client-1 (//TODO) request ....");        
        if (checkStudentRegistered(studentId)) {
          sendStudentGrades(studentId, moduleName);
        }
      }
    } catch (IOException e1) {
      gui.appendToLog("Data IO Error: " + e1.getMessage());
    } catch (SQLException e2) {
      gui.appendToLog("Error reading rows from database. " + e2.getMessage());
    }
  }
  
  
  
  /**
   * Get a new database connection
   * 
   * @throws SQLException
   */
  private void getConnection() throws SQLException {
    Properties connectionProps = new Properties();
    connectionProps.put("user", this.userName);
    connectionProps.put("password", this.password);

    this.conn = DriverManager.getConnection("jdbc:mysql://" + this.serverName + ":" + this.portNumber + "/" + this.dbName,
        connectionProps);
  }
  
  
  /**
   * Create the data IO stream for communicating with server.
   * 
   * @throws IOException
   */
  private void createIoStreams() throws IOException {
    inputFromClient = new DataInputStream(socket.getInputStream());
    outputToClient = new DataOutputStream(socket.getOutputStream());
  }
  
  
  /**
   * Run an SQL query against the database.
   * 
   * @param conn The connection to the Table.
   * @param command The query to run.
   * @return The result set from the query.
   * @throws SQLException
   */
  private ResultSet executeQuery(Connection conn, String command) {
    try {
      Statement statement = conn.createStatement();
      statement.executeQuery(command); // This will throw a SQLException if it fails
      return statement.getResultSet();
    } catch (SQLException e) {
      System.out.println("Error executing query. " + e.getMessage());
    }
    return null;
  }
  
  
  /**
   * Check if a given student is registered.
   * 
   * @param studentId The ID of the student to check.
   * @return True is the student is registered, false otherwise.
   * @throws IOException
   * @throws SQLException
   */
  private boolean checkStudentRegistered(int studentId) throws IOException, SQLException {
    
    ResultSet rs = getStudent(studentId);
    if (rs.next()) {
      
      studentFirstName = rs.getString("FNAME");
      studentSurname = rs.getString("SNAME");
          
      outputToClient.writeUTF("Welcome " 
          + studentFirstName + " " + studentSurname
          + "... you are now connected to the Server");
      return true;
    } else {
      outputToClient.writeUTF("Sorry " + studentId
          + ". You are not a registered student.");
      outputToClient.writeUTF("Bye.");
      return false;
    }
  }
  
  
  /**
   * Queries the database for a single student.
   * 
   * @param studentId The ID of the student being queried
   * @return A ResultSet containing any rows returned by the query.
   */
  private ResultSet getStudent(int studentId) {
    return executeQuery(conn, "SELECT * FROM `students` WHERE `STUD_ID` = " + studentId);
  }
  
  
  /**
   * 
   * Send a students grades to the client.
   * 
   * @param studentId The students whose grades are being sent.
   * @param moduleName The module for which the grades are being sent.
   * @throws SQLException 
   * @throws IOException 
   */
  private void sendStudentGrades(int studentId, String moduleName) throws SQLException, IOException {
    ResultSet rs = getStudentRecord(studentId, moduleName);
    String result = "";
    
    
    if (rs.next()) {
      double caMarkWeighted = toOneDecimalPlace(rs.getInt("CA_MARK") * 0.3);
      double examMarkWeighted = toOneDecimalPlace(rs.getInt("EXAM_MARK") * 0.7);
      double overallGrade = caMarkWeighted + examMarkWeighted;
      
      // Send result comma separated values
      result = rs.getInt("STUD_ID") + "," + 
          studentFirstName + "," +
          studentSurname + "," +
          rs.getString("MODULENAME") + "," +
          rs.getInt("CA_MARK") +  "(" + caMarkWeighted + ")," +
          rs.getInt("EXAM_MARK") +  "(" + examMarkWeighted + ")," +
          overallGrade + ",";
    } else {
      result = "Grades not found. Please ensure you typed the module correclty!";
    }
    System.out.println("Sending Response");
    outputToClient.writeUTF(result);
  }
  
  
  /**
   * Get a students grades from the database.
   * 
   * @param studentId The ID of the student whose grades are being queried.
   */
  private ResultSet getStudentRecord(int studentId, String moduleName) {
    return executeQuery(conn,
        "SELECT * FROM `modulegrades` " +
        "WHERE `STUD_ID` = " + studentId + 
        " AND `ModuleName` = " + "'" + moduleName + "'");
  }
  
  
  /**
   * Rounds a number to a single decimal place.
   * 
   * @param num The number to round.
   * @return A double rounded to 1 decimal place.
   */
  private double toOneDecimalPlace(Double num) {
    return (double) Math.round(num * 10) / 10;
  }
}
