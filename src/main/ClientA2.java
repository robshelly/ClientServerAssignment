package main;

import java.io.*;
import java.net.*;

public class ClientA2 {

  // Socket
  private Socket socket;
  // IO streams
  private DataOutputStream toServer;
  private DataInputStream fromServer;

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
    
    try {
      // Create a socket to connect to the server
      socket = new Socket("localhost", 8000);
      // Socket socket = new Socket("130.254.204.36", 8000);
      // Socket socket = new Socket("drake.Armstrong.edu", 8000);

      // Create an input stream to receive data from the server
      fromServer = new DataInputStream(socket.getInputStream());

      // Create an output stream to send data to the server
      toServer = new DataOutputStream(socket.getOutputStream());
      
      sendRequest();
    }
    catch (IOException e) {
      System.out.println(e.getMessage()+ '\n');
    }
  }

  
  /**
   * Sends a request for grades to the server 
   */
  public void sendRequest() {
    try {

      
      // Send the request to the server!
      toServer.writeInt(1);
      toServer.writeUTF("Game Design");
      toServer.flush();
      
      // Listen for successful/unsuccessful validation, print
      String validation = fromServer.readUTF();
      System.out.println(validation);
      
      // Listen for response
      String response = fromServer.readUTF();
      System.out.println("The server has sent the following data...\n" + response);
    }
    catch (IOException ex) {
      System.err.println(ex);
    }
  }
}
