package com.calculatorproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class CosineRule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cosine_rule);
    }

    public void handleBackButton(View view) {

        startActivity(new Intent(CosineRule.this, MathsEquations.class));
    }
}
