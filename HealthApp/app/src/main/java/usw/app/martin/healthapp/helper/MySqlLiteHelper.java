package usw.app.martin.healthapp.helper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import usw.app.martin.healthapp.model.ExcerciseModel;
import usw.app.martin.healthapp.model.HistoryWeightModel;
import usw.app.martin.healthapp.model.MealModel;

/**
 * Created by Martin on 25. 04. 2015.
 */

/**
 * Main class for DB handling
 */
public class MySqlLiteHelper extends SQLiteOpenHelper {

    //DB information
    private static final String DB_NAME = "HDBAAA.db";
    private static final int DB_VER = 1;

    //table definition
    private static final String TABLE_EXCERCISES = "excercices";
    private static final String TABLE_MEALS = "meals";
    private static final String TABLE_WEIGHT = "weightTab";
    private static final String TABLE_WEIGHT_ACTUAL = "weighttabactual";


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
        final String CREATE_WEIGHT_TABLE_ACTUAL = "CREATE TABLE IF NOT EXISTS " + TABLE_WEIGHT_ACTUAL + " ( id integer AUTO INCREMENT PRIMARY KEY, weight integer, entered text)";
        final String CREATE_WEIGHT_TABLE = "CREATE TABLE IF NOT EXISTS weightTab ( id integer AUTO INCREMENT PRIMARY KEY, weight integer, entered text)";
        final String CREATE_MEALS_TABLE = "CREATE TABLE IF NOT EXISTS meals ( id integer AUTO INCREMENT PRIMARY KEY, name TEXT, portions integer, calories integer, eaten text)";
        final String CREATE_EXCERCICES_TABLE = "CREATE TABLE IF NOT EXISTS excercices ( id integer AUTO INCREMENT PRIMARY KEY, name TEXT, duration integer, executed text, calories integer)";

