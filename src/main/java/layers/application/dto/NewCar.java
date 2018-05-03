package layers.application.dto;

import java.time.ZonedDateTime;

public class NewCar {

    private String licencePlates;
    private String brand;
    private ZonedDateTime productionDate;

    public NewCar(String licencePlates, String brand, ZonedDateTime productionDate) {
        this.licencePlates = licencePlates;
        this.brand = brand;
        this.productionDate = productionDate;
    }

    public String getLicencePlates() {
        return licencePlates;
    }

    public String getBrand() {
        return brand;
    }

    public ZonedDateTime getProductionDate() {
        return productionDate;
    }
}
