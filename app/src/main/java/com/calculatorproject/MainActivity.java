package com.calculatorproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.EmptyStackException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String expression;
    private TextView mCalculatorDisplay;
    private TextView mOutputDisplay;
    private ShuntingYard mShuntingYard;
    private double mAnswer;
    private String mPostfix;
    private Boolean mNumberIsBeingWritten = true;
    private int mErrorCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_main);

        // Initialise toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        // Make button variables
        findViewById(R.id.enter0).setOnClickListener(this);
        findViewById(R.id.enter1).setOnClickListener(this);
        findViewById(R.id.enter2).setOnClickListener(this);
        findViewById(R.id.enter3).setOnClickListener(this);
        findViewById(R.id.enter4).setOnClickListener(this);
        findViewById(R.id.enter5).setOnClickListener(this);
        findViewById(R.id.enter6).setOnClickListener(this);
        findViewById(R.id.enter7).setOnClickListener(this);
        findViewById(R.id.enter8).setOnClickListener(this);
        findViewById(R.id.enter9).setOnClickListener(this);
        findViewById(R.id.enterDECIMAL).setOnClickListener(this);
        findViewById(R.id.enterANS).setOnClickListener(this);
        findViewById(R.id.enterEQUALS).setOnClickListener(this);
        findViewById(R.id.enterADD).setOnClickListener(this);
        findViewById(R.id.enterMINUS).setOnClickListener(this);
        findViewById(R.id.enterMULT).setOnClickListener(this);
        findViewById(R.id.enterDIVIDE).setOnClickListener(this);
        findViewById(R.id.enterDEL).setOnClickListener(this);
        findViewById(R.id.enterAC).setOnClickListener(this);
        findViewById(R.id.leftBracket).setOnClickListener(this);
        findViewById(R.id.rightBracket).setOnClickListener(this);
        findViewById(R.id.xyPower).setOnClickListener(this);
        findViewById(R.id.squareRoot).setOnClickListener(this);
        findViewById(R.id.squarePower).setOnClickListener(this);
        findViewById(R.id.xyRoot).setOnClickListener(this);

        // reset the display text sizes onCreate().
        mCalculatorDisplay = findViewById(R.id.calculatorDisplay);
        mCalculatorDisplay.setTextSize(getResources().getDimension(R.dimen.regular));
        mOutputDisplay = findViewById(R.id.outputDisplay);
        mOutputDisplay.setTextSize(getResources().getDimension(R.dimen.regular));

        // set HTML markup on buttons so the superscript and appears correctly.
        Button xyPowerButton = findViewById(R.id.xyPower);
        Button squarePowerButton = findViewById(R.id.squarePower);
        Button xyRootButton = findViewById(R.id.xyRoot);

        xyPowerButton.setText(Html.fromHtml("x<sup><small> y</small></sup>"));
        squarePowerButton.setText(Html.fromHtml("x<sup><small> 2</small></sup>"));
        xyRootButton.setText(Html.fromHtml(getString(R.string.xyRoot)));



        // reset the expression onCreate().
        expression = "";

        mShuntingYard = new ShuntingYard();

        // error code -1 means
        mErrorCode = -1;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate toolbar_main xml file so buttons show up
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // TODO: send use to settings layout

                return true;

            default:
                // User's action is unrecognised, invoke superclass to handle it
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.enterDEL:

                if (expression.length() > 0) {

                    // Removes whitespace in between characters (inserted to ensure conversion to
                    // RPN works correctly.
                    String expressionNoWhiteSpace = expression.replaceAll("\\s", "");

                    // removes last character
                    expressionNoWhiteSpace = expressionNoWhiteSpace.substring(0,
                            expressionNoWhiteSpace.length() -1);

                    // inserts whitespace back into the expression (regex means all characters are
                    // replaced itself and a space except the final one.
                    expression = expressionNoWhiteSpace.replaceAll(".(?=.)", "$0 ");

                    mNumberIsBeingWritten = false;
                }
                break;

            case R.id.enterAC:
                expression = "";
                mOutputDisplay.setText("");
                break;

            case R.id.enter0:
                expression = expression + "0 ";
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter1:
                expression = expression + "1 ";
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter2:
                expression = expression + "2 ";
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter3:
                expression = expression + "3 ";
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter4:
                expression = expression + "4 ";
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter5:
                expression = expression + "5 ";
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter6:
                expression = expression + "6 ";
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter7:
                expression = expression + "7 ";
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter8:
                expression = expression + "8 ";
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter9:
                expression = expression + "9 ";
                mNumberIsBeingWritten = true;
                break;

            case R.id.enterDECIMAL:
                expression = expression + ".";
                mNumberIsBeingWritten = false;
                break;

            case R.id.leftBracket:
                expression = expression + " ( ";
                mNumberIsBeingWritten = false;
                checkBracketMultiplication();
                break;

            case R.id.rightBracket:
                expression = expression + " ) ";
                mNumberIsBeingWritten = false;
                break;

            case R.id.enterADD:
                expression = expression + " + ";
                mNumberIsBeingWritten = false;
                break;

            case R.id.enterMULT:
                expression = expression + " × ";
                mNumberIsBeingWritten = false;
                break;

            case R.id.enterDIVIDE:
                expression = expression + " ÷ ";
                mNumberIsBeingWritten = false;
                break;

            case R.id.enterMINUS:
                expression = expression + " - ";
                mNumberIsBeingWritten = false;
                break;

            case R.id.xyPower:
                expression = expression + " ^ ";
                mNumberIsBeingWritten = false;
                break;

            case R.id.squareRoot:
                expression = expression + " 2 √ ";
                mNumberIsBeingWritten = false;
                break;

            case R.id.squarePower:
                expression = expression + " ^ 2";
                mNumberIsBeingWritten = false;
                break;

            case R.id.xyRoot:
                expression = expression + " √ ";
                mNumberIsBeingWritten = false;
                break;
                //TODO: add further operations, e.g. roots

            case R.id.enterEQUALS:

                if (expression.length() > 0) {

                    mPostfix = ShuntingYard.infixToPostfix(expression);

                    try {
                        mAnswer = ShuntingYard.evaluateRPN(mPostfix);
                        mOutputDisplay.setText("= " + String.valueOf(mAnswer));
                    } catch (EmptyStackException e) {
                        e.printStackTrace();
                        // error code in the 100s means the error is from the expression being formed
                        // wrong
                        mErrorCode = 100;
                    }
                }


        }

        if(mNumberIsBeingWritten) {
            // removes trailing whitespace if a number is written to allow multi-digit numbers
            expression = expression.replaceFirst("\\s++$", "");
        }

        if (mErrorCode == -1) {
            mCalculatorDisplay.setText(expression);

        } else if (mErrorCode == 100){
            // show Toast to user explaining error
            Toast.makeText(this, "ERROR: expression is malformed",
                    Toast.LENGTH_SHORT).show();

            // change text size to allow for more text
            mCalculatorDisplay.setTextSize(getResources().getDimension(R.dimen.error));

            // display error to user again through calculator display
            mCalculatorDisplay.setText("ERROR: expression is malformed. Click AC to continue.");

            // reset error code
            mErrorCode = -1;
            mCalculatorDisplay.setTextSize(getResources().getDimension(R.dimen.regular));

        }


    }

    public void checkBracketMultiplication() {

        // removes whitespace
        String expressionNoWhiteSpace = expression.replaceAll("\\s", "");

        // the index value of the character before the bracket
        int numberIndex = expressionNoWhiteSpace.length() - 2;

        // the character at the index before the bracket.
        char character = expressionNoWhiteSpace.charAt(numberIndex);

        // if the character is a digit, the string will be broken up and a multiplication sign will
        // be inserted between the digit and the opening bracket.
        if (Character.isDigit(character)) {
            expressionNoWhiteSpace = expressionNoWhiteSpace.substring(0,
                    expressionNoWhiteSpace.length() -1)
                    + "×"
                    + expressionNoWhiteSpace.substring(expressionNoWhiteSpace.length() - 1,
                    expressionNoWhiteSpace.length());
        }

        // inserts whitespace back into the expression (regex means all characters are
        // replaced itself and a space.
        expression = expressionNoWhiteSpace.replaceAll(".(?=)", "$0 ");
    }

}