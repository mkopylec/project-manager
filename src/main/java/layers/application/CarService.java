package layers.application;

import layers.application.dto.BuyResult;
import layers.application.dto.NewCar;
import layers.domain.car.Car;
import layers.domain.car.CarFactory;
import layers.domain.car.CarRepository;
import layers.domain.order.Order;
import layers.domain.order.OrderFactory;
import layers.domain.services.OrderFinalizer;

/**
 * Application service
 */
public class CarService {

    private CarFactory carFactory;
    private CarRepository carRepository;
    private OrderFactory orderFactory;
    private OrderFinalizer orderFinalizer;

    public void addNewCar(NewCar newCar) {
        Car car = carFactory.createCar(newCar.getLicencePlates(), newCar.getBrand(), newCar.getProductionDate());
        carRepository.save(car);
    }

    public BuyResult buyCar(String vin) {
        Car car = carRepository.findByVin(vin);
        Order order = orderFactory.createOrder("order-id");
        orderFinalizer.finalizeOrder(order, car);
        return new BuyResult();
    }
}
