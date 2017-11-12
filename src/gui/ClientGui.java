package gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import main.ClientA2;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGui {

  private JFrame frame;
  private JTextField txtStudentId;
  private JTextField txtModuleName;
  private JButton btnSubmit;  
  private JTextArea txtResponse;
  private ClientResponseFields responseFields;
  
  private ClientA2 engine;

  /**
   * Create the Client GUI.
   */
  public ClientGui(ClientA2 engine) {
    this.engine = engine;
    initialize();
  }

  /**
   * Initialize the contents of the GUI.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new BorderLayout());
    
    JPanel pnlRequestFields = new JPanel();
    frame.getContentPane().add(pnlRequestFields, BorderLayout.NORTH);
    
    // Input Fields
    
    GridBagLayout gbl_pnlRequestFields = new GridBagLayout();
    gbl_pnlRequestFields.columnWidths = new int[]{0, 0, 0};
    gbl_pnlRequestFields.rowHeights = new int[]{0, 0};
    gbl_pnlRequestFields.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
    gbl_pnlRequestFields.rowWeights = new double[]{0.0, Double.MIN_VALUE};
    pnlRequestFields.setLayout(gbl_pnlRequestFields);

    
    JLabel lblStudentId = new JLabel("Student ID:");
    GridBagConstraints gbc_lblStudentId = new GridBagConstraints();
//    gbc_lblStudentId.insets = new Insets(0, 0, 5, 5);
    gbc_lblStudentId.anchor = GridBagConstraints.EAST;
    gbc_lblStudentId.gridx = 0;
    gbc_lblStudentId.gridy = 0;
    pnlRequestFields.add(lblStudentId, gbc_lblStudentId);
    
    txtStudentId = new JTextField();
    GridBagConstraints gbc_txtStudentId = new GridBagConstraints();
//    gbc_txtStudentId.insets = new Insets(0, 0, 5, 0);
    gbc_txtStudentId.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtStudentId.gridx = 1;
    gbc_txtStudentId.gridy = 0;
    pnlRequestFields.add(txtStudentId, gbc_txtStudentId);
    txtStudentId.setColumns(10);
    
    JLabel lblModuleName = new JLabel("Module Name");
    GridBagConstraints gbc_lblModuleName = new GridBagConstraints();
    gbc_lblModuleName.anchor = GridBagConstraints.EAST;
//    gbc_lblModuleName.insets = new Insets(0, 0, 0, 5);
    gbc_lblModuleName.gridx = 0;
    gbc_lblModuleName.gridy = 1;
    pnlRequestFields.add(lblModuleName, gbc_lblModuleName);
    
    txtModuleName = new JTextField();
    GridBagConstraints gbc_txtModuleName = new GridBagConstraints();
    gbc_txtModuleName.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtModuleName.gridx = 1;
    gbc_txtModuleName.gridy = 1;
    pnlRequestFields.add(txtModuleName, gbc_txtModuleName);
    txtModuleName.setColumns(10);
    
    // Submit Button
    
    btnSubmit = new JButton("Submit");
    GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
    gbc_btnSubmit.anchor = GridBagConstraints.EAST;
    gbc_btnSubmit.insets = new Insets(0, 0, 0, 5);
    gbc_btnSubmit.gridx = 0;
    gbc_btnSubmit.gridy = 2;
    pnlRequestFields.add(btnSubmit, gbc_btnSubmit);
    btnSubmit.addActionListener(new Listener());
    
    // Response Area
    
    txtResponse = new JTextArea();
    txtResponse.setRows(5);
    txtResponse.setColumns(30);
    txtResponse.setLineWrap(true);
    txtResponse.setWrapStyleWord(true);
    txtResponse.setEditable(false);
    
    JScrollPane scroll = new JScrollPane(txtResponse);
    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    frame.getContentPane().add(scroll, BorderLayout.CENTER);
    
    // Response fields
    responseFields = new ClientResponseFields();
    frame.getContentPane().add(responseFields, BorderLayout.SOUTH);
    
    frame.pack();
    frame.setVisible(true);
  }
  
  
  
  /**
   * Getter for text in Student ID field.
   * 
   * @return The String in the field
   */
  public String getStudentId() {
    return txtStudentId.getText();
  }
  
  /**
   * Getter for text in Module Name field.
   * 
   * @return The String in the field.
   */
  public String getModuleName() {
    return txtModuleName.getText();
  }
  
  
  /**
   * Appends txt to the Response field.
   * 
   * @param response The text to append.
   */
  public void addResponse(String response) {
    txtResponse.append(response + "\n");
  }
  
  /**
   * Set the text in the student ID field on the responseFields panel.
   * 
   * @param txt The text to set in the field.
   */
  public void setStudentIdField(String txt) {
    responseFields.setStudentIdField(txt);
  }
  
  /**
   * Set the text in the first name field on the responseFields panel.
   * 
   * @param txt The text to set in the field.
   */
  public void setFirstNameField(String txt) {
    responseFields.setFirstNameField(txt);
  }
  
  /**
   * Set the text in the surname field on the responseFields panel.
   * 
   * @param txt The text to set in the field.
   */
  public void setSurnameField(String txt) {
    responseFields.setSurnameField(txt);
  }
  
  /**
   * Set the text in the module field on the responseFields panel.
   * 
   * @param txt The text to set in the field.
   */
  public void setModuleField(String txt) {
    responseFields.setModuleField(txt);
  }
  
  /**
   * Set the text in the CA mark field on the responseFields panel.
   * 
   * @param txt The text to set in the field.
   */
  public void setCaMarkField(String txt) {
    responseFields.setCaMarkField(txt);
  }
  
  /**
   * Set the text in the exam mark field on the responseFields panel.
   * 
   * @param txt The text to set in the field.
   */
  public void setExamMarkField(String txt) {
    responseFields.setExamMarkField(txt);
  }
  
  /**
   * Set the text in the surname field on the responseFields panel.
   * 
   * @param txt The text to set in the field.
   */
  public void setOverallGradeField(String txt) {
    responseFields.setOverallGradeField(txt);
  }
  
  public void clearResponseFields() {
    responseFields.clearFields();
  }
  
  /**
   * Action Listener for the Client GUI.
   * 
   * @author Rob Shelly
   *
   */
  private class Listener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      if (getStudentId().length() > 0 && getModuleName().length() > 0) {
        engine.sendRequest(getStudentId(), getModuleName());
      }
    }
  }
    

}
