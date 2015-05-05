package usw.app.martin.healthapp.dao;

import android.content.Context;

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

    public HistoryWeightModel getLastWeight(){
        return helper.getLastWeight();
    }

    public void insertWeight(HistoryWeightModel model) {
        helper.insertWeight(model);
    }

    public HistoryWeightModel getLastActualWeight(){
        return helper.getLastActualWeight();
    }

    public void insertActualWeight(HistoryWeightModel model){
        helper.insertLastWeight(model);
    }
}
