package com.calculatorproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class QuadraticEquation extends AppCompatActivity {
    TextView quadraticDisplay;
    String a;
    String b;
    String c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quadratic_equation);

        // set default quadratic equation
        a = "a";
        b = "b";
        c = "c";

        quadraticDisplay = findViewById(R.id.quadratic_display);
        Spanned quadratic = Html.fromHtml(getString(R.string.quadratic_display, a, b, c));
        quadraticDisplay.setText(quadratic);

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
                a = s.toString();
                Spanned quadratic = Html.fromHtml(getString(R.string.quadratic_display, a, b, c));
                quadraticDisplay.setText(quadratic);

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
                b = s.toString();
                Spanned quadratic = Html.fromHtml(getString(R.string.quadratic_display, a, b, c));
                quadraticDisplay.setText(quadratic);
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
                c = s.toString();
                Spanned quadratic = Html.fromHtml(getString(R.string.quadratic_display, a, b, c));
                quadraticDisplay.setText(quadratic);
            }
        };

        // add TextWatchers to the EditTexts
        aInput.addTextChangedListener(aTextWatcher);
        bInput.addTextChangedListener(bTextWatcher);
        cInput.addTextChangedListener(cTextWatcher);

    }

    // send user to MathsEquations class onclick
    public void handleBackButton(View view) {
        startActivity(new Intent(QuadraticEquation.this, MathsEquations.class));
    }
}
