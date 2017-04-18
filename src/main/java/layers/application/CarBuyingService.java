package layers.application;

import layers.application.dto.BuyResult;
import layers.domain.car.CarRepository;
import layers.domain.services.OrderFinalizer;

/**
 * Application service
 */
public class CarBuyingService {

    private CarRepository carRepository;
    private OrderFinalizer orderFinalizer;

    public CarBuyingService(CarRepository carRepository, OrderFinalizer orderFinalizer) {
        this.carRepository = carRepository;
        this.orderFinalizer = orderFinalizer;
    }

    public BuyResult buyCar(String licencePlates) {
        return null;
    }
}
