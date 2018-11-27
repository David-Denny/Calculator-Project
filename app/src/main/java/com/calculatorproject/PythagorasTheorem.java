package com.calculatorproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class PythagorasTheorem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pythagoras_theorem);
    }

    // send user back to MathsEquation
    public void handleBackButton(View view) {

        startActivity(new Intent(PythagorasTheorem.this, MathsEquations.class));
    }
}
