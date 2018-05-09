package layers.domain.order;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Entity
 */
public class Order {

    private String identifier;
    private boolean fulfilled;
    private BigDecimal price;

    Order(String identifier, BigDecimal price) {
        validateIdentifier(identifier);
        validateIdentifier(price);
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

    private void validateIdentifier(String identifier) {
        if (isBlank(identifier)) {
            throw new IllegalArgumentException("Empty order's identifier");
        }
    }

    private void validateIdentifier(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException("Empty order's price");
        }
        if (price.compareTo(ZERO) < 0) {
            throw new IllegalArgumentException("Invalid order's price");
        }
    }
}
