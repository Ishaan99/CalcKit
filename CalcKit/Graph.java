/* @name Graph
 * @author Manraj Thind
 * @since June7 2017
 * creates the graph that is diplayed to the user**/

//changed

import javax.swing.*;             
import java.awt.*;
import java.util.*;
import java.awt.font.TextAttribute; 
import java.awt.geom.*;
import java.awt.Graphics2D;

public class Graph extends JComponent
{
     //private char [] numbers = new char[18]; 
     private String stringNumber = "";
     private VectorModel vModel;      //the vector model
     private static Graphics2D g2;    //the graphics 2d used to draw and scale the vectors and graph
     
     /* @name Graph
      * @param vModel the vector model being used
      * Used to construct Graph Object**/
     public Graph(VectorModel vModel) 
     {
          super();
          Dimension prefSize = new Dimension(500, 500);
          this.setPreferredSize(prefSize);
          this.vModel = vModel;
     }
     
     /* @name paintComponenet
      * @param g - the GRaphics object used to draw things with
      * creates the graph and vectors**/
     public void paintComponent(Graphics g)
     {    
          super.paintComponent(g);
          g2 = (Graphics2D)g;
          
          Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>(); //create map for attributes of font
          attributes.put(TextAttribute.TRACKING, -0.15);                    //increse the spacing
          attributes.put(TextAttribute.SIZE, 0.85);                         //change the size 
          Font font = new Font("Serif", Font.PLAIN, 1);                     //create font
          Font font2 = font.deriveFont(attributes);                         //derive new font with updated attributes
          
          g2.setFont(font2);   
          
          //scale the graph, set the stroke, draw the graph
          g2.scale(this.getWidth()/22, this.getWidth()/22);    
          g2.setStroke(new BasicStroke(1.0F/this.getWidth()));
          g2.setColor(Color.WHITE);
          g2.fillRect(0, 0, 600, 600);
          g2.setColor(Color.BLACK);
          
          this.generateGraph();
          
          this.generateXAxis(g);
          this.generateYAxis(g);
          this.drawLines(vModel.getNumOfCoords());
     }
     
     /* @name generateGraph
      * @param g2 - the 2d graphics object
      * this menthod genenrates the graph**/
     private void generateGraph()
     {
          //draw the horizontal lines
          for(int i = 0; i < 28; i++)
          {
               if(i==11)
               {
                    g2.setStroke(new BasicStroke(10.0F/this.getWidth()));
               }
               
               else
               {
                    g2.setColor(new Color(211, 211, 211));
                    g2.setStroke(new BasicStroke(1.0F/this.getWidth()));
               }
              
               g2.drawLine(i, 0, i, 100);
               g2.setColor(Color.BLACK);
          }
          
          //draw the vertical lines
          for(int i = 0; i < 29; i++)
          {
               if(i==11)
               {
                    g2.setStroke(new BasicStroke(10.0F/this.getWidth()));
               }
               
               else
               {
                    g2.setColor(new Color(211,211,211));
                    g2.setStroke(new BasicStroke(1.0F/this.getWidth()));
               }
               g2.drawLine(0, i, 100, i);
               g2.setColor(Color.BLACK);
          }
          
          g2.setColor(Color.BLACK);
     }
     
     /*@name generateXAxis
      * @param g - variable that draws graphics
      * generates the x axis**/
     private void generateXAxis(Graphics g)
     {
          //draws numbers in the positive x axis
          for(int i = 11; i < 22; i++)
          {
               if((i - 1)%2 == 0)
               {
                    //draw tick
                    g2.draw(new Line2D.Double(i, 10.9, i, 11.1));
                    
                    //draw numbers from 1 - 10
                    g2.drawString(String.valueOf(i - 11) + " ", i, 12);
               }
              
          }
          
          //draws numbers in the negative y axis
          for(int i = 10; i > 0; i--)
          {
               if(i%2 == 0)
               {
                    //draw tick
                    g2.draw(new Line2D.Double(11 - i, 10.9, 11 - i, 11.1));
                    
                    //draw numbers fom -1 to -10
                    g.drawString(String.valueOf(i *-1) + " ", 11 - i, 12);
               }
               
          }
     }
     
     /*@name generateYAxis every 2 numbers*
      * @param g - the graphics object used to draw the y axis*
      */
     private void generateYAxis(Graphics g)
     {
          //draws numbers in the positive y axis
          for(int i = 11; i > 1; i--)
          {
               if(i % 2 ==0)  
               {
                    //draw the tick
                    g2.draw(new Line2D.Double(10.9, i-1, 11.1, i-1));
                    
                    //draw the number, from 1 to 10
                    g2.drawString(String.valueOf(11 - (i-1)), (float)10, (float)i - (float)0.8);
               }
               
          }
          
          //draws numbers in the negative Y axis
          for(int i = 13; i < 23; i++)
          {
               if(i % 2 ==0)
               {
                    //draw the tick
                    g2.draw(new Line2D.Double(10.9, i - 1, 11.1, i - 1));
                    
                    //draw the number -1 to -10
                    g2.drawString(String.valueOf(12 - i), (float)10, (float)i - (float)0.8);
               }
              
          }
     }
     
     /**@name drawLines
       * @param numOfCoords - the number of vectors*/
     public void drawLines(int numOfCoords)
     {
          int[][]coords = vModel.getGraphCoordinates();      //the coordinaes adjusted for the graph
          g2.setStroke(new BasicStroke(3.0F/this.getWidth()));
          
          //draw all the vectors the user has entered
          for(int row = 0; row < numOfCoords; row++)
          {
               g2.drawLine(coords[row][0], coords[row][1], coords[row][2], coords[row][3]);
               g2.fill(new Ellipse2D.Double(coords[row][2] - 0.1, coords[row][3] - 0.1, 0.2, 0.2));
          }
          
          if(this.vModel.getEvaluate() == true)
          {
               this.drawResultant(numOfCoords);
          }
          
          g2.setStroke(new BasicStroke(1.0F/this.getWidth()));
     }
     
     /**@name drawResultant*
       * @param numOfCoords - the number of vetors*/
     private void drawResultant(int numOfCoords)
     {
          int [][] coords = this.vModel.getGraphCoordinates();    //the coordinates adjusted for the graph
          
          g2.setColor(Color.RED);
          g2.setStroke(new BasicStroke(8.0F/this.getWidth()));
          
          //draw the line from the origin to the end point of last vector
          g2.drawLine(11, 11, coords[numOfCoords-1][2], coords[numOfCoords-1][3]);   
          g2.setColor(Color.BLACK);
     }
     
     
}


















