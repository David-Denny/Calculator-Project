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

import io.github.kexanie.library.MathView;


public class SineRule extends AppCompatActivity {
    MathView sineDisplay;
    String aString;
    String bString;
    String aCapitalString;
    String bCapitalString;
    EditText aInput;
    EditText aCapitalInput;
    EditText bInput;
    EditText bCapitalInput;
    TextView sineTextOutput;
    TextView sineAnswerOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sine_rule);

        // initialise variables
        aString = "a";
        bString = "b";
        aCapitalString = "A";
        bCapitalString = "B";

        // get MathView display
        sineDisplay = findViewById(R.id.sine_display);

        // update display
        updateDisplay();

        // initialise EditTexts
        aInput = findViewById(R.id.a_sine_input);
        aCapitalInput = findViewById(R.id.a_capital_sine_input);
        bInput = findViewById(R.id.b_sine_input);
        bCapitalInput = findViewById(R.id.b_capital_sine_input);

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
                aCapitalString = s.toString();

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
                bCapitalString = s.toString();

                // display new input to user
                updateDisplay();
            }
        };

        aInput.addTextChangedListener(aTextWatcher);
        aCapitalInput.addTextChangedListener(aCapitalTextWatcher);
        bInput.addTextChangedListener(bTextWatcher);
        bCapitalInput.addTextChangedListener(bCapitalTextWatcher);

    }

    public void updateDisplay() {
        String sineEquation = String.format(
                "$$\\color{white}{\\frac{%1$s}{sin%2$s} = \\frac{%3$s}{sin%4$s}}$$",
                aString, aCapitalString, bString, bCapitalString);
        sineDisplay.setText(sineEquation);
    }

    public void calculateSineRule(View view) {

        // hide keyboard on button click
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputManager != null;
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

    }
    // send user back to Equations
    public void handleBackButton(View view) {
        startActivity(new Intent(SineRule.this, Equations.class));
    }
}
