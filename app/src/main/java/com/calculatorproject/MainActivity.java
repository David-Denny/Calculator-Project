package com.calculatorproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.EmptyStackException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String expression;
    private TextView mCalculatorDisplay;
    private TextView mOutputDisplay;
    private ShuntingYard mShuntingYard;
    // using BigDecimal rather than long or double as it will return exact results and prevent
    // floating point arithmetic rounding errors even though it's slower. Precision is of utmost
    // importance as the calculator could be used for financial calculations or similar.
    private double mAnswer;
    private String mPostfix;
    private Boolean mNumberIsBeingWritten = true;
    private int mErrorCode;

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
        findViewById(R.id.XYpower).setOnClickListener(this);

        mCalculatorDisplay = findViewById(R.id.calculatorDisplay);
        mCalculatorDisplay.setTextSize(getResources().getDimension(R.dimen.regular));
        mOutputDisplay = findViewById(R.id.outputDisplay);
        mOutputDisplay.setTextSize(getResources().getDimension(R.dimen.regular));

        Button XYPowerButton = findViewById(R.id.XYpower);
        XYPowerButton.setText(Html.fromHtml("x<sup><small> y</small></sup>"));

        expression = "";

        mShuntingYard = new ShuntingYard();

        // error code -1 means
        mErrorCode = -1;

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

                if (expression.length() > 0) {
                    expression = expression.substring(0, expression.length() - 1);
                    mNumberIsBeingWritten = false;
                }
                break;

            case R.id.enterAC:
                expression = "";
                mOutputDisplay.setText("");
                break;

            case R.id.enter0:
                expression = expression + "0 ";
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter1:
                expression = expression + "1 ";
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter2:
                expression = expression + "2 ";
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter3:
                expression = expression + "3 ";
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter4:
                expression = expression + "4 ";
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter5:
                expression = expression + "5 ";
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter6:
                expression = expression + "6 ";
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter7:
                expression = expression + "7 ";
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter8:
                expression = expression + "8 ";
                mNumberIsBeingWritten = true;
                break;

            case R.id.enter9:
                expression = expression + "9 ";
                mNumberIsBeingWritten = true;
                break;

            case R.id.enterDECIMAL:
                expression = expression + ".";
                mNumberIsBeingWritten = false;
                break;

            case R.id.leftBracket:
                expression = expression + " ( ";
                mNumberIsBeingWritten = false;
                break;

            case R.id.rightBracket:
                expression = expression + " ) ";
                mNumberIsBeingWritten = false;
                break;

            case R.id.enterADD:
                expression = expression + " + ";
                mNumberIsBeingWritten = false;
                break;

            case R.id.enterMULT:
                expression = expression + " × ";
                mNumberIsBeingWritten = false;
                break;

            case R.id.enterDIVIDE:
                expression = expression + " ÷ ";
                mNumberIsBeingWritten = false;
                break;

            case R.id.enterMINUS:
                expression = expression + " - ";
                mNumberIsBeingWritten = false;
                break;

            case R.id.XYpower:
                expression = expression + " ^ ";
                mNumberIsBeingWritten = false;
                break;

                //TODO: add further operations, e.g. square

            case R.id.enterEQUALS:

                if (expression.length() > 0) {

                    mPostfix = ShuntingYard.infixToPostfix(expression);

                    try {
                        mAnswer = ShuntingYard.evaluateRPN(mPostfix);
                        mOutputDisplay.setText("= " + String.valueOf(mAnswer));
                    } catch (EmptyStackException e) {
                        e.printStackTrace();
                        // error code in the 100s means the error is from the expression being formed
                        // wrong
                        mErrorCode = 100;
                    }
                }


        }

        if(mNumberIsBeingWritten) {
            // removes trailing whitespace if a number is written to allow multi-digit numbers
            expression = expression.replaceFirst("\\s++$", "");
        }

        if (mErrorCode == -1) {
            mCalculatorDisplay.setText(expression);
        } else if (mErrorCode == 100){
            Toast.makeText(this, "ERROR: expression is malformed",
                    Toast.LENGTH_SHORT).show();

            mCalculatorDisplay.setTextSize(getResources().getDimension(R.dimen.error));
            mCalculatorDisplay.setText("ERROR: expression is malformed. Click AC to continue.");

            mErrorCode = -1;
            mCalculatorDisplay.setTextSize(getResources().getDimension(R.dimen.regular));
        }


    }


}