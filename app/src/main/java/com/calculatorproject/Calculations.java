package com.calculatorproject;

public class Calculations {

    private double mOperand;
    private double mWaitingOperand;
    private String mWaitingOperator;
    private double mCalculatorMemory;

    // operators
    public static final String ADD = "+";
    public static final String SUBTRACT = "−";
    public static final String MULTIPLY = "×";
    public static final String DIVIDE = "÷";

    public static final String CLEAR = "AC";

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

    public String toString() {
        return Double.toString(mOperand);
    }

    protected double performOperation(String operator) {

        switch(operator) {

            case CLEAR:
                mOperand = 0;
                mWaitingOperator = "";
                mWaitingOperand = 0;

                break;

                // TODO: add operations such as memory and trigonometry

            default:

                performWaitingOperation();
                mWaitingOperator = operator;
                mWaitingOperand = mOperand;
        }

        return mOperand;
    }

    protected void performWaitingOperation() {
        switch (mWaitingOperator) {

            case ADD:
                mOperand = mWaitingOperand + mOperand;
                break;

            case SUBTRACT:
                mOperand = mWaitingOperand - mOperand;
                break;

            case MULTIPLY:
                mOperand = mWaitingOperand * mOperand;
                break;

            case DIVIDE:
                if (mOperand != 0) {
                    mOperand = mWaitingOperand / mOperand;
                }
        }
    }
}

