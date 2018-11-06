package com.calculatorproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.EmptyStackException;

public class Calculator extends AppCompatActivity {

    private String mInfix;
    private String displayExpression;
    private boolean isTypingPower;
    private TextView mCalculatorDisplay;
    private TextView mOutputDisplay;
    private ShuntingYard mShuntingYard;
    private double mAnswer;
    private String mPostfix;
    private Boolean mNumberIsBeingWritten = true;
    private int mCurrentNumLength;
    final String ops = "-+÷×^√  ";
    private int mPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.calc_main);

        // Initialise toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        // Make button variables

        // reset the display text sizes onCreate().
        mCalculatorDisplay = findViewById(R.id.calculatorDisplay);
        mCalculatorDisplay.setTextSize(getResources().getDimension(R.dimen.regular));
        mOutputDisplay = findViewById(R.id.outputDisplay);
        mOutputDisplay.setTextSize(getResources().getDimension(R.dimen.regular));

        // set HTML markup on buttons so the superscript and appears correctly.
        Button xyPowerButton = findViewById(R.id.xyPower);
        Button squarePowerButton = findViewById(R.id.squarePower);
        Button xyRootButton = findViewById(R.id.xyRoot);

        xyPowerButton.setText(Html.fromHtml(getString(R.string.xyPower)));
        squarePowerButton.setText(Html.fromHtml(getString(R.string.squarePower)));
        xyRootButton.setText(Html.fromHtml(getString(R.string.xyRoot)));

        // reset the mInfix onCreate().
        mInfix = "";
        displayExpression = "";

        mShuntingYard = new ShuntingYard();

    }

    public void inputAC(View view) {

        // reset all displays and variables
        mInfix = "";
        mPosition = 0;
        mCalculatorDisplay.setText("");
        mOutputDisplay.setText("");
    }

    public void inputDigit(View view) {

        // insert input into correct mPosition
        String input = (String) view.getTag();
        StringBuilder string = new StringBuilder(mInfix);
        string.insert(mPosition, input);

        mInfix = string.toString();

        // increment mPosition variable
        mPosition++;

        mCalculatorDisplay.setText(mInfix);
    }

    public void inputOperator(View view) {

        String input = (String) view.getTag();

        // add buffer spaces
        StringBuilder stringInput = new StringBuilder(input);
        stringInput.insert(0, " ")
                .insert(stringInput.length(), " ");
        input = stringInput.toString();

        // insert input into correct mPosition
        StringBuilder string = new StringBuilder(mInfix);
        string.insert(mPosition, input);
        mInfix = string.toString();

        // adds +3 for the character and 2 whitespaces
        mPosition = mPosition + 3;

        mCalculatorDisplay.setText(mInfix);
    }

    public void submitInfix(View view) {

        if (mInfix.length() > 0) {

            try {
                mPostfix = ShuntingYard.infixToPostfix(mInfix);
                mAnswer = ShuntingYard.evaluateRPN(mPostfix);

                mOutputDisplay.setText(getString(R.string.answer, String.valueOf(mAnswer)));
            } catch (EmptyStackException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
