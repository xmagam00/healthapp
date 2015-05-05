package usw.app.martin.healthapp.activity;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import usw.app.martin.healthapp.R;
import usw.app.martin.healthapp.customComponents.DatePickerFragment;
import usw.app.martin.healthapp.customComponents.ExpandableListAdapter;
import usw.app.martin.healthapp.dao.MealDao;
import usw.app.martin.healthapp.model.MealModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MealsActivity extends ActionBarActivity {

    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private Spinner mealsSpinner;
    private int mealsSpinnerPos;
    private int mYear, mMonth, mDay;
    private MealDao mealDao;
    private EditText portions;
    private Button btnSave,btnSetDate;
    private TextView textViewDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        mealDao = new MealDao(this);

        initializeVariables();


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp2);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.setBackgroundColor(Color.BLUE);
        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

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

    private void initializeVariables(){

        mealsSpinner = (Spinner)findViewById(R.id.spinnerMeals);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new String[]{"Rice", "Pasta", "Meat", "Fish",
                "Chips", "Popcorn", "Hard cheese", "Bread", "Apple", "Cucumber"});
        mealsSpinner.setAdapter(adapter);

        mealsSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        mealsSpinnerPos = position;
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


        portions = (EditText)findViewById(R.id.editTextPortions);
        textViewDate = (TextView)findViewById(R.id.textViewDate2);

        btnSave = (Button)findViewById(R.id.btnSaveMeal);
        btnSetDate = (Button)findViewById(R.id.btnSaveDateMeal);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w){
                if (portions.getText().length() <= 0) {
                    Toast.makeText(MealsActivity.this, "Error: Enter portion", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (textViewDate.getText().toString().length() <= 0){
                    Toast.makeText(MealsActivity.this, "Error: Set date of eating", Toast.LENGTH_SHORT).show();
                    return;
                }
                Date date = new Date();
                Date enteredDate = new Date(textViewDate.getText().toString());

                if (enteredDate.after(date)){
                    Toast.makeText(MealsActivity.this, "Error: Entered date is in the future", Toast.LENGTH_SHORT).show();
                    return;
                }

                Long portionsVal = new Long(portions.getText().toString());
                Long calories = null;

                if (mealsSpinnerPos == 0) {
                    calories = 200l;

                } else if (mealsSpinnerPos == 1){
                    calories = 200l;
                } else if (mealsSpinnerPos == 2){
                    calories = 160l;
                } else if (mealsSpinnerPos == 3){
                    calories = 160l;
                } else if (mealsSpinnerPos == 4){
                    calories = 150l;
                } else if (mealsSpinnerPos == 5){
                    calories = 120l;
                } else if (mealsSpinnerPos == 6){
                    calories = 100l;
                } else if (mealsSpinnerPos == 7){
                    calories = 100l;
                } else if (mealsSpinnerPos == 8){
                    calories = 100l;
                } else if (mealsSpinnerPos == 9){
                    calories = 30l;
                }

                calories *= portionsVal;

                MealModel model = new MealModel();
                model.setName(mealsSpinner.getSelectedItem().toString());
                model.setPortions(portionsVal);
                model.setCalories(calories);
                model.setEaten(textViewDate.getText().toString());
                mealDao.insertMeal(model);

                prepareListData();
                listAdapter = new ExpandableListAdapter(getApplicationContext(), listDataHeader, listDataChild);
                // setting list adapter
                expListView.setAdapter(listAdapter);
                expListView.setBackgroundColor(Color.BLUE);
                //expListView.invalidate();
                Toast.makeText(MealsActivity.this, "You have eaten " + calories.toString() + " calories", Toast.LENGTH_LONG).show();
            }
        });

        btnSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(MealsActivity.this,
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
        getMenuInflater().inflate(R.menu.menu_meals, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent intent = null;

        if (id == R.id.action_bmi) {
            intent = new Intent(MealsActivity.this, BmiActivity.class);
        } else if (id == R.id.action_meals){
            intent = new Intent(MealsActivity.this, MealsActivity.class);
        }  else if (id == R.id.action_help){
            intent = new Intent(MealsActivity.this, HelpActivity.class);
        } else if (id == R.id.action_about){
            intent = new Intent(MealsActivity.this, AboutActivity.class);
        } else if (id == R.id.action_overview){
            intent = new Intent(MealsActivity.this, MainActivity.class);
        } else if (id == R.id.action_excercises){
            intent = new Intent(MealsActivity.this, ExcerciseActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void prepareListData() {
        HashMap<String,List<MealModel>> meals = mealDao.getAllMeals();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        for (Map.Entry<String, List<MealModel>> entry : meals.entrySet()) {
            String key = entry.getKey();
            List<String> tmpList = new ArrayList<String>();
            List<MealModel> value = entry.getValue();
            for (MealModel model : value){
                tmpList.add("" + model.getName() + " Portion(s): " + model.getPortions() + " " + model.getPortions() + "cal(s)");
            }
            listDataHeader.add(key);
            listDataChild.put(key, tmpList);
        }
    }

}
