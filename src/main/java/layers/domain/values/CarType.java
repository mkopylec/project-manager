package layers.domain.values;

/**
 * Value object
 */
public class CarType {

    private String brand;
    private String model;

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
