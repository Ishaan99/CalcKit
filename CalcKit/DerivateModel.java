import java.util.*;

public class DerivateModel extends Object
{
     protected DerivateGUI derivateView;
     
     protected StringBuffer function = new StringBuffer("");
     protected StringBuffer derivative = new StringBuffer("");
     protected int numSteps = 0;
     protected StringBuffer steps = new StringBuffer("Steps:");
     protected int state = 0; //0 - entering function, 1 - Showing Derivative and Steps  
     
     //Pre-made steps
     protected String expSteps = "\nTo get the derivative of %s, you have to multiply the coef, %s, with the exp, %s, then  minus one from the exponent. Which is %s.";
     protected String productSteps = "\nTo get the derivative of %s, you have to follow the product rule: if f(x)= a*b then f'(x)=a'*b+a*b'.";
     protected String quotientSteps = "\nTo get the derivative of %s, you have to follow the quotient rule: if f(x)= a/b then f'(x)=(a'*b-a*b')/b^2";
     protected String chainSteps = "\nTo get the derivative of %s, you have to follow the chain rule: if f(x)= a(x)^n then f'(x)=(a*n)(x)^(n-1)*(x')";
     
     /**
      *  Finds the closing parenthesis based on the opening parenthesis
      */
     public DerivateModel()
     {
          super();
     }
     
     /**
      *  Finds the closing parenthesis based on the opening parenthesis
      */
     public void setGUI(DerivateGUI currentGUI)
     {
          this.derivateView = currentGUI;
     }
     
     /**
      *  Finds the closing parenthesis based on the opening parenthesis
      */
     public void setFunction(String aFunction)
     {
          this.function.append(aFunction);
          this.updateView();
     }
     
     /**
      *  Finds the closing parenthesis based on the opening parenthesis
      */
     public String getFunction()
     {
          return function.toString();
     }
     
     /**
      *  Finds the closing parenthesis based on the opening parenthesis
      */
     public String getDerivative()
     {
          return derivative.toString();
     }
     /**
      *  Finds the closing parenthesis based on the opening parenthesis
      */
     
     public String getSteps()
     {
          return steps.toString();
     }
     
     /**
      *  Sets the state
      *  @param state - Number to change the state. 0 - Entering Function, 1 - Showing Derivative
      */
     public void setState(int state){
          this.state = state;
          this.updateView();
     }
     
     /**
      *  Returns the current state
      */
     public int getState()
     {
          return this.state;
     }
     
     /**
      *  Performs the derivative on the entered function
      */
     public void derivate(String fx)
     {
          //Sets the state as showing the derivative
          this.setState(1); 
          
          //Resets the derivative stringbuffer
          this.derivative.setLength(0);
          
          //Calculates the derivative for the function entered
          String derivative = this.derivative(fx);
          
          //Checks if derivative was calculated correctly
          if(derivative.contains("not"))
               this.derivative.append("Could not derive function");
          else
               this.derivative.append("f'(x) = "+this.derivative(fx));
          
          //Updates view
//          String[] result = stepForDeriv.split("\n");
//          for(int i = 0; i<result.length(); i++)
//          {
//               if(
//          }
          this.updateView();
     }     
     /* @name derivative
      * @param function the function which you want to calculate the derivative for
      * @return the derivative as a string
      * Calculates the derivative of the function entered
      */
     public String derivative(String function)
     {
          StringBuffer derivative = new StringBuffer(""); //Creates a string buffer for the derivative
          String fx; //Creates a new fx variable for the edited function
          
          //Checks if function is a constant
          if(isNumeric(function))
               return "0";
          
          //Try statement to catch any errors calculating the derivative
          try{
               //Checks if the first character of the function is a '+' or '-'
               if(function.charAt(0)!='+'||function.charAt(0)!='-')
                    fx = (new StringBuffer("+"+function)).toString(); //Adds a '+' sign to the start of the function
               else 
                    fx = function;
               
               //A string array list to store all the terms of the function
               ArrayList<String> partsList = new ArrayList<String>();
               
               //Gets and stores all the terms of the function to the partsList
               for(int i = 0; i < fx.length(); i++){ 
                    
                    //Checks to see if there are any opening brackets in the term
                    if(fx.charAt(i)=='('){
                         i = findClosingParen(function.toCharArray(), i); //Finds the closing bracket and sets 'i' to the index of the closing bracket
                    }
                    //Sees if the current index is a '+' or '-'
                    else if(fx.charAt(i)=='+' || fx.charAt(i)=='-'){ 
                         int endOfTerm = findEndOfTerm(fx, i+1); //finds the end of the term
                         partsList.add(fx.substring(i, endOfTerm+1)); //Adds the term plus the operator in front of it to the partsList
                         i = endOfTerm; //Sets i to the end of term to find a new term in the function
                    }
               }
               
               //Converts the partsList list to a string array
               String[] parts = partsList.toArray(new String[partsList.size()]);
               
               //Decides what rule to apply to the function to calculate the function
               for(int i = 0; i < parts.length; i++){
                    String sign = parts[i].substring(0,1); //Stores the sign of the term
                    String partsFX = parts[i].substring(1,parts[i].length()); //Removes the sign from the term
                    //Checks what rule to use
                    if(partsFX.contains(")^")){ //Chain rule
                         derivative.append(sign + chainRule(partsFX));
                    }
                    else if(partsFX.contains("/")) //Quotient Rule
                         derivative.append(sign + quotientRule(partsFX));
                    else if(partsFX.contains("*")) //Product rule with the '*' sign
                         derivative.append(sign + productRule(partsFX, true));
                    else if(partsFX.contains(")(")) //Product ruel without the '*' sign
                         derivative.append(sign + productRule(partsFX, false));
                    else //The exponent rule
                         derivative.append(sign + exponentRule(partsFX));
               }
          }catch(Exception ex){ //Catches any errors and returns that it cant find the derivative
               return "Could not derivate function.";
          }
          return removeExtraSigns(derivative.toString()); //Removes the extra signs in the derivative and returns it
     }
     
