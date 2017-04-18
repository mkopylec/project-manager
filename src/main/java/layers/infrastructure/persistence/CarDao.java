package layers.infrastructure.persistence;

import java.util.List;

import layers.domain.car.Car;
import layers.domain.car.CarRepository;

/**
 * Repository implementation
 */
class CarDao implements CarRepository {

    @Override
    public Car findByVin(String vin) {
        return null;
    }

    @Override
    public List<Car> findAll() {
        return null;
    }

    @Override
    public void save(Car car) {

    }
}
