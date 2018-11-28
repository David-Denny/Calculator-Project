package com.calculatorproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.github.kexanie.library.MathView;

public class QuadraticEquation extends AppCompatActivity {
    MathView quadraticDisplay;
    String aString;
    String bString;
    String cString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quadratic_equation);

        // set default quadratic equation
        aString = "a";
        bString = "b";
        cString = "c";

        // initialise display TextView
        quadraticDisplay = findViewById(R.id.quadratic_display);

        // Use string substitution to get text to be displayed and set it to the textview
        updateDisplay();

        // get EditText
        EditText aInput = findViewById(R.id.a_input);
        EditText bInput = findViewById(R.id.b_input);
        EditText cInput = findViewById(R.id.c_input);

        // Set TextWatcher to update display when user inputs into "a" EditText
        TextWatcher aTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                aString = s.toString();
                updateDisplay();

            }
        };

        // Set TextWatcher to update display when user inputs into "b" EditText
        TextWatcher bTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bString = s.toString();
                updateDisplay();
            }
        };

        // Set TextWatcher to update display when user inputs into "c" EditText
        TextWatcher cTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cString = s.toString();
                updateDisplay();
            }
        };

        // add TextWatchers to the EditTexts
        aInput.addTextChangedListener(aTextWatcher);
        bInput.addTextChangedListener(bTextWatcher);
        cInput.addTextChangedListener(cTextWatcher);

    }

    public void calculateQuadratic(View view) {


        // hide keyboard on button click
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputManager != null;
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        // initialise output TextViews
        TextView textOutput = findViewById(R.id.quadratic_output_text);
        TextView rootOutput = findViewById(R.id.quadratic_output_roots);
        TextView discriminantOutput = findViewById(R.id.quadratic_output_discriminant);

        // validation to prevent crashes from NumberFormatException caused by the user not
        // inputting all the required values
        try {

            // initialise variable values
            double a = Double.parseDouble(aString);
            double b = Double.parseDouble(bString);
            double c = Double.parseDouble(cString);

            // calculate the discriminant
            double discriminant = b * b - 4 * a * c;

            // two real roots
            if (discriminant > 0) {

                // calculate roots
                double firstRoot = (-b + Math.sqrt(discriminant)) / (2 * a);
                double secondRoot = (-b - Math.sqrt(discriminant)) / (2 * a);

                // display roots
                textOutput.setText(R.string.quadraticTextMultipleRoots);
                rootOutput.setText(Html.fromHtml(getString(R.string.quadraticOutputMultipleRoots,
                        String.valueOf(firstRoot), String.valueOf(secondRoot)))
                );

                // repeated real roots
            } else if (discriminant == 0) {

                // calculate root
                double root = (-b + Math.sqrt(discriminant)) / (2 * a);

                // display root
                textOutput.setText(R.string.quadraticRepeatedRootsText);
                rootOutput.setText(getString(R.string.quadraticOutputRepeatedRoots, String.valueOf(root)));

                // no real roots
            } else {
                // display lack of roots to the user.
                textOutput.setText(R.string.quadraticOutputNoRealRoots);
            }

            // display the discriminant
            discriminantOutput.setText(
                    getString(R.string.quadraticOutputDiscriminant, String.valueOf(discriminant)));
        } catch (NumberFormatException e) {
            
            // inform user of error
            Toast.makeText(this, "ERROR: input all required values", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateDisplay() {

        String quadraticEquation = String.format(
                "$$\\color{white}{x = \\frac{- %2$s \\pm \\sqrt{%2$s^2 - 4 \\times %1$s \\times %3$s}}{2 \\times %1$s}}$$",
                aString, bString, cString);

//         "$$\\color{white}{\\frac{%2$s \\pm \\sqrt{%2$s^2 - 4 \\times %1$s \\times %3$s}{2 \\times %1$s}}$$"
        quadraticDisplay.setText(quadraticEquation);

    }

    // send user to Equations class onclick
    public void handleBackButton(View view) {
        startActivity(new Intent(QuadraticEquation.this, Equations.class));
    }

}
