package layers.domain.values;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

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

    public boolean hasModel() {
        return isNotBlank(model);
    }
}
