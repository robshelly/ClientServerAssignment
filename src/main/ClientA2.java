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

      
      // Send the radius to the server
      toServer.writeInt(1);
      toServer.writeUTF("Distributed Systems");
      toServer.flush();

      // Get area from the server
      String response = fromServer.readUTF();

      // Display to the text area
      System.out.println("Request Sent");
      System.out.println("Response received: "
        + response + '\n');
    }
    catch (IOException ex) {
      System.err.println(ex);
    }
  }
}
