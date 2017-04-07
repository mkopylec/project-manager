package layers.domain;

import java.time.ZonedDateTime;

/**
 * Factory
 */
public class CarFactory {

    private final VinProvider vinProvider;

    public CarFactory(VinProvider vinProvider) {
        this.vinProvider = vinProvider;
    }

    public Car createCar(String licencePlates, CarType type, ZonedDateTime productionDate) {
        String vin = vinProvider.getVin(licencePlates);
        return new Car(vin, productionDate, type, false);
    }
}
