package main;

import java.io.*;
import java.net.*;
import java.util.*;

public class MultiThreadedServerA2 {

  private ServerSocket serverSocket;

  public static void main(String[] args) {
    new MultiThreadedServerA2();
  }

  /**
   *  Constructor for Multithreaded Server 
   */
  public MultiThreadedServerA2() {
   
    // Create a server socket
    try {
      serverSocket = new ServerSocket(8000);
      System.out.println("Server started at " + new Date() + '\n');
    } catch (IOException e) {
      System.out.println("Could not create server socket on port 8888. Quitting.");
      System.exit(-1);
    }


    // Listen for connection requests
    while (true) {
      try {
        Socket socket = serverSocket.accept();
        ServerA2Thread sThread = new ServerA2Thread(socket);
        sThread.start();
      } catch (IOException e) {
        System.out.println("Error receiving from client! " + e.getMessage());
      }
    }
  }
}