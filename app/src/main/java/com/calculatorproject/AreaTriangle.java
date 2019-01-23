package com.calculatorproject;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.github.kexanie.library.MathView;

public class AreaTriangle extends AppCompatActivity {

    MathView areaTriangleDisplay;
    String aString;
    String bString;
    String cAngleString;
    EditText aInput;
    EditText bInput;
    EditText cAngleInput;
    TextView areaTriangleAnswer;
    TextView areaTriangleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.area_triangle);

        // initialise variables
        aString = "a";
        bString = "b";
        cAngleString = "C";

        // get MathView display
        areaTriangleDisplay = findViewById(R.id.area_triangle_display);

        // update display
        updateDisplay();

        // initialise EditTexts
        aInput = findViewById(R.id.area_triangle_a_input);
        bInput = findViewById(R.id.area_triangle_b_input);
        cAngleInput = findViewById(R.id.area_triangle_c_angle_input);

        // initialise output TextViews
        areaTriangleText = findViewById(R.id.area_triangle_output_text);
        areaTriangleAnswer = findViewById(R.id.area_triangle_output_answer);

        // set TextWatchers to update display after user input
        TextWatcher aTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // get input
                aString = s.toString();

                // display new input to user
                updateDisplay();
            }
        };

        TextWatcher bTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // get input
                bString = s.toString();

                // display new input to user
                updateDisplay();
            }
        };

        TextWatcher cAngleTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // get input
                cAngleString = s.toString();

                // display new input to user
                updateDisplay();
            }
        };

        // add TextWatchers
        aInput.addTextChangedListener(aTextWatcher);
        bInput.addTextChangedListener(bTextWatcher);
        cAngleInput.addTextChangedListener(cAngleTextWatcher);

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

    public void updateDisplay() {

        // create new TeX code string
        String areaTriangle = String.format(
                "$$\\color{white}{\\frac{1}{2} \\times %1$s \\times %2$s \\times sin(%3$s)}$$",
                aString, bString, cAngleString);

        // render new TeX equation
        areaTriangleDisplay.setText(areaTriangle);
    }

    public void calculateAreaOfATriangle(View view) {

        // hide keyboard on button click
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputManager != null;
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        // prevent crashes from NumberFormatException (when user hasn't entered required input)
        try {

            double a = Double.parseDouble(aString);
            double b = Double.parseDouble(bString);
            double cAngle = Double.parseDouble(cAngleString);
            cAngle = Math.toRadians(cAngle);

            double area = 0.5 * a * b * Math.sin(cAngle);


            areaTriangleText.setText(R.string.area_triangle_text);
            areaTriangleAnswer.setText(String.valueOf(area));

        } catch (NumberFormatException e) {

            Toast.makeText(this,
                    "ERROR: input all required values", Toast.LENGTH_SHORT).show();
        }
    }

    // send user back to Equations
    public void handleBackButton(View view) {
        startActivity(new Intent(AreaTriangle.this, Equations.class));
    }
}
