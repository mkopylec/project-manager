package layers.domain;

import org.springframework.context.ApplicationEventPublisher;

/**
 * Domain service
 */
public class OrderFinalizer {

    private final ApplicationEventPublisher publisher;

    public OrderFinalizer(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void finalizeOrder(Order order, Car car) {
        car.sell(publisher);
        order.fulfill();
    }
}
