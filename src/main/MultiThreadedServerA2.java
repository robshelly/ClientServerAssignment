package main;

import java.io.*;
import java.net.*;
import java.util.*;

import gui.ServerGui;

public class MultiThreadedServerA2 {

  private ServerSocket serverSocket;
  private ServerGui gui;

  public static void main(String[] args) {
    new MultiThreadedServerA2();
  }

  /**
   *  Constructor for Multithreaded Server 
   */
  public MultiThreadedServerA2() {
    
    gui = new ServerGui(this);
   
    // Create a server socket
    try {
      serverSocket = new ServerSocket(8000);
      gui.appendToLog("Server started at " + new Date() + '\n');
    } catch (IOException e) {
      gui.appendToLog("Could not create server socket on port 8888. Quitting.");
      System.exit(-1);
    }


    // Listen for connection requests
    while (true) {
      try {
        Socket socket = serverSocket.accept();
        ServerA2Thread sThread = new ServerA2Thread(socket, gui);
        sThread.start();
      } catch (IOException e) {
        System.out.println("Error receiving from client! " + e.getMessage());
      }
    }
  }
}