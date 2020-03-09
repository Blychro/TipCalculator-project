package com.example.tipcalculator_counter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private Button reset;
    private Button calc;
    private RadioButton fifteen;
    private RadioButton ten;
    private RadioButton twenty;
    private RadioButton custom;
    private EditText amount;
    private EditText count;
    private TextView total;
    private EditText cNum;

    /*@param errorMessage
    String the error message to show
    @param fieldId
    the Id of the field which caused the error.
    This is required so that the focus can be
    set on that field once the dialog is
    dismissed.*/

    private void showErrorAlert(String errorMessage, final int fieldId) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(errorMessage)
                .setNeutralButton("Close",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                findViewById(fieldId).requestFocus();
                            }
                        }).show();
    }

    private View.OnKeyListener mKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            switch (v.getId()) {
                case R.id.editText:
                case R.id.editText2:
                case R.id.editText3:
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calc = findViewById(R.id.button);
        reset = findViewById(R.id.button2);
        fifteen = findViewById(R.id.radioButton2);
        ten = findViewById(R.id.radioButton);
        twenty = findViewById(R.id.radioButton3);
        custom = findViewById(R.id.radioButton4);
        amount = findViewById(R.id.editText);
        count = findViewById(R.id.editText2);
        total = findViewById(R.id.textView);
        cNum = findViewById(R.id.editText3);
        cNum.setEnabled(false);
        calc.setEnabled(false);

        total.setText("Awaiting Input");

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText("");
                count.setText("");
                cNum.setEnabled(false);
                cNum.setText("");
                ten.setChecked(false);
                twenty.setChecked(false);
                fifteen.setChecked(false);
                custom.setChecked(false);
                total.setText("Awaiting Input");
                calc.setEnabled(false);
            }
        });

        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fifteen.setChecked(false);
                twenty.setChecked(false);
                custom.setChecked(false);
                cNum.setEnabled(false);

                if (mKeyListener.onKey(v, 1, null))
                    calc.setEnabled(true);
            }
        });

        fifteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ten.setChecked(false);
                twenty.setChecked(false);
                custom.setChecked(false);
                cNum.setEnabled(false);

                if (mKeyListener.onKey(v, 1, null))
                    calc.setEnabled(true);
            }
        });

        twenty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fifteen.setChecked(false);
                ten.setChecked(false);
                custom.setChecked(false);
                cNum.setEnabled(false);

                if (mKeyListener.onKey(v, 1, null))
                    calc.setEnabled(true);
            }
        });

        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fifteen.setChecked(false);
                twenty.setChecked(false);
                ten.setChecked(false);
                cNum.setEnabled(true);
            }
        });

        cNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mKeyListener.onKey(v, 1, null))
                    calc.setEnabled(true);
            }
        });

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Double num = Double.parseDouble(amount.getText().toString());
                int people = Integer.parseInt(count.getText().toString());
                Double tip;

                if (num < 1.0)
                    showErrorAlert("The amount given was less than $1.00", 1);
                if (people < 1)
                    showErrorAlert("The number of people provided was less than 1", 1);

                if (ten.isChecked()) {
                    tip = 0.10;
                    num *= 1.1;
                }
                else if (fifteen.isChecked()) {
                    tip = 0.15;
                    num *= 1.15;
                }
                else if (twenty.isChecked()) {
                    tip = 0.20;
                    num *= 1.2;
                }
                else {
                    if (cNum.getText().toString() == null)
                        showErrorAlert("No tip amount was provided", 1);

                    tip = Double.parseDouble(cNum.getText().toString());

                    if (tip < .01)
                        showErrorAlert("The tip inputted was less than 1%", 1);

                    num += num * tip;
                }
                Double end = num / people;

                DecimalFormat percentageFormat = new DecimalFormat("00.00");
                String percent = percentageFormat.format(num);
                String endS = percentageFormat.format(end);
                total.setText("The tip was " + (tip * 100) +
                        "%\nThe total is $" + percent +
                        "\nThe total per person is $" + endS);
            }
        });

    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);


        String value = total.getText().toString();
        outState.putString("key", value);
    }

    @Override
    protected void onRestoreInstanceState (@NonNull Bundle savedInstanceSate) {
        super.onRestoreInstanceState(savedInstanceSate);

        String value = savedInstanceSate.getString("key");
        total.setText(value);
    }
}
