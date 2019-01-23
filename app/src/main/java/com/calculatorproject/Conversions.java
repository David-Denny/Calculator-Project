package com.calculatorproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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

        // gets SharedPreferences
        SharedPreferences mPrefs = this.getSharedPreferences("calculator", Context.MODE_PRIVATE);

        // orientation is locked depending on user's choice. Defaults to portrait if no choice is
        // made in Settings class (as true corresponds to portrait)
        if (mPrefs.getBoolean("isPortrait", true)) {

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    public void handleBackButton(View view) {

        // send user back to main activity
        startActivity(new Intent(Conversions.this, Calculator.class));
    }
}
