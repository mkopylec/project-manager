package layers.application.dto;

/**
 * Data transfer object
 */
public class BuyResult {

    private String carVin;
    private boolean bought;

    public String getCarVin() {
        return carVin;
    }

    public void setCarVin(String carVin) {
        this.carVin = carVin;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }
}
