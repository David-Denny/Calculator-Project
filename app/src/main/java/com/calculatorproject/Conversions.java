package com.calculatorproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class Conversions extends AppCompatActivity {

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.conversions);

        // initialise RecyclerView
        mRecyclerView = findViewById(R.id.conversions_recycler);
        ConversionsRAdapter adapter = new ConversionsRAdapter(this);

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void handleBackButton(View view) {

        // send user back to main activity
        startActivity(new Intent(Conversions.this, Calculator.class));
    }
}
