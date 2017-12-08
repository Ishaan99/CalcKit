import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class ButtonController implements ActionListener
{     
     private DerivateModel model;
     private CalcKitModel calcModel;
     ImageIcon exitIcon = new ImageIcon("close-button.png");
     ImageIcon minimizeIcon = new ImageIcon("diminish.png");
     
     public ButtonController(DerivateModel aModel, CalcKitModel calcModel)
     {
          this.model = aModel;
          this.calcModel = calcModel;
     }
     public void actionPerformed(ActionEvent e)
     {
          JButton button = (JButton)e.getSource();
          String iconDesc = null;
          boolean exitMinBtn = false;
          try{
               iconDesc = ((ImageIcon)button.getIcon()).getDescription();
               exitMinBtn = true;
          }catch(NullPointerException ex){}
          
          if(button.getText()=="Home"){
               this.calcModel.setState(0);
          }
          else if(button.getText()=="Derivatives"){
               this.calcModel.setState(1);
          }
          else if(button.getText()=="Vectors"){
               this.calcModel.setState(2);
          }
          else if(button.getText()=="New Function")
               this.model.reset();
          else if(button.getText()=="Derivate")
               this.model.derivate(this.model.getFunction());
          else if(exitMinBtn){
               if(iconDesc=="Exit icon")
                    System.exit(0);
               else if(iconDesc=="Minimize icon"){
                    JFrame topFrame = (JFrame)SwingUtilities.getRoot(this.model.derivateView.main);
                    topFrame.setExtendedState(topFrame.getExtendedState() | Frame.ICONIFIED);
               }
          }
          else if(button.getText()=="Backspace")
               this.model.backspace();
          else{
               this.model.setFunction(((JButton)e.getSource()).getText());
               
          }
     }
}