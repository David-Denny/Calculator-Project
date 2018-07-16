package com.calculatorproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String equation;
    private TextView mCalculatorDisplay;
    private ShuntingYard mShuntingYard;
    private String mAnswer;
    private String mInfix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc_main);

        // Initialise toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        Button enter0 = findViewById(R.id.enter0);

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
        findViewById(R.id.leftBracket).setOnClickListener(this);
        findViewById(R.id.rightBracket).setOnClickListener(this);

        mCalculatorDisplay = findViewById(R.id.calculatorDisplay);

        equation = "";

        mShuntingYard = new ShuntingYard();

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

        switch (view.getId()) {

            case R.id.enterDEL:
                equation = equation.substring(equation.length() -1 );
                break;

            case R.id.enterAC:
                equation = "";
                break;

            case R.id.enter0:
                equation = equation + "0 ";
                break;

            case R.id.enter1:
                equation = equation + "1 ";
                break;

            case R.id.enter2:
                equation = equation + "2 ";
                break;

            case R.id.enter3:
                equation = equation + "3 ";
                break;

            case R.id.enter4:
                equation = equation + "4 ";
                break;

            case R.id.enter5:
                equation = equation + "5 ";
                break;

            case R.id.enter6:
                equation = equation + "6 ";
                break;

            case R.id.enter7:
                equation = equation + "7 ";
                break;

            case R.id.enter8:
                equation = equation + "8 ";
                break;

            case R.id.enter9:
                equation = equation + "9 ";
                break;

            case R.id.leftBracket:
                equation = equation + "( ";
                break;

            case R.id.rightBracket:
                equation = equation + ") ";
                break;

            case R.id.enterADD:
                equation = equation + "+ ";
                break;

            case R.id.enterMULT:
                equation = equation + "× ";
                break;

            case R.id.enterDIVIDE:
                equation = equation + "÷ ";
                break;

            case R.id.enterMINUS:
                equation = equation + "- ";
                break;

            case R.id.enterEQUALS:
                // TODO: use ShuntingYard class to convert infix to postfix

                mInfix = ShuntingYard.infixToPostfix(equation);

        }

        mCalculatorDisplay.setText(equation);
    }


}