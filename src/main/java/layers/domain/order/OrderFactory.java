package layers.domain.order;

import static java.math.BigDecimal.ZERO;

public class OrderFactory {

    public Order createOrder(String identifier) {
        return new Order(identifier, ZERO);
    }
}
