package layers.domain.order;

import java.math.BigDecimal;

/**
 * Entity
 */
public class Order {

    private String identifier;
    private boolean fulfilled;
    private BigDecimal price;

    Order(String identifier, BigDecimal price) {
        this.identifier = identifier;
        this.price = price;
        this.fulfilled = false;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void fulfill() {
        fulfilled = true;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }

    public void calculateFinalPrice(RebateCalculator rebateCalculator) {
        price = price.subtract(rebateCalculator.calculateRebate(price));
    }

    public BigDecimal getPrice() {
        return price;
    }
}
