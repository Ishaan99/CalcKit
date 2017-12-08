/* @name CalcKitGUI
 * @author Manraj Thind
 * @author Ishaan Sharma
 * @since June 16th, 2017
 * The main class for the CalcKit program
 */

import javax.swing.JFrame;

public class CalcKit {     
     public static void main(String[] args) {
          
          CalcKitModel calcKit = new CalcKitModel(); //Calckit model
          CalcKitGUI gui = new CalcKitGUI(calcKit); //Calckit gui
          
          JFrame frame = new JFrame("Derivative Calculator");
          frame.setContentPane(gui); //Sets the frame's content pane to the calckit gui
          frame.setUndecorated(true); //Removes frame border
          frame.pack(); //Packs the frame
          frame.setLocationRelativeTo(null); //Centers the frame on screen
          frame.setVisible(true); //Makes frame visible
     }
}