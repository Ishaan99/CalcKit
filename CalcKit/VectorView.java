/*@name VectoreView
 * @since june 6 2017
 * @author Manraj Thind
 * creates the view for the program and updates it**/
//changed
import javax.swing.*;
import java.awt.*;

public class VectorView extends JComponent
{
     private VectorModel vModel;                                  //the model object
     private Graph graph;                                        //the graph
     private JTextField xCord = new JTextField(2);                //the text field for the x coordinate
     private JTextField yCord = new JTextField(2);                //the text field for the y cordinate
     private JButton evaluateButton = new JButton("Evaluate");    //the button used to exalute expression
     private JButton plusButton = new JButton("+");               //the muttons used to add to another vector
     private JButton minusButton = new JButton("-");              //the button used to subtract from another vector
     private JTextArea equation = new JTextArea(1, 20);           //the textfield used to display the vector equation
     private JPanel equationPanel = new JPanel();
     private JScrollPane equationScrollPane = new JScrollPane(this.equation, 
                                                              JScrollPane.VERTICAL_SCROLLBAR_NEVER, 
                                                              JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); //the vertical scroll pane to diplay the equation
     private JTextArea explainArea = new JTextArea(8,80);        //the text area 
     private JLabel firstBracket = new JLabel("(");             //the first practed label
     private JLabel secondBracket = new JLabel(")");            //the second bracket label
     private JLabel comma = new JLabel(",");                    //the comma label
     private JPanel graphPanel = new JPanel();                 //the graph's jpanel
     private JPanel eastPanel = new JPanel();                  //the panel used to hold components in the east
     private JPanel explainPanel = new JPanel();               //the panel used to old the explanation pane
     private JScrollPane explainScrollPane = new JScrollPane(this.explainArea
                                                            ,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
                                                            ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);    //the scrollpane for the explanation pane
     private JPanel inputPanel = new JPanel();                                   //the panel that contains all the possible input areas
     private JButton clearButton = new JButton(" Clear ");                       //the clear button
     private JPanel southPanel = new JPanel();                 //holds all the southern components
     
     /*@name VectorView
      * @param vModel the main model
      * the main constructir**/
     public VectorView(VectorModel vModel)
     {
          this.vModel = vModel;
          this.vModel.setGUI(this);
          this.graph = new Graph(this.vModel);
          this.layoutView();
          this.registerControllers();
     }
     
     /*@name layoutView
      * intialises the layout**/
     private void layoutView()
     {
          this.graphPanel.add(this.graph);
          
          this.inputPanel.add(firstBracket);
          this.inputPanel.add(xCord);
          this.inputPanel.add(comma);
          this.inputPanel.add(yCord);
          this.inputPanel.add(secondBracket);
          this.inputPanel.add(plusButton);
          this.inputPanel.add(minusButton);
          this.inputPanel.add(evaluateButton);
          this.inputPanel.add(clearButton);
          
          this.equation.setEditable(false);
          this.equation.setWrapStyleWord(true);
          //this.equation.setLineWrap(true);
          this.equation.setFocusable(true);
          this.equation.setOpaque(false);
          
          this.explainArea.setEditable(false);
          this.explainArea.setWrapStyleWord(true);
          this.explainArea.setLineWrap(true);
          this.explainArea.setFocusable(true);
          this.equation.setOpaque(false);
          
          this.equationPanel.add(equationScrollPane);
          
          this.eastPanel.setLayout(new BoxLayout(this.eastPanel, BoxLayout.Y_AXIS));
          this.eastPanel.add(this.inputPanel);
          this.eastPanel.add(this.equationPanel);
          
          this.southPanel.add(explainScrollPane);
          
          this.setLayout(new BorderLayout());
          this.add(graphPanel, BorderLayout.WEST);
          this.add(eastPanel, BorderLayout.EAST);
          this.add(southPanel, BorderLayout.SOUTH);
               
     }
     
     /*@name registerControllers
      * intilises all the controllers**/
     private void registerControllers()
     {
          NewVectorController vectorController = new NewVectorController(this.vModel, xCord, yCord);
          plusButton.addActionListener(vectorController);
          minusButton.addActionListener(vectorController);
          
          ClearScreenController clearController = new ClearScreenController(this.vModel);
          clearButton.addActionListener(clearController);
          
          EvaluateController evalController = new EvaluateController(this.vModel, xCord, yCord);
          evaluateButton.addActionListener(evalController);
     }
     
     /*@name updateView
      * updates the view**/
     public void updateView()
     {
          this.graph.repaint();
          this.equation.setText(this.vModel.getEquation());
          this.explainArea.setText(this.vModel.getExplanation());
     }
}







