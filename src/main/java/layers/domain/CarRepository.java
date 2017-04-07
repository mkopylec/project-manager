package layers.domain;

import java.util.List;

/**
 * Repository
 */
public interface CarRepository {

    Car findByVin(String vin);

    List<Car> findAll();

    void save(Car car);
}
