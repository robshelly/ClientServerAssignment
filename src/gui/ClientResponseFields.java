package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ClientResponseFields extends JPanel {
  
  public String[] labels = {"Student ID", "First Name", "Surname", "Module", "CA Mark", "Exam Mark", "Overall Grade"};
  public Map<String, JTextField> inputMap = new HashMap<String, JTextField>();

  /**
   * Create the panel.
   */
  public ClientResponseFields() {
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[]{0, 0};
    gridBagLayout.rowHeights = new int[]{0, 0, 0};
    gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
    gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
    setLayout(gridBagLayout);
    
    for(int i=0; i< labels.length; i++){
      
      // Create all the labels
      JLabel lbl = new JLabel(labels[i]);
      GridBagConstraints gbc_lbl = new GridBagConstraints();
      gbc_lbl.fill = GridBagConstraints.HORIZONTAL;
      gbc_lbl.gridx = 0;
      gbc_lbl.gridy = i;
      add(lbl, gbc_lbl);
      
      // Create the text fields
      JTextField txtField = new JTextField(10);
      lbl.setLabelFor(txtField);
      inputMap.put(labels[i], txtField);
      
      GridBagConstraints gbc_txt = new GridBagConstraints();
      gbc_txt.fill = GridBagConstraints.HORIZONTAL;
      gbc_txt.gridx = 1;
      gbc_txt.gridy = i;
      add(txtField, gbc_txt);
    }
  }
  
  /**
   * Set the text in the Student ID field.
   * 
   * @param txt The text to enter in the field.
   */
  public void setStudentIdField(String txt) {
    inputMap.get(labels[0]).setText(txt);   
  }
  
  /**
   * Set the text in the First Name field.
   * 
   * @param txt The text to enter in the field.
   */
  public void setFirstNameField(String text) {
    inputMap.get(labels[1]).setText(text);   
  }
  
  /**
   * Set the text in the Surname field.
   * 
   * @param txt The text to enter in the field.
   */
  public void setSurnameField(String text) {
    inputMap.get(labels[2]).setText(text);   
  }
  
  /**
   * Set the text in the Module field.
   * 
   * @param txt The text to enter in the field.
   */
  public void setModuleField(String text) {
    inputMap.get(labels[3]).setText(text);   
  }
  
  /**
   * Set the text in the CA MArk field.
   * 
   * @param txt The text to enter in the field.
   */
  public void setCaMarkField(String text) {
    inputMap.get(labels[4]).setText(text);   
  }
  
  /**
   * Set the text in the Exam Mark field.
   * 
   * @param txt The text to enter in the field.
   */
  public void setExamMarkField(String text) {
    inputMap.get(labels[5]).setText(text);   
  }
  
  /**
   * Set the text in the Overall Grade field.
   * 
   * @param txt The text to enter in the field.
   */
  public void setOverallGradeField(String text) {
    inputMap.get(labels[6]).setText(text);   
  }
  
  public void clearFields() {
    for(int i=0; i< labels.length; i++){
      inputMap.get(labels[i]).setText("");
    }
  }
}
