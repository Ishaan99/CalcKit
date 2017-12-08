/*@name VectorModel
 * @author Manraj Thind
 * @since june 6 2017
 * responsible for handling the model for the program**/
//changed

import java.util.*;
import java.io.*;
import javax.swing.*;

public class VectorModel
{
     VectorView vView;                                //the view being used
     private int[][]coords = new int[100][2];         //the double array used to hold all coordinates
     private int numOfCoords = 0;                     //the number of coordinates that there currently are.
                                                      //This is used so that program will not have to 
                                                      //needlessly cycle through entire array
     private int[][]graphCoords = new int[100][4];    //the coordinates adjusted to fit on the graph
     private String equation = "";                    //the equation so far
     private String prevOperation = "";               //the previous operation submitted by the user
     private int totalX = 0;                          //the end x coordinate
     private int totalY = 0;                          //the end y coordinate 
     private boolean evaluate = false;                //determines weather or not the evaluate button has been pressed
     private boolean evaluated = false;               //determines whether or not the equation has been evaluated
     private BufferedReader file;                             //the file that contains the script for the evaluate button explanation 
     private String explanation = "";                              //the explanation displayed to the user
     
     /*@name VectorModel
      * constructor for class**/
     public VectorModel()
     {
          numOfCoords = 0;
     }
     
     /*@name addVector
      * @param x - the x coordinate
      * @param y - the y coordinate
      * @param operation - the operation the user has selected**/
     public void addVector(int x, int y, String operation)
     {
          //calculate new totals
          
          //check if the user has already seen the resultant
          //if so, reset everything
          if(this.evaluated == true)
          {
               this.clear();
               this.evaluated = false;
               this.evaluate = false;
          }
          
          totalX = totalX + x;
          totalY = totalY + y;
          
          //check of totals have not gone out of range
          if(this.totalX > 10 || this.totalY > 10
            || this.totalX < -10 || this.totalY < -10)
          {
               JOptionPane.showMessageDialog(this.vView, "Your resultant vector is too large. it must be between -10 and 10",
                                            "Error", JOptionPane.ERROR_MESSAGE);
          }
          
          else
          {
               
               //add coordinates
               coords[numOfCoords][0]  = x;     
               coords[numOfCoords][1] = y;
               
               
               this.addGraphVector(x, y);
               numOfCoords++;
               this.prevOperation = operation;  //update the operation
               equation = equation + '(' + x + ',' + y + ')' + prevOperation;
               
               //if the user wants to evaluate, calculate resultant
               if(this.evaluate == true)
               {
                    this.evaluate(false);
                    this.evaluated = true;
               }
               
               this.updateView();
          }
     }
     
     /* @name graphVector
      * a method that adjusts the coordinates to be displayed on the graph**/
     private void addGraphVector(int x, int y)
     {
          int [][] tempCords = new int[1][4];      //to hold temperary coordinates
          
          //fist check to see if this vector is being subtracted
          if(prevOperation.equals("-"))
          {
               //use distributive property to multiply negative sign in, leacving a positive sign
               x = -1 * x;
               y = -1 * y;
          }
          
          //if its not the first vector, make it start at the end of the last vector
          if(numOfCoords > 0)
          {
               graphCoords[numOfCoords][0] = graphCoords[numOfCoords - 1][2];
               graphCoords[numOfCoords][1] = graphCoords[numOfCoords - 1][3];
               this.endCoordinates(x, y);
          }
          
          //otherwise start it at the origin
          else
          {
               graphCoords[numOfCoords][0] = 11;
               graphCoords[numOfCoords][1] = 11;
               graphCoords[numOfCoords][2] = x + 11;
               graphCoords[numOfCoords][3] = -1 * y + 11;
          }
          
          
     }
     
