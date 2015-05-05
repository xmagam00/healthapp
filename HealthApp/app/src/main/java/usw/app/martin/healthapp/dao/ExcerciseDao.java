package usw.app.martin.healthapp.dao;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import usw.app.martin.healthapp.helper.MySqlLiteHelper;
import usw.app.martin.healthapp.model.ExcerciseModel;
import usw.app.martin.healthapp.model.MealModel;

/**
 * Created by Martin on 25. 04. 2015.
 */
public class ExcerciseDao {

    private MySqlLiteHelper helper;

    public ExcerciseDao(Context context){
        helper = new MySqlLiteHelper(context);
    }

    public HashMap<String, List<ExcerciseModel>> getAllExcercises(){
        return helper.getAllExcercises();
    }

    public void insertExcercise(ExcerciseModel model){
        helper.insertExcercise(model);
    }

    public HashMap<String,Long> getBurnCaloriesForLastWeek() throws Exception {
        return helper.getBurnCaloriesForLastWeek();
    }
}
