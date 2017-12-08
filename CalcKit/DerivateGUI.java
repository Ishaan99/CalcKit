/* @name DerivativeGUI
 * @author Ishaan Sharma
 * @since June 16th, 2017
 * Creates the view for the derivative calculator and updates it
 */

import javax.swing.*;
import java.awt.*;

public class DerivateGUI extends JPanel
{
     private DerivateModel model; //The derivative model
     
     JPanel main = new JPanel(); //The main panel
     JPanel function = new JPanel(); //Panel for the function text box and buttons to calculate the derivative
     JTextField functionBox = new JTextField("Enter Function", 20); //The functions text box
     JButton calcDeriv = new JButton("Derivate"); //Button to calculate the derivative
     
     JPanel keyboard = new JPanel(); //Keyboard panel
     
     //Panels that will be inside the keyboard panel
     JPanel nums = new JPanel(); //Numbers panel
     JPanel operations = new JPanel(); //Operations panel
     JPanel operations2 = new JPanel(); //Second operations panel
     
     //Buttons for all the numbers for the keyboard
     JButton zero = new JButton("0");
     JButton one = new JButton("1");
     JButton two = new JButton("2");
     JButton three = new JButton("3");
     JButton four = new JButton("4");
     JButton five = new JButton("5");
     JButton six = new JButton("6");
     JButton seven = new JButton("7");
     JButton eight = new JButton("8");
     JButton nine = new JButton("9");
     
     //Buttons for the signs for the keyboard
     JButton lb = new JButton("("); //Left bracket
     JButton rb = new JButton(")"); //Right bracket
     JButton x = new JButton("x"); //Variable 'x'
     JButton exp = new JButton("^"); //Exponent button
     
     //Buttons for the operations for the keyboard
     JButton add = new JButton("+"); 
     JButton subtract = new JButton("-");
     JButton multiply = new JButton("*");
     JButton divide = new JButton("/");
     
     //Reset button to clear function textbox
     JButton reset = new JButton("New Function");
     
     //Backspace button to remove the last character from the function box
     JButton bs = new JButton("Backspace");
     
     //Steps panel
     JPanel stepPanel = new JPanel();
     JTextArea steps = new JTextArea(5, 40); //Creates a text area for the step panel
     JScrollPane scrollPane = new JScrollPane(steps); //Makes the steps text area into a scroll pane
     
     //The derivative text pane
     JTextPane derivative = new JTextPane();
     
     /* @name DerivateGUI
      * @param newModel the derivateModel
      * Constructor for this class
      */
     public DerivateGUI(DerivateModel newModel)
     {
          super();
          this.model = newModel;
          this.model.setGUI(this);
          this.layoutView();
          this.registerControllers();
          this.update();
     }
     /* @name layoutView
      * Initialises the view
      */
     private void layoutView()
     {
          this.setBackground(new Color(252, 119, 83)); //Sets the background color of this panel
          
          main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
          keyboard.setLayout(new BoxLayout(keyboard, BoxLayout.Y_AXIS));
          
          //Adds the function box, calculate derivate button, reset button, and backspace button to the function panel
          function.add(functionBox);
          function.add(calcDeriv);
          function.add(reset);
          function.add(bs);
          function.setBackground(new Color(242, 239, 234)); //Sets the background color of the function panel
          
          //Adds the number buttons to the nums panel
          nums.add(zero);
          nums.add(one);
          nums.add(two);
          nums.add(three);
          nums.add(four);
          nums.add(five);
          nums.add(six);
          nums.add(seven);
          nums.add(eight);
          nums.add(nine);
          
          //Adds the operation buttons to the first operations panel
          operations.add(add);
          operations.add(subtract);
          operations.add(multiply);
          operations.add(divide);
          
          //Adds the sign buttons to the second operations panel
          operations2.add(lb);
          operations2.add(rb);
          operations2.add(x);
          operations2.add(exp);
          
          keyboard.add(nums);
          keyboard.add(operations2);
          keyboard.add(operations);
          
          functionBox.setEditable(false);
          derivative.setEditable(false);
          
          steps.setLineWrap(true);
          steps.setWrapStyleWord(true);
          stepPanel.add(scrollPane, BorderLayout.CENTER);
          
          main.add(function);
          main.add(keyboard);
          main.add(stepPanel);
          main.add(derivative);
          
          this.add(main);
     }
     /**
      *  Registers controllers
      */
     private void registerControllers()
     {
          //Button controller
          ButtonController btnController = new ButtonController(this.model, new CalcKitModel());
          
          //Add action listeners to all the buttons on the derivative gui
          this.calcDeriv.addActionListener(btnController);
          this.reset.addActionListener(btnController);
          this.bs.addActionListener(btnController);
          this.zero.addActionListener(btnController);
          this.one.addActionListener(btnController);
          this.two.addActionListener(btnController);
          this.three.addActionListener(btnController);
          this.four.addActionListener(btnController);
          this.five.addActionListener(btnController);
          this.six.addActionListener(btnController);
          this.seven.addActionListener(btnController);
          this.eight.addActionListener(btnController);
          this.nine.addActionListener(btnController);
          this.lb.addActionListener(btnController);
          this.rb.addActionListener(btnController);
          this.x.addActionListener(btnController);
          this.exp.addActionListener(btnController);
          this.add.addActionListener(btnController);
          this.subtract.addActionListener(btnController);
          this.multiply.addActionListener(btnController);
          this.divide.addActionListener(btnController);
     }
     /**
      *  Updates the view
      */
     public void update()
     {
          //If state = 0 it sets the gui to show the keyboard
          if(this.model.getState()==0){
               calcDeriv.setVisible(true);
               reset.setVisible(false);
               bs.setVisible(true);
               scrollPane.setVisible(false);
               nums.setVisible(true);
               operations2.setVisible(true);
               operations.setVisible(true);
               derivative.setVisible(false);
          }
          //If state = 1 it sets the gui to show the derivative and the steps
          else if(this.model.getState()==1){
               calcDeriv.setVisible(false);
               reset.setVisible(true);
               bs.setVisible(false);
               scrollPane.setVisible(true);
               nums.setVisible(false);
               operations2.setVisible(false);
               operations.setVisible(false);
               derivative.setVisible(true);
          }
          
          functionBox.setText(this.model.getFunction()); //Sets the function box to the function
          derivative.setText(this.model.getDerivative()); //Sets the derivative box to the derivative
          
          steps.setText(""); //Resets the steps text area
          steps.append(this.model.getSteps()); //Sets the steps in the text area
          
          this.model.setKeyboard(); //Sets the keyboard based on the last character entered
          
          this.setVisible(true); //Makes the panel visible
     }
}