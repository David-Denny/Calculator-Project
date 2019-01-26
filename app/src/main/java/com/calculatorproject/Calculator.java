package com.calculatorproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;

public class Calculator extends AppCompatActivity {

    private String mInfix;
    private TextView mCalculatorDisplay;
    private TextView mOutputDisplay;
    final String ops = "-+÷×^√";
    private int mPosition;
    final String trigOps = "sctzef";
    private int counter;
    private double roundValue;
    private boolean hapticFeedback;
    private Vibrator v;

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

        Button arcsinButton = findViewById(R.id.sin_inverse);
        Button arccosButton = findViewById(R.id.cos_inverse);
        Button arctanButton = findViewById(R.id.tan_inverse);

        arcsinButton.setText(Html.fromHtml(getString(R.string.sin_inverse)));
        arccosButton.setText(Html.fromHtml(getString(R.string.cos_inverse)));
        arctanButton.setText(Html.fromHtml(getString(R.string.tan_inverse)));

        // reset the mInfix onCreate().
        mInfix = "";
        counter = 0;

        // gets SharedPreferences
        SharedPreferences mPrefs = this.getSharedPreferences("calculator", Context.MODE_PRIVATE);

        if (mPrefs.contains("decimalPoints")) {

            // gets user specified rounding value
            int decimalPoints = mPrefs.getInt("decimalPoints", -1);

            StringBuilder roundValueString = new StringBuilder("10");

            // creates string with zeroes corresponding to the user's value
            for (int i = 0; i < decimalPoints; i++) {

                roundValueString.append("0");
            }

            //  converts string to a double
            roundValue = Double.valueOf(roundValueString.append(".0").toString());

        } else {

            // default rounding rounds to 10 decimal points
            roundValue = 10000000000.0;
        }

        // orientation is locked depending on user's choice. Defaults to portrait if no choice is
        // made in Settings class (as true corresponds to portrait)
        if (mPrefs.getBoolean("isPortrait", true)) {

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        hapticFeedback = mPrefs.getBoolean("feedbackIsChecked", true);
    }

    public void inputAC(View view) {

        vibratePhone();

        // reset all displays and variables
        mInfix = "";
        mPosition = 0;
        mCalculatorDisplay.setText("");
        mOutputDisplay.setText("");
        counter = 0;
    }

