package usw.app.martin.healthapp.model;

import java.util.Date;

/**
 * Created by Martin on 25. 04. 2015.
 */
public class ExcerciseModel {

    private String name;
    private long duration;
    private long calories;
    private Date executed;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCalories() {
        return calories;
    }

    public void setCalories(long calories) {
        this.calories = calories;
    }

    public Date getExecuted() {
        return executed;
    }

    public void setExecuted(Date executed) {
        this.executed = executed;
    }


}