     /* @name removeExtraSigns
      * @param function The function that you want to remove extra signs for
      * @return The final function as a string
      * Removes all the extra signs that are in the derivative
      */
     public String removeExtraSigns(String fx){
          if(fx.charAt(0)=='+') //Checks if the first character is a plus sign
               return fx.substring(1,fx.length()); //Removes the sign
          else //Changes nothing
               return fx;
     }
     
     /* @name findEndOfTerm
      * @param function the function the term is in
      * @param startIndex the start of the term
      * @return the index of the end of the term
      * Finds the index of the end of term based on the start index of the term
      */
     public int findEndOfTerm(String function, int startIndex){
          int endOfTerm; //Holds the end of term position
          char temp; //Temp variable to hold the char at the index
          
          //Loops through the function starting at the start of the term
          for(int i = startIndex; i<function.length(); i++){
               temp = function.charAt(i); //Gets the char at the index
               
               //Checks if there is a bracket in the term
               if(temp=='('){
                    i = findClosingParen(function.toCharArray(), i); //Gets the closing pos of the bracket and sets it to 'i'
               }
               else if(temp=='+'||temp=='-'){ //If the index has a plus or minus sign, that is the end of the term
                    return i-1; //Returns the position of the end of the term
               }
               else if(i == function.length()-1) //Checks if the index is at the end of the function
                    return function.length()-1; //Returns the end of the term as the end of the function
          }
          return function.length()-1; //Returns the end of the term as the end of the function
     }
     
     /* @name findOpeningParen
      * @param text the function as a character array
      * @param closePos the index of the closing parenthesis
      * @return the index of the opening parenthesis
      * Finds the index of the matching opening parenthesis
      */
     public int findOpeningParen(char[] text, int closePos) {
          int openPos = closePos; //Sets the opening pos to the close pos
          int counter = 1; //Sets the counter to 1. Checks how many brackets are left to find
          while (counter > 0) {
               char c = text[--openPos]; //Sets c to the character at the openPos minus 1 and minuses 1 from the openPos
               
               //Finds the matching bracket
               if (c == '(') {
                    counter--;
               }
               else if (c == ')') {
                    counter++;
               }
          }
          return openPos;
     }
     
     /* @name findClosingParen
      * @param text the function as a character array
      * @param openPos the index of the opening parenthesis
      * @return the index of the closing parenthesis
      * Finds the index of the matching closing parenthesis
      */
     public int findClosingParen(char[] text, int openPos) {
          int closePos = openPos; //Sets the close pos to the opening pos
          int counter = 1; //Sets the counter to 1. Checks how many brackets are left to find
          while (counter > 0) {
               char c = text[++closePos]; //Sets c to the character at the closePos minus 1 and minuses 1 from the closePos
               
               //Finds the matching bracket
               if (c == '(') {
                    counter++;
               }
               else if (c == ')') {
                    counter--;
               }
          }
          return closePos;
     }
     
