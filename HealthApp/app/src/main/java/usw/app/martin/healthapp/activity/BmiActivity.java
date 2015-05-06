package usw.app.martin.healthapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import usw.app.martin.healthapp.R;


public class BmiActivity extends ActionBarActivity {

    private SeekBar weightSeekBar, heightUpHeightSeekBar, heightDownHeightSeekBar, ageSeekBar;
    private Spinner heightSpinner, weightSpinner;
    private int posSpinner = 0, posSpinner2 = 0;
    private TextView ageTextView, weightTextView, heightUpperTextView, heightDownTextView, resultTextTextView, resultValueTextView, downTextTextView, heightBottomTextView, upper2TextView, textViewAgeValue, textViewWeightText;
    private int weightSeekValue, heightUpSeekValue, heightDownSeekValue, ageSeekValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        initializeVariables();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bmi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent = null;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_bmi) {
            intent = new Intent(BmiActivity.this, BmiActivity.class);
        } else if (id == R.id.action_meals) {
            intent = new Intent(BmiActivity.this, MealsActivity.class);
        } else if (id == R.id.action_help) {
            intent = new Intent(BmiActivity.this, HelpActivity.class);
        } else if (id == R.id.action_about) {
            intent = new Intent(BmiActivity.this, AboutActivity.class);
        } else if (id == R.id.action_overview) {
            intent = new Intent(BmiActivity.this, MainActivity.class);
        } else if (id == R.id.action_excercises) {
            intent = new Intent(BmiActivity.this, ExcerciseActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void calculateBmi(double age, double weight, double heightUp, double heightDown) {


        double weightResult = 0;
        double heightResult = 0;
        double bmiResult = 0;
        String bmiResultText = "";
        String color = "";

        if (posSpinner == 1) {
            weightResult = weight * 0.45359237;

        } else {
            weightResult = weight;
        }

        if (posSpinner2 == 0) {
            heightResult = heightUp / 100;
        } else {
            heightResult = heightUp * 0.3048;
            heightResult += heightDown * 0.0254;
        }

        if (Double.compare(heightResult, 0.0) != 0) {
            bmiResult = weight / Math.pow(heightResult, 2.0);
        }
        bmiResult -= 0.4246 * ageSeekValue;
        if (bmiResult < 0) {
            bmiResult = 0;
        }

        bmiResult = Math.round(bmiResult * 100.0) / 100.0;


        if (bmiResult < 15.0) {
            bmiResultText = "Very severely underweight";
            color = "#FF0000";
        } else if (bmiResult >= 15.0 && bmiResult < 16.0) {
            bmiResultText = "severely underweight";
            color = "#FF0000";
        } else if (bmiResult >= 16.0 && bmiResult < 18.5) {
            bmiResultText = "underweight";
            color = "#FF9A00";
        } else if (bmiResult >= 18.5 && bmiResult < 25) {
            bmiResultText = "Normal";
            color = "#1DCC1D";
        } else if (bmiResult >= 25 && bmiResult < 30) {
            bmiResultText = "OverWeight";
            color = "#FF9A00";
        } else if (bmiResult >= 30 && bmiResult < 35) {
            bmiResultText = "Obese Class I";
            color = "#FF9A00";
        } else if (bmiResult >= 35 && bmiResult < 40) {
            bmiResultText = "Obese Class II";
            color = "#FF0000";
        } else {
            bmiResultText = "Obese Class III";
            color = "#FF0000";
        }

        resultValueTextView.setText(Double.toString(bmiResult));
        resultTextTextView.setText(bmiResultText);
        resultTextTextView.setTextColor(Color.parseColor(color));

    }

    private void initializeVariables() {

        //wire textviews
        ageTextView = (TextView) findViewById(R.id.textViewAge);
        weightTextView = (TextView) findViewById(R.id.textViewWeight);
        heightUpperTextView = (TextView) findViewById(R.id.textViewHeightUpper);
        heightDownTextView = (TextView) findViewById(R.id.textViewHeightDown);
        resultValueTextView = (TextView) findViewById(R.id.textViewResultValue);
        resultTextTextView = (TextView) findViewById(R.id.textView13);
        downTextTextView = (TextView) findViewById(R.id.textView8);
        heightBottomTextView = (TextView) findViewById(R.id.textViewHeightBottom);
        upper2TextView = (TextView) findViewById(R.id.textViewHeightUpper2);
        textViewAgeValue = (TextView) findViewById(R.id.textViewAgeValue);
        textViewWeightText = (TextView) findViewById(R.id.textViewWeightText);

        heightDownTextView.setVisibility(View.GONE);

        //wire spinners
        weightSpinner = (Spinner) findViewById(R.id.spinner);
        heightSpinner = (Spinner) findViewById(R.id.spinner2);

        //wire seek bars
        ageSeekBar = (SeekBar) findViewById(R.id.seekBarAge);
        weightSeekBar = (SeekBar) findViewById(R.id.seekBarWeight);
        heightUpHeightSeekBar = (SeekBar) findViewById(R.id.seekBarHeightUp);
        heightDownHeightSeekBar = (SeekBar) findViewById(R.id.seekBarHeightBottom);
        heightDownHeightSeekBar.setVisibility(View.GONE);
        downTextTextView.setVisibility(View.GONE);
        heightDownTextView.setVisibility(View.GONE);
        heightBottomTextView.setVisibility(View.GONE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new String[]{"kg", "pounds"});
        weightSpinner.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new String[]{"cm", "feets, inches"});
        heightSpinner.setAdapter(adapter2);


        weightSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        posSpinner = position;
                        calculateBmi(ageSeekValue, weightSeekValue, heightUpSeekValue, heightDownSeekValue);

                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        heightSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        posSpinner2 = position;
                        if (posSpinner2 == 0) {
                            heightDownHeightSeekBar.setVisibility(View.GONE);
                            downTextTextView.setVisibility(View.GONE);
                            heightDownTextView.setVisibility(View.GONE);
                            heightBottomTextView.setVisibility(View.GONE);
                            upper2TextView.setText("Centimeters");
                        } else {
                            heightDownHeightSeekBar.setVisibility(View.VISIBLE);
                            downTextTextView.setVisibility(View.VISIBLE);
                            heightDownTextView.setVisibility(View.VISIBLE);
                            heightBottomTextView.setVisibility(View.VISIBLE);
                            upper2TextView.setText("Feets");
                        }
                        calculateBmi(ageSeekValue, weightSeekValue, heightUpSeekValue, heightDownSeekValue);
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        ageSeekBar.setOnSeekBarChangeListener(AgeBar);
        weightSeekBar.setOnSeekBarChangeListener(WeightBar);
        heightUpHeightSeekBar.setOnSeekBarChangeListener(HeightUpBar);
        heightDownHeightSeekBar.setOnSeekBarChangeListener(HeightDownBar);


    }

    SeekBar.OnSeekBarChangeListener AgeBar = new SeekBar.OnSeekBarChangeListener() {
        int progress = 0;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
            ageSeekValue = progresValue;
            textViewAgeValue.setText(Double.toString(ageSeekValue));
            calculateBmi(ageSeekValue, weightSeekValue, heightUpSeekValue, heightDownSeekValue);

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

    };

    SeekBar.OnSeekBarChangeListener WeightBar = new SeekBar.OnSeekBarChangeListener() {
        int progress = 0;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
            weightSeekValue = progresValue;
            textViewWeightText.setText(Double.toString(weightSeekValue));
            calculateBmi(ageSeekValue, weightSeekValue, heightUpSeekValue, heightDownSeekValue);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

    };

    SeekBar.OnSeekBarChangeListener HeightUpBar = new SeekBar.OnSeekBarChangeListener() {
        int progress = 0;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
            heightUpSeekValue = progresValue;
            heightUpperTextView.setText(Double.toString(heightUpSeekValue));
            calculateBmi(ageSeekValue, weightSeekValue, heightUpSeekValue, heightDownSeekValue);
        }


        @Override

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    SeekBar.OnSeekBarChangeListener HeightDownBar = new SeekBar.OnSeekBarChangeListener() {

        int progress = 0;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
            heightDownSeekValue = progresValue;
            heightDownTextView.setText(Double.toString(heightDownSeekValue));
            calculateBmi(ageSeekValue, weightSeekValue, heightUpSeekValue, heightDownSeekValue);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

    };


}
