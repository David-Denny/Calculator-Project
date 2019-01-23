package com.calculatorproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    private SharedPreferences mPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings);

        mPrefs = this.getSharedPreferences("calculator", Context.MODE_PRIVATE);

        NumberPicker decimalPicker = findViewById(R.id.decimal_picker);

        // add values to the NumberPicker
        final String[] pickerValues = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        decimalPicker.setMinValue(0);
        decimalPicker.setMaxValue(pickerValues.length - 1);
        decimalPicker.setDisplayedValues(pickerValues);
        decimalPicker.setWrapSelectorWheel(true);

        decimalPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                decimalPicker.setValue(newVal);

                // update preferences with new value
                mPrefs.edit()
                        .putInt("decimalPoints", newVal)
                        .apply();
            }
        });

        SwitchCompat orientationSwitch = findViewById(R.id.orientation_switch);
        TextView orientationLabel = findViewById(R.id.orientation_label);

        // gets portrait setting
        Boolean isPortrait = mPrefs.getBoolean("isPortrait", true);

        // changes the label to portrait or landscape depending on isPortrait variable
        orientationLabel
                // uses a ternary operator to set the text to either of two values depending on the
                // value of the boolean conditional. In this case, true corresponds to portrait
                // and false to landscape.
                .setText(isPortrait ? getString(R.string.portrait) : getString(R.string.landscape));

        // sets switch to corresponding state
        orientationSwitch.setChecked(isPortrait);

        orientationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    // alters label
                    orientationLabel.setText(getString(R.string.portrait));

                    // saves user input in SharedPreferences
                    mPrefs.edit()
                            .putBoolean("isPortrait", true)
                            .apply();

                    // changes orientation
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {

                    // alters label
                    orientationLabel.setText(getString(R.string.landscape));

                    // saves user input in SharedPreferences
                    mPrefs.edit()
                            .putBoolean("isPortrait", false)
                            .apply();

                    // changes orientation
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        });

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


    public void onClickBackButton(View view) {

        // send user back to main calculator activity
        startActivity(new Intent(Settings.this, Calculator.class));
    }
}
