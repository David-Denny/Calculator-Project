package com.calculatorproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.github.kexanie.library.MathView;


public class SineRule extends AppCompatActivity {
    MathView sineDisplay;
    String aString;
    String bString;
    String aAngleString;
    String bAngleString;
    EditText aInput;
    EditText aAngleInput;
    EditText bInput;
    EditText bAngleInput;
    TextView sineTextOutput;
    TextView sineAnswerOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sine_rule);

        // initialise variables
        aString = "a";
        bString = "b";
        aAngleString = "A";
        bAngleString = "B";

        // get MathView display
        sineDisplay = findViewById(R.id.sine_display);

        // update display
        updateDisplay();

        // initialise EditTexts
        aInput = findViewById(R.id.a_sine_input);
        aAngleInput = findViewById(R.id.a_capital_sine_input);
        bInput = findViewById(R.id.b_sine_input);
        bAngleInput = findViewById(R.id.b_capital_sine_input);

        // initialise output TextViews
        sineTextOutput = findViewById(R.id.sine_output_text);
        sineAnswerOutput = findViewById(R.id.sine_output_answer);

        // set TextWatchers to update display after user input
        TextWatcher aTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // get input
                aString = s.toString();

                // display new input to user
                updateDisplay();
            }
        };

        TextWatcher aCapitalTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // get input
                aAngleString = s.toString();

                // display new input to user
                updateDisplay();
            }
        };

        TextWatcher bTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // get input
                bString = s.toString();

                // display new input to user
                updateDisplay();
            }
        };

        TextWatcher bCapitalTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // get input
                bAngleString = s.toString();

                // display new input to user
                updateDisplay();
            }
        };

        aInput.addTextChangedListener(aTextWatcher);
        aAngleInput.addTextChangedListener(aCapitalTextWatcher);
        bInput.addTextChangedListener(bTextWatcher);
        bAngleInput.addTextChangedListener(bCapitalTextWatcher);

    }

    public void updateDisplay() {
        String sineEquation = String.format(
                "$$\\color{white}{\\frac{%1$s}{sin%2$s} = \\frac{%3$s}{sin%4$s}}$$",
                aString, aAngleString, bString, bAngleString);
        sineDisplay.setText(sineEquation);
    }

    public void calculateSineRule(View view) {

        // hide keyboard on button click
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputManager != null;
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        
        try {

            if (aInput.getText().toString().equals("") || bInput.getText().toString().equals("")) {

                double angleA = Double.parseDouble(aAngleString);
                double angleB = Double.parseDouble(bAngleString);
                angleA = Math.toRadians(angleA);
                angleB = Math.toRadians(angleB);

                if (aInput.getText().toString().equals("")) {

                    double b = Double.parseDouble(bString);

                    double a = Math.sin(angleA) * (b / Math.sin(angleB));

                    sineTextOutput.setText(R.string.sine_text_a);
                    sineAnswerOutput.setText(String.valueOf(a));
                } else {

                    double a =  Double.parseDouble(aString);

                    double b = Math.sin(angleB) * (a / Math.sin(angleA));

                    sineTextOutput.setText(R.string.sine_text_b);
                    sineAnswerOutput.setText(String.valueOf(b));
                }
            } else if (aAngleInput.getText().toString().equals("")
                    || bAngleInput.getText().toString().equals("")) {

                double a = Double.parseDouble(aString);
                double b = Double.parseDouble(bString);

                if (aAngleInput.getText().toString().equals("")) {

                    double bAngle = Double.parseDouble(bAngleString);
                    bAngle = Math.toRadians(bAngle);

                    double aAngle = Math.toDegrees(Math.asin(a * Math.sin(bAngle) / b));

                    sineTextOutput.setText(R.string.sine_text_aAngle);
                    sineAnswerOutput.setText(String.valueOf(aAngle));
                } else {

                    double aAngle = Double.parseDouble(aAngleString);
                    aAngle = Math.toRadians(aAngle);

                    double bAngle = Math.toDegrees(Math.asin(b *  (Math.sin(aAngle) / a)));

                    sineTextOutput.setText(R.string.sine_text_bAngle);
                    sineAnswerOutput.setText(String.valueOf(bAngle));
                }
            } else {
                Toast.makeText(this,
                        "ERROR: input all required values", Toast.LENGTH_SHORT).show();
            }
            
        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "ERROR: input all required values", Toast.LENGTH_SHORT).show();
        }

    }
    // send user back to Equations
    public void handleBackButton(View view) {
        startActivity(new Intent(SineRule.this, Equations.class));
    }
}
