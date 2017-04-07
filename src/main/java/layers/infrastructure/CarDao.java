package layers.infrastructure;

import java.util.List;

import layers.domain.Car;
import layers.domain.CarRepository;

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
