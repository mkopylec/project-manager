package layers.domain;

/**
 * Domain event
 */
public class CarSold {

    private final String vin;

    public CarSold(String vin) {
        this.vin = vin;
    }

    public String getVin() {
        return vin;
    }
}
