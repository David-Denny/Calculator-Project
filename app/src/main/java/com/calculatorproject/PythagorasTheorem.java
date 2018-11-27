package com.calculatorproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PythagorasTheorem extends AppCompatActivity {

    TextView pythagorasDisplay;
    String aString;
    String bString;
    String cString;
    EditText aInput;
    EditText bInput;
    EditText cInput;
    TextView pythagorasTextOutput;
    TextView pythagorasAnswerOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pythagoras_theorem);

        // set default quadratic equation
        aString = "a";
        bString = "b";
        cString = "c";

        // initialise display TextView
        pythagorasDisplay = findViewById(R.id.pythagoras_display);

        // find and set display text
        updateDisplay();

        // initialise output TextViews
        pythagorasTextOutput = findViewById(R.id.pythagoras_output_text);
        pythagorasAnswerOutput = findViewById(R.id.pythagoras_output_answer);

        // initialise input EditTexts
        aInput = findViewById(R.id.pythagorasAInput);
        bInput = findViewById(R.id.pythagorasBInput);
        cInput = findViewById(R.id.pythagorasCInput);

        // set TextWatcher to update display after user input
        TextWatcher aTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // get input
                aString = s.toString();

                // display new input to user
                updateDisplay();

            }
        };

        TextWatcher bTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // get input
                bString = s.toString();

                // apply new input
                updateDisplay();

            }
        };

        TextWatcher cTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //get input
                cString = s.toString();

                // apply input
                updateDisplay();

            }
        };

        // add listeners to each EditText
        aInput.addTextChangedListener(aTextWatcher);
        bInput.addTextChangedListener(bTextWatcher);
        cInput.addTextChangedListener(cTextWatcher);
    }

    public void calculatePythagoras(View view) {

        // hide keyboard on button click
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputManager != null;
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        // prevents crashes from NumberFormatException when the user hasn't input the required
        // values
        try {

            // "c" is left empty, so calculate hypotenuse
            if (cInput.getText().toString().equals("")) {

                // get values of triangle sides
                double a = Double.parseDouble(aString);
                double b = Double.parseDouble(bString);

                // calculate the hypotenuse
                double c = Math.sqrt((a * a) + (b * b));

                // display answer
                pythagorasTextOutput.setText(R.string.pythagoras_text_hypotenuse);
                pythagorasAnswerOutput.setText(String.valueOf(c));

                // "a" XOR "b" is empty so calculate the side. Exclusive Or is used here to make sure
                // at least one side is inputted
            } else if (aInput.getText().toString().equals("") ^ bInput.getText().toString().equals("")) {

                // calculate a
                if (aInput.getText().toString().equals("")) {

                    // get side and hypotenuse values
                    double b = Double.parseDouble(bString);
                    double c = Double.parseDouble(cString);

                    // only continue if hypotenuse is greater than the side
                    if (c > b) {

                        // calculate side a
                        double a = Math.sqrt((c * c) - (b * b));

                        // display answer
                        pythagorasTextOutput.setText(R.string.pythagoras_text_a);
                        pythagorasAnswerOutput.setText(String.valueOf(a));
                    } else {

                        Toast.makeText(this,
                                "ERROR: a side cannot be larger than the hypotenuse.",
                                Toast.LENGTH_SHORT).show();
                    }

                    // calculate b
                } else {

                    // get side and hypotenuse values
                    double a = Double.parseDouble(aString);
                    double c = Double.parseDouble(cString);

                    // only continue if hypotenuse is greater than the side
                    if (c > a) {

                        // calculate side b
                        double b = Math.sqrt((c * c) - (a * a));

                        // display answer
                        pythagorasTextOutput.setText(R.string.pythagoras_text_b);
                        pythagorasAnswerOutput.setText(String.valueOf(b));
                    } else {

                        Toast.makeText(this,
                                "ERROR: a side cannot be larger than the hypotenuse.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

        } catch (NumberFormatException e) {

            // inform user of error
            Toast.makeText(this,
                    "ERROR: input all required values", Toast.LENGTH_SHORT).show();
        }
    }

    // update user's input in display expression
    public void updateDisplay() {

        // create Spanned containing new input
        Spanned pythagorasExpression = Html.fromHtml(
                getString(R.string.pythagoras_display, aString, bString, cString));

        // display new input
        pythagorasDisplay.setText(pythagorasExpression);
    }

    // send user back to MathsEquation
    public void handleBackButton(View view) {

        startActivity(new Intent(PythagorasTheorem.this, MathsEquations.class));
    }
}
