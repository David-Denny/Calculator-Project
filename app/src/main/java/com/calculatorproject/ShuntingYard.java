package com.calculatorproject;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * This class handles everything relevant to the process of getting a value from the user's
 * input expression.
 *
 * The user's input is converted to a postfix in Reverse Polish Notation using the infixToPostfix
 * method. The postfix can then be used to calculate a result that is returned to the user.
 *
 * @author David Denny
 * */

public class ShuntingYard {

    // TODO: add documentation comments for the class and method overviews

    /**
     * Method that takes the user's input and iterates through it to create a postfix in the form
     * of Reverse Polish Notation and returns the postfix.
     *
     * @param infix user's input string
     * @return postfix
     * */

    static String infixToPostfix(String infix) {

        // string that represents every operator. Each operator's precedence can be found by
        // dividing the index of the operator by 2
        final String mOps = "-+÷×^√";

        // creates the postfix stringBuilder
        StringBuilder mPostfix = new StringBuilder();

        // Create new stack containing integers
        Stack<Integer> mStack = new Stack<>();

        // iteras through each token in the user's infix
        for (String token : infix.split("\\s")) {
            if (token.isEmpty()) {

                // if there isn't a token, it returns to the for-each loop
                continue;
            }

            // Char variable containing current token
            char character = token.charAt(0);

            // index of where the token exists in the operator member variable
            int index = mOps.indexOf(character);

            // if the token is an operator (i.e. exists in the mOps string)
            if (index != -1) {

                if (mStack.isEmpty()) {

                    // push index of token onto the stack
                    mStack.push(index);

                } else {

                    while (!mStack.isEmpty()) {

                        // find precedence value of current and previous operators by dividing
                        // the index by two
                        int previousPrecedence = mStack.peek() / 2;
                        int currentPrecedence = index / 2;

                        //if the previous operator is greater than the current operator or is the
                        // same as long as the current isn't a power
                        if (previousPrecedence > currentPrecedence ||
                                (previousPrecedence == currentPrecedence && character != '^')) {

                            // index of previous operator is popped off the stack and appends its
                            // corresponding character in the mOps string to the mPostfix string
                            mPostfix.append(mOps.charAt(mStack.pop())).append(' ');

                        } else {

                            // break out of loop if prevPrecedence is not greater
                            break;
                        }
                    }

                    // at the end of the stack, push the current token's index to the stack
                    mStack.push(index);
                }

            } else if (character == '(') {

                // push "-2" onto the stack to represent the starting bracket
                mStack.push(-2);

            } else if (character == ')') {

                // loops over the tokens inside the brackets
                while (mStack.peek() != -2) {

                    // appends all tokens inside the brackets to postfix stringBuilder to ensure
                    // that calculations inside the brackets are done first
                    mPostfix.append(mOps.charAt(mStack.pop())).append(' ');
                }

                // pops the bracket off the stack
                mStack.pop();

            } else {

                // if the token is a digit (not bracket or operator), append it to the mPostfix
                // stringBuilder
                mPostfix.append(token).append(' ');
            }
        }

        while (!mStack.isEmpty()) {

            // pops off and appends the remaining tokens to the mPostfix stringBuilder
            mPostfix.append(mOps.charAt(mStack.pop())).append(' ');
        }

        // returns the created postfix string which is now in Reverse Polish Notation
        return mPostfix.toString();
    }

    /**
     * Method to take the postfix string and convert it into a numerical result to be returned the
     * user.
     *
     * @param postfix user's postfix that is used to calculate a result
     * @return numerical value that is the result of the user's input expression
     **/
    static Double evaluateRPN(String postfix) {

        // make a stack containing Doubles
        Stack<Double> tokens = new Stack<>();

        // for-each loop iterating over every token in the postfix (removing whitespaces to split
        // each token up)
        for (String token : postfix.split(" ")) {

            // finds the corresponding enum to the token
            Sign sign = Sign.find(token);

            // if the .find() function succesfully finds a corresponding enum
            // (i.e. the token is an operator)
            if (sign != null) {

                // calls calcSign() function to apply the current operator to the first two
                // doubles popped off the stack
                calcSign(tokens, sign);

            } else { // i.e. if the token is a number

                // the token is casted to a Double to ensure data types are compatible
                Double doubleToken = new Double(token);

                // push double onto the stack
                tokens.push(doubleToken);

            }
        }

        // pops off the only number remaining in the stack after the loop. This will be the user's
        // answer and returns it.
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

                // adds the two input numbers
                return num2 + num1;
            }
        },
        SUBTRACT("-") {
            public Double apply(Double num1, Double num2) {

                // subtracts the first number from the second number
                return num2 - num1;
            }
        },
        MULTIPLY("×") {
            public Double apply(Double num1, Double num2) {

                // multiplies the input numbers
                return num2 * num1;
            }
        },
        DIVIDE("÷") {
            public Double apply(Double num1, Double num2) {

                // returns the second number divided by the first
                return num2 / num1;
            }
        },
        POWER("^") {
            public Double apply(Double num1, Double num2) {

                // returns the second number raised by the first
                return Math.pow(num2, num1);
            }
        },
        ROOT("√") {
            public Double apply(Double num1, Double num2) {

                // returns the second number rooted by the first
                return Math.pow(num1, 1.0 / num2);
            }
        };

        // operator text constructor
        private final String mOperatorText;

        // sets the corresponding string denoting the operator to the constructor
        Sign(String operatorText) {
            this.mOperatorText = operatorText;
        }

        // abstract class to make the Sign's corresponding subclasses
        public abstract Double apply(Double num1, Double num2);

        // create Map member variable made up of a string and enum
        private static final Map<String, Sign> mMap;

        static {

            // initialise mMap variable as a HashMap
            mMap = new HashMap<>();

            // use a for-each loop to populate HashMap with the enum and it's corresponding string
            for (Sign sign : Sign.values()) {
                mMap.put(sign.mOperatorText, sign);
            }
        }


        public static Sign find(String sign) {

            // returns the Sign enum if it exists in the HashMap
            return mMap.get(sign);
        }

    }

}