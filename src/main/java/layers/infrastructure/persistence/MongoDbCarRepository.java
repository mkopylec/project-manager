package layers.infrastructure.persistence;

import layers.domain.car.Car;
import layers.domain.car.CarRepository;

import java.util.List;

/**
 * Repository implementation
 */
class MongoDbCarRepository implements CarRepository {

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
