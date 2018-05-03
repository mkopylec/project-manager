package layers.api;

import layers.application.CarService;
import layers.application.dto.BuyResult;
import layers.application.dto.NewCar;

/**
 * API
 */
public class CarController {

    private CarService carService;

    public void addNewCar(NewCar newCar) {
        carService.addNewCar(newCar);
    }

    public BuyResult buyCar(String licencePlates) {
        return carService.buyCar(licencePlates);
    }
}
