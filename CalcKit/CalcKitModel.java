/* @name CalcKitModel
 * @author Ishaan Sharma
 * @since June 16th, 2017
 * Handles the model of the CalcKit program
 */

import javax.swing.*;

public class CalcKitModel extends Object
{
     protected CalcKitGUI calcKitView; //CalcKitGUI view
     
     int state = 0; //0 - Home, 1 - Derivatives, 2 - Vectors
     
     /* @name CalcKitModel
      * The constructor for this class
      */
     public CalcKitModel()
     {
          super();
     }
     /* @name getState
      * @return the current state of the gui
      * Returns the state to be used to show the correct gui
      */
     public int getState()
     {
          return this.state;
     }
     /* @name setState
      * @param state - the state you want to set it to
      * Sets the new state and updates the view
      */
     public void setState(int state)
     {
          this.state = state;
          this.updateView();
          this.repack();
     }
     /* @name repack
      * Repacks the top frame to resize the frame
      */
     public void repack()
     {
          JFrame topFrame = (JFrame)SwingUtilities.getRoot(this.calcKitView.main);
          topFrame.pack();
          topFrame.setLocationRelativeTo(null);
     }
     /* @name setGUI
      * @param currentGUI - The CalcKitGUI to set this class's GUI
      */
     public void setGUI(CalcKitGUI currentGUI)
     {
          this.calcKitView = currentGUI;
     }
     /* @name updateView
      * Updates the view using the method in the CalcKitGUI
      */
     public void updateView()
     {
          calcKitView.update();
     }
}