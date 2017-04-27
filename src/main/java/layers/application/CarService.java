package layers.application;

import layers.application.dto.BuyResult;
import layers.application.dto.NewCar;
import layers.domain.car.CarRepository;
import layers.domain.services.OrderFinalizer;

/**
 * Application service
 */
public class CarService {

    private CarRepository carRepository;
    private OrderFinalizer orderFinalizer;

    public CarService(CarRepository carRepository, OrderFinalizer orderFinalizer) {
        this.carRepository = carRepository;
        this.orderFinalizer = orderFinalizer;
    }

    public void addNewCar(NewCar newCar) {

    }

    public BuyResult buyCar(String licencePlates) {
        return null;
    }
}
