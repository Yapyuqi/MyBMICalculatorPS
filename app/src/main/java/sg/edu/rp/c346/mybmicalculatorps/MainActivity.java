package sg.edu.rp.c346.mybmicalculatorps;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etWeight;
    EditText etHeight;
    Button btnCalculation;
    Button btnReset;
    TextView tvdate;
    TextView tvbmi;
    TextView tvenhance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etHeight = findViewById(R.id.editTextHeight);
        etWeight = findViewById(R.id.editTextWeight);
        btnCalculation = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvdate = findViewById(R.id.textViewDate);
        tvbmi = findViewById(R.id.textViewBMI);
        tvenhance = findViewById(R.id.textViewEnhancement);

        final Calendar now = Calendar.getInstance();
        final String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);

        btnCalculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float fltWeight = Float.parseFloat(etWeight.getText().toString());
                float fltHeight = Float.parseFloat(etHeight.getText().toString());
                float calcBMI = fltWeight/(fltHeight*fltHeight);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.putFloat("weight", fltWeight);
                prefEdit.putFloat("height", fltHeight);
                prefEdit.putString("date", datetime);
                prefEdit.putFloat("bmi", calcBMI);
                prefEdit.commit();

                etWeight.setText(null);
                etHeight.setText(null);
                tvdate.setText("Last Calculated Date:" + datetime);
                tvbmi.setText("Last Calculated BMI:" + calcBMI);

                if (calcBMI >= 25 && calcBMI <= 29.9) {
                    tvenhance.setText("You are overweight");
                } else if (calcBMI >= 18.5 && calcBMI <= 24.9) {
                    tvenhance.setText("Your BMI is normal");
                } else if (calcBMI < 18.5) {
                    tvenhance.setText("You are underweight");
                } else {
                    tvenhance.setText("You are obese");
                }

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWeight.setText(null);
                etHeight.setText(null);
                tvdate.setText("Last Calculated Date:" );
                tvbmi.setText("Last Calculated BMI:" );
                tvenhance.setText(null);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String displayWeight = String.valueOf(prefs.getFloat("weight", 0));
        String displayHeight = String.valueOf(prefs.getFloat("height", 0));
        etWeight.setText(null);
        etHeight.setText(null);

    }

    @Override
    protected void onPause() {
        super.onPause();

        Float weight = Float.parseFloat(etWeight.getText().toString());
        Float height = Float.parseFloat(etHeight.getText().toString());
        Float bmi = Float.parseFloat(tvbmi.getText().toString());
        String datetime = tvdate.getText().toString();


        //Step 1a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        //Step 1b: Obtain an instance of the SharedPreference Editor for update later
        SharedPreferences.Editor prefEdit = prefs.edit();

        //Step 1c: Add the key-value pair
        prefEdit.putFloat("weight", weight);
        prefEdit.putFloat("height", height);
        prefEdit.putString("date", datetime);
        prefEdit.putFloat("bmi", bmi);

        //Step 1d: Call commit() method to save the changes into the Shared Preferences
        prefEdit.commit();
    }
}

