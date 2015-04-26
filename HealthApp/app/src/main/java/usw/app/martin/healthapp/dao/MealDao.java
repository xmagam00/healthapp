package usw.app.martin.healthapp.dao;

import android.content.Context;

import java.util.HashMap;
import java.util.List;

import usw.app.martin.healthapp.helper.MySqlLiteHelper;
import usw.app.martin.healthapp.model.HistoryWeightModel;
import usw.app.martin.healthapp.model.MealModel;

/**
 * Created by Martin on 25. 04. 2015.
 */
public class MealDao {

    private MySqlLiteHelper helper;

    public MealDao(Context context){
        helper = new MySqlLiteHelper(context);
    }

    public HashMap<String,List<MealModel>> getAllMeals(){
        return helper.getAllMeals();
    }

    public void insertMeal(MealModel model){
        helper.insertMeal(model);
    }



}
