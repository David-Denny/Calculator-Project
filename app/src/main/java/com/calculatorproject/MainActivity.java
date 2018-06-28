package com.calculatorproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText output;
    String equation;

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
        enterANS.setOnClickListener(this);
        Button enterEQUALS = findViewById(R.id.enterEQUALS);
        enterEQUALS.setOnClickListener(this);
        Button enterADD = findViewById(R.id.enterADD);
        enterADD.setOnClickListener(this);
        Button enterMINUS = findViewById(R.id.enterMINUS);
        enterMINUS.setOnClickListener(this);
        Button enterMULT = findViewById(R.id.enterMULT);
        enterMULT.setOnClickListener(this);
        Button enterDIVIDE = findViewById(R.id.enterDIVIDE);
        enterDIVIDE.setOnClickListener(this);
        Button enterDEL = findViewById(R.id.enterDEL);
        enterDEL.setOnClickListener(this);
        Button enterAC = findViewById(R.id.enterAC);
        enterAC.setOnClickListener(this);

        // initialise output EditText
        output = findViewById(R.id.output);

        // TODO: set up SharedPreferences for all values that need to be saved, e.g. equation, ANS, etc.
        equation = "";

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

    /** Finds button by ID and will perform a different function depending on the ID.*/

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.enter0:

                equation = equation +  "0";
                break;

            case R.id.enter1:

                equation = equation + "1";
                break;

            case R.id.enter2:

                equation = equation + "2";
                break;

            case R.id.enter3:

                equation = equation + "3";
                break;

            case R.id.enter4:

                equation = equation + "4";
                break;

            case R.id.enter5:

                equation = equation + "5";
                break;

            case R.id.enter6:

                equation = equation + "6";
                break;

            case R.id.enter7:

                equation = equation + "7";
                break;

            case R.id.enter8:

                equation = equation + "8";
                break;

            case R.id.enter9:

                equation = equation + "9";
                break;

            case R.id.enterDEL:

                equation = equation.substring(0, equation.length() - 1);
        }

        output.setText(equation);
    }
}