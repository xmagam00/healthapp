package usw.app.martin.healthapp.model;

import java.util.Date;

/**
 * Created by Martin on 25. 04. 2015.
 */
public class HistoryWeightModel {

    private long weight;
    private Date entered;

    public Date getEntered() {
        return entered;
    }

    public void setEntered(Date entered) {
        this.entered = entered;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }
}
