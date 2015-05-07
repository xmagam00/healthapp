package usw.app.martin.healthapp.model;



/**
 * Created by Martin on 25. 04. 2015.
 */

/**
 * Class for weight
 */
public class HistoryWeightModel {

    private Long weight;
    private String entered;

    public String getEntered() {
        return entered;
    }

    public void setEntered(String entered) {
        this.entered = entered;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }
}
