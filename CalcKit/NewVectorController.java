/* @name - NewVectorController
 * @author Manraj Thind
 * @since June 7, 2017
 * controller to handel an event in which the user wants to add or subtract a new vector**/

//changed

import javax.swing.*;
import java.awt.event.*;

public class NewVectorController implements ActionListener
{
     private VectorModel vModel;     //the vector model
     private int xCord;              //the x coordinate
     private int yCord;              //the y coordinate
     private String operation;       //the operation the user has selected
     private JTextField xCordField;  //the text field holding the x coordinate 
     private JTextField yCordField;  //the text field hilding the y coordinate
     private JButton button;         //the button (plus or minus) the user pressed
     private JFrame frame = new JFrame(); //the jframe being used
     
     /*@name newVectorController
      * @param vModel - the model being used
      * xCord - the field holding the x coordinate
      * yCord - the field holding the y coordinate**/
     public NewVectorController(VectorModel vModel, JTextField xCord, JTextField yCord)
     {
          this.xCordField = xCord;
          this.yCordField = yCord;
          this.vModel = vModel;
     }
     
     /*@name actionPrformed
      * @param e - the ActionEvent variable**/
     public void actionPerformed(ActionEvent e)
     {
          button = (JButton)e.getSource(); 
          operation = button.getText();
          try
          {
               //get the x coordinate and y coordinate
               xCord = Integer.parseInt(xCordField.getText()); 
               yCord = Integer.parseInt(yCordField.getText());
               
               //check to see they are within the bounds of the graph
               if((xCord >= -10 && xCord <= 10) && 
                  (yCord >= -10 && yCord <= 10))
               {
                    vModel.addVector(xCord, yCord, operation);
               }
               
               else
               {
                    JOptionPane.showMessageDialog(this.vModel.getGUI(), "Your coordinates are too large. They must be between -10 and 10",
                                            "Error", JOptionPane.ERROR_MESSAGE);
               }
               xCordField.setText("");
               yCordField.setText("");
          }
          
          catch(NumberFormatException n)
          {
               JOptionPane.showMessageDialog(this.vModel.getGUI(), "Only Integers valus are valid. Please try again",
                                            "Error", JOptionPane.ERROR_MESSAGE);
               xCordField.selectAll();
               yCordField.selectAll();
          }
     }     
}








