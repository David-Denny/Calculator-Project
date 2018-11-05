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
    private String displayExpression;
    private boolean isTypingPower;
    private TextView mCalculatorDisplay;
    private TextView mOutputDisplay;
    private ShuntingYard mShuntingYard;
    private double mAnswer;
    private String mPostfix;
    private Boolean mNumberIsBeingWritten = true;
    private int mErrorCode;
    private int mCurrentNumLength;
    private int pointerIndex;
    final String ops = "-+÷×^√  ";
    private int realPosition;

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

        xyPowerButton.setText(Html.fromHtml(getString(R.string.xyPower)));
        squarePowerButton.setText(Html.fromHtml(getString(R.string.squarePower)));
        xyRootButton.setText(Html.fromHtml(getString(R.string.xyRoot)));

        // reset the expression onCreate().
        expression = "";
        displayExpression = "";

        mShuntingYard = new ShuntingYard();

        // error code -1 means no error
        mErrorCode = -1;

        // starts pointer at 0
        pointerIndex = 0;
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

        pointerIndex ++;

        Log.d("onclick pointer", String.valueOf(pointerIndex));

        switch (view.getId()) {

            case R.id.enterDEL:

                if (expression.length() > 0) {

                    // Removes whitespace in between characters (inserted to ensure conversion to
                    // RPN works correctly.
                    String expressionNoWhiteSpace = expression.replaceAll("\\s", "");

                    // removes last character
                    expressionNoWhiteSpace = expressionNoWhiteSpace.substring(0,
                            expressionNoWhiteSpace.length() - 1);

                    // inserts whitespace back into the expression (regex means all characters are
                    // replaced itself and a space except the final one.
                    expression = expressionNoWhiteSpace.replaceAll(".(?=.)", "$0 ");

                    mNumberIsBeingWritten = false;
                }

                // -2 to counteract the +1 at the start of this method
                pointerIndex = pointerIndex - 2;
                break;

            case R.id.enterAC:
                expression = "";
                displayExpression = "";
                mOutputDisplay.setText("");
                mCurrentNumLength = 0;
                pointerIndex = 0;
                realPosition = 0;
                break;

            case R.id.enter0:
                mCurrentNumLength++;

                insertIntoRealExpression("0 ");
                insertIntoDisplay("0 ");
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter1:
                mCurrentNumLength++;

                insertIntoRealExpression("1 ");
                insertIntoDisplay("1 ");
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter2:
                mCurrentNumLength++;

                insertIntoRealExpression("2 ");
                insertIntoDisplay("2 ");
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter3:
                mCurrentNumLength++;

                insertIntoRealExpression("3 ");
                insertIntoDisplay("3 ");
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter4:
                mCurrentNumLength++;

                insertIntoRealExpression("4 ");
                insertIntoDisplay("4 ");
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter5:
                mCurrentNumLength++;

                insertIntoRealExpression("5 ");
                insertIntoDisplay("5 ");
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter6:
                mCurrentNumLength++;

                insertIntoRealExpression("6 ");
                insertIntoDisplay("6 ");
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter7:
                mCurrentNumLength++;

                insertIntoRealExpression("7 ");
                insertIntoDisplay("7 ");
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter8:
                mCurrentNumLength++;

                insertIntoRealExpression("8 ");
                insertIntoDisplay("8 ");
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter9:
                mCurrentNumLength++;

                insertIntoRealExpression("9 ");
                insertIntoDisplay("9 ");
                mNumberIsBeingWritten = true;
                break;

            case R.id.enterDECIMAL:
                mCurrentNumLength = 0;

                insertIntoRealExpression(". ");
                insertIntoDisplay(".");
                mNumberIsBeingWritten = false;
                break;

            case R.id.leftBracket:
                mCurrentNumLength = 0;
                checkBracketMultiplication();

                insertIntoRealExpression("( ");
                insertIntoDisplay("(");
                mNumberIsBeingWritten = false;
                break;

            case R.id.rightBracket:
                mCurrentNumLength = 0;

                insertIntoRealExpression(") ");
                insertIntoDisplay(")");
                mNumberIsBeingWritten = false;
                break;

            case R.id.enterADD:
                mCurrentNumLength = 0;

                insertIntoRealExpression(" + ");
                insertIntoDisplay(" +");
                mNumberIsBeingWritten = false;
                break;

            case R.id.enterMULT:
                mCurrentNumLength = 0;

                insertIntoRealExpression(" × ");
                insertIntoDisplay(" ×");
                mNumberIsBeingWritten = false;
                break;

            case R.id.enterDIVIDE:
                mCurrentNumLength = 0;

                insertIntoRealExpression(" ÷ ");
                insertIntoDisplay(" ÷");
                mNumberIsBeingWritten = false;
                break;

            case R.id.enterMINUS:
                mCurrentNumLength = 0;

                insertIntoRealExpression(" - ");
                insertIntoDisplay(" -");
                mNumberIsBeingWritten = false;
                break;

            case R.id.xyPower:
                mCurrentNumLength = 0;

                insertIntoRealExpression(" ^ ");
                insertIntoDisplay("^");
                mNumberIsBeingWritten = false;
                isTypingPower = true;
                break;

            case R.id.xyRoot:

                insertIntoRealExpression(" √ ");
                appendRootToDisplay();
                insertIntoDisplay("√");
                mNumberIsBeingWritten = false;
                mCurrentNumLength = 0;

                break;

            case R.id.squareRoot:
                expression = expression + " 2 √ ";
                mNumberIsBeingWritten = false;
                mCurrentNumLength = 0;

                insertIntoRealExpression(" √ ");
                displayExpression = displayExpression + "<sup> 2 </sup> √ ";
                break;

            case R.id.squarePower:
                expression = expression + " ^ 2 ";
                mNumberIsBeingWritten = false;
                mCurrentNumLength = 0;
                insertIntoRealExpression(" √ ");
                insertShortcutToDisplay("square");
                break;


            case R.id.enterEQUALS:

                if (expression.length() > 0) {


                    try {

                        expression = expression.replaceAll("_", "");
                        mPostfix = ShuntingYard.infixToPostfix(expression);
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

        if (mNumberIsBeingWritten) {
            // removes trailing whitespace if a number is written to allow multi-digit numbers
            expression = expression.replaceFirst("\\s++$", "");
            displayExpression = displayExpression.replaceFirst("\\s++$", "");
        }

        // -1 means no error
        if (mErrorCode == -1) {

            if (!mNumberIsBeingWritten) {
                displayExpression = displayExpression + " ";
            }
            mCalculatorDisplay.setText(Html.fromHtml(displayExpression));

        } else if (mErrorCode == 100) {
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

    /*
     * This method is used to prevent an error where having a digit next a bracket (e.g. "2(5)")
     * didn't register as multiplication.
     *
     * Whenever a opening bracket is entered by the user, this method is called and checks if the
     * character before it is a digit. If the character is a digit, a multiplication sign is
     * inserted into the expression so it is calculated correctly by the ShuntingYard class.
     */
    public void checkBracketMultiplication() {

        // checks length so there isn't an ArrayOutOfBoundsException if ".length() - 4" is called on
        // an expression with less than 3 characters.
        if (expression.length() > 4) {

            // checking if the character is a number
            if (Character.isDigit(expression.charAt(expression.length() - 4))) {

                // replaces space with a multiplication sign
                StringBuilder string = new StringBuilder(expression);
                string.replace(string.length() - 3, string.length() - 3, " ×");
                expression = string.toString();

                // checking if the character is a bracket
            } else if (expression.charAt(expression.length() - 5) == ')') {

                // replaces space with a multiplication sign
                StringBuilder string = new StringBuilder(expression);
                string.replace(string.length() - 3, string.length() - 3, "×");
                expression = string.toString();
            }
        }

    }

    /* This method is invoked whenever the user clicks a button that will change the calculator's
    * display.
    *
    * The method concatenates the user's input to the displayExpression. There are special
    * exceptions for different inputs, for example, a power sign means that the '<sup>' tag should
    * be appended to the displayExpression
    */
    public void insertIntoDisplay(String input) {

        String cleansedExpression = displayExpression.replaceAll("\\s", "");
        StringBuilder string = new StringBuilder(cleansedExpression);

        // prevent StringIndexOutOfBoundsException
        if (pointerIndex != 0 ) {

            // default case where the user is just inputting a number or an operator
            if (Character.isDigit(input.charAt(0))
                    || ops.indexOf(input.charAt(0)) != -1) {

                string.insert(pointerIndex - 1, input);


                // case where the user inputs a power sign which should be replaced by a superscript
                // HTML tag.
            } else if (input.equals("^")) {
                string.insert(pointerIndex, input + "<sup");

                // special case where a non-digit character has been entered so the closing superscript
                // tag must be included to cancel the power and then insert the user's input.
            } else {
                string.insert(pointerIndex, "</sup> " + input);
            }

        } else {
            string.append(input);
        }
        displayExpression = string.toString().replaceAll(".(?=.)", "$0 ");
        Log.d("Display", displayExpression);
    }

    public void insertShortcutToDisplay(String input) {

        String cleansedExpression = displayExpression.replaceAll("\\s", "");
        StringBuilder string = new StringBuilder(cleansedExpression);

        Log.d("string", string.toString());
        Log.d("position", String.valueOf(pointerIndex));

        if (string.length() > 2) {
            if (input.equals("square")) {
                string.insert(pointerIndex - 1, "<sup> 2 </sup>");
            }
        } else {
            string.append("<sup> 2 </sup>");
        }


        displayExpression = string.toString().replaceAll(".(?=.)", "$0 ");
        mCalculatorDisplay.setText(Html.fromHtml(displayExpression));
    }

    /*
     * Depending on the length of the expression, HTML markup will be inserted proactively into
     * the expression so the digit before the radical sign appears as superscript.
     *
     * Appending roots is in a separate function than the other characters as it has to
     * retroactively edit the display string and is a different case compared to the others.
     *
     * Therefore, for understandability's sake I have created it's own function.
     */
    public void appendRootToDisplay() {

        StringBuilder string = new StringBuilder(displayExpression);

        // inserts the opening tag (mCurrentNumLength is used to ensure the entire number is inside the <sup> HTML tags)
        string.replace(string.length() - mCurrentNumLength, string.length() - mCurrentNumLength, " <sup>")

                // appends the closing tag and radical sign
                .append(" </sup>  √ ");

        displayExpression = string.toString();
        mNumberIsBeingWritten = false;
    }

    public void insertIntoRealExpression(String input) {
        StringBuilder string = new StringBuilder(expression);

        if (string.length() == 0) {
            string.append(input);
        } else {

            // digits
            if (Character.isDigit(input.charAt(0))) {

                string.insert(realPosition, input);
                realPosition = realPosition + 2;

                if (mNumberIsBeingWritten) {
                    realPosition --;
                }
                // operators
            } else if (ops.contains(input.replaceAll("\\s", ""))) {

                string.insert(realPosition, input);
                realPosition = realPosition + 3;

                // brackets
            } else {
                string.insert(realPosition, input);
                realPosition = realPosition + 2;
            }

        }
        Log.d("real string", string.toString());
        expression = string.toString();
    }

    public void shiftPosition(View view) {

        String cleansedExpression = displayExpression.replaceAll("\\s", "").replaceAll("\\|", "");

        if (view.getId() == R.id.shiftLeft) {

            if (pointerIndex > 0) {
                pointerIndex --;
            }

            Log.d("pointer left", String.valueOf(pointerIndex));

        } else if (view.getId() == R.id.shiftRight && pointerIndex < cleansedExpression.length()) {

            pointerIndex ++;

            Log.d("pointer right", String.valueOf(pointerIndex));
        }

        // add underscore to the position

        StringBuilder string = new StringBuilder(cleansedExpression);

        if (pointerIndex <= string.length()) {
            string.insert(pointerIndex, "|");

        }
        displayExpression = string.toString().replaceAll(".(?=.)", "$0 ");
        mCalculatorDisplay.setText(Html.fromHtml(displayExpression));
        Log.d("Display Expression", displayExpression);
    }
}
