package layers.infrastructure.services;

import layers.domain.car.CarSold;
import org.springframework.context.event.EventListener;

/**
 * Domain event listener
 */
class SoldCarsReporter {

    @EventListener
    public void reportSoldCar(CarSold carSold) {

    }
}
