package com.calculatorproject;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
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

    // TODO: add square and root functionality (again)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.calc_main);

        // Initialise toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

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

        // display to user
        updateDisplay();
    }

    public void inputOperator(View view) {

        // get input operator from the view parameter's Tag (XML characteristic)
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

        // display to user
        updateDisplay();
    }

    // TODO: add markers that signify where the HTML tags should be placed on update
    // (and removed when used in equals function)

    public void inputOpShortcut(View view) {

        StringBuilder stringBuilderInfix = new StringBuilder(mInfix);

        // if user taps square power button
        if (view.getId() == R.id.squarePower) {

            // requires a whitespace at the start as it will always immediately follow a digit
            // TODO: add special case where no starting whitespace is added when there is a bracket
            stringBuilderInfix.insert(mPosition, " ^ 2");

        } else { // if user taps square root button

            // requires a trailing whitespace as a number or bracket will immediately follow
            stringBuilderInfix.insert(mPosition, "2 √ ");
        }

        // update position
        mPosition = mPosition + 4;

        // set member infix variable to stringBuilder
        mInfix = stringBuilderInfix.toString();

        // update GUI
        updateDisplay();
    }

    //TODO: add bracket multiplication checking

    public void inputBracket(View view) {

        // get input from the view's tag
        String input = (String) view.getTag();

        // add buffer spaces to input
        StringBuilder stringBuilderInput = new StringBuilder(input);
        stringBuilderInput.insert(0, " ")
                            .append(" ");
        input = stringBuilderInput.toString();

        // insert input into infix
        StringBuilder stringBuilderInfix = new StringBuilder(mInfix);
        stringBuilderInfix.insert(mPosition, input);
        mInfix = stringBuilderInfix.toString();

        // update position
        mPosition = mPosition + 3;

        // update GUI to show new infix
        updateDisplay();

    }

    public void submitInfix(View view) {

        // validation check to prevent StringIndexOutOfBounds
        if (mInfix.length() > 0) {

            // try-catch to prevent crashes from badly formed input expressions
            try {

                // get postfix
                mPostfix = ShuntingYard.infixToPostfix(removePositionMarker(mInfix));

                // get answer
                mAnswer = ShuntingYard.evaluateRPN(mPostfix);

                // display answer to user
                mOutputDisplay.setText(getString(R.string.answer, String.valueOf(mAnswer)));

            } catch (EmptyStackException e) {
                e.printStackTrace();

                //TODO: Add better errors
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void shiftPosition(View view) {

        // user taps right button
        if (view.getId() == R.id.shiftRight) {

            // validation to prevent StringIndexOutOfBoundsException
            if (mPosition < mInfix.length() - 1) {

                // validation check to prevent StringIndexOutOfBoundsException
                if (mInfix.length() - mPosition > 2) {

                    // skips whitespace
                    if (mInfix.charAt(mPosition + 2) == ' ') {

                        // increments position variable by 2 to cover the whitespace
                        mPosition = mPosition + 2;
                    }
                } else {

                    // increments position variable
                    mPosition++;
                }
            } else {
                Log.d("WTF", "BITCH");
            }

        } else { // user shifts left

            // validation check to prevent StringIndexOutOfBoundsException
            if (mPosition > 0) {

                // skips whitespaces
                if (mInfix.charAt(mPosition - 1) == ' ') {

                    // decreases position variable by 2 to cover the whitespace
                    mPosition = mPosition - 3;

                } else {

                    // decreases position variable
                    mPosition--;
                }
            }
        }

        // update display
        updateDisplay();

    }


    public void updateDisplay() {

        // remove the position marker
        String cleanExpression = removePositionMarker(mInfix);


        // add underscore as a position marker to GUI
        StringBuilder underscoreString = new StringBuilder(cleanExpression);
        underscoreString.insert(mPosition, "_");
        mInfix = underscoreString.toString();

        // update display
        mCalculatorDisplay.setText(mInfix);
    }

    public String removePositionMarker(String infix) {

        // replace underscore with empty string to remove it
        return infix.replaceAll("_", "");
    }
}
