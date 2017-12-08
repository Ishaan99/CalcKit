/*@name EvaluateController
 * @since June 7, 2017
 * @author Manraj Thind
 * handels the event in which the user wants to evaluate the vector equation**/
//changed

import javax.swing.*;
import java.awt.event.*;
public class EvaluateController implements ActionListener
{
     private JTextField xCordField;
     private JTextField yCordField; 
     private int xCord;
     private int yCord;
     private VectorModel vModel;
     
     /**@name EvaluateController
       * the class constructor
       * @param vModel - the vector model
       * @param xCordField - x coordinate
       * @param yCordField - y Coordinate*/
     public EvaluateController(VectorModel vModel, JTextField xCordField, JTextField yCordField)
     {
          this.vModel = vModel;
          this.xCordField = xCordField;
          this.yCordField = yCordField;
     }
     
     /**@name actionPerformed
       * @param ActionEvent e - the AvtionEvent variable*/
     public void actionPerformed(ActionEvent e)
     {
          //check to see of the fields have a value in them
          //if not, and there are already vectors displayed, evaluate
          if((this.xCordField.getText().equals("") || this.yCordField.getText().equals(""))
            && this.vModel.getNumOfCoords() > 0)
          {
               this.vModel.setEvaluate(true);
               this.vModel.evaluate(true);
          }
          
          //otherwise, evaluate controller
          else
          {
               //check to see if it can be converted into an integer
               try
               {
                    //get the x coordinate and y coordinate
                    xCord = Integer.parseInt(xCordField.getText()); 
                    yCord = Integer.parseInt(yCordField.getText());
                    
                    //check to see they are within the bounds of the graph
                    if((xCord >= -10 && xCord <= 10) && 
                       (yCord >= -10 && yCord <= 10))
                    {
                         this.vModel.setEvaluate(true);
                         vModel.addVector(xCord, yCord, "=");
                    }
                    
                    //display error
                    else
                    {
                         JOptionPane.showMessageDialog(this.vModel.getGUI(), "Your coordinates are too large. They must be between -10 and 10",
                                                       "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    //reset text
                    xCordField.setText("");
                    yCordField.setText("");
               }
               
               //display error if variable type is invalid
               catch(NumberFormatException n)
               {
                    JOptionPane.showMessageDialog(this.vModel.getGUI(), "Only Integers valus are valid. Please try again",
                                                  "Error", JOptionPane.ERROR_MESSAGE);
                    xCordField.selectAll();
                    yCordField.selectAll();
               }
          }//end of if
     }//end of method
}//end of class






