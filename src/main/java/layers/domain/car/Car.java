package layers.domain.car;

import layers.domain.values.CarType;
import org.springframework.context.ApplicationEventPublisher;

import java.time.ZonedDateTime;

import static java.time.ZoneOffset.UTC;
import static java.time.ZonedDateTime.of;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Entity
 */
public class Car {

    private String vin;
    private ZonedDateTime productionDate;
    private CarType type;
    private boolean sold;

    Car(String vin, ZonedDateTime productionDate, CarType type, boolean sold) {
        validateVin(vin);
        validateCarType(type);
        validateProductionDate(productionDate);
        this.vin = vin;
        this.productionDate = productionDate;
        this.type = type;
        this.sold = sold;
    }

    public boolean hasVin(String vin) {
        return this.vin.equals(vin);
    }

    public boolean isAntique() {
        return productionDate.isBefore(of(1980, 1, 1, 1, 1, 1, 1, UTC));
    }

    public CarType getType() {
        return type;
    }

    public void changeType(CarType type) {
        validateCarType(type);
        this.type = type;
    }

    public void sell(ApplicationEventPublisher publisher) {
        sold = true;
        CarSold event = new CarSold(vin);
        publisher.publishEvent(event);
    }

    private void validateVin(String vin) {
        if (isBlank(vin)) {
            throw new IllegalArgumentException("Empty VIN");
        }
    }

    private void validateCarType(CarType type) {
        if (type == null) {
            throw new IllegalArgumentException("Empty car type");
        }
    }

    private void validateProductionDate(ZonedDateTime productionDate) {
        if (productionDate == null) {
            throw new IllegalArgumentException("Empty car production date");
        }
    }
}
