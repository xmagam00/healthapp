package usw.app.martin.healthapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class BmiActivity extends ActionBarActivity {

    private SeekBar weightSeekBar, heightUpHeightSeekBar, heightDownHeightSeekBar, ageSeekBar;
    private Spinner heightSpinner, weightSpinner;
    private int posSpinner = 0, posSpinner2 = 0;
    private TextView ageTextView, weightTextView, heightUpperTextView, heightDownTextView, resultTextTextView, resultValueTextView;

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
        } else if (id == R.id.action_meals){
            intent = new Intent(BmiActivity.this, MealsActivity.class);
        } else if (id == R.id.action_progress){
            intent = new Intent(BmiActivity.this, ProgressActivity.class);
        } else if (id == R.id.action_help){
            intent = new Intent(BmiActivity.this, HelpActivity.class);
        } else if (id == R.id.action_about){
            intent = new Intent(BmiActivity.this, AboutActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void calculateBmi(double age, double weight, double height){

    }

    private void initializeVariables(){

        //wire textviews
        ageTextView = (TextView)findViewById(R.id.textViewAge);
        weightTextView = (TextView)findViewById(R.id.textViewWeight);
        heightUpperTextView = (TextView) findViewById(R.id.textViewHeightUpper);
        heightDownTextView = (TextView) findViewById(R.id.textViewHeightDown);
        resultValueTextView = (TextView)findViewById(R.id.textViewResultValue);
        resultTextTextView = (TextView)findViewById(R.id.textViewResultText);

        //wire spinners
        weightSpinner = (Spinner)findViewById(R.id.spinner);
        heightSpinner = (Spinner)findViewById(R.id.spinner2);

        //wire seek bars
        ageSeekBar = (SeekBar)findViewById(R.id.seekBarAge);
        weightSeekBar = (SeekBar)findViewById(R.id.seekBarWeight);
        heightUpHeightSeekBar = (SeekBar)findViewById(R.id.seekBarHeightUp);
        heightDownHeightSeekBar = (SeekBar)findViewById(R.id.seekBarHeightBottom);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new String[]{"kg","pounds"});
        weightSpinner.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new String[]{"cm","feets, inches"});
        heightSpinner.setAdapter(adapter);


        weightSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        posSpinner = position;
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        heightSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        posSpinner2 = position;
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        ageSeekBar.setOnSeekBarChangeListener(BatteryBar);





    }

    SeekBar.OnSeekBarChangeListener  BatteryBar = new SeekBar.OnSeekBarChangeListener() {

        int progress = 0;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {

            progress = progresValue;
            Log.v("Test", (new Integer(progress).toString()));

            Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();

        }


        @Override

        public void onStartTrackingTouch(SeekBar seekBar) {

            Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();

        }

        @Override

        public void onStopTrackingTouch(SeekBar seekBar) {

            //textView.setText("Covered: " + progress + "/" + seekBar.getMax());

            Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();

        }

    };




}