    public void inputDigit(View view) {

        vibratePhone();

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

    public void inputTrigonometry(View view) {

        vibratePhone();

        // get operator from view's tag
        String input = (String) view.getTag();

        // add buffer spaces to input
        StringBuilder stringInput = new StringBuilder(input);
        stringInput.
                insert(stringInput.length(), " ")
                .insert(0, "0 ");

        input = stringInput.toString();


        // insert input into infix String
        StringBuilder string = new StringBuilder(mInfix);
        string.insert(mPosition, input);
        mInfix = string.toString();

        // update position
        mPosition = mPosition + 4;

        // update display
        updateDisplay();
    }

    public void inputOperator(View view) {

        vibratePhone();

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

    public void inputOpShortcut(View view) {

        vibratePhone();

        StringBuilder stringBuilderInfix = new StringBuilder(mInfix);

        // if user taps square power button
        if (view.getId() == R.id.squarePower) {

            // requires a whitespace at the start as it will always immediately follow a digit
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

    public void inputBracket(View view) {

        vibratePhone();

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

        validateBracketMultiplication();
        // update GUI to show new infix
        updateDisplay();

    }

    public void validateBracketMultiplication() {

        vibratePhone();

        StringBuilder stringBuilderInfix = new StringBuilder(mInfix);

        // validate length to prevent StringIndexOutOfBounds
        if (mInfix.length() > 4) {

            // check if opening bracket
            if (mInfix.charAt(mPosition - 2) == '('
                    // check if previous token is a digit
                    && Character.isDigit(mInfix.charAt(mPosition - 4))) {

                // insert multiplication token
                stringBuilderInfix.insert(mPosition - 2, "× ");

                // update position variable
                mPosition = mPosition + 2;
            }
        }

        mInfix = stringBuilderInfix.toString();
    }

    public void delete(View view) {

        vibratePhone();

        StringBuilder stringBuilderInfix = new StringBuilder(mInfix);


        // validate string length to prevent crashes from StringIndexOutOfBounds
        if (stringBuilderInfix.length() > 1) {

            // delete a single character by itself
            if (Character.isDigit(stringBuilderInfix.charAt(mPosition - 1))
                    || ops.contains(String.valueOf(stringBuilderInfix.charAt(mPosition - 1)))
                    || stringBuilderInfix.charAt(mPosition - 1) == '.') {

                // delete character
                stringBuilderInfix.deleteCharAt(mPosition - 1);
                mPosition --;

            // delete single character including whitespace (e.g. when there's an operator)
            } else if (stringBuilderInfix.charAt(mPosition -1) == ' ') {

                // remove token
                stringBuilderInfix.deleteCharAt(mPosition - 1);

                // position must be updated before editing infix to prevent crashes
                mPosition --;

                // remove whitespace
                stringBuilderInfix.deleteCharAt(mPosition - 1);
                mPosition --;
            }


        }

        mInfix = stringBuilderInfix.toString();
        updateDisplay();
    }

    public void submitInfix(View view) {

        vibratePhone();

        // validation check to prevent StringIndexOutOfBounds
        if (mInfix.length() > 0) {

            // try-catch to prevent crashes from badly formed input expressions
            try {


                // get postfix
                String mPostfix = ShuntingYard.infixToPostfix(removePositionMarker(mInfix));

                // get answer
                double mAnswer = ShuntingYard.evaluateRPN(mPostfix);

                mAnswer = Math.round(mAnswer * roundValue) / roundValue;
                // display answer to user
                mOutputDisplay.setText(getString(R.string.answer, String.valueOf(mAnswer)));

            } catch (EmptyStackException e) {
                e.printStackTrace();

                Toast.makeText(this, "ERROR: expression is malformed",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void shiftPosition(View view) {

        vibratePhone();

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

                        // skips two whitespaces
                    } else if(mInfix.charAt(mPosition + 1) == ' ') {

                        // increment position by 3 when cursor is to the right of a digit to cover
                        // two whitespaces
                        mPosition = mPosition + 3;

                    } else if (Character.isDigit(mInfix.charAt(mPosition + 1))
                            || trigOps.contains(String.valueOf(mInfix.charAt(mPosition + 1)))) {

                        mPosition = mPosition + 1;
                    }

                } else {

                    // increments position variable
                    mPosition++;
                }
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

        // convert infix to array so it can be used in a for-each loop
        ArrayList<String> infixList = new ArrayList<>(Arrays.asList(mInfix.split("")));

        // create array of replacement strings
        String[] trigArray = new String[]{"sin", "cos", "tan", "arcsin", "arccos", "arctan"};

        // create new display StringBuilder
        StringBuilder displayInfix = new StringBuilder();

        // iterate over each element in the infix
        for (String character : infixList) {

            // if the current element is a trigonometric operator
            if (trigOps.contains(character)) {

                // if counter is above 0 to prevent strange bug where there was always a "sin" at
                // the start of the string
                if (counter > 0) {

                    // get index of character in the trigOps string
                    int trigIndex = trigOps.indexOf(character);

                    // remove the place holder "0 "
                    displayInfix.deleteCharAt(displayInfix.length() -1 )
                            .deleteCharAt(displayInfix.length() - 1);
                    // replace operator with string from the array
                    displayInfix.append(trigArray[trigIndex]);
                }

                // iterate counter
                counter ++;

            } else {

                // add character to string as normal
                displayInfix.append(character);
            }
        }

        // reset counter
        counter = 0;

        // update display
        mCalculatorDisplay.setText(displayInfix.toString());
    }

    public void vibratePhone() {
        // if haptic feedback is enabled, vibrate the phone for 500ms
        if(hapticFeedback) {
            v.vibrate(100);
        }
    }

    public String removePositionMarker(String infix) {

        // replace underscore with empty string to remove it
        return infix.replaceAll("_", "");
    }

    public void onClickEquations(View view) {

        vibratePhone();

        // send to Equations activity
        startActivity(new Intent(Calculator.this, Equations.class));
    }

    public void onClickConversions(View view) {

        vibratePhone();

        // send to Conversion activity
        startActivity(new Intent(Calculator.this, Conversions.class));
    }

    public void settings(View view) {

        vibratePhone();

        // send to Settings activity
        startActivity(new Intent(Calculator.this, Settings.class));
    }
}
