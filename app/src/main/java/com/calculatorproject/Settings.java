package com.calculatorproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.NumberPicker;

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

        // todo: add switch functionality


    }



    public void onClickBackButton(View view) {

        // send user back to main calculator activity
        startActivity(new Intent(Settings.this, Calculator.class));
    }
}
