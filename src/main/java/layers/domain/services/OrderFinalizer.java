package layers.domain.services;

import layers.domain.car.Car;
import layers.domain.order.Order;

import org.springframework.context.ApplicationEventPublisher;

/**
 * Domain service
 */
public class OrderFinalizer {

    private ApplicationEventPublisher publisher;

    public OrderFinalizer(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void finalizeOrder(Order order, Car car) {
        car.sell(publisher);
        order.fulfill();
    }
}
