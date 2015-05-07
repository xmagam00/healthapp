package usw.app.martin.healthapp.dao;

import android.content.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import usw.app.martin.healthapp.helper.MySqlLiteHelper;
import usw.app.martin.healthapp.model.HistoryWeightModel;

/**
 * Created by Martin on 25. 04. 2015.
 */
public class HistoryWeightDao {

    private MySqlLiteHelper helper;

    public HistoryWeightDao(Context context){
        helper = new MySqlLiteHelper(context);
    }

    //method for get last target weight
    public HistoryWeightModel getLastWeight(){
        return helper.getLastWeight();
    }

    //insert target weight
    public void insertWeight(HistoryWeightModel model) {
        helper.insertWeight(model);
    }

    public HistoryWeightModel getLastActualWeight(){
        return helper.getLastActualWeight();
    }

    public void insertActualWeight(HistoryWeightModel model){
        helper.insertLastWeight(model);
    }

    //method for geting all burnt calories for last week
    public Map<Date,Long> getBurnCaloriesForLastWeek() throws Exception {
        return helper.getBurnCaloriesForLastWeek();
    }
}
