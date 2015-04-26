package usw.app.martin.healthapp.model;



/**
 * Created by Martin on 25. 04. 2015.
 */
public class ExcerciseModel {

    private String name;
    private Long duration;
    private Long calories;
    private String executed;

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCalories() {
        return calories;
    }

    public void setCalories(Long calories) {
        this.calories = calories;
    }

    public String getExecuted() {
        return executed;
    }

    public void setExecuted(String executed) {
        this.executed = executed;
    }


}
