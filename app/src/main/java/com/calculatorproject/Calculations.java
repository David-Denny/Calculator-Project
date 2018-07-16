package com.calculatorproject;

public class Calculations {

    private double mOperand;
    private double mWaitingOperand;
    private String mWaitingOperator;
    private double mCalculatorMemory;

    // operators
    public static final String ADD = "+";
    public static final String SUBTRACT = "-";
    public static final String MULTIPLY = "*";
    public static final String DIVIDE = "/";

    public static final String CLEAR = "C";

    // TODO: will be added upon

    public Calculations() {
        mOperand = 0;
        mWaitingOperand = 0;
        mWaitingOperator = "";
        mCalculatorMemory = 0;
    }

    public void setOperand (double operand) {
        mOperand = operand;
    }

    public double getResult() {
        return mOperand;
    }
}

