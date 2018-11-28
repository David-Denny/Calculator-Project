package com.calculatorproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Equations extends AppCompatActivity {
    Toolbar mToolbar;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maths_equations);

        // initialise toolbar
        mToolbar = findViewById(R.id.quadratic_equation_toolbar);
        setSupportActionBar(mToolbar);

        // initialise RecyclerView
        mRecyclerView = findViewById(R.id.maths_equation_recycler);
        EquationsRAdapter adapter = new EquationsRAdapter(this);

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void handleBackButton(View view) {

        startActivity(new Intent(Equations.this, Calculator.class));
    }
}