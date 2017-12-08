/*@name ClearSceenController
 * @author Manraj Thind
 * @since June 7th, 2017
 * handels the event in which the user wants to clear the screen**/

import javax.swing.*;
import java.awt.event.*;

public class ClearScreenController implements ActionListener
{
     private VectorModel vModel;
     
     /*@name ClearScreenController
      * @param vModel - the model being used
      * the class constructor**/
     public ClearScreenController(VectorModel vModel)
     {
          this.vModel = vModel;
     }
     
     /**@name actionPerformed*
       * This method calls the clear() method from the VectorModel Class 
       * @param e - the ActionEvent variable to get source*/
     public void actionPerformed(ActionEvent e)
     {
          this.vModel.clear();
     }
}