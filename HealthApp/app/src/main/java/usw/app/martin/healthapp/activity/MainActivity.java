package usw.app.martin.healthapp.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import usw.app.martin.healthapp.R;
import usw.app.martin.healthapp.customComponents.MyValueFormatter;
import usw.app.martin.healthapp.dao.HistoryWeightDao;
import usw.app.martin.healthapp.model.HistoryWeightModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private SeekBar mSeekBarX, mSeekBarY, weightSeekBar;
    private TextView tvX, tvY;
    private Typeface mTf;
    private TextView weightSeekValueTextView, previousWeightTextWeight;
    private int weightSeekValue;
    private HistoryWeightDao historyWeightDao;
    private Button btnSaveWeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historyWeightDao = new HistoryWeightDao(this);

        previousWeightTextWeight = (TextView)findViewById(R.id.previousWeightTextWeight);

        //previousWeightTextWeight.setText(historyWeightDao.getLastWeight().getEntered()+ " kg");

        weightSeekValueTextView = (TextView)findViewById(R.id.weightMaintextView);

        btnSaveWeight = (Button)findViewById(R.id.btnSaveMeal);

        weightSeekBar = (SeekBar)findViewById(R.id.seekBarWeightMain);
        weightSeekBar.setOnSeekBarChangeListener(weightBar);

        mChart = (BarChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        mChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        //mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        //xAxis.setTypeface(mTf);
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

        setData(12, 50);

        btnSaveWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();

                HistoryWeightModel model = new HistoryWeightModel();
                model.setWeight(new Long(weightSeekValue));
                model.setEntered(dateFormat.format(date));

                historyWeightDao.insertWeight(model);
                HistoryWeightModel lastWeight = historyWeightDao.getLastWeight();
                previousWeightTextWeight.setText( lastWeight.getWeight() + " kg Date: " + lastWeight.getEntered());
                Toast toast = Toast.makeText(MainActivity.this, "Inserted target weight", Toast.LENGTH_LONG);
                toast.show();

            }
        });

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
    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add( (new Integer(i % 12)).toString());
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
            float mult = (range + 1);
            float val = (float) (Math.random() * mult);
            yVals1.add(new BarEntry(val, i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "DataSet");
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(10f);
        data.setValueTypeface(mTf);

        mChart.setData(data);
    }

    SeekBar.OnSeekBarChangeListener  weightBar = new SeekBar.OnSeekBarChangeListener() {
        int progress = 0;
        @Override
        public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
            progresValue += 30;
            weightSeekValue = progresValue;
            weightSeekValueTextView.setText(Double.toString(weightSeekValue));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

    };
}