     /**USed to get end coordinates for the graph
       * @param x - x coordinate
       * @param y - y coordinate*/
     private void endCoordinates(int x, int y)
     {
          int endX = x + 11;
          int endY = -1 * y + 11;
          
          //add all previous graph heights and widths
          for(int i = 0; i < numOfCoords; i++)
          {
               endX = endX + coords[i][0];
               endY = endY - coords[i][1];
          }
          
          graphCoords[numOfCoords][2] = endX;
          graphCoords[numOfCoords][3] = endY;
     }
     
     /*@name evaluate
      * evaluates the final vector
      * @param direct - determines weather or not another vector needs to be added**/
     public void evaluate(boolean direct)
     {    
          char replaceChar;   //the character to be replaced
          this.parseFile();
    
          //If a new vector has not been added befor evaluation, replace last character and update view
          if(direct == true)
          {
               this.equation = this.equation.substring(0, equation.length()-1) + "=";
               this.equation = this.equation + "(" + totalX + "," + totalY + ")";
               System.out.println(totalX);
               this.updateView();
          }
          
          //else only update the equation variable to include the total
          else
          {
               this.equation = this.equation + "(" + totalX + "," + totalY + ")"; 
          }
          this.evaluated = true;
     }
     
     /**@name - parseFile
       * parses ResultantScript.txt whenever the user wants the resultant script
       * */
     private void parseFile()
     {
          try
          {
               Scanner file = new Scanner(VectorModel.class.getResourceAsStream("ResultantScript.txt"));
               String line;
               StringBuffer input = new StringBuffer();
               
               //read through all lines, and append new line at each one
               while(file.hasNextLine())
               {
                    line = file.nextLine();
                    input.append(line);
                    input.append('\n');
               }
               
               explanation = input.toString();
               file.close();
               
               //add in the first coordinate
               String temp = "(" + coords[0][0] + "," + coords[0][1] + ")";
               explanation = explanation.replace("1(x,y)", temp);
               
               //add in the second coordinate
               temp = "(" + coords[numOfCoords - 1][0] + "," + coords[numOfCoords - 1][1] + ")";
               explanation = explanation.replace("2(x,y)", temp);
               
          }
          
          //catch any exceptions tha may be thrown
          catch(Exception ex)
          {
               System.out.println(ex.getMessage());
               System.out.println("in" + System.getProperty("user.dir"));
          }
     }
     
     /*@name clear
      * resets the values of the model**/
     public void clear()
     {
          this.numOfCoords = 0;
          this.prevOperation = "";
          this.equation = "";
          this.explanation = "";
          this.totalX = 0;
          this.totalY = 0;
          this.evaluate = false;
          this.updateView();
     }
     
     /*@name getGraphCoordinates
      * returns the graphCoordinates**/
     public int [][] getGraphCoordinates()
     {
          return this.graphCoords;
     }
     
     /*@name getNumOfCoords
      * returns the numbe rof coordinates**/
     public int getNumOfCoords()
     {
          return this.numOfCoords;
     }
     
     /*@name setGUI
      * intialises the GUI of the model**/
     public void setGUI(VectorView vView)
     {
          this.vView = vView;
     }
     
     /*@name getGUI
      * returns the GUI of the model**/
     public VectorView getGUI()
     {
          return this.vView;
     }
     
     /*@name getEquation
      * returns the equation**/
     public String getEquation()
     {
          return this.equation;
     }
     
     /**@name setEvaluate
       * changes state of evaluate variable
       * @param evaluate - indicates weather or not user wants to evaluate*/
     public void setEvaluate(boolean evaluate)
     {
          this.evaluate = evaluate;
     }
     
     /**@name getEvaluate
       * returns evaluate variable*/
     public boolean getEvaluate()
     {
          return evaluate;
     }
     
     /**@name getExplanation
       * returns explanation variable*/
     public String getExplanation()
     {
          return this.explanation;
     }
     
     /*@name updateView
      * updates the view**/
     public void updateView()
     {
          this.vView.updateView();
     }
}












