package layers.api;

import layers.application.CarService;
import layers.application.dto.BuyResult;
import layers.application.dto.NewCar;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API
 */
@RestController
public class CarController {

    private CarService carService;

    @PostMapping("/car")
    public void addNewCar(NewCar newCar) {
        carService.addNewCar(newCar);
    }

    @PostMapping("/car/buy")
    public BuyResult buyCar(String vin) {
        return carService.buyCar(vin);
    }
}
