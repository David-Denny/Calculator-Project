package com.calculatorproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import io.github.kexanie.library.MathView;


public class SineRule extends AppCompatActivity {
    MathView sineDisplay;
    String aString;
    String bString;
    String aCapitalString;
    String bCapitalString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sine_rule);

        aString = "a";
        bString = "b";
        aCapitalString = "A";
        bCapitalString = "B";

        sineDisplay = findViewById(R.id.sine_display);

        updateDisplay();
    }

    public void updateDisplay() {
        String sineEquation = String.format(
                "$$\\color{white}{\\frac{%1$s}{sin%2$s} = \\frac{%3$s}{sin%4$s}}$$",
                aString, aCapitalString, bString, bCapitalString);
        sineDisplay.setText(sineEquation);
    }
    // send user back to Equations
    public void handleBackButton(View view) {
        startActivity(new Intent(SineRule.this, MathsEquations.class));
    }
}