     /* @name exponentRule
      * @param function The function to apply the exponent rule to
      * @return the derivative
      * Applies the exponent rule to the function given
      */
     public String exponentRule(String aFunction)
     {
          int coef = 1; //Defaults coef to 1
          int exp = 1; //Defaults exp to 1
          String expDerivative; //Derivative
          
          //Checks if the function does not have 'x'
          if(aFunction.indexOf("x") == -1)
               return "0"; //Returns nothing because the derivative of a constant equals zero
          
          else{ //Function has x
               String[] a = aFunction.split("[x\\^]+"); //Splits into coef and exponent
               if(a.length == 0) //Function is only f(x)=x
                    exp = 0; 
               else{ //Function has a coef or exp
                    if(a.length>1){ //Function has an exp and a coef which may be empty
                         if(a[0].length() == 0 )//Function only has an exponent
                              exp = Integer.valueOf(a[1]);
                         else{ //A coef and an exponent
                              coef = Integer.valueOf(a[0]);
                              exp = Integer.valueOf(a[1]);
                         }
                    }
                    else //Function only has coef. Ex. f(x)=5x
                         coef = Integer.valueOf(a[0]);
               }
          }
          if(exp==0) //If exp = 0, the derivative is the coef
               expDerivative = String.valueOf(coef);
          else if(coef==0) //If the coef = 0, the derivative is zero
               expDerivative = "0";
          else if(coef==1 && exp==1) //f(x)=1x1
               expDerivative = "1";
          else if(coef==1 && exp!=1) //Ex. f(x)=1x2
               expDerivative = exp+"x^"+(exp-1);
          else if(coef!=1 && exp==1)
               expDerivative = String.valueOf(coef);
          else{
               if(exp==2) //If exp = 2
                    expDerivative = (coef*exp)+"x"; //The derivative doesn't have an exponent value
               else
                    expDerivative = (coef*exp)+"x^"+(exp-1); //The derivative's exponent is the exponent minus one
          }
          steps.append(String.format(expSteps, aFunction, coef, exp, expDerivative)); 
          return expDerivative; //Returns the derivative
     }
     
     /* @name productRule
      * @param fx The function to apply the product rule to
      * @param multSign Boolean wether function has a '*' sign or not
      * @return the derivative
      * Applies the product rule to the function given
      */
     public String productRule(String fx, boolean multSign){
          //Stores the positions of the 2 opening and closing bracket positions
          int[] oPos = new int[2];
          int[] cPos = new int[2];
          
          String part1, part2; //Strings to store the two terms
          String sign; //Holds the sign string that indicates the product rule
          
          //Checks which sign is in the function
          if(multSign)
               sign = "*";
          else
               sign = ")(";
          
          //Finds the position of the sign
          int i = fx.indexOf(sign);
          
          //Gets the left term
          if(fx.charAt(i-1)==')'){ //If there are brackets
               oPos[0] = findOpeningParen(fx.toCharArray(), i-1);
               cPos[0] = i-1;
          }
          else{
               oPos[0] = 1;
               cPos[0] = i;
          }
          
          //Gets the right term
          if(fx.charAt(i+1)=='('){ //If there are brackets
               oPos[1] = i+1;
               cPos[1] = findClosingParen(fx.toCharArray(), i+1);
          }
          else{
               oPos[1] = i;
               cPos[1] = fx.length();
          }
          
          //Gets the substrings of the parts
          part1 = fx.substring(oPos[0]+1,cPos[0]);
          part2 = fx.substring(oPos[1]+1,cPos[1]);
          
          String productDerivative = "("+derivative(part1)+")("+part2+")+("+part1+")("+derivative(part2)+")";
          steps.append(String.format(productSteps, fx)); 
          return productDerivative; //Calculates and returns the derivative
     }
     
     /* @name quotientRule
      * @param fx - Function to find the quotient rule for
      * @return the derivative
      * Performs the quotient rule on the given function
      */
     public String quotientRule(String fx){
          //Stores the positions of the 2 opening and closing bracket positions
          int[] oPos = new int[2];
          int[] cPos = new int[2];
          
          String part1, part2; //Strings to store the two terms
          
          int i = fx.indexOf("/"); //Gets the index of '/'
          
          //Finds opening and closing positions of brackets in left term
          if(fx.charAt(i-1)==')'){ //There are brackets
               oPos[0] = findOpeningParen(fx.toCharArray(), i-1);
               cPos[0] = i-1;
          }
          else{ //No brackets
               oPos[0] = -1;
               cPos[0] = i;
          }
          
          //Finds opening and closing positions of brackets in right term
          if(fx.charAt(i+1)=='('){ //There are brackets
               oPos[1] = i+1;
               cPos[1] = findClosingParen(fx.toCharArray(), i+1);
          }
          else{ //No brackets
               oPos[1] = i;
               cPos[1] = fx.length();
          }
          
          //Gets the substrings of the parts
          part1 = fx.substring(oPos[0]+1,cPos[0]);
          part2 = fx.substring(oPos[1]+1,cPos[1]);
          
          steps.append(String.format(quotientSteps, fx));  //Formats the pre-made step string to fill in the function the user inputted
          
          String quoDerivative = "(("+exponentRule(part1)+")("+part2+")-("+part1+")("+exponentRule(part2)+"))/("+part2+")^2"; //Calculates the derivative
          
          return quoDerivative; //Returns the derivative
     }
     
