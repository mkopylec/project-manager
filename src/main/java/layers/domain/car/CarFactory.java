package layers.domain.car;

import layers.domain.services.VinProvider;
import layers.domain.values.CarType;

import java.time.ZonedDateTime;

/**
 * Factory
 */
public class CarFactory {

    private VinProvider vinProvider;

    public CarFactory(VinProvider vinProvider) {
        this.vinProvider = vinProvider;
    }

    public Car createCar(String licencePlates, String brand, ZonedDateTime productionDate) {
        String vin = vinProvider.getVin(licencePlates);
        CarType type = new CarType(brand, null);
        return new Car(vin, productionDate, type, false);
    }
}