        //create schema
        db.execSQL(CREATE_WEIGHT_TABLE);
        db.execSQL(CREATE_MEALS_TABLE);
        db.execSQL(CREATE_EXCERCICES_TABLE);
        db.execSQL(CREATE_WEIGHT_TABLE_ACTUAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    //method for getting all excercises
    public HashMap<String, List<ExcerciseModel>> getAllExcercises() {

        HashMap<String, List<ExcerciseModel>> excercices = new HashMap<String, List<ExcerciseModel>>();
        ArrayList<ExcerciseModel> tmpExcercice = new ArrayList<ExcerciseModel>();

        String allDateQuery = "SELECT DISTINCT executed FROM " + TABLE_EXCERCISES;

        String query = "SELECT name, duration, calories FROM " + TABLE_EXCERCISES + " WHERE executed=?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(allDateQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                Cursor c = db.rawQuery(query, new String[] { cursor.getString(0) });
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    ExcerciseModel model = new ExcerciseModel();

                    model.setName(c.getString(0));
                    model.setDuration(new Long(c.getString(1)));
                    model.setCalories(new Long(c.getString(2)));
                    model.setExecuted((cursor.getString(0)));
                    tmpExcercice.add(model);
                    c.moveToNext();
                }
                excercices.put(cursor.getString(0), tmpExcercice);
                tmpExcercice = new ArrayList<ExcerciseModel>();
                cursor.moveToNext();
            }
        }
        cursor.close();
        return excercices;
    }

    /**
     * Method for inserting excercise
     * @param model
     */
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

    /**
     * Method for inserting weight
     * @param model
     */
    public void insertWeight(HistoryWeightModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_WEIGHT, model.getWeight());
        values.put(KEY_ENTERED, model.getEntered().toString());

        db.insert(TABLE_WEIGHT, null, values);
        db.close();
    }

    /**
     * Method return last targer weight
     * @return
     */
    public HistoryWeightModel getLastWeight() {

        final String query = "SELECT weight, entered FROM " + TABLE_WEIGHT + " ORDER BY id DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();

        HistoryWeightModel model = new HistoryWeightModel();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                model.setWeight(new Long(cursor.getString(0)));
                model.setEntered((cursor.getString(1)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return model;
    }

    /**
     * Method returns all meals
     * @return
     */
    public HashMap<String, List<MealModel>> getAllMeals() {

        HashMap<String, List<MealModel>> meals = new HashMap<String, List<MealModel>>();
        ArrayList<MealModel> tmpMeals = new ArrayList<MealModel>();

        String allDateQuery = "SELECT DISTINCT eaten FROM " + TABLE_MEALS;

        String query = "SELECT name, portions, calories FROM " + TABLE_MEALS + " WHERE eaten=?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(allDateQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                Cursor c = db.rawQuery(query, new String[] { cursor.getString(0) });
                c.moveToFirst();
                if (c.getCount() > 0) {
                    while (!c.isAfterLast()) {
                        MealModel model = new MealModel();

                        model.setName(c.getString(0));
                        model.setPortions(new Long(c.getString(1)));
                        model.setCalories(new Long(c.getString(2)));
                        model.setEaten((cursor.getString(0)));
                        tmpMeals.add(model);
                        c.moveToNext();
                    }
                }
                meals.put(cursor.getString(0), tmpMeals);
                tmpMeals = new ArrayList<MealModel>();
                cursor.moveToNext();
            }
        }
        cursor.close();

        return meals;
    }

    /**
     * Method for inserting meal
     * @param model
     */
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

    /**
     * Method for getting calories over last week
     * @return
     * @throws ParseException
     */
    public Map<Date, Long> getBurnCaloriesForLastWeek() throws ParseException {

        Map<Date, Long> calories = new TreeMap<Date, Long>();

        List<Date> dates = new ArrayList<Date>();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //get current date time with Date()
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 6);

        String str_date = dateFormat.format(cal.getTime());
        String end_date = dateFormat.format(date);

        DateFormat formatter;

        formatter = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat insertFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = (Date) formatter.parse(str_date);
            endDate= (Date) formatter.parse(end_date);
        } catch (ParseException e){
            e.printStackTrace();
        }
        long interval = 24 * 1000 * 60 * 60;
        long endTime = endDate.getTime();
        long curTime = startDate.getTime();
        while (curTime <= endTime) {
            dates.add(new Date(curTime));
            curTime += interval;
        }

        final String query = "SELECT calories FROM " + TABLE_EXCERCISES + " WHERE executed=?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        for (int i = 0; i < dates.size(); i++) {

            Date lDate = (Date) dates.get(i);
            String ds = formatter.format(lDate);

            cursor = db.rawQuery(query, new String[]{ds});
            Long caloriesVal = new Long(0);
            cursor.moveToNext();
            if (cursor.getCount() > 0) {
                while (!cursor.isAfterLast()) {
                    caloriesVal += new Long(cursor.getString(0));
                    cursor.moveToNext();
                }
            } else {
                caloriesVal = new Long(0);
            }
            calories.put(insertFormat.parse(insertFormat.format(lDate)), caloriesVal);

            caloriesVal = new Long(0);
        }

        cursor.close();
        return calories;
    }

    /**
     * Method returns last actual weight
     * @return
     */
    public HistoryWeightModel getLastActualWeight() {
        final String query = "SELECT weight, entered FROM " + TABLE_WEIGHT_ACTUAL + " ORDER BY id DESC LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();

        HistoryWeightModel model = new HistoryWeightModel();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            while (!cursor.isAfterLast()) {
                model.setWeight(new Long(cursor.getString(0)));
                model.setEntered((cursor.getString(1)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return model;
    }

    /**
     * Methods inserts last actual weight
     * @param model
     */
    public void insertLastWeight(HistoryWeightModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_WEIGHT, model.getWeight());
        values.put(KEY_ENTERED, model.getEntered().toString());

        db.insert(TABLE_WEIGHT_ACTUAL, null, values);
        db.close();
    }
}