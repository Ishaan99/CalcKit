/* @name CalcKitGUI
 * @author Ishaan Sharma
 * @since June 16th, 2017
 * Creates the view for CalcKit and updates it 
 */

import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.net.*;

public class CalcKitGUI extends JPanel
{
     private CalcKitModel model; //Calckit model
     
     private DerivateModel derivate = new DerivateModel(); //Derivate model
     private DerivateGUI guiD = new DerivateGUI(derivate); //Derivate GUI
     
     private VectorModel vector = new VectorModel(); //Vector model
     private VectorView guiV = new VectorView(vector); //Vector GUI
     
     protected JPanel main = new JPanel(); //The main panel
     
     private JLabel programName = new JLabel("CalcKit"); //Holds the program name that shows on the top left
     private JPanel buttons = new JPanel(); //Buttons panel to show the program name, and the exit and minimize buttons
     private JButton exit, minimize; //Creates JButtons for the exit and minimize buttons
     
     private JPanel logoPanel = new JPanel(new BorderLayout()); //Panel that holds the home screen CalcKit image
     
     private JPanel tabButtons = new JPanel(); //Panel that holds the button tabs the user can click to change the view
     private JButton homeBtn = new JButton("Home"); //Home view button
     private JButton derivativesBtn = new JButton("Derivatives"); //Derivatives view button
     private JButton vectorsBtn = new JButton("Vectors"); //Vectors view button
     
     /* @name calcKitGUI
      * @param newModel - The CalcKit model
      * Constructor of the class
      */
     public CalcKitGUI(CalcKitModel newModel)
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
          //Creates a border for this panel
          BorderLayout borderLayout = new BorderLayout();
          borderLayout.setHgap(30);
          borderLayout.setVgap(30);
          this.setLayout(borderLayout);
          this.setBorder(BorderFactory.createEmptyBorder(2,10,10,10)); //Sets border to 2px on top, and 10px on the other sides
          
          //ImageIcon exitIcon = new ImageIcon(getClass().getResource("exit-icon.png"), "Exit icon"); //Makes an exit icon from the image
          //ImageIcon minimizeIcon = new ImageIcon(getClass().getResource("minimize-icon.png"), "Minimize icon"); //Makes a minimize icon from the image
          
          exit = new JButton(/*exitIcon*/); //Sets the exit icon to the exit button
          minimize = new JButton(/*minimizeIcon*/); //Sets the minimize icon to the minimize button
          
          //Creates the look of the exit and minimize buttons
          exit.setContentAreaFilled(false);
          exit.setBorder(null);
          exit.setRolloverEnabled(false);
          //exit.setPreferredSize(new Dimension(exitIcon.getIconWidth(), exitIcon.getIconHeight())); //Sets exit button the size of the exit icon
          minimize.setContentAreaFilled(false);
          minimize.setBorder(null);
          minimize.setRolloverEnabled(false);
          //minimize.setPreferredSize(new Dimension(minimizeIcon.getIconWidth(), minimizeIcon.getIconHeight())); //Sets minimize button the size of the minimize icon
          
          //Panels that will be inside the buttons panel
          JPanel buttonsLeft = new JPanel(); //Holds the program name
          JPanel buttonsRight = new JPanel(); //Holds the minimize and exit buttons
          
          //buttons.setMaximumSize(new Dimension(Integer.MAX_VALUE, exitIcon.getIconHeight()+10)); //Sets the max height for the buttons panel
          //Sets the layout of the buttons panel as a 1x2 grid layout
          buttons.setLayout(new GridLayout(1,2));
          
          //Adds the program name to the buttons panel
          programName.setForeground(Color.WHITE); //Sets the font color to white
          buttonsLeft.setLayout(new FlowLayout(FlowLayout.LEADING)); //Sets the buttonsLeft panel to a leading flow layout
          buttonsLeft.add(programName);
          buttons.add(buttonsLeft);
          
          //Adds the exit and minimize buttons to the buttons panel
          buttonsRight.setLayout(new FlowLayout(FlowLayout.TRAILING)); //Sets the buttonsRight panel to a trailing flow layout
          buttonsRight.add(minimize);
          buttonsRight.add(exit);
          buttons.add(buttonsRight);
          
          //Creates the logo panel for the home page
          //ImageIcon image = new ImageIcon(getClass().getResource("logo.png"));
          JLabel logoLabel = new JLabel(""/*, image, JLabel.CENTER*/);
          logoPanel.add(logoLabel, BorderLayout.CENTER);
          
          //Add buttons to switch between windows to the tabButtons panel
          tabButtons.add(homeBtn);
          tabButtons.add(derivativesBtn);
          tabButtons.add(vectorsBtn);
          
          //Setting background colors
          Color mainBGColor = new Color(252, 119, 83);
          this.setBackground(mainBGColor);
          buttons.setBackground(mainBGColor);
          buttonsLeft.setBackground(mainBGColor);
          buttonsRight.setBackground(mainBGColor);
          logoPanel.setBackground(mainBGColor);
          tabButtons.setBackground(mainBGColor);
          main.setBackground(mainBGColor);
          
          //Adds the components to the main panel and the main panel to the CalcKitGUI
          main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
          main.add(buttons); //Program name and exit/minimize buttons
          main.add(logoPanel); //Home screen logo panel
          main.add(tabButtons); //Buttons to switch between the different panels
          main.add(guiD); //Derivatives GUI
          main.add(guiV); //Vectors GUI
          this.add(main); //Main Panel
     }
     /**
      *  Registers controllers
      */
     private void registerControllers()
     {
          //Registers the controllers for all the buttons in the CalcKitVIew
          ButtonController btnController = new ButtonController(this.derivate, this.model); //Button Controller
          this.homeBtn.addActionListener(btnController);
          this.derivativesBtn.addActionListener(btnController);
          this.vectorsBtn.addActionListener(btnController);
          this.exit.addActionListener(btnController);
          this.minimize.addActionListener(btnController);
          
          //Controller to drag the buttons panel to move the frame
          MotionController drag = new MotionController(buttons); //Motion Controller
          this.buttons.addMouseListener(drag);
          this.buttons.addMouseMotionListener(drag);
     }
     /**
      *  Updates the view
      */
     public void update()
     {
          this.setVisible(true); //Sets this panel to visible
          //Checks the current state and sets the view accordingly 
          if(this.model.getState()==0){ //Home
               this.logoPanel.setVisible(true);
               this.homeBtn.setVisible(false);
               this.derivativesBtn.setVisible(true);
               this.vectorsBtn.setVisible(true);
               this.guiD.setVisible(false);
               this.guiV.setVisible(false);
          }
          else if(this.model.getState()==1){ //Derivatives Panel
               this.logoPanel.setVisible(false);
               this.homeBtn.setVisible(true);
               this.derivativesBtn.setVisible(false);
               this.vectorsBtn.setVisible(true);
               this.guiD.setVisible(true);
               this.guiV.setVisible(false);
          }
          else if(this.model.getState()==2){ //Vectors Panel
               this.logoPanel.setVisible(false);
               this.homeBtn.setVisible(true);
               this.derivativesBtn.setVisible(true);
               this.vectorsBtn.setVisible(false);
               this.guiD.setVisible(false);
               this.guiV.setVisible(true);
          }
     }
}