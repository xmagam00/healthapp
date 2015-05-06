package usw.app.martin.healthapp.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import usw.app.martin.healthapp.R;
import usw.app.martin.healthapp.customComponents.MyValueFormatter;
import usw.app.martin.healthapp.dao.HistoryWeightDao;
import usw.app.martin.healthapp.model.HistoryWeightModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.ValueFormatter;


public class MainActivity extends ActionBarActivity  implements OnChartValueSelectedListener {

    protected BarChart mChart;
    private SeekBar weightSeekBar, actualWeightSeekbar;
    private TextView weightSeekValueTextView, previousWeightTextWeight,textViewActual,textViewActualWeight, textViewDays;
    private long weightSeekValue, weightSeekValueActual;
    private HistoryWeightDao historyWeightDao;
    private Button btnSaveWeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historyWeightDao = new HistoryWeightDao(this);
        textViewActual = (TextView)findViewById(R.id.textViewActual);

        textViewActualWeight = (TextView)findViewById(R.id.textViewActualW);
        textViewDays = (TextView)findViewById(R.id.textViewDays);

        previousWeightTextWeight = (TextView) findViewById(R.id.previousWeightTextWeight);

        weightSeekValueTextView = (TextView)findViewById(R.id.weightMaintextView);

        btnSaveWeight = (Button)findViewById(R.id.btnSaveMeal);

        weightSeekBar = (SeekBar)findViewById(R.id.seekBarWeightMain);
        weightSeekBar.setOnSeekBarChangeListener(weightBar);

        actualWeightSeekbar = (SeekBar)findViewById(R.id.seekBarActuaWeight);
        actualWeightSeekbar.setOnSeekBarChangeListener(actualWeight);

        mChart = (BarChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.setDescription("");

        mChart.setMaxVisibleValueCount(60);

        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);

        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        ValueFormatter custom = new MyValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
       // leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(8);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        //rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(8);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);

        Legend l = mChart.getLegend();
        l.setPosition(LegendPosition.BELOW_CHART_LEFT);
        l.setForm(LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        setData();

        btnSaveWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((new Long(textViewActual.getText().toString())) <= (new Long(weightSeekValueTextView.getText().toString()))){
                    Toast toast = Toast.makeText(MainActivity.this, "Target weight should be less than actual", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();

                HistoryWeightModel model = new HistoryWeightModel();
                HistoryWeightModel modelActual = new HistoryWeightModel();

                model.setWeight(new Long(weightSeekValueTextView.getText().toString()));

                model.setEntered(dateFormat.format(date));
                modelActual.setEntered(dateFormat.format(date));
                modelActual.setWeight(new Long(textViewActual.getText().toString()));

                historyWeightDao.insertWeight(model);
                historyWeightDao.insertActualWeight(modelActual);

                HistoryWeightModel lastWeight = historyWeightDao.getLastWeight();
                previousWeightTextWeight.setText( lastWeight.getWeight() + " kg");

                HistoryWeightModel lastActualWeight = historyWeightDao.getLastActualWeight();
                textViewActualWeight.setText( lastActualWeight.getWeight() + " kg");

                //recalculate days
                daysToTargetWeight(lastActualWeight, lastWeight);

                Toast toast = Toast.makeText(MainActivity.this, "Inserted target weight and Actual weight", Toast.LENGTH_LONG);
                toast.show();

            }
        });

        if (historyWeightDao.getLastActualWeight().getEntered() != null && historyWeightDao.getLastActualWeight().getEntered().length() > 0) {
            textViewActualWeight.setText(historyWeightDao.getLastActualWeight().getWeight() + " kg");
        } else {
            textViewActualWeight.setText("");
        }

        if (historyWeightDao.getLastWeight().getEntered() != null && historyWeightDao.getLastWeight().getEntered().length() > 0) {
            previousWeightTextWeight.setText(historyWeightDao.getLastWeight().getWeight() + " kg");
        } else {
            previousWeightTextWeight.setText("");
        }

        daysToTargetWeight(historyWeightDao.getLastActualWeight(),historyWeightDao.getLastWeight() );


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            intent = new Intent(MainActivity.this, BmiActivity.class);
        } else if (id == R.id.action_meals){
            intent = new Intent(MainActivity.this, MealsActivity.class);
        }  else if (id == R.id.action_help){
            intent = new Intent(MainActivity.this, HelpActivity.class);
        } else if (id == R.id.action_about){
            intent = new Intent(MainActivity.this, AboutActivity.class);
        } else if (id == R.id.action_overview){
            intent = new Intent(MainActivity.this, MainActivity.class);
        } else if (id == R.id.action_excercises){
            intent = new Intent(MainActivity.this, ExcerciseActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {

    }

    @Override
    public void onNothingSelected() {

    }
    private void setData() {
        Map<Date,Long> caloriesPerWeek = null;
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        try {
             caloriesPerWeek =  historyWeightDao.getBurnCaloriesForLastWeek();
            int index = 0;
            for (Map.Entry<Date, Long> entry : caloriesPerWeek.entrySet()) {
                String key = format.format(entry.getKey()).toString().substring(0,2);


                Long calories =  entry.getValue();




               xVals.add(key);
               yVals1.add(new BarEntry(calories,index));
                index++;
            }


            BarDataSet set1 = new BarDataSet(yVals1, "Calories burn over last week");
            set1.setBarSpacePercent(35f);

            ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(xVals, dataSets);
          //  data.setValueFormatter(new MyValueFormatter());
            data.setValueTextSize(10f);

            mChart.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    SeekBar.OnSeekBarChangeListener  weightBar = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
            progresValue += 30;
            weightSeekValue = progresValue;
            weightSeekValueTextView.setText(Long.toString(weightSeekValue));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

    };

    SeekBar.OnSeekBarChangeListener  actualWeight = new SeekBar.OnSeekBarChangeListener() {
        int progress = 0;
        @Override
        public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
            progresValue += 30;
            weightSeekValueActual = progresValue;
            textViewActual.setText(Long.toString(weightSeekValueActual));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

    };

    private void daysToTargetWeight(HistoryWeightModel actualWeight, HistoryWeightModel targetWeight){
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

        final double averageDayCalories = 18369;
        double weightDifferenceInGrams = (actualWeight.getWeight() - targetWeight.getWeight())*1000;

        double caloriesToBurn = Math.round(weightDifferenceInGrams*9);

        try {
            Date date1 = myFormat.parse(actualWeight.getEntered().toString());
            Date date2 = new Date();
            Date date3 = myFormat.parse(myFormat.format(date2));
            long diff = date3.getTime() - date1.getTime();
            long daysDifference = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            caloriesToBurn -= daysDifference*averageDayCalories;

            long days = Math.round(caloriesToBurn/averageDayCalories);
            textViewDays.setText((new Long(days)).toString());

        } catch (ParseException e){
            e.printStackTrace();
        }





    }


}