     /* @name chainRule
      * @param aFunction - Function to apply the chain rule for
      * @return the derivative
      * Performs the chain rule on the given function
      */
     public String chainRule(String aFunction){
          //Finds the opening and closing brackets in the function
          int openingBR = aFunction.indexOf("("); 
          int closingBR = findClosingParen(aFunction.toCharArray(), openingBR);
          
          int coef = 1; //Sets the default coefficent to 1
          
          //Checks if there is a coefficent
          if(openingBR!=0){
               //Gets the coefficent
               if(Character.isDigit(aFunction.charAt(openingBR-1)))//If there is no operator inbetween coef and bracket
                    coef = Integer.valueOf(aFunction.substring(0, openingBR));
               else if(aFunction.charAt(openingBR-1)=='*')//If there is a '*' character inbetween coef and bracket
                    coef = Integer.valueOf(aFunction.substring(0, openingBR-1));
               else
                    return "Cannot Solve";
          }
          
          //The derivative part of the exponent 
          String expD;
          
          //Calculates the exponent value of the derivative
          if(Integer.valueOf(aFunction.substring(closingBR+2, aFunction.length()))==2) //Checks if the exponent of the function is a '2'
               expD = ""; //Sets the derivative exponent to nothing
          else //If the exponent is greater than '2'
               expD = "^"+(Integer.valueOf(aFunction.substring(closingBR+2, aFunction.length()))-1); //Gets the functions exponent and minuses 1 from it
          
          //Calculates the derivative of the function inside the brackets
          String midDeriv = derivative(aFunction.substring(openingBR+1, closingBR));
          
          //Writes the steps from the pre-made steps and fills in the function
          steps.append(String.format(chainSteps, aFunction));
          
          //Calculates the final derivative
          String derivative = (coef*Integer.valueOf(aFunction.substring(closingBR+2, aFunction.length()))+"("+aFunction.substring(openingBR+1, closingBR)+")"+expD+"*("+midDeriv+")");
          
          return derivative; //Returns the derivative
     }
     
