package usw.app.martin.healthapp.model;

import java.util.Date;

/**
 * Created by Martin on 25. 04. 2015.
 */
public class MealModel {

    private String name;
    private long portions;
    private double calories;
    private Date eaten;

    public Date getEaten() {
        return eaten;
    }

    public void setEaten(Date eaten) {
        this.eaten = eaten;
    }


    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
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
