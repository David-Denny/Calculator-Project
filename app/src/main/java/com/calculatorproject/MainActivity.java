package com.calculatorproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_main);

        // Initialise toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        // Make button variables
        // WARNING - no listener on Button enterBLANK - WARNING //
        Button enter0 = findViewById(R.id.enter0);
        enter0.setOnClickListener(this);
        Button enter1 = findViewById(R.id.enter1);
        enter1.setOnClickListener(this);
        Button enter2 = findViewById(R.id.enter2);
        enter2.setOnClickListener(this);
        Button enter3 = findViewById(R.id.enter3);
        enter3.setOnClickListener(this);
        Button enter4 = findViewById(R.id.enter4);
        enter4.setOnClickListener(this);
        Button enter5 = findViewById(R.id.enter5);
        enter5.setOnClickListener(this);
        Button enter6 = findViewById(R.id.enter6);
        enter6.setOnClickListener(this);
        Button enter7 = findViewById(R.id.enter7);
        enter7.setOnClickListener(this);
        Button enter8 = findViewById(R.id.enter8);
        enter8.setOnClickListener(this);
        Button enter9 = findViewById(R.id.enter9);
        enter9.setOnClickListener(this);
        Button enterDECIMAL = findViewById(R.id.enterDECIMAL);
        enterDECIMAL.setOnClickListener(this);
        Button enterANS = findViewById(R.id.enterANS);

        Button enterEQUALS = findViewById(R.id.enterEQUALS);

        Button enterADD = findViewById(R.id.enterADD);

        Button enterMINUS = findViewById(R.id.enterMINUS);

        Button enterMULT = findViewById(R.id.enterMULT);

        Button enterDIVIDE = findViewById(R.id.enterDIVIDE);

        Button enterDEL = findViewById(R.id.enterDEL);

        Button enterAC = findViewById(R.id.enterAC);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate toolbar_main xml file so buttons show up
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // TODO: send use to settings layout

                return true;

            default:
                // User's action is unrecognised, invoke superclass to handle it
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {

    }
}