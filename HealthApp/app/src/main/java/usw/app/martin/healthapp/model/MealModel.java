package usw.app.martin.healthapp.model;


/**
 * Created by Martin on 25. 04. 2015.
 */
public class MealModel {

    private String name;
    private long portions;
    private long calories;
    private String eaten;

    public String getEaten() {
        return eaten;
    }

    public void setEaten(String eaten) {
        this.eaten = eaten;
    }


    public double getCalories() {
        return calories;
    }

    public void setCalories(long calories) {
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPortions() {
        return portions;
    }

    public void setPortions(long portions) {
        this.portions = portions;
    }


}
