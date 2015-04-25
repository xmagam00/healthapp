package usw.app.martin.healthapp.dao;

import android.content.Context;

import java.util.List;

import usw.app.martin.healthapp.helper.MySqlLiteHelper;
import usw.app.martin.healthapp.model.MealModel;

/**
 * Created by Martin on 25. 04. 2015.
 */
public class ExcerciseDao {

    private MySqlLiteHelper helper;

    public ExcerciseDao(Context context){
        helper = new MySqlLiteHelper(context);
    }

    public List<MealModel> getAllMeals(){
        return helper.getAllExcercises();
    }

    public void insertMeal(MealModel model){
        helper.insertExcercise(model);
    }
}