     /* @name isNumeric
      * @param str String to check if numeric
      * @return Boolean if numeric or not
      * Checks if a string is numeric
      */
     public boolean isNumeric(String str)  
     {  
          try {  
               double d = Double.parseDouble(str); //Tries to parse the string to a double
          }  
          catch(NumberFormatException ex){  //If it catches an error it returns false
               return false;  
          }  
          return true; //Returns true if a number
     }
     /**
      *  Disables some buttons on the keyboard based on input
      */
     public void setKeyboard()
     {
          if(function.length()==0){ //If function length is zero
               //Sets keyboard buttons either enabled or disabled
               this.derivateView.calcDeriv.setEnabled(false);
               this.derivateView.bs.setVisible(false);
               this.derivateView.nums.setEnabled(true);
               this.derivateView.x.setEnabled(true);
               this.derivateView.lb.setEnabled(true);
               this.derivateView.rb.setEnabled(false);
               this.derivateView.add.setEnabled(false);
               this.derivateView.subtract.setEnabled(true);
               this.derivateView.multiply.setEnabled(false);
               this.derivateView.divide.setEnabled(false);
               this.derivateView.exp.setEnabled(false);
          }
          else if(Character.isDigit(function.charAt(function.length()-1))){ //If last character entered is a digit
               //Sets keyboard buttons either enabled or disabled
               this.derivateView.calcDeriv.setEnabled(true);
               this.derivateView.nums.setEnabled(true);
               this.derivateView.x.setEnabled(true);
               this.derivateView.lb.setEnabled(true);
               this.derivateView.rb.setEnabled(true);
               this.derivateView.add.setEnabled(true);
               this.derivateView.subtract.setEnabled(true);
               this.derivateView.multiply.setEnabled(true);
               this.derivateView.divide.setEnabled(true);
               this.derivateView.exp.setEnabled(false);
          }
          else if(function.charAt(function.length()-1)=='x'){ //If last character is 'x'
               //Sets keyboard buttons either enabled or disabled
               this.derivateView.calcDeriv.setEnabled(true);
               this.derivateView.nums.setEnabled(false);
               this.derivateView.x.setEnabled(false);
               this.derivateView.lb.setEnabled(true);
               this.derivateView.rb.setEnabled(true);
               this.derivateView.add.setEnabled(true);
               this.derivateView.subtract.setEnabled(true);
               this.derivateView.multiply.setEnabled(true);
               this.derivateView.divide.setEnabled(true);
               this.derivateView.exp.setEnabled(true);
          }
          else if(function.charAt(function.length()-1)=='^'){ //If last character is '^'
               //Sets keyboard buttons either enabled or disabled
               this.derivateView.calcDeriv.setEnabled(false);
               this.derivateView.nums.setEnabled(true);
               this.derivateView.x.setEnabled(false);
               this.derivateView.lb.setEnabled(false);
               this.derivateView.rb.setEnabled(false);
               this.derivateView.add.setEnabled(false);
               this.derivateView.subtract.setEnabled(true);
               this.derivateView.multiply.setEnabled(false);
               this.derivateView.divide.setEnabled(false);
               this.derivateView.exp.setEnabled(false);
          }
          else if(function.charAt(function.length()-1)=='('){ //If last character is '('
               //Sets keyboard buttons either enabled or disabled
               this.derivateView.calcDeriv.setEnabled(false);
               this.derivateView.nums.setEnabled(true);
               this.derivateView.x.setEnabled(true);
               this.derivateView.lb.setEnabled(false);
               this.derivateView.rb.setEnabled(false);
               this.derivateView.add.setEnabled(false);
               this.derivateView.subtract.setEnabled(true);
               this.derivateView.multiply.setEnabled(false);
               this.derivateView.divide.setEnabled(false);
               this.derivateView.exp.setEnabled(false);
          }
          else if(function.charAt(function.length()-1)==')'){ //If last character is ')'
               //Sets keyboard buttons either enabled or disabled
               this.derivateView.calcDeriv.setEnabled(true);
               this.derivateView.nums.setEnabled(false);
               this.derivateView.x.setEnabled(false);
               this.derivateView.lb.setEnabled(false);
               this.derivateView.rb.setEnabled(false);
               this.derivateView.add.setEnabled(true);
               this.derivateView.subtract.setEnabled(true);
               this.derivateView.multiply.setEnabled(true);
               this.derivateView.divide.setEnabled(true);
               this.derivateView.exp.setEnabled(true);
          }
          else if(function.charAt(function.length()-1)=='+' ||
                  function.charAt(function.length()-1)=='-' ||
                  function.charAt(function.length()-1)=='*' ||
                  function.charAt(function.length()-1)=='/' ){ //If last character is an operation
               //Sets keyboard buttons either enabled or disabled
               this.derivateView.calcDeriv.setEnabled(false);
               this.derivateView.nums.setEnabled(true);
               this.derivateView.x.setEnabled(true);
               this.derivateView.lb.setEnabled(true);
               this.derivateView.rb.setEnabled(false);
               this.derivateView.add.setEnabled(false);
               this.derivateView.subtract.setEnabled(false);
               this.derivateView.multiply.setEnabled(false);
               this.derivateView.divide.setEnabled(false);
               this.derivateView.exp.setEnabled(false);
          }
          //Finds the number of opening and closing brackets in the function
          int numOpenBrackets = 0;
          int numClosingBrackets = 0;
          for(int i = 0; i < function.length(); i++){
               if(function.charAt(i)=='(')
                    numOpenBrackets++;
               else if(function.charAt(i)==')')
                    numClosingBrackets++;
          }
          if(numOpenBrackets==numClosingBrackets) //If brackets are matched
               this.derivateView.rb.setEnabled(false);
          if(numOpenBrackets > numClosingBrackets){ //Opening number of brackets is greater than the number of closing brackets
               this.derivateView.calcDeriv.setEnabled(false);
               this.derivateView.lb.setEnabled(false);
          }
          if(numOpenBrackets == 2 && numClosingBrackets == 2){ //Max number of brackets entered in the function
               this.derivateView.lb.setEnabled(false);
               this.derivateView.rb.setEnabled(false);
          }
     }
     
     /**
      *  Allows user to remove the last character entered
      */
     public void backspace()
     {
          function.setLength(function.length() - 1);
          this.updateView();
     }
     
     /**
      *  Resets the calculator
      */
     public void reset()
     {
          this.setState(0);
          function.setLength(0);
          steps.setLength(0);
          steps.append("Steps:");
          this.updateView();
     }
     
     /**
      *  View updater
      */
     public void updateView()
     {
          derivateView.update();
     }
}