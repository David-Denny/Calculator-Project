package com.calculatorproject;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AreaTriangle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.area_triangle);


    }

    // send user back to Equations
    public void handleBackButton(View view) {
        startActivity(new Intent(AreaTriangle.this, Equations.class));
    }
}
