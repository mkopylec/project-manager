package layers.domain.order;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

/**
 * Policy variant
 */
public class StudentRebateCalculator implements RebateCalculator {

    @Override
    public BigDecimal calculateRebate(BigDecimal stockPrise) {
        return stockPrise.multiply(valueOf(0.1));
    }
}
