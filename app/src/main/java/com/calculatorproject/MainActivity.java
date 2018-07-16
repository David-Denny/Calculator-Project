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
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText calculatorDisplay;
    Boolean userIsTypingNumber = false;
    String DIGITS = "01234567890.";
    private Calculations mCalculations;

    DecimalFormat decimalFormat = new DecimalFormat("@###########");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_main);

        // Initialise toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        decimalFormat.setMinimumFractionDigits(0);
        decimalFormat.setMinimumIntegerDigits(1);
        decimalFormat.setMaximumIntegerDigits(8);


        // Make button variables
        findViewById(R.id.enter0).setOnClickListener(this);
        findViewById(R.id.enter1).setOnClickListener(this);
        findViewById(R.id.enter2).setOnClickListener(this);
        findViewById(R.id.enter3).setOnClickListener(this);
        findViewById(R.id.enter4).setOnClickListener(this);
        findViewById(R.id.enter5).setOnClickListener(this);
        findViewById(R.id.enter6).setOnClickListener(this);
        findViewById(R.id.enter7).setOnClickListener(this);
        findViewById(R.id.enter8).setOnClickListener(this);
        findViewById(R.id.enter9).setOnClickListener(this);
        findViewById(R.id.enterDECIMAL).setOnClickListener(this);
        findViewById(R.id.enterANS).setOnClickListener(this);
        findViewById(R.id.enterEQUALS).setOnClickListener(this);
        findViewById(R.id.enterADD).setOnClickListener(this);
        findViewById(R.id.enterMINUS).setOnClickListener(this);
        findViewById(R.id.enterMULT).setOnClickListener(this);
        findViewById(R.id.enterDIVIDE).setOnClickListener(this);
        findViewById(R.id.enterDEL).setOnClickListener(this);
        findViewById(R.id.enterAC).setOnClickListener(this);

        mCalculations = new Calculations();

        // initialise calculatorDisplay EditText
        calculatorDisplay = findViewById(R.id.output);

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

        String buttonPressed = ( (Button) view).getText().toString();
        Toast.makeText(this, buttonPressed,+ Toast.LENGTH_SHORT).show();

        if (DIGITS.contains(buttonPressed)) {

            // a digit is entered
            if (userIsTypingNumber) {

                if (buttonPressed.equals(".") && calculatorDisplay.getText().toString().contains(".")) {
                    // prevents multiple decimals
                } else {
                    calculatorDisplay.append(buttonPressed);
                }

            } else {

                if (buttonPressed.equals(".")) {
                    calculatorDisplay.setText(0 + buttonPressed);
                } else {
                    calculatorDisplay.setText(buttonPressed);
                }

                userIsTypingNumber = true;
            }
        } else {
            // operator has been entered

            if (userIsTypingNumber) {
                mCalculations.setOperand(Double.parseDouble(calculatorDisplay.getText().toString()));
                userIsTypingNumber = false;
            }

            mCalculations.performOperation(buttonPressed);
            calculatorDisplay.setText(decimalFormat.format(mCalculations.getResult()));
        }
    }
}