package usw.app.martin.healthapp.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import usw.app.martin.healthapp.customComponents.ExpandableListAdapter;
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
    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise);

        exerciseDao = new ExcerciseDao(this);
        initializeVariables();

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
    }

    //method for wiring ui components with activity
    private void initializeVariables() {

        editTextMinutes = (EditText) findViewById(R.id.editTextMinutes);
        textViewDate = (TextView) findViewById(R.id.textViewDate2);
        exerciseSpinner = (Spinner) findViewById(R.id.spinnerExcercices);

        //set exercies spinner with values
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new String[]{"Jogging", "Stretching", "Elliptical trainer", "Circuit training - cross fit",
                "Bowling", "Zumba", "Ski machine", "Pushups", "Rope jumping"});
        exerciseSpinner.setAdapter(adapter);

        //set value change listener for excercise spinner
        exerciseSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        exerciseSpinnerPos = position;
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        //wire buttons
        btnSave = (Button) findViewById(R.id.btnSaveMeal);
        btnDate = (Button) findViewById(R.id.btnDate);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
                //check entered value
                if (editTextMinutes.getText().length() <= 0) {
                    Toast.makeText(ExcerciseActivity.this, "Error: Enter duration", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editTextMinutes.getText().toString().equals("0")){
                    Toast.makeText(ExcerciseActivity.this, "Error: Enter correct minutes", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (textViewDate.getText().toString().length() <= 0) {
                    Toast.makeText(ExcerciseActivity.this, "Error: Set date of excercise", Toast.LENGTH_SHORT).show();
                    return;
                }
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                Date enteredDate = null;
                try {
                    enteredDate = (format.parse(textViewDate.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (enteredDate.after(date)) {
                    Toast.makeText(ExcerciseActivity.this, "Error: Entered date is in the future", Toast.LENGTH_SHORT).show();
                    return;
                }

                Long minutes = new Long(editTextMinutes.getText().toString());
                Long calories = null;

                //set calories according to excercise
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

                //calculate calories
                calories *= minutes;

                //create new model of excercise and save it
                ExcerciseModel model = new ExcerciseModel();
                model.setName(exerciseSpinner.getSelectedItem().toString());
                model.setDuration(minutes);
                model.setCalories(calories);
                model.setExecuted(textViewDate.getText().toString());

                exerciseDao.insertExcercise(model);

                //redraw data in expandable list view
                prepareListData();
                listAdapter = new ExpandableListAdapter(getApplicationContext(), listDataHeader, listDataChild);
                // setting list adapter
                expListView.setAdapter(listAdapter);
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

                //create new dialog window
                DatePickerDialog dpd = new DatePickerDialog(ExcerciseActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                String tmp = (new Integer(dayOfMonth)).toString();
                                String resultString = "";
                                if (tmp.length() == 1){
                                    resultString += "0";
                                    resultString += tmp;
                                } else {
                                    resultString += tmp;
                                }
                                resultString += "/";
                                tmp = (new Integer(monthOfYear+1)).toString();
                                if (tmp.length() == 1){
                                    resultString += "0";
                                    resultString += tmp;
                                } else {
                                    resultString += tmp;
                                }
                                resultString += "/";
                                resultString += year;
                                textViewDate.setText(resultString);


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

        //start new activity
        if (intent != null) {
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    //method which set data from exercise to expanadable listview
    private void prepareListData() {
        HashMap<String, List<ExcerciseModel>> excercises = exerciseDao.getAllExcercises();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        //iterate over results
        for (Map.Entry<String, List<ExcerciseModel>> entry : excercises.entrySet()) {
            String key = entry.getKey();
            List<String> tmpList = new ArrayList<String>();
            List<ExcerciseModel> value = entry.getValue();
            for (ExcerciseModel model : value) {
                tmpList.add("" + model.getName() + " Minute(s): " + model.getDuration() + " Cal(s): " + model.getCalories());
            }
            listDataHeader.add(key);
            listDataChild.put(key, tmpList);
        }
    }
}
