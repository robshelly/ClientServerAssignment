package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerA2Thread extends Thread {
  private Socket socket;
  private Thread thread;
  
  public ServerA2Thread(Socket socket) {
    this.socket = socket;
  }
  
  
  public void run() {    
    
    try {
      // Create data input and output streams
      DataInputStream inputFromClient = new DataInputStream(
        socket.getInputStream());
      DataOutputStream outputToClient = new DataOutputStream(
        socket.getOutputStream());
    
      while (true) {
        
        
        // Receive radius from the client
        String test = inputFromClient.readUTF();
    
        // Send area back to the client
        outputToClient.writeUTF("Hello From Server");
    
       System.out.println("Message Recieved from Client: " + test + '\n');
       System.out.println("Response sent \n");
       
       
      }
    } catch (IOException e) {
      System.out.println("IO Error: " + e.getMessage());
    }
  }
  
  public void start() {
    thread = new Thread (this);
    thread.start ();
  }

}
