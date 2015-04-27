package usw.app.martin.healthapp.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import usw.app.martin.healthapp.R;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import usw.app.martin.healthapp.dao.ExcerciseDao;
import usw.app.martin.healthapp.model.ExcerciseModel;

public class ExcerciseActivity extends ActionBarActivity {

    private Spinner exerciseSpinner;
    private Button btnSave, btnDate;
    private ExcerciseDao exerciseDao;
    private int exerciseSpinnerPos;
    private int mYear, mMonth, mDay;
    private TextView textViewDate;
    private EditText editTextMinutes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise);

        exerciseDao = new ExcerciseDao(this);
        initializeVariables();


    }

    private void initializeVariables() {

        editTextMinutes = (EditText) findViewById(R.id.editTextMinutes);

        textViewDate = (TextView) findViewById(R.id.textViewDate2);
        exerciseSpinner = (Spinner) findViewById(R.id.spinnerExcercices);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new String[]{"Jogging", "Stretching", "Elliptical trainer", "Circuit training - cross fit",
                "Bowling", "Zumba", "Ski machine", "Pushups", "Rope jumping"});
        exerciseSpinner.setAdapter(adapter);


        exerciseSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        exerciseSpinnerPos = position;
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


        btnSave = (Button) findViewById(R.id.btnSaveMeal);
        btnDate = (Button) findViewById(R.id.btnDate);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
                if (editTextMinutes.getText().length() <= 0) {
                    Toast.makeText(ExcerciseActivity.this, "Error: Enter duration", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (textViewDate.getText().toString().length() <= 0) {
                    Toast.makeText(ExcerciseActivity.this, "Error: Set date of excercise", Toast.LENGTH_SHORT).show();
                    return;
                }
                Long minutes = new Long(editTextMinutes.getText().toString());
                Long calories = null;

                if (exerciseSpinnerPos == 0) {
                    calories = 4l;

                } else if (exerciseSpinnerPos == 1) {
                    calories = 2l;
                } else if (exerciseSpinnerPos == 2) {
                    calories = 7l;
                } else if (exerciseSpinnerPos == 3) {
                    calories = 5l;
                } else if (exerciseSpinnerPos == 4) {
                    calories = 7l;
                } else if (exerciseSpinnerPos == 5) {
                    calories = 5l;
                } else if (exerciseSpinnerPos == 6) {
                    calories = 23l;
                } else if (exerciseSpinnerPos == 7) {
                    calories = 3l;
                } else if (exerciseSpinnerPos == 8) {
                    calories = 6l;
                }

                calories *= minutes;

                ExcerciseModel model = new ExcerciseModel();
                model.setName(exerciseSpinner.getSelectedItem().toString());
                model.setDuration(minutes);
                model.setCalories(calories);
                model.setExecuted(textViewDate.getText().toString());
                exerciseDao.insertExcercise(model);
                Toast.makeText(ExcerciseActivity.this, "You have burnt " + calories.toString() + " calories", Toast.LENGTH_LONG).show();
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(ExcerciseActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                textViewDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);


                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_excercise, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;

        if (id == R.id.action_bmi) {
            intent = new Intent(ExcerciseActivity.this, BmiActivity.class);
        } else if (id == R.id.action_meals) {
            intent = new Intent(ExcerciseActivity.this, MealsActivity.class);
        } else if (id == R.id.action_help) {
            intent = new Intent(ExcerciseActivity.this, HelpActivity.class);
        } else if (id == R.id.action_about) {
            intent = new Intent(ExcerciseActivity.this, AboutActivity.class);
        } else if (id == R.id.action_overview) {
            intent = new Intent(ExcerciseActivity.this, MainActivity.class);
        } else if (id == R.id.action_excercises) {
            intent = new Intent(ExcerciseActivity.this, ExcerciseActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
