package layers.domain.car;

/**
 * Domain event
 */
public class CarSold {

    private String vin;

    public CarSold(String vin) {
        this.vin = vin;
    }

    public String getVin() {
        return vin;
    }
}
