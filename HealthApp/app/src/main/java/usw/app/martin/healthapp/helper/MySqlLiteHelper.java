package usw.app.martin.healthapp.helper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import usw.app.martin.healthapp.model.ExcerciseModel;
import usw.app.martin.healthapp.model.HistoryWeightModel;
import usw.app.martin.healthapp.model.MealModel;

/**
 * Created by Martin on 25. 04. 2015.
 */
public class MySqlLiteHelper extends SQLiteOpenHelper {

    //DB information
    private static final String DB_NAME = "HealthDB.db";
    private static final int DB_VER = 1;

    //table definition
    private static final String TABLE_EXCERCISES = "excercices";
    private static final String TABLE_MEALS = "meals";
    private static final String TABLE_WEIGHT = "weightTab";


    //columns def
    private static final String KEY_ID = "id";

    //table weight
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_ENTERED = "entered";

    //table meals
    private  static final String KEY_NAME = "name";
    private  static final String KEY_PORTIONS = "portions";
    private  static final String KEY_CALORIES = "calories";
    private  static final String KEY_EATEN = "eaten";

    private static final String KEY_DURATION = "duration";
    private  static final String KEY_EXECUTED = "executed";


    public MySqlLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_WEIGHT_TABLE = "CREATE TABLE IF NOT EXISTS weightTab ( id integer AUTO INCREMENT PRIMARY KEY, weight integer, entered text)";
        final String CREATE_MEALS_TABLE = "CREATE TABLE IF NOT EXISTS meals ( id integer AUTO INCREMENT PRIMARY KEY, name TEXT, portions integer, calories integer, eaten text)";
        final String CREATE_EXCERCICES_TABLE = "CREATE TABLE IF NOT EXISTS excercices ( id integer AUTO INCREMENT PRIMARY KEY, name TEXT, duration integer, executed text, calories integer)";

        db.execSQL(CREATE_WEIGHT_TABLE);
        db.execSQL(CREATE_MEALS_TABLE);
        db.execSQL(CREATE_EXCERCICES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public HashMap<String, List<ExcerciseModel>> getAllExcercises() {

        HashMap<String, List<ExcerciseModel>> excercices = new HashMap<String, List<ExcerciseModel>>();
        ArrayList<ExcerciseModel> tmpExcercice = new ArrayList<ExcerciseModel>();

        String allDateQuery = "SELECT DISTINCT executed FROM " + TABLE_EXCERCISES;

        String query = "SELECT name, duration, calories FROM " + TABLE_EXCERCISES + " WHERE executed=?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(allDateQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Cursor c = db.rawQuery(query, new String[] { cursor.getString(0) });
                do {
                    ExcerciseModel model = new ExcerciseModel();

                    model.setName(c.getString(0));
                    model.setDuration(Long.getLong(c.getString(1)));
                    model.setCalories(Long.getLong(c.getString(2)));
                    model.setExecuted((cursor.getString(0)));
                    tmpExcercice.add(model);

                } while(c.moveToNext());
                excercices.put(cursor.getString(0), tmpExcercice);
            } while (cursor.moveToNext());
        }
        db.close();
        return excercices;
    }

    public void insertExcercise(ExcerciseModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, model.getName());
        values.put(KEY_CALORIES, model.getCalories());
        values.put(KEY_DURATION, model.getDuration());
        values.put(KEY_EXECUTED, model.getExecuted().toString());

        db.insert(TABLE_EXCERCISES, null, values);
        db.close();
    }

    public void insertWeight(HistoryWeightModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WEIGHT, model.getWeight());
        values.put(KEY_ENTERED, model.getEntered().toString());

        db.insert(TABLE_WEIGHT, null, values);
        db.close();
    }

    public HistoryWeightModel getLastWeight() {

        final String query = "SELECT weight, entered FROM " + TABLE_WEIGHT + " ORDER BY id DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();

        HistoryWeightModel model = new HistoryWeightModel();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                model.setWeight(Long.getLong(cursor.getString(0)));
                model.setEntered((cursor.getString(1)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return model;
    }

    public HashMap<String, List<MealModel>> getAllMeals() {

        HashMap<String, List<MealModel>> meals = new HashMap<String, List<MealModel>>();
        ArrayList<MealModel> tmpMeals = new ArrayList<MealModel>();

        String allDateQuery = "SELECT DISTINCT eaten FROM " + TABLE_MEALS;

        String query = "SELECT name, portions, calories FROM " + TABLE_MEALS + " WHERE eaten=?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(allDateQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Cursor c = db.rawQuery(query, new String[] { cursor.getString(0) });
                do {
                    MealModel model = new MealModel();

                    model.setName(c.getString(0));
                    model.setPortions(Long.getLong(c.getString(1)));
                    model.setCalories(Long.getLong(c.getString(2)));
                    model.setEaten((cursor.getString(0)));
                    tmpMeals.add(model);

                } while(c.moveToNext());
                meals.put(cursor.getString(0), tmpMeals);
            } while (cursor.moveToNext());
        }
        db.close();
        return meals;
    }

    public void insertMeal(MealModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, model.getName());
        values.put(KEY_EATEN, model.getEaten().toString());
        values.put(KEY_PORTIONS, model.getPortions());
        values.put(KEY_CALORIES, model.getCalories());

        db.insert(TABLE_MEALS, null, values);
        db.close();

    }

    public HashMap<String, List<Long>> getBurnCaloriesForLastWeek() throws Exception{

        HashMap<String, List<Long>> calories = new HashMap<String, List<Long>>();

        List<Date> dates = new ArrayList<Date>();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //get current date time with Date()
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)-6);

        String str_date = dateFormat.format(cal.getTime());
        String end_date = dateFormat.format(date);

        DateFormat formatter ;

        formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date  startDate = (Date)formatter.parse(str_date);
        Date  endDate = (Date)formatter.parse(end_date);
        long interval = 24*1000 * 60 * 60; // 1 hour in millis
        long endTime =endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
        long curTime = startDate.getTime();
        while (curTime <= endTime) {
            dates.add(new Date(curTime));
            curTime += interval;
        }

        final String query = "SELECT calories FROM " + TABLE_EXCERCISES + " WHERE executed=?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        for(int i=0;i<dates.size();i++){

            Date lDate =(Date)dates.get(i);
            String ds = formatter.format(lDate);
            cursor = db.rawQuery(query, new String[]{ds});
            List<Long> caloriesList = new ArrayList<Long>();
            do {
                caloriesList.add(new Long(cursor.getString(0)));
            } while(cursor.moveToNext());
            calories.put(ds, caloriesList);
        }

        return calories;
    }
}