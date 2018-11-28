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

public class CosineRule extends AppCompatActivity {

    MathView cosineDisplay;
    String aString;
    String bString;
    String cString;
    String angleString;
    EditText aInput;
    EditText bInput;
    EditText cInput;
    EditText angleInput;
    TextView cosineTextOutput;
    TextView cosineAnswerOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cosine_rule);

        // set generic cosine equation values
        aString = "a";
        bString = "b";
        cString = "c";
        angleString = "A";

        // initialise display MathView
        cosineDisplay = findViewById(R.id.cosine_display);

        // find and set display text
        setDisplayExpression();

        // initialise output TextViews
        cosineTextOutput = findViewById(R.id.cosine_output_text);
        cosineAnswerOutput = findViewById(R.id.cosine_output_answer);

        // initialise input EditTexts
        aInput = findViewById(R.id.cosine_a_input);
        bInput = findViewById(R.id.cosine_b_input);
        cInput = findViewById(R.id.cosine_c_input);
        angleInput = findViewById(R.id.cosine_angle_input);

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
                setDisplayExpression();
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
                setDisplayExpression();
            }
        };

        TextWatcher cTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // get input
                cString = s.toString();

                // display new input to user
                setDisplayExpression();
            }
        };

        TextWatcher angleTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // get input
                angleString = s.toString();

                // display new input to user
                setDisplayExpression();
            }
        };

        aInput.addTextChangedListener(aTextWatcher);
        bInput.addTextChangedListener(bTextWatcher);
        cInput.addTextChangedListener(cTextWatcher);
        angleInput.addTextChangedListener(angleTextWatcher);
    }

    public void setDisplayExpression() {

        // get the String to be used with string substitution string substitution
        String cosineRule = String.format(
                "$$\\color{white}{%1$s^2 = %2$s^2 + %3$s^2 \\times %2$s \\times %3$s \\times cos(%4$s)}$$",
                aString, bString, cString, angleString);


        // set TextView to user's new expression
        cosineDisplay.setText(cosineRule);
    }

    public void calculateCosineRule(View view) {

        // hide keyboard on button click
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputManager != null;
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        
        try {


            // user wants to calculate "a"
            if (aInput.getText().toString().equals("")) {

                // get sides and angle values
                double b = Double.parseDouble(bString);
                double c = Double.parseDouble(cString);
                double angle = Double.parseDouble(angleString);

                // convert angle to radians as Math.cos() interprets the input as radians
                angle = Math.toRadians(angle);

                // calculate the side a
                double a = Math.sqrt((b * b) + (c * c) * 2.0 * b * c * Math.cos(angle));

                // display answer to user
                cosineTextOutput.setText(R.string.cosine_text_side);
                cosineAnswerOutput.setText(String.valueOf(a));

                // user wants to calculate the angle
            } else if (angleInput.getText().toString().equals("")) {

                double a = Double.parseDouble(aString);
                double b = Double.parseDouble(bString);
                double c = Double.parseDouble(cString);

                double angle = Math.toDegrees(Math.acos(((a * a) - (b * b) - (c * c)) / (2 * b * c)));

                cosineTextOutput.setText(R.string.cosine_text_angle);
                cosineAnswerOutput.setText(getString(R.string.cosine_answer_angle, String.valueOf(angle)));

                // user has made an error inputting their values
            } else {
                Toast.makeText(this,
                        "ERROR: your inputs are not valid", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {

            Toast.makeText(this,
                    "ERROR: input all required values", Toast.LENGTH_SHORT).show();
        }
    }

    public void handleBackButton(View view) {

        startActivity(new Intent(CosineRule.this, Equations.class));
    }
}
