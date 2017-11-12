package main;

import java.io.*;
import java.net.*;

import gui.ClientGui;

public class ClientA2 {

  // Socket
  private Socket socket;
  // IO streams
  private DataOutputStream toServer;
  private DataInputStream fromServer;
  
  // GUI
  private ClientGui gui;

  /**
   * Main Method
   * 
   */
  public static void main(String[] args) {
    new ClientA2();
  }

  /**
   * Constructor for Client.
   */
  public ClientA2() {
    
    gui = new ClientGui(this);
    
    try {
      // Create a socket to connect to the server
      socket = new Socket("localhost", 8000);
      // Socket socket = new Socket("130.254.204.36", 8000);
      // Socket socket = new Socket("drake.Armstrong.edu", 8000);

      // Create an input stream to receive data from the server
      fromServer = new DataInputStream(socket.getInputStream());

      // Create an output stream to send data to the server
      toServer = new DataOutputStream(socket.getOutputStream());
    }
    catch (IOException e) {
      System.out.println(e.getMessage()+ '\n');
    }
  }

  
  /**
   * Sends a request for grades to the server 
   */
  public void sendRequest(String studentId, String moduleName) {
    try {

      // Send the request to the server!
      toServer.writeInt(Integer.parseInt(studentId));
      toServer.writeUTF(moduleName);
      toServer.flush();
      
      // Listen for successful/unsuccessful validation, print
      String validation = fromServer.readUTF();
      gui.addResponse(validation);
      
      // Listen for response
      String response = fromServer.readUTF();
      parseResponse(response);
      
    }
    catch (IOException ex) {
      System.err.println(ex);
    }
  }
  
  /**
   * Parse a comma separated values in a response to a request.
   * 
   * @param response The response received.
   */
  private void parseResponse(String response) {
    
    String[] data = response.split(",");
    // If only one value received, must be some sort of error message
    // instead of the multiple requested values
    
    // Clear the fields, if there is a error it will be reported
    // but don't want to leave request data visible
    gui.clearResponseFields();
    
    if (data.length == 1) {
      gui.addResponse(data[0]);
    } else {
      // Otherwise, correct number of values should be present
      gui.addResponse("Server has sent the following data ....");
      gui.setStudentIdField(data[0]);
      gui.setFirstNameField(data[1]);
      gui.setSurnameField(data[2]);
      gui.setModuleField(data[3]);
      gui.setCaMarkField(data[4]);
      gui.setExamMarkField(data[5]);
      gui.setOverallGradeField(data[6]);
    }
  }
  
}
