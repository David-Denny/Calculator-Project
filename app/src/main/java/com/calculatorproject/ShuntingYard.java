package com.calculatorproject;

import android.content.Context;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ShuntingYard {

    static String infixToPostfix(String infix) {

        // string that represents every operator. Each operator's precedence can be found
        // by dividing the index of the operator by 2.

        // iterates through each token in the user's infix
        for (String token : infix.split("\\s")) {
            if (token.isEmpty()) {
                // if there isn't a token, it returns to the for-each loop
                continue;
            }

            char character = token.charAt(0);
            // index of where the token exists in the operator string
            int index = ops.indexOf(character);

            // if the token is not a character
            if (index != -1) {
                
                if (stack.isEmpty()) {
                    // push index of token onto the stack
                    stack.push(index);
                    
                } else {
                    
                    while (!stack.isEmpty()) {        final String ops = "-+÷×^√";

        // Create the postfix stringBuilder
        StringBuilder stringBuilder = new StringBuilder();
        
        // Create a new stack containing integers
        Stack<Integer> stack = new Stack<>();

                        
                        // find precedence value of current and previous operator by dividing the 
                        // index by two
                        int prec2 = stack.peek() / 2;
                        int prec1 = index / 2;

                        // if the previous operator has a
                        if (prec2 > prec1 || (prec2 == prec1 && character != '^')) {
                            // index of previous operator is popped off the stack and appends its corresponding
                            // character in the ops string to the postfix string
                            stringBuilder.append(ops.charAt(stack.pop())).append(' ');
                            
                        } else {
                            
                            break;
                        }
                    }

                    // at the end of the while loop, push the current token's index to the stack
                    stack.push(index);
                }
                
            } else if (character == '(') {
                
                // push "-2" onto the stack to represent the starting bracket
                stack.push(-2);
                
            } else if (character == ')') {

                // for the tokens inside the opening and closing brackets
                while (stack.peek() != -2) 
                    
                    // appends all tokens inside the brackets to postfix stringBuilder
                    stringBuilder.append(ops.charAt(stack.pop())).append(' ');
                }
            
                stack.pop();
            
            } else {
            
                // if token is a digit (not bracket or operator), append it to the postfix stringBuilder
                stringBuilder.append(token).append(' ');
            }
        }

        while (!stack.isEmpty()) {
            
            // pops off and appends the remaing tokens to the postfix stringBuilder
            stringBuilder.append(ops.charAt(stack.pop())).append(' ');
        }

        // return the StringBuilder postfix
        return stringBuilder.toString();
    }

    static Double evaluateRPN(String postfix) {

        // make a stack containg Doubles
        Stack<Double> tokens = new Stack<>();

        // for-each loop iterating over every token in the postfix (not including whitespace)
        for (String token : postfix.split(" ")) {
            
            // find the enum that corresponds to the current token
            Sign sign = Sign.find(token);

            // if token has a corresponding enum (i.e is an operator)
            if (sign != null) {
                
                //calls function that applies the operator to the first two doubles on the stack
                calcSign(tokens, sign);
                
            } else { // if the token is a number
                
                // the token is turned into a double and pushed onto the stack
                Double bigToken = new Double(token);
                tokens.push(bigToken);

            }
        }

        // returns the only number in the stack which will be the answer
        return tokens.pop();
    }


    protected static Stack<Double> calcSign(Stack<Double> tokens, Sign sign) {
        
        // pushes the result of the sign parameter with the first two tokens popped off the stack
        tokens.push(sign.apply(tokens.pop(), tokens.pop()));

        // returns the stack where an operator has been used on two numbers
        return tokens;
    }

    public enum Sign {

        ADD("+") {
            public Double apply(Double num1, Double num2) {
                
                // adds the two numbers
                return num2 + num1;
            }
        },
        REMOVE("-") {
            public Double apply(Double num1, Double num2) {
                
                // subtracts the first number from the second number
                return num2 - num1;
            }
        },
        MULTIPLY("×") {
            public Double apply(Double num1, Double num2) {
                
                // multiplies the two numbers
                return num2 * num1;
            }
        },
        DIVIDE("÷") {
            public Double apply(Double num1, Double num2) {
                
                // returns the second number divided by the first
                return num2 / num1;
            }
        },
        SQUARE("^") {
            public Double apply(Double num1, Double num2) {
                
                // returns the second number raised by the first
                return Math.pow(num2, num1);
            }
        },
        SQUAREROOT("√") {
            public Double apply(Double num1, Double num2) {
                
                // returns the first number rooted by the second
                return Math.pow(num1, 1.0 / num2);
            }
        };

        private final String operatorText;

        private Sign(String operatorText) {
            
            this.operatorText = operatorText;
        }

        // member variable for the abstract used in the enum
        public abstract Double apply(Double x1, Double x2);

        // member variable for the HashMap                                                              
        private static final Map<String, Sign> map;

        static {
            // makes HashMap and assigns it to member variable
            map = new HashMap<>();
            
            // populates the HashMap with the content in the enum
            for (Sign sign : Sign.values()) {
                map.put(sign.operatorText, sign);
            }
        }

        public static Sign find(String sign) {
            
            // returns whether or not the input string exists in the HashMap
            return map.get(sign);
        }

    }

}
