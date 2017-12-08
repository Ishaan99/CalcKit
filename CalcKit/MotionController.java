/* @name MotionController
 * @author Ishaan Sharma
 * @since June 16th, 2017
 * A motion controller to drag the frame around the screen
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MotionController implements MouseListener, MouseMotionListener
{
     private JPanel panel;
     private Point startClick;
     
     /* @name MotionController
      * @param aPanel The panel that you will be dragging to move the frame
      * The constructor for the class 
      */
     public MotionController(JPanel aPanel)
     {
          this.panel = aPanel;
     }
     
     /* @name mouseDragged
      * @param e The MouseEvent event
      * Gets the displacement of the drag and sets the frame to its new location
      */
     public void mouseDragged(MouseEvent e) {
          int deltaX = e.getX()-startClick.x;
          int deltaY = e.getY()-startClick.y;
          JFrame root = (JFrame)SwingUtilities.getRoot(panel);
          root.setLocation(root.getLocation().x+deltaX, root.getLocation().y+deltaY);
     }
     
     /* @name mousePressed
      * @param e MouseEvent event
      * Gets the start click point
      */
     public void mousePressed(MouseEvent e) {
          startClick = e.getPoint();
     }
     
     public void mouseMoved(MouseEvent e){
          
     }
     @Override
     public void mouseClicked(MouseEvent e) {
          
     }
     @Override
     public void mouseEntered(MouseEvent e) {
          
     }
     @Override
     public void mouseExited(MouseEvent e) {
          
     }
     @Override
     public void mouseReleased(MouseEvent e) {
          
     }
}