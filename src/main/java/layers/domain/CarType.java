package layers.domain;

/**
 * Value object
 */
public class CarType {

    private final String brand;
    private final String model;

    public CarType(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }
}
