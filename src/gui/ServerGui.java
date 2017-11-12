package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import main.MultiThreadedServerA2;

@SuppressWarnings("serial")
public class ServerGui extends JFrame {
  
  private JFrame frame; 
  private JTextArea log ;
  
  /**
   * Create the Server GUI.
   * 
   * @param engine
   */
  public ServerGui(MultiThreadedServerA2 engine) {
    initialize();
    
  }
  
  /**
   * Initialize the contents of the GUI.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    log = new JTextArea();
    frame.getContentPane().add(new JScrollPane(log), BorderLayout.CENTER);

    frame.setTitle("Server");
    frame.setSize(500, 300);
    frame.setVisible(true);
  }
  
  
  /**
   * Append text to the log.
   * 
   * @param txt The text to append.
   */
  public void appendToLog(String txt) {
    log.append(txt + "\n");
  }

}
