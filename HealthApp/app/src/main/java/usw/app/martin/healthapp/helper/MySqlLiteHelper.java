package usw.app.martin.healthapp.helper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import usw.app.martin.healthapp.model.HistoryWeightModel;
import usw.app.martin.healthapp.model.MealModel;

/**
 * Created by Martin on 25. 04. 2015.
 */
public class MySqlLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "BooksDB.db";
    private static final int DB_VER = 1;

    private static final String TABLE_BOOKS = "books";

    //columns def
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_AUTHOR = "author";


    public MySqlLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_BOOK_TITTLE = "CREATE TABLE BOOKS ( id integer AUTO INCREMENT PRIMARY KEY, title TEXT, author TEXT)";
        db.execSQL(CREATE_BOOK_TITTLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertBook(String title, String author) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_AUTHOR, author);
        db.insert(TABLE_BOOKS, null, values);
        db.close();

    }
/*
    public HashMap<String, DBObject> getAllBooks() {
        HashMap<String, DBObject> list = new HashMap<String, DBObject>();
        String query = "SELECT * FROM " + TABLE_BOOKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Log.v("MyBooks", cursor.getString(0) + "-" + cursor.getString(1) + "-" + cursor.getString(2));
                DBObject object = new DBObject(cursor.getString(1), cursor.getString(2));
                list.put(cursor.getString(0), object);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }*/

    public void deleteAuthor(String[] author) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOKS, KEY_AUTHOR + "=?", author);
        db.close();
    }

    public void updateAuthor(String title, String author, String where) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_AUTHOR, author);
        ;

        db.update(TABLE_BOOKS, values, KEY_AUTHOR + "=?", new String[]{where});
        db.close();
    }

    public List<MealModel> getAllExcercises() {
        return null;
    }

    public void insertExcercise(MealModel model) {
    }

    public void insertWeight(HistoryWeightModel model) {
    }

    public HistoryWeightModel getLastWeight() {
        return null;
    }

    public List<MealModel> getAllMeals() {
        return null;
    }

    public void insertMeal(MealModel model) {

    }
}